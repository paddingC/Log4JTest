package cn.microanswer.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/")
public class Index {

    private Log log = LogFactory.getLog(getClass());

    @RequestMapping("/")
    public ModelAndView index () {
        ModelAndView modelAndView = new ModelAndView();

        log.  info("我是 Info 信息日志哈哈哈。");
        log.  warn("我是 Warn 警告日志哈哈哈。");
        log.  error("我是 Error 错误日志哈哈哈。");

        modelAndView.setViewName("json");
        modelAndView.addObject("code", "1a0");
        modelAndView.addObject("msg", "success");
        modelAndView.addObject("data", "(function(){alert('哈哈aaaaaaaa')})()");

        return modelAndView;
    }

    @RequestMapping("/test")
    public void test () {
        throw new RuntimeException("出错了");
    }

}
