package cn.hot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUpload {

    private static Logger logger = LoggerFactory.getLogger(FileUpload.class);

    public static List<String> upload(MultipartFile[] uploadFile, String fileName) throws IOException {
        List<String> fileList = new ArrayList<String>();
        for (MultipartFile item : uploadFile) {
            //获取文件名称
            String uploadFileName = item.getOriginalFilename();
            if (uploadFileName.endsWith("jpg") || uploadFileName.endsWith("png") || uploadFileName.endsWith("p12")) {
                String timeId = DateUtil.getYMD();
                File upload = new File(fileName + File.separator + timeId + File.separator);
                if (!upload.exists()) {
                    upload.mkdirs();
                }
                String filePath = "";
                if (uploadFileName.endsWith("p12")) {
                    filePath = System.currentTimeMillis() + ".p12";
                } else {
                    filePath = System.currentTimeMillis() + ".jpg";
                }
                File upload_file = new File(upload.getAbsolutePath(), filePath);

                logger.info("文件上传:{}", upload_file);
                fileList.add(timeId + File.separator + filePath);
                item.transferTo(upload_file);
            }
        }
        return fileList;
    }

}
