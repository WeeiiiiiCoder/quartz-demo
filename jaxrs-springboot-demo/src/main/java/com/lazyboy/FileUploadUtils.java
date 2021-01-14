package com.lazyboy;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * apache common fileupload 文件上传工具类
 *
 * @auther: zhouwei
 * @date: 2021/1/13 18:10
 */
public class FileUploadUtils {

    public static String defaultUploadPath = "C:\\Users\\ZHOUWEI\\Desktop\\csvtest";

    //默认支持的文件类型

    //默认支持的文件大小

    //

    /**
     * 解析上传的文件
     *
     * @param request
     */
    public static Map<String,Object> parseUploadFile(HttpServletRequest request) {
        Map<String, Object> multiPartData = new HashMap<>();
        //1.创建DiskFileItem工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置阈值，设置JVM一次能够处理的文件大小(默认吞吐量是10KB),超过该阈值文件会直接被写到硬盘中
        factory.setSizeThreshold(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
        //设置临时文件的存储位置(文件大小大于吞吐量的话就必须设置这个值，比如文件大小：1GB ，一次吞吐量：1MB)
        factory.setRepository(new File(defaultUploadPath));
        //2.创建核心对象
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        //设置最大可支持的单个文件大小(10MB),超出会抛异常FileSizeLimitExceededException
        fileUpload.setFileSizeMax(1024 * 1024 * 10);
        //设置转换时使用的字符集
        fileUpload.setHeaderEncoding("UTF-8");
        if (ServletFileUpload.isMultipartContent(request)) {
            //处理上传的文件
            try {
                List<FileItem> fileItems = fileUpload.parseRequest(request);
                for (FileItem fileItem : fileItems) {
                    if (fileItem.isFormField()) {//判断该FileItem为一个普通的form元素
                        //获取字段名
                        String fieldName = fileItem.getFieldName();
                        //获取字段值，并解决乱码
                        String fieldValue = fileItem.getString("UTF-8");
                        System.out.println(fieldName + " : " + fieldValue);
                        multiPartData.put(fieldName, fieldValue);
                    } else {//判断该FileItem为一个文件

                        System.out.println("Start to upload file!");
                        //获取文件名

                        String fileName = fileItem.getName();
                        System.out.println("fileName : " + fileName);
                        //获取文件大小
                        long fileSize = fileItem.getSize();
                        if (fileSize > 10 * 1024 * 1024) {
                            return null;
                        }
                        System.out.println("fileSize : " + fileSize);
                        //文件路径去除目录和后缀后的文件名
                        String baseFileName = FilenameUtils.getBaseName(fileName);
                        //获取文件后缀
                        String extension = FilenameUtils.getExtension(fileName);

                        /*if (!EXCEL_XLS_SUFFIX.equals(extension)) {
                            response.resultWithDesc(1001, "只支持.xls文件");
                            return response;
                        }*/
                        // 写文件到path目录，文件名问filename
                        String absoluteFilePath = FilenameUtils.concat(defaultUploadPath, fileName);
                        File file = new File(absoluteFilePath);
                        fileItem.write(file);
                        multiPartData.put(fileItem.getFieldName(), file);
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return multiPartData;
    }


}