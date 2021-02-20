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
 * 测试 rest控制器
 * </p>
 *
 * @author zigar
 * @since 2021-02-20
 */

@Api(tags = "测试")
@RestController
public class TestRestController {

    @PassToken
    @GetMapping("/zigar/test")
    String test(MultipartFile file) {
        return "zigar test";
    }

}
