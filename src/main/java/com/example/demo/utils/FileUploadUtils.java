package com.example.demo.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUploadUtils {
    public static final String PATH="D:/ideaproject/springboot_08blog/target/classes/static";
    public static final String PATH1="D:/ideaproject/springboot_08blog/src/main/resources/static";

    /**
     * @param file:文件
     * @param path:上传的绝对路径
     * @param fileName:上传的文件名
     * @return:存入数据库的文件路径
     * @throws IOException
     */
    public static String FileUpload(MultipartFile file,String path,String fileName) throws IOException {
        String path1=PATH+path;
        String path2=PATH1+path;
        file.transferTo(new File(path1,fileName));
        FileUtils.copyFile(new File(path1,fileName),new File(path2,fileName));
        return path+fileName;
    }

    /*public static void main(String[] args) throws IOException {
        String dddd = FileUpload(null, "/static/image/blog/", "dddd");
        System.out.println(dddd);
    }*/
}
