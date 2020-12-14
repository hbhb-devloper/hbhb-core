package com.hbhb.core.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaokang
 * @since 2020-06-29
 */
@Slf4j
@SuppressWarnings(value = {"rawtypes"})
public class ExcelUtil {

    public static void export2Web(HttpServletResponse response,
                                  String fileName,
                                  String sheetName,
                                  Class clazz,
                                  List data) {
        fileName += ExcelTypeEnum.XLSX.getValue();
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            EasyExcel.write(response.getOutputStream(), clazz)
                    .sheet(sheetName)
                    .doWrite(data);
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new RuntimeException("导出Excel失败，请联系网站管理员！");
        }
    }

    public static void export2WebWithHead(HttpServletResponse response,
                                          String fileName,
                                          String sheetName,
                                          List<List<String>> head,
                                          List data) {
        fileName += ExcelTypeEnum.XLSX.getValue();
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            EasyExcel.write(response.getOutputStream()).head(head).sheet(sheetName).doWrite(data);
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new RuntimeException("导出Excel失败，请联系网站管理员！");
        }
    }

    public static void export2WebWithTemplate(HttpServletResponse response,
                                              String fileName,
                                              String sheetName,
                                              String templateFileName,
                                              List data) {
        fileName += ExcelTypeEnum.XLSX.getValue();
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            EasyExcel.write(response.getOutputStream())
                    .withTemplate(templateFileName)
                    .sheet(sheetName)
                    .doWrite(data);
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new RuntimeException("导出Excel失败，请联系网站管理员！");
        }
    }

    public static String encodingFileName(HttpServletRequest request, String fileName) {
        final String userAgent = request.getHeader("user-agent");
        if (StringUtils.isEmpty(userAgent)) {
            return fileName;
        }
        try {
            if (userAgent.contains("Chrome")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1.name());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return fileName;
    }
}
