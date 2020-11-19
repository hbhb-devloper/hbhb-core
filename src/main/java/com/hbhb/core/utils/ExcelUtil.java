package com.hbhb.core.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

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

    private static final HorizontalCellStyleStrategy HORIZONTAL_CELL_STYLE_STRATEGY;

    static {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 单元格自动换行
        contentWriteCellStyle.setWrapped(Boolean.TRUE);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HORIZONTAL_CELL_STYLE_STRATEGY =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

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
                    .registerWriteHandler(HORIZONTAL_CELL_STYLE_STRATEGY)
                    .doWrite(data);
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
                    .registerWriteHandler(HORIZONTAL_CELL_STYLE_STRATEGY)
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
