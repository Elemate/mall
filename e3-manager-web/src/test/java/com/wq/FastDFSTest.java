package com.wq;

import org.csource.fastdfs.*;
import org.junit.Test;

public class FastDFSTest {

    public void testUploadImage(){
        try {
            //加载配置文件
            ClientGlobal.init("F:\\IntelliJ WorkSpace\\E3_mall\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
            //新建一个trackerClient对象
            TrackerClient client = new TrackerClient();
            //获得trackerServer对象
            TrackerServer trackerServer = client.getConnection();
            //创建一个storageServer对象为null
            StorageServer storageServer = null;
            //创建一个storageClient对象,传入两个参数
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            //使用storageclient上传图片，第一个参数图片路径，第二个图片类型
            String[] strings = storageClient.upload_file("C:\\Users\\youto8023\\Pictures\\Jesscia\\j0.jpg","jpg",null);

            for (String str : strings){
                System.out.println(str);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test1(){
        System.out.println(0.1*3);
        System.out.println(0.1*3==0.3);
    }
}
