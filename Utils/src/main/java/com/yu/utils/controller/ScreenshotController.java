package com.yu.utils.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/screenshot")
public class ScreenshotController {

    @Autowired
    private RestTemplate restTemplate;

    /*@PostMapping("/save")
    public ResultVo add(@RequestBody MultipartFile multipartFile, @RequestParam String screenshotId,
                        @RequestParam Integer width, @RequestParam Integer high) {
        if (multipartFile == null) {
            return ResultVo.error("图片不能为空");
        }
        return screenShotService.add(multipartFile, taskId, width, high);
    }*/

    /*@PostMapping()
    public void screenImgTask() throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        //获取系统临时缓存目录
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String tempFilePath = System.getProperty("java.io.tmpdir") + fileName + ".png";
        //生成一个临时文件
        File file = new File(tempFilePath);
        ImageIO.write(image, "png", file);
        System.out.println("文件名称 -> " + file.getName());
        //把临时文件转换成FileSystemResource
        FileSystemResource resource = new FileSystemResource(file);
        //创建请求头
        HttpHeaders headers = new HttpHeaders();
        //设置头格式
        MediaType type = MediaType.parseMediaType(MediaType.MULTIPART_FORM_DATA_VALUE);
        headers.setContentType(type);
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        //由于上传文件不能直接使用MultipartFile类型来进行上传，需要转换为FileSystemResource类型进行上传
        bodyMap.add("multipartFile", resource);
        bodyMap.add("screenshotId", "123");
        bodyMap.add("width", image.getWidth());
        bodyMap.add("high", image.getHeight());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/screenshot/save", HttpMethod.PUT, httpEntity, String.class);
        //删除临时文件
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(responseEntity.getBody());
    }*/
}
