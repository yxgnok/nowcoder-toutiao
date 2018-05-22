package com.nowcoder.toutiao.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.nowcoder.toutiao.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

import static com.nowcoder.toutiao.util.ToutiaoUtil.ALIYUN_IMAGE_URL;

@Service
public class AliyunService {
    private static final Logger logger = LoggerFactory.getLogger(AliyunService.class);

    // endpoint以杭州为例，其它region请按实际情况填写
    String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
    String accessKeyId = "LTAIq3lmDoyahYBy";
    String accessKeySecret = "5JDCmnPlBik4dRIFBveLySvacSigY5";
    // 创建OSSClient实例
    OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!ToutiaoUtil.isFileAllowed(fileExt)) {
                return null;
            }

            String fileName = UUID.randomUUID().toString().replaceAll("-", "")+"."+fileExt;

            // 上传byte数组
            byte[] content = file.getBytes();
            ossClient.putObject("toutiaoimage", fileName, new ByteArrayInputStream(content));
            return ALIYUN_IMAGE_URL+fileName;
        } catch (OSSException oe) {
            logger.error("阿里云异常：", oe.getMessage());
            return null;
        }
    }

}

