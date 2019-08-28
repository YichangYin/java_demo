package com.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * @program: java_demo
 * @description:  文件操作测试
 * @author: Mr.Walloce
 * @create: 2019/08/08 15:14
 **/
public class FileTest {

    private final static int byteLen = 1024;

    /**
      * @Description 文件复制（使用Stream流操作）
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/8/24 15:15
      */
    public static void copyFileByStream(File srcFile, File tarFile) {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(srcFile);
            output = new FileOutputStream(tarFile);
            byte[] bytes = new byte[byteLen];
            //字节长度
            int byteLen = 0;
            while (true) {
                //字节为空时，跳出循环
                if (!((byteLen = input.read(bytes)) > 0)) {
                    break;
                }
                //向目标文件写入流数据
                output.write(bytes, 0, byteLen);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (output != null) {
                IOUtils.closeQuietly(output);
            }
            if (input != null) {
                IOUtils.closeQuietly(input);
            }
        }
    }

    /**
      * @Description 使用FileChannel对文件进行复制，其实质还是通过
      *              FileInputStream和FileOutStream对文件流进行操作
      * @param srcFile
      * @param targetFile
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/8/24 14:43
      */
    public static void copyFileByChannel(File srcFile, File targetFile) {
        FileChannel inputFileChannel = null;
        FileChannel outputFileChannel = null;
        try {
            inputFileChannel = new FileInputStream(srcFile).getChannel();
            outputFileChannel = new FileOutputStream(targetFile).getChannel();
            outputFileChannel.transferFrom(inputFileChannel, 0, inputFileChannel.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (outputFileChannel != null) {
                IOUtils.closeQuietly(outputFileChannel);
            }
            if (inputFileChannel != null) {
                IOUtils.closeQuietly(inputFileChannel);
            }
        }
    }
    
    /**
      * @Description 压缩文件，将文件压缩在源文件所在的目录下
      * @param srcFilePath 要压缩的文件
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/8/11 15:16
      */
    public static void zipFile(String srcFilePath) {
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()){
            return;
        }

        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        //获取到文件路径
        String zipFilePath = srcFile.getPath();
        //压缩文件的路径
        String destFilePath = zipFilePath.substring(0, zipFilePath.indexOf(".") + 1) + "zip";
        File dstFile = new File(destFilePath);
        try {
            //输出的文件
            fos = new FileOutputStream(dstFile);
            CheckedOutputStream cos = new CheckedOutputStream(fos, new CRC32());
            zos = new ZipOutputStream(cos);
            //数据写入
            writeDataToZip(srcFile, zos, "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zos != null) {
                IOUtils.closeQuietly(zos);
            }
            if (fos != null) {
                IOUtils.closeQuietly(fos);
            }
        }
    }

    /**
      * @Description 数据写入压缩文件
      * @param srcFile
      * @param zos
      * @param baseDir
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/8/28 17:18
      */
    private static void writeDataToZip(File srcFile, ZipOutputStream  zos, String baseDir) {
        BufferedInputStream bis = null;
        try {
            //源文件流
            bis = new BufferedInputStream(new FileInputStream(srcFile));
            //创建压缩文件实体
            String name = baseDir + srcFile.getName();
            ZipEntry entry = new ZipEntry(name);
            zos.putNextEntry(entry);
            int count;
            byte data[] = new byte[byteLen];
            //写入数据
            while ((count = bis.read(data, 0, byteLen)) != -1) {
                zos.write(data, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                IOUtils.closeQuietly(bis);
            }
        }
    }

    /**
      * @Description 压缩一个文件夹下的所有文件
      * @param srcFilePath
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/8/24 15:59
      */
    public static void zipFilePath(String srcFilePath) {
        System.out.println(srcFilePath);
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()) {
            System.out.println("文件路径错误！");
            return;
        }
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        //获取到文件路径
        String zipFilePath = srcFile.getPath();
        //压缩文件的路径
        String destFilePath = srcFile.getParent() +"\\"+ zipFilePath.substring(zipFilePath.lastIndexOf("\\") + 1) + ".zip";
        File dstFile = new File(destFilePath);
        try {
            fos = new FileOutputStream(dstFile);
            CheckedOutputStream cos = new CheckedOutputStream(fos,new CRC32());
            zos = new ZipOutputStream(cos);
            File[] files = srcFile.listFiles();
            //遍历文件压缩
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    zipDirectory(files[i], zos, files[i].getName() + "/");
                } else {
                    writeDataToZip(files[i], zos, "");
                }
            }
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (zos != null) {
                IOUtils.closeQuietly(zos);
            }
            if (fos != null) {
                IOUtils.closeQuietly(fos);
            }
        }
    }

    /**
      * @Description 遍历文件夹下的文件
      * @param directoryFile
      * @param zos
      * @param baseDir
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/8/28 18:05
      */
    private static void zipDirectory(File directoryFile, ZipOutputStream zos, String baseDir) {
        File[] files = directoryFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                zipDirectory(files[i], zos, baseDir + files[i].getName() + "/");
            } else {
                if (files[i].exists()) {
                    writeDataToZip(files[i], zos, baseDir);
                }
            }
        }
    }
    
    /**
      * @Description 解压文件
      * @param zipFile  需要解压的文件
      * @param unZipPath 文件解压后存放的路径
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/8/25 15:17
      */
    public static void unZipFile(File zipFile, String unZipPath) {
        File pathFile = new File(unZipPath);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zip = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            zip = new ZipFile(zipFile);
            //遍历解压文件
            for(Enumeration entries = zip.entries();entries.hasMoreElements();){
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                is =  zip.getInputStream(entry);
                String outPath = (unZipPath+"/"+zipEntryName).replaceAll("\\*", "/");;
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if(!file.exists()){
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹
                if(new File(outPath).isDirectory()){
                    continue;
                }
                //解压后输出文件
                os = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while((len = is.read(buf1)) > 0){
                    os.write(buf1,0,len);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                IOUtils.closeQuietly(is);
            }
            if (os != null) {
                IOUtils.closeQuietly(os);
            }
        }
    }

    public static void main(String[] args) {
        String srcFilePath = "F:\\testFiles\\file1.txt";
        String zipFilePath = "F:\\testFiles\\test.zip";
        String unZipFilePath = "F:\\testFiles\\unzip";
        File srcFile = new File(srcFilePath);
        String targetFilePath = "F:\\testFiles\\file1Copy1.txt";
        File targetFile = new File(targetFilePath);
        /*try {
            compress(srcFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
//        copyFileByStream(srcFile, targetFile);
//        copyFileByChannel(srcFile, targetFile);
        /*try {
            FileUtils.copyFile(srcFile, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
//        zipFile(srcFilePath);
        /*String destFilePath = "F:\\testFiles\\test";
        zipFilePath(destFilePath);*/
        unZipFile(new File(zipFilePath), unZipFilePath);
    }
}
