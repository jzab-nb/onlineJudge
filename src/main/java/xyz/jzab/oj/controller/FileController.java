package xyz.jzab.oj.controller;

import cn.hutool.core.io.FileUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.jzab.oj.annotation.AuthCheck;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.ErrorCode;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.config.FileConfig;
import xyz.jzab.oj.exception.BusinessException;
import xyz.jzab.oj.model.dto.file.FileUploadRequest;
import xyz.jzab.oj.model.enums.FileTypeEnums;
import xyz.jzab.oj.service.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    FileConfig fileConfig;
    @Resource
    UserService userService;

    @PostMapping("/upload")
    // 上传文件接口
    public BaseResponse<String> upload(@RequestPart("file") MultipartFile multipartFile, FileUploadRequest fileUploadRequest, HttpServletRequest request) {

        // 获取上传的类型(用途)
        String type = fileUploadRequest.getType( );
        // 文件用途合法
        FileTypeEnums fileType = FileTypeEnums.getEnumFromValue(type);
        if(fileType==null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        // 文件大小和类型合法
        isValid(multipartFile, fileType);
        // 文件名称拼接(随机串+原文件名)
        try{
            HttpSession session = request.getSession( );
            // 文件数据
            session.setAttribute("tempFile",multipartFile.getBytes());
            // 文件用途
            session.setAttribute("fileType", fileType);
            return ResultUtils.success("/temp");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件存储失败");
        }

    }
    @GetMapping("/get/temp")
    @AuthCheck()
    public void getTempFile(HttpServletResponse response, HttpServletRequest request){
        ServletOutputStream outputStream = null;
        byte[] tempFile = (byte[]) request.getSession( ).getAttribute("tempFile");
        if(tempFile==null) throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "没有临时文件");
        try {
            response.setContentType("image; charset=UTF-8");
            outputStream = response.getOutputStream( );
            outputStream.write(tempFile);
        } catch (IOException e) {
            e.printStackTrace( );
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取文件失败");
        }

    }

    @GetMapping("/get/{type}/{id}/{name}")
    public void getFile(
            HttpServletResponse response,
            @PathVariable("type") String type,
            @PathVariable("id") String id,
            @PathVariable("name") String name
    ){
        String filePath = String.format("%s/%s/%s/%s", fileConfig.getPath(), type, id, name);
        File file = new File(filePath);
        if(!file.exists()) throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        try {
            byte[] data = new byte[(int)file.length()];
            FileTypeEnums enumByType = FileTypeEnums.getEnumFromValue(type);
            if(enumByType==null) throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "type错误");
            response.setContentType(enumByType.getContentType());
            ServletOutputStream outputStream = response.getOutputStream( );
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(data);
            outputStream.write(data);
        } catch (Exception e) {
            e.printStackTrace( );
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "读取文件失败");
        }
    }
    private void isValid(MultipartFile multipartFile, FileTypeEnums fileType){
        // 最大字节数()mb*1024*1024
        long max_size = fileConfig.getMaxSize()*1024*1024;
        // 判断后缀
        String suffix = FileUtil.getSuffix(multipartFile.getOriginalFilename( ));
        // 文件类型
        if(FileTypeEnums.USER_AVATAR.equals(fileType)) {
            // 判断文件大小
            if(multipartFile.getSize()>max_size) throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件不能大于 "+fileConfig.getMaxSize()+" MB");
            if(!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(suffix)){
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
    }
}