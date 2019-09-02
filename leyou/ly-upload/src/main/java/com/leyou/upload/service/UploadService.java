package com.leyou.upload.service;

import org.springframework.web.multipart.MultipartFile; /**
 * @author river
 * @title: UploadService
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/614:30
 */
public interface UploadService {

    String uploadImage(MultipartFile file);
}
