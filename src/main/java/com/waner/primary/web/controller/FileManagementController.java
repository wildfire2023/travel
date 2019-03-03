package com.waner.primary.web.controller;

import com.waner.primary.common.result.Response;
import com.waner.primary.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件管理控制器
 * @author Monster
 * @date 2019/3/3 11:58
 * @since 1.8
 */
@Controller
@RequestMapping("file")
public class FileManagementController {

    private final UserService userService;

    public FileManagementController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 文件上传映射
     * @return
     */
    @PostMapping("upload")
    public Response<String> uploadImg(MultipartFile file) {

        return Response.success("111");
    }
}
