package com.yu.utils.service.impl;

import com.yu.utils.service.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CommonUtilsImpl implements CommonUtils {

    @Override
    public String getRandom(Integer i) {
        Random random = new Random();
        StringBuilder sb=new StringBuilder();
        for (int j = 0; j < i; j++) {
            int x = random.nextInt(10);
            sb.append(x);
        }
        return sb.toString();
    }
}
