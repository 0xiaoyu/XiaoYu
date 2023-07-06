package com.yu.utils.controller;

import com.yu.common.Result.R;
import com.yu.utils.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/translation")
public class TranslationControoler {

    @Autowired
    private TranslationService translationService;

    @PostMapping
    public R<String> translation(@RequestBody String s,
                                 @RequestParam(required = false) String from,
                                 @RequestParam(required = false) String to) {
        if (s == null) return R.error("输入不能为空");
        if (from == null)
            return R.success(translationService.getTranslation(s));
        else
            return R.success(translationService.getTranslation(s,from,to));
    }

}
