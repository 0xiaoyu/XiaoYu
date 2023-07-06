package com.yu.utils.controller;

import com.baomidou.kaptcha.Kaptcha;
import com.yu.common.Result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kaptcha")
@Slf4j
public class KaptchaController {
    @Autowired
    private Kaptcha kaptcha;

    @GetMapping("/render")
    public R<String> render() {
        String s = kaptcha.render();
        log.info("验证码为"+s);
        return R.success("发送成功");
    }

    @PostMapping("/valid")
    public R<String> validDefaultTime(@RequestParam String code) {
        //default timeout 900 seconds
        boolean validate = kaptcha.validate(code);
        if (validate)
            return R.success("成功");
        else
            return R.error("校验失败");
    }

    @PostMapping("/validTime")
    public void validWithTime(@RequestParam String code) {
        kaptcha.validate(code, 60);
    }
}
