package com.yu;


import com.baidu.aip.util.Base64Util;
import com.yu.uitils.FileUtil;
import com.yu.uitils.HttpUtil;
import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * 身份证识别
 */
public class Idcard {

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

    public static String idcard() {

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            // 本地文件路径
            String filePath = "D:\\0\\0\\身份证\\IMG_20220908_100026_edit_127357997312283.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "id_card_side=" + "front" + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.7cf7f88ffb455aa7329dc1df2d81c3c9.2592000.1686716113.282335-33567306";

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "");
//        Request request = new Request.Builder()
//                .url("https://aip.baidubce.com/oauth/2.0/token?client_id=6TwxyIxa0jftj3VxiboAtsmo&client_secret=R01k5bSs3kvhKntQvBdYOaGymoAYReTi&grant_type=client_credentials")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Accept", "application/json")
//                .build();
//        Response response = HTTP_CLIENT.newCall(request).execute();
//        System.out.println(response.body().string());
        Idcard.idcard();
    }
}
