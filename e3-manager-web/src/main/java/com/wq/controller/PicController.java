package com.wq.controller;

import com.wq.common.utils.FastDFSClient;
import com.wq.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/*
    上传图片用的
*/

@Controller
public class PicController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAAGE_SERVER;

    /*
    这种浏览器兼容性最好，返回的格式是text/plain 下面那种返回的是application/json
    */
    @RequestMapping("/pic/upload")
    @ResponseBody
    public String upload(MultipartFile uploadFile){
        try {
            FastDFSClient client = new FastDFSClient("classpath:conf/client.conf");

            //获取上传文件的后缀名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

            //上传
            String path = client.uploadFile(uploadFile.getBytes(),extName);
            //拼接成符合规定的url
            String url = IMAAGE_SERVER + path;
            System.out.println(url);

            //返回json结果
            Map<String,Object> result = new HashMap();
            result.put("error",0);
            result.put("url",url);
            return JsonUtils.objectToJson(result);
        } catch (Exception e){
            e.printStackTrace();
            Map<String,Object> result = new HashMap();
            result.put("error", 1);
            result.put("message","图片上传失败");
            return JsonUtils.objectToJson(result);
        }

    }

    /*
    * @RequestMapping("/pic/upload")
    @ResponseBody
    public Map upload(MultipartFile uploadFile){
        try {
            FastDFSClient client = new FastDFSClient("classpath:conf/client.conf");

            //获取上传文件的后缀名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

            //上传
            String path = client.uploadFile(uploadFile.getBytes(),extName);
            //拼接成符合规定的url
            String url = IMAAGE_SERVER + path;
            System.out.println(url);

            //返回json结果
            Map result = new HashMap();
            result.put("error",0);
            result.put("url",url);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            Map result = new HashMap();
            result.put("error", 1);
            result.put("message","图片上传失败");
            return result;
        }

    }*/
}
