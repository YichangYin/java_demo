package com.aspose;

import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import com.aspose.cells.Workbook;
import com.aspose.words.*;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;

/**
 * @program: java_demo
 * @description:
 * @author: Mr.Walloce
 * @create: 2019/07/26 11:10
 **/
public class FileCustUtil {

    /**
      * @Description TODO
      * @param licensePath 版权文件路径
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/7/29 22:18
      */
    private static void setLicense(String licensePath) {
        try {
            InputStream inputStream = FileCustUtil.class.getClassLoader().getResourceAsStream(licensePath);
            License aposeLicense = new License();
            aposeLicense.setLicense(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
      * @Description TODO
      * @param inputPath  需要被转换的word全路径带文件名
      * @param outPath 转换之后pdf的全路径带文件名
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/7/27 13:47
      */
    public static void docToPdf(String inputPath, String outPath) {
        try {
            //word文档
            Document doc = new Document(inputPath);

            //新建一个pdf文档
            File file = new File(outPath);
            FileOutputStream os = new FileOutputStream(file);

            //保存为pdf文件，saveFormat取的是words包下的，值为：40
            doc.save(os, com.aspose.words.SaveFormat.PDF);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
      * @Description TODO
      * @param inputPath 需要被转换的excel全路径带文件名
      * @param outPath 转换之后pdf的全路径带文件名
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/7/27 13:48
      */
    public static void excelToPdf(String inputPath, String outPath) {
        /*if (!getLicense()) {
            return;
        }*/
        try {
            //Excel文件数据
            Workbook wb = new Workbook(inputPath);
            FileOutputStream fileOS = new FileOutputStream(new File(outPath));
            //保存为pdf文件，saveFormat取的是cells包下的，值为：13
            wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
            fileOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
      * @Description TODO 文档拼接
      * @param mainDoc 主文档
      * @param addDoc 要拼接的文档
      * @param isPortrait 是否横向拼接
      * @Return com.aspose.words.Document 返回拼接后新的文档
      * @Author Mr.Walloce
      * @Date 2019/7/27 18:31
      */
    public static Document appendDocument(Document mainDoc, Document addDoc, boolean isPortrait) {

        String bookmark = "单位信息";
        DocumentBuilder builder = null;
        try {
            builder = new DocumentBuilder(mainDoc);
            BookmarkCollection bms = mainDoc.getRange().getBookmarks();
            Bookmark bm = bms.get(bookmark);
            if (bm != null) {
                builder.moveToBookmark(bookmark, true, false);
                builder.writeln();
                builder.getPageSetup().setPaperSize(PaperSize.A4);
                Node insertAfterNode = builder.getCurrentParagraph().getPreviousSibling();
                insertDocumentAfterNode(insertAfterNode, mainDoc, addDoc);
            }
            //builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
            if (isPortrait) {
                // 纵向纸张
                builder.getPageSetup().setOrientation(Orientation.PORTRAIT);
                //builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
            } else {
                // 横向
                builder.getPageSetup().setOrientation(Orientation.LANDSCAPE);
            }
            builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
            // builder.insertBreak(BreakType.PAGE_BREAK);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mainDoc;
    }

    /**
      * @Description 向书签后插入文档
      * @param mainDoc 主文档
      * @param tobeInserted 拼接的文档
      * @param bookmark 书签
      * @Return com.aspose.words.Document
      * @Author Mr.Walloce
      * @Date 2019/7/27 18:33
      */
    private static Document insertDocumentAfterBookMark(Document mainDoc, Document tobeInserted, String bookmark)
            throws Exception {
        if (mainDoc == null) {
            return null;
        } else if (tobeInserted == null) {
            return mainDoc;
        } else {
            //构建新文档
            DocumentBuilder mainDocBuilder = new DocumentBuilder(mainDoc);
            if (bookmark != null && bookmark.length() > 0) {
                //获取到书签
                BookmarkCollection bms = mainDoc.getRange().getBookmarks();
                Bookmark bm = bms.get(bookmark);
                if (bm != null) {
                    mainDocBuilder.moveToBookmark(bookmark, true, false);
                    mainDocBuilder.writeln();
                    //获取到插入的位置
                    Node insertAfterNode = mainDocBuilder.getCurrentParagraph().getPreviousSibling();
                    insertDocumentAfterNode(insertAfterNode, mainDoc, tobeInserted);
                }
            } else {
                appendDoc(mainDoc, tobeInserted, true);
            }

            return mainDoc;
        }
    }

    /**
      * @Description TODO
      * @param insertAfterNode 插入的位置
      * @param mainDoc
      * @param srcDoc
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/7/27 14:51
      */
    private static void insertDocumentAfterNode(Node insertAfterNode, Document mainDoc, Document srcDoc)
            throws Exception {
        if (insertAfterNode.getNodeType() != 8 & insertAfterNode.getNodeType() != 5) {
            throw new Exception("The destination node should be either a paragraph or table.");
        } else {
            CompositeNode dstStory = insertAfterNode.getParentNode();

            while (null != srcDoc.getLastSection().getBody().getLastParagraph()
                    && !srcDoc.getLastSection().getBody().getLastParagraph().hasChildNodes()) {
                srcDoc.getLastSection().getBody().getLastParagraph().remove();
            }

            NodeImporter importer = new NodeImporter(srcDoc, mainDoc, 1);
            int sectCount = srcDoc.getSections().getCount();

            for (int sectIndex = 0; sectIndex < sectCount; ++sectIndex) {
                Section srcSection = srcDoc.getSections().get(sectIndex);
                int nodeCount = srcSection.getBody().getChildNodes().getCount();

                for (int nodeIndex = 0; nodeIndex < nodeCount; ++nodeIndex) {
                    Node srcNode = srcSection.getBody().getChildNodes().get(nodeIndex);
                    Node newNode = importer.importNode(srcNode, true);
                    dstStory.insertAfter(newNode, insertAfterNode);
                    insertAfterNode = newNode;
                }
            }

        }
    }

    /**
      * @Description 文档拼接
      * @param dstDoc
      * @param srcDoc
      * @param includeSection
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/7/27 14:53
      */
    private static void appendDoc(Document dstDoc, Document srcDoc, boolean includeSection) throws Exception {
        if (includeSection) {
            Iterator<Section> var3 = srcDoc.getSections().iterator();
            while (var3.hasNext()) {
                Section srcSection = (Section) var3.next();
                Node dstNode = dstDoc.importNode(srcSection, true, 0);
                dstDoc.appendChild(dstNode);
            }
        } else {
            Node node = dstDoc.getLastSection().getBody().getLastParagraph();
            if (node == null) {
                node = new Paragraph(srcDoc);
                dstDoc.getLastSection().getBody().appendChild(node);
            }

            if (node.getNodeType() != 8 & node.getNodeType() != 5) {
                throw new Exception("Use appendDoc(dstDoc, srcDoc, true) instead of appendDoc(dstDoc, srcDoc, false)");
            }

            insertDocumentAfterNode(node, dstDoc, srcDoc);
        }

    }

    /*public static void appendDoc(Document dstDoc, Document srcDoc) throws Exception {
        appendDoc(dstDoc, srcDoc, true);
    }*/

    public static void main(String[] args) {

        //word 和excel 转为pdf
        String filePaths="E:\\IdeaWorkspace\\java_demo\\src\\main\\java\\com\\aspose\\infiles\\Atest11.docx";
        String fileName1 = "wordToPdf" + (int)(Math.random() * 100000);
        System.out.println("fileName1:" + fileName1);
        String outPath = "E:\\IdeaWorkspace\\java_demo\\src\\main\\java\\com\\aspose\\outfiles\\"+ fileName1 +".pdf";
        //word转pdf
        docToPdf(filePaths, outPath);

        String fileName2 = "wordToPdf" + (int)(Math.random() * 100000);
        System.out.println("fileName2:" + fileName2);
        outPath = "E:\\IdeaWorkspace\\java_demo\\src\\main\\java\\com\\aspose\\outfiles\\"+ fileName2 +".pdf";
        String excel2pdf = "E:\\IdeaWorkspace\\java_demo\\src\\main\\java\\com\\aspose\\infiles\\Atest11.xlsx";
        //excel转pdf
        //excelToPdf(excel2pdf, outPath);
    }
}
