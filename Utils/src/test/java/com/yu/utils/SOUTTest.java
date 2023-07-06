package com.yu.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SOUTTest {

    @Test
    public void systemOut(){
        System.out.println("hello world");
        System.out.println("\033[30m"+"hello world");
        System.out.println("\033[30;1;4m"+"hello world"+"\033[0m");
        System.out.println("\033[30;4m"+"hello world");
        System.out.println("\033[31;4m"+"hello world");
        System.out.println("\033[46;30;4m"+"hello world");// 颜色、背景颜色、样式
        System.out.println("\033[91;34;4m"+"hello world");// 颜色、背景颜色、样式
        System.out.println("\033[91;34;7m"+"hello world");// 颜色、背景颜色、样式
        /**
         *    0  空样式
         *     1  粗体
         *     4  下划线
         *     7  反色
         *     颜色1：
         *     30  白色
         *     31  红色
         *     32  绿色
         *     33  黄色
         *     34  蓝色
         *     35  紫色
         *     36  浅蓝
         *     37  灰色
         *
         *     背景颜色：
         *     40-47 和颜色顺序相同
         *     颜色2：
         *     90-97  比颜色1更鲜艳一些
         */
    }
}
