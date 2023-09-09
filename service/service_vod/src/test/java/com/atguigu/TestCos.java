package com.atguigu;

import com.alibaba.fastjson.JSON;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;

public class TestCos {
    public static void main(String[] args) {
        String secretId = "AKIDCDyTigOpzsUpthETRWNyIK7dsrZOA3g2";
        String secretKey = "6vTmoZ3GCrGPHsCF2XliOnvmNhkW1FCs";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-beijing");
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {
            File localFile = new File("C:\\Users\\Administrator\\Desktop\\1.png");
            String bucketName = "ggkt-atguigu-1310644373";
            String key = "/2023/06/1.png";
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(JSON.toJSONString(putObjectResult));
        } catch (Exception clientException) {
            clientException.printStackTrace();
        }
    }
}
