package com.yu.utils.service.impl;

import com.yu.utils.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;

@Service
public class TranslationServiceImpl implements TranslationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${baidu.translate.appid}")
    private String appid;

    @Value("${baidu.translate.securityKey}")
    private String sKey;

    @Override
    public String getTranslation(String s) {

        return this.getTranslation(s, "auto", "en");
    }

    @Override
    public String getTranslation(String s, String from, String to) {

        String salt = String.valueOf(System.currentTimeMillis());
        String sign = appid + s + salt + sKey;
        sign = DigestUtils.md5DigestAsHex(sign.getBytes());

        //post请求发出
        /*MultiValueMap<String, String> m = new LinkedMultiValueMap<>();
        m.add("salt", salt);
        m.add("sign", sign);
        m.add("q", s);
        m.add("appid", appid);
        m.add("from", from);
        m.add("to", to);
        String s1 = postTranslation(m);*/

        //get请求
        String s1 = getTranslation(s, from, to, salt, sign);

        return s1;
    }

    //get请求翻译文本
    private String getTranslation(String s, String from, String to, String salt, String sign) {
        String url = "https://api.fanyi.baidu.com/api/trans/vip/translate?" +
                "q={s}&from={from}&to={to}&appid={appid}&sign={sign}&salt={salt}";
        Map body = restTemplate.getForObject(url, Map.class, s, from, to, appid, sign, salt);
        if ("auto".equals(from)&&equalsFromAndTo(body)) {
            body = restTemplate.getForObject(url, Map.class, s, from, "zh", appid, sign, salt);
        }
        return getResult(body);
    }

    private String getResult(Map body) {
        ArrayList result = (ArrayList) body.get("trans_result");
        String x = result.toString();
        int i = x.lastIndexOf('=');
        String substring = x.substring(i + 1, x.length() - 2);
        return substring;
    }


    private String postTranslation(MultiValueMap m) {
        ResponseEntity<Map> entity = restTemplate.postForEntity("https://api.fanyi.baidu.com/api/trans/vip/translate", m, Map.class);
        Map map = entity.getBody();
        if ("[auto]".equals(m.get("from").toString())&&equalsFromAndTo(map)) {
            m.add("to","zh");
            entity = restTemplate.postForEntity("https://api.fanyi.baidu.com/api/trans/vip/translate", m, Map.class);
            map = entity.getBody();
        }
        map.get("trans_result");
        return getResult(map);
    }

    private Boolean equalsFromAndTo(Map map){
        Object f = map.get("from");
        Object t = map.get("to");
        return f.equals(t);
    }
}
