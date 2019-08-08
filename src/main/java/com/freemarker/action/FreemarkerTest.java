package com.freemarker.action;

import freemarker.template.*;

import java.io.*;
import java.util.*;

/**
 * @program: java_demo
 * @description:
 * @author: Mr.Walloce
 * @create: 2018/11/19 13:28
 **/
public class FreemarkerTest {
    //模版存放路径
    String pathname = "E:\\IdeaWorkspace\\java_demo\\src\\main\\java\\com\\freemarker\\demo\\template";
    //文件生成路径
    String filePath = "E:\\IdeaWorkspace\\java_demo\\src\\main\\java\\com\\freemarker\\demo\\out";
    //生成的文件名称
    String fileName = "testOut.doc";
    //模版名称
    String templateName = "test.ftl";
    //编码格式
    String enCode = "UTF-8";

    /**
     *
     * @param dataModel 要导出的数据模型
     * @return
     */
    public File createWord(Map dataModel) {
        // 输出文件
        File outFile = null;
        try {
            // 创建配置实例
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
            // 设置编码
            cfg.setDefaultEncoding(enCode);
            // ftl模板文件统一放至 com.lun.template 包下面
            cfg.setDirectoryForTemplateLoading(new File(pathname));
            //设置错误显示方式
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            //设置不抛出异常
            cfg.setLogTemplateExceptions(false);
            // 设置对象包装器
            cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
            //配置缓存
            cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
            // 配置加载模板
            Template template = cfg.getTemplate(templateName);
            // 输出文件
            outFile = new File(filePath + File.separator + fileName);
            // 如果输出目标文件不存在，则创建
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            // 将模板和数据模型合并生成文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), enCode));
            // 生成文件
            template.process(dataModel, out);
            // 将缓冲区数据写到文件中
            out.flush();
            //关闭缓冲区
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFile;
    }

    /**
     *
     * @param dataMap
     *            word中需要展示的动态数据
     * @param templateName
     *            word模板名称 eg：test.ftl
     * @param filePath
     *            文件生成的目标路径，eg：D:/wordFile/
     * @param fileName
     *            生成的文件名称，eg：test.doc
     */
    private File createWord(Map<String, Object> dataMap, String templateName, String filePath, String fileName) {
        // 输出文件
        File outFile = null;
        try {
            // 创建配置实例
            Configuration configuration = new Configuration();
            // 设置编码
            configuration.setDefaultEncoding(enCode);
            // ftl模板文件统一放至 com.lun.template 包下面
            //configuration.setClassForTemplateLoading(WordOprationService.class, templatePath);
            //设置错误显示方式
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            // 设置对象包装器
            //configuration.setObjectWrapper(new DefaultObjectWrapper());
            // 获取模板
            Template template = configuration.getTemplate(templateName);
            // 输出文件
            outFile = new File(filePath + File.separator + fileName);
            // 如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            // 将模板和数据模型合并生成文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), enCode));
            // 生成文件
            template.process(dataMap, out);
            // 关闭流
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFile;
    }

    public Map<String, Object> makeDutyExportData(String dutyinfoGuid) {

        Calendar calendar = Calendar.getInstance();
        // 导出word的数据模型
        Map<String, Object> exportData = new HashMap<String, Object>();

        // 节假日对应的信息
        Map<String, Map<String, Object>> dutyTimesMap = new LinkedHashMap<String, Map<String, Object>>();

        // 每一天的对应的数据
        Map<String, Object> dutyTimeMap = null;

        // 对应的中文星期几
        String weeknum = "日一二三四五六";
        String weeknumCh = "";

        // 获取要导出的数据信息
        //SjDutyInfo dutyinfo = dutyInfoservice.find(SjDutyInfo.class, dutyinfoGuid);

        //节假日开始结束时间
        //Date beginTime = EpointDateUtil.convertString2Date(dutyinfo.getFestivalstartdate(), "yyyy-MM-dd");
        //calendar.setTime(beginTime);
        weeknumCh = String.valueOf(weeknum.charAt(calendar.get(Calendar.DAY_OF_WEEK) - 1));
        //Date endTime = EpointDateUtil.convertString2Date(dutyinfo.getFestivalenddate(), "yyyy-MM-dd");

        //long from = beginTime.getTime();
        //long to = endTime.getTime();
        // 计算放假的天数
        //int days = (int) ((to - from)/(1000 * 60 * 60 * 24));

        // 节假日前一晚的信息
        //SjDutyTime dutyTime = dutyTimeService.findOne(dutyinfo.getFestivalstartdate(), String.valueOf(1), dutyinfoGuid);
        /*if (dutyTime == null) {
            dutyTime = new SjDutyTime();
        } else {
            dutyTime = this.changeEnter(dutyTime);
        }
        dutyTime.set("dutytime", SjSupervisionUtils.getDataStr(beginTime, "MM月dd日"));
        dutyTime.set("weeknum", weeknumCh);
        exportData.put("first", dutyTime);

        // 节日
        exportData.put("dutyinfo", dutyinfo);
        // 日期
        String timeStr = "";
        String timeEnStr = "";
        for (int i = 1; i <= days; i++) {
            dutyTimeMap = new HashMap<String, Object>();
            calendar.setTimeInMillis(from + 1000 * 60 * 60 * 24 * i);
            weeknumCh = String.valueOf(weeknum.charAt(calendar.get(Calendar.DAY_OF_WEEK) - 1));
            timeStr = SjSupervisionUtils.getDataStr(calendar.getTime(), "MM月dd日");
            timeEnStr = SjSupervisionUtils.getDataStr(calendar.getTime(), "yyyy-MM-dd");
            // 白天
            dutyTime = dutyTimeService.findOne(timeEnStr, String.valueOf(0), dutyinfoGuid);
            if (dutyTime == null) {
                dutyTime = new SjDutyTime();
            } else {
                dutyTime = this.changeEnter(dutyTime);
            }
            dutyTimeMap.put("day", dutyTime);
            // 添加后数据置空
            dutyTime = null;
            // 晚上
            dutyTime = dutyTimeService.findOne(timeEnStr, String.valueOf(1), dutyinfoGuid);
            if (dutyTime == null) {
                dutyTime = new SjDutyTime();
            } else {
                dutyTime = this.changeEnter(dutyTime);
            }
            dutyTimeMap.put("night", dutyTime);
            // 添加后数据置空
            dutyTime = null;
            dutyTimeMap.put("weeknum", weeknumCh);
            dutyTimesMap.put(timeStr, dutyTimeMap);
        }*/
        exportData.put("others", dutyTimesMap);
        return exportData;
    }

    public File getLeaderDutyInfoWordByData(Map<String, Object> dataMap) {
        String savePath = (int) (Math.random() * 100000) + ".doc";
        return createWord(dataMap, "leaderdutyinfo.ftl", filePath, savePath);
    }

    public static void main(String[] args) {
        FreemarkerTest ft = new FreemarkerTest();
        Map<String, Object> animalMap = new HashMap<String, Object>();
        List<Animal> animals = new ArrayList<Animal>();
        Animal animal = new Animal();
        animal.setName("mouse");
        animals.add(animal);
        animalMap.put("animals", animals);
        ft.createWord(animalMap);
    }
}
