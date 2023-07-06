package com.yu.utils;

import com.yu.utils.service.KaptchaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KaptchaTest {

    @Autowired
    private KaptchaService kaptchaService;

    @Test
    public void render() {
        kaptchaService.render();
    }



}
