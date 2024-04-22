package xyz.jzab.oj.utils;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import xyz.jzab.oj.common.ErrorCode;
import xyz.jzab.oj.config.FileConfig;
import xyz.jzab.oj.exception.BusinessException;
import xyz.jzab.oj.model.enums.FileTypeEnums;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Component
public class FileUtils {
    @Resource
    FileConfig fileConfig;
    public String StoreTemp(HttpSession session, FileTypeEnums FileTypeEnums, int id){
        byte[] data = (byte[]) session.getAttribute("tempFile");
        xyz.jzab.oj.model.enums.FileTypeEnums fileType = (FileTypeEnums)session.getAttribute("fileType");
        if (data==null || fileType==null) throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "不存在临时文件");
        if(!fileType.equals(FileTypeEnums)) throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型不一致");
        String folderPath = String.format("%s/%s/%s/", fileConfig.getPath(),FileTypeEnums.getType(), id);
        File folder = new File(folderPath);
        if(!folder.isDirectory()) {
            if(!folder.mkdirs()) throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建文件夹失败");
        }
        String fileName = UUID.randomUUID( ).toString( );
        File file = new File(folderPath+ fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace( );
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建文件失败");
        }
        return String.format("/%s/%s/%s",FileTypeEnums.getType(),id,fileName);
    }
}