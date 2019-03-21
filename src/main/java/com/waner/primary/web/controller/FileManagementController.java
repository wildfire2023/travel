package com.waner.primary.web.controller;

import com.waner.primary.common.constants.Constants;
import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.common.util.QiniuFileUploadUtil;
import com.waner.primary.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 文件管理控制器
 * @author Monster
 * @since 1.0.0-SNAPSHOT
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
    @ResponseBody
    public Response<String> uploadImg(MultipartFile file, HttpSession session,
                                       @RequestParam(name = "way", required = false) String way) {
        if (file.isEmpty()) {
            return Response.fail(CodeMsg.EMPTY_FILE);
        }
        String imgUrl;
        try {
            imgUrl = QiniuFileUploadUtil.uploadImg(file);
            imgUrl = Constants.QINIU_PROTOCOL + Constants.QINIU_HEAD_IMG_BUCKET_URL + "/" + imgUrl;
            if ("user".equals(way)) {
                // 图片地址存储
                userService.saveImg(imgUrl, session);
            }
        } catch (IOException e) {
            throw new GlobalException("图片上传错误", 501001);
        }
        return Response.success(imgUrl);
    }
}
