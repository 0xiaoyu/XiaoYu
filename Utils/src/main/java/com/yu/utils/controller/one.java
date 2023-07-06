package com.yu.utils.controller;

import com.yu.common.Result.R;
import com.yu.utils.service.CommonUtils;
import com.yu.utils.service.KaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utils")
public class one {

    @Autowired
    private CommonUtils utils;

    @Autowired
    private KaptchaService kaptchaService;


    @PostMapping("randomCode")
    public R<String> randomCode(Integer i){
        if (i == null ){
            i=4;
            R.error("参数i为空，采取默认4位");
        }
        return R.success(utils.getRandom(i));
    }

    @PostMapping("/render")
    public void render(){
        kaptchaService.render();
    }
}
