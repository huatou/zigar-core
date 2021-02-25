package com.zigar.zigarcore.rest;

import com.zigar.zigarcore.entity.FileEntity;
import com.zigar.zigarcore.entity.UserEntity;
import com.zigar.zigarcore.exception.BusinessLogicException;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.service.FileService;
import com.zigar.zigarcore.utils.jwt.PassToken;
import com.zigar.zigarcore.utils.security.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * <p>
 * 文件表 rest控制器
 * </p>
 *
 * @author zigar
 * @since 2020-06-12
 */

@Api(tags = "文件系统管理")
@RestController
@RequestMapping("/api/zigarcore/file")
public class FileRestController {

    private final FileService fileService;

    public FileRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    Results<FileEntity> fileUpload(MultipartFile file) {
        UserEntity currentUser = SecurityUtils.getCurrentUser();
        String currentUserId = currentUser.getUserId();
        return fileService.uploadFile(file, currentUserId);
    }

    //文件下载
    @ApiOperation("文件下载")
    @GetMapping("/{fileId}")
    @PassToken
    public void fileList(@PathVariable String fileId, HttpServletResponse httpServletResponse) {
        FileEntity fileEntity = fileService.getById(fileId);
        if (fileEntity == null) {
            throw new BusinessLogicException("文件不存在");
        }
        try {
            String url = fileEntity.getUrl();
            File file = ResourceUtils.getFile(url);
            byte[] buff = new byte[1024];
            //创建缓冲输入流
            BufferedInputStream bis;
            OutputStream outputStream = httpServletResponse.getOutputStream();
            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(file));
            int read = bis.read(buff);
            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessLogicException("下载失败");
        }
    }

}
