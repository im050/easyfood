package com.im050.easyfood.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Tools {

    public static <T> List<T> changeToVo(List<?> list, Class<T> c) {
        List<T> finalList = new ArrayList<>();
        list.forEach(x -> {
            try {
                T obj = c.newInstance();
                BeanUtils.copyProperties(x, obj);
                finalList.add(obj);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return finalList;
    }

    public static String md5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String genTmpFileName() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public static String saveFile(MultipartFile file, String filePath) throws IOException {
        return saveFile(file, genTmpFileName(), filePath);
    }

    public static String saveFile(MultipartFile file, String fileName, String filePath) throws IOException {
        String originalName = file.getOriginalFilename();
        String ext = originalName.substring(originalName.lastIndexOf(".") + 1, originalName.length());
        fileName = fileName + "." + ext;
        filePath = filePath + fileName;

        File targetFile = new File(filePath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        file.transferTo(targetFile);

        return fileName;
    }

}
