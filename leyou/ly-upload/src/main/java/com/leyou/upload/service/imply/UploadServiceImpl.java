package com.leyou.upload.service.imply;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.upload.config.UploadProperties;
import com.leyou.upload.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author river
 * @title: UploadServiceImpl
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/614:31
 */
@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadServiceImpl implements UploadService {
    @Autowired
    private UploadProperties uploadProperties;
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    @Override
    public String uploadImage(MultipartFile file) {
        //对文件进行校验
        //对文件格式进行校验
        String contentType = file.getContentType();
        if (!uploadProperties.getAllowTypes().contains(contentType)){
            throw new LyException(ExceptionEnum.INVALID_FILE_FORMAT);
        }
        //检验文件内容
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                log.info("【文件上传】上传文件格式错误");
                throw new LyException(ExceptionEnum.INVALID_FILE_FORMAT);
            }
        } catch (IOException e) {
            log.info("【文件上传】文件上传失败", e);
            throw new LyException(ExceptionEnum.INVALID_FILE_FORMAT);
        }
        //保存图片
        try {
            String extensionName = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extensionName, null);
            //返回保存图片的完整url
            return uploadProperties.getBaseUrl() + storePath.getFullPath();
        } catch (IOException e) {
            throw new LyException(ExceptionEnum.UPLOAD_IMAGE_EXCEPTION);
        }

    }
}
