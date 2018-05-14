package com.nowcoder.toutiao.service;

import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class QiniuService {
    private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);
    //设置账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "mAR5Vb04L7HuRqrEKT_kxPgmoruqdMoiJXKginKN";
    String SECRET_KEY = "PHs9jm4Kx_qil7clZ3Zy5_6EMB4IFK6dTOjbUUu8";
    //要上传的空间
    String bucketname = "toutiao";

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager();

    private static String QINIU_IMAGE_DOMAIN = ""
}
