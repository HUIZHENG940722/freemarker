package com.example.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.utils.WordUtil;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author HUIZHENG
 * @date 2019/4/22
 * @time 10:34
 * Created by IntelliJ IDEA.
 */
public class WordAction extends ActionSupport {
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件唯一名称
     */
    private String fileOnlyName;

    /**
     * 生成word文档
     * @return
     */
    public String createWord() {
        /**
         * 用于组装word页面需要的数据
         */
        Map<String, Object> dataMap = new HashMap<String, Object>();
        /** 组装数据 */
        dataMap.put("userName", "张三");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        dataMap.put("currDate", sdf.format(new Date()));
        dataMap.put("content", "这是其它内容这是其它内容这是其它内容这是其它内容这是其它内容这是其它内容这是其它内容" +
                "这是其它内容这是其它内容这是其它内容这是其它内容这是其它内容这是其它内容");

        List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
        for (int i = 1; i <= 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", "标题" + i);
            map.put("content", "内容" + (i * 2));
            map.put("author", "作者" + (i * 3));
            newsList.add(map);
        }
        dataMap.put("newsList", newsList);

        /**
         * 文件名称，唯一字符串
         */
        Random r = new Random();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
        StringBuffer sb = new StringBuffer();
        sb.append(sdf1.format(new Date()));
        sb.append("_");
        sb.append(r.nextInt(100));

        //文件路径
        filePath = ServletActionContext.getServletContext().getRealPath("/") + "upload";

        //文件唯一名称
        fileOnlyName = "用freemarker导出的Word文档_" + sb + ".doc";

        //文件名称
        fileName = "用freemarker导出的Word文档.doc";

        /** 生成word */
        WordUtil.createWord(dataMap, "news.ftl", filePath, fileOnlyName);

        return "createWordSuccess";
    }

    /**
     * 下载生成的word文档，入口，用来跳转至struts XML配置
     * @return
     */
    public String downloadWord() {
        /** 先判断文件是否已生成  */
        try {
            //解决中文乱码
            filePath = URLDecoder.decode(filePath, "UTF-8");
            fileOnlyName = URLDecoder.decode(fileOnlyName, "UTF-8");
            fileName = URLDecoder.decode(fileName, "UTF-8");

            //如果文件不存在，则会跳入异常，然后可以进行异常处理
            new FileInputStream(filePath + File.separator +  fileOnlyName);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "downloadWord";
    }

    /**
     * 下载生成的word文档
     * @return
     */
    public InputStream getWordFile(){
        try {
            //解决中文乱码
            fileName = URLDecoder.decode(fileName, "UTF-8");

            /** 返回最终生成的word文件流  */
            return new FileInputStream(filePath + File.separator + fileOnlyName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileOnlyName() {
        return fileOnlyName;
    }

    public void setFileOnlyName(String fileOnlyName) {
        this.fileOnlyName = fileOnlyName;
    }
}
