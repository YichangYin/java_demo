package com.xmlfile;

import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * @program: java_demo
 * @description:
 * @author: Mr.Walloce
 * @create: 2019/09/05 09:39
 **/
public class XmlFileService {

    /**
      * @Description 通过数据创建Xml文件
      * @param rootName
      * @param channelName
      * @param fileName
      * @param mapItems
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/9/5 11:59
      */
    public void createXml(String rootName, String channelName, String fileName, List<Map<String, String>> mapItems) {
        try {
            // 生成一个根节点
            Element root = new Element(rootName);

            // 生成一个document对象
            Document document = new Document(root);

            //添加子节点数据
            this.buildNodes(root, channelName, mapItems);

            Format format = Format.getCompactFormat();
            // 设置换行Tab或空格
            format.setIndent("	");
            format.setEncoding("UTF-8");

            // 创建XMLOutputter的对象
            XMLOutputter outputer = new XMLOutputter(format);
            // 利用outputer将document转换成xml文档
            File file = new File(fileName);
            outputer.output(document, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成xml失败");
        }
    }

    /**
      * @Description 构建子节点数据
      * @param root
      * @param channelName
      * @param mapItems
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/9/5 11:56
      */
    public void buildNodes(Element root, String channelName, List<Map<String, String>> mapItems) {
        Element channel = null;
        Element node = null;
        for (Map<String, String> mapItem : mapItems) {
            channel = new Element(channelName);
            for (Map.Entry<String, String> entry : mapItem.entrySet()) {
                node = new Element(entry.getKey());
                node.setText(entry.getValue());
                channel.addContent(node);
            }
            root.addContent(channel);
        }
    }
}
