package com.example.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Word操作工具类
 * @author HUIZHENG
 * @date 2019/4/22
 * @time 10:16
 * Created by IntelliJ IDEA.
 */
public class WordUtil {
    /**
     * 生成Word文件
     * @param dataMap word中需要展示的动态数据，用map来保存
     * @param templateName word模板名称
     * @param filePath 文件生成的目标路径
     * @param fileName 生成的文件名称
     */
    public static void createWord(Map dataMap, String templateName, String filePath, String fileName) {
        try {
            // 创建配置实例
            Configuration configuration = new Configuration();
            // 设置编码
            configuration.setDefaultEncoding("UTF-8");
            // ftl模板文件统一放至 com.example.template 包下面
            configuration.setClassForTemplateLoading(WordUtil.class,
                    "/com/example/template/");
            // 获取模板
            Template template = configuration.getTemplate(templateName);
            // 输出文件
            File outFile = new File(filePath + File.separator + fileName);
            // 如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            // 将模板和数据模型合并生成文件
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), "UTF-8"));
            // 生成文件
            template.process(dataMap, out);
            // 关闭流
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
