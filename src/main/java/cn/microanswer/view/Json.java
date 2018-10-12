package cn.microanswer.view;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 渲染 json 数据的 View
 */
@Component
public class Json implements View {
    private Log log = LogFactory.getLog(getClass());

    /**
     * model 数据如果是通过错误捕获传递过来的， 则错误信息的key是：
     */
    private static final String __exception = "__exception";

    private static final String ContentType = "application/json;charset-utf-8";

    private static final String KEY_CODE = "code";
    private static final String KEY_MSG = "msg";
    private static final String KEY_DATA = "data";

    @Override
    public String getContentType() {
        return ContentType;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setCharacterEncoding("UTF-8");
        response.setContentType(getContentType());

        JSONObject res = new JSONObject();

        if (model.containsKey(__exception)) {
            Exception e = (Exception) model.get(__exception);
            // 系统错误
            res.put(KEY_CODE, -1);
            res.put(KEY_MSG, String.format("[%s] %s", e.getClass().getName(), e.getMessage()));
            res.put(KEY_DATA, "");

        } else {

            // 业务数据

            int code = 0;
            String msg = "";
            Object data = null;

            if (model.containsKey(KEY_CODE)) {
                try {
                    code = Integer.valueOf(String.valueOf(model.get(KEY_CODE)));
                }catch (Exception e){
                    log.error(String.format("将状态码[%s]转换成int类型时出错", String.valueOf(model.get(KEY_CODE))));
                    e.printStackTrace();
                    code = 200;
                }
            } else {
                code = 200;
            }
            if (model.containsKey(KEY_MSG)) {
                msg = String.valueOf(model.get(KEY_MSG));
            } else {
                msg = "";
            }
            if (model.containsKey(KEY_DATA)) {
                data = model.get(KEY_DATA);
            }

            res.put(KEY_CODE,code);
            res.put(KEY_MSG,msg);
            res.put(KEY_DATA, data);
        }

        response.getWriter().print(res.toJSONString());
    }
}
