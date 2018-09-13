package com.fykj._b._core.file;

import com.fykj._b._core.Constants;
import com.fykj.kernel._c.model.JFile;
import com.fykj.util.JUniqueUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: songzhonglin
 * Date: 2017/11/20
 * Time: 13:51
 * Description:
 **/
public class FileHelper {

    public static String TMP_PATH = "tmp/";

    public static String excel2003 = "xls";
    public static String excel2007 = "xlsx";

    public static final Logger logger = LoggerFactory.getLogger(FileHelper.class);

    public static void deleteFileWithLongDate(File file,long date){
        if(file.isFile()){
            //1200000=20*60*1000
            if((date - file.lastModified())> 1200000){
                file.delete();
            }
        }else if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File file2 : files) {
                deleteFileWithLongDate(file2,date);
            }
            if(file.listFiles().length == 0){
                file.delete();
            }
        }
    }

    /**
     * 创建唯一的临时目录
     * @param path
     * @return
     */
    public static String tmpUniquefilePath(String path){
        String uuid = JUniqueUtils.unique()+"/";
        return FileHelper.TMP_PATH+path+uuid;
    }

    /**
     * 文件转换成流
     * @param file
     * @return
     */
    public static byte[] toBytes(File file){
        byte[] bytes = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            bytes = IOUtils.toByteArray(IOUtils.toBufferedInputStream(inputStream));
        } catch (IOException e) {
            throw new IllegalArgumentException("文件出错",e);
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
        return bytes;
    }

    /**
     * 保存文件异步
     * @param jfile
     */
    public static JFile writeFileToDiskAsyn(final JFile jfile, final String absolutePath){
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    writeFileToDisk(jfile,absolutePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return jfile;
    }

    /**
     * 保存文件
     * @param jfile
     * @throws IOException
     */
    public static JFile writeFileToDisk(JFile jfile,String absolutePath) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/");
        String appendDatePath = sdf.format(new Date());
        String relativePath = wrapRelativePath(jfile.getRelativePath())+appendDatePath;
        String fileExtension = appendIfMissingDot(jfile.getFileExtension());
        jfile.setExpectedFullFileName(UUID.randomUUID().toString()+fileExtension);
        jfile.setRelativePath(relativePath);
        String dirPath = wrapAbsolutePath(absolutePath)+relativePath;
        String fileName = jfile.getExpectedFullFileName();
        logger.debug("dirPath: "+dirPath);
        logger.debug("fileName: "+fileName);
        byte[] fileContent = jfile.getFileContent();
        writeBytesToDisk(fileContent, dirPath, fileName);
        return jfile;
    }

    /**
     * 写入文件流到硬盘中
     * @param fileContent
     * @param dirPath
     * @param fileName
     * @throws IOException
     */
    public static void writeBytesToDisk(byte[] fileContent,String dirPath,String fileName) throws IOException {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        File dir = new File(dirPath);
        File file = new File(dirPath+fileName);
        logger.debug("dirPath: "+dirPath);
        logger.debug("filePath: "+dirPath+fileName);
        if(!dir.exists() || !dir.isDirectory()){
            dir.mkdirs();
        }
        try {
            logger.debug("prepared writting : "+dirPath+fileName);
            if(!file.exists()){
                logger.debug("prepared creating new file  : "+dirPath+fileName);
                file.createNewFile();
            }
            logger.debug("new file created : "+dirPath+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(fileContent);
            bos.flush();
            fos.flush();
            logger.debug("file created (1) : "+dirPath+fileName);
            logger.debug("file created (2) : "+file.getAbsolutePath());
        } finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(bos);
        }
    }

    /**
     * 确保绝对路径以slash结尾
     * @param absolutePath
     * @return
     */
    public static String wrapAbsolutePath(String absolutePath){
        if(!absolutePath.endsWith(Constants.SLASH)){
            absolutePath = absolutePath + Constants.SLASH;
        }
        return absolutePath;
    }

    /**
     * 确保相对路径不以slash开头以slash结尾
     * @param relativePath
     * @return
     */
    public static String wrapRelativePath(String relativePath) {
        if (StringUtils.isBlank(relativePath) || Constants.SLASH.equals(relativePath)) {
            relativePath = StringUtils.defaultString(relativePath);
        } else {
            relativePath = StringUtils.appendIfMissing(relativePath, Constants.SLASH);
            if (StringUtils.startsWith(relativePath, Constants.SLASH)) {
                relativePath = relativePath.substring(1);
            }
        }
        return relativePath;
    }

    public static String appendIfMissingDot(String fileExtension){
        if(StringUtils.isBlank(fileExtension)){
            fileExtension = StringUtils.defaultString(fileExtension);
        }else{
            fileExtension = Constants.DOT+fileExtension;
        }
        return fileExtension;
    }
}
