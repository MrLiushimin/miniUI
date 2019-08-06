package com.liushimin.miniUI.util;

//import cn.com.servyou.framework.exception.SystemException;
import org.apache.commons.io.IOUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZip 工具类
 *<p>Title:
 *<p>Description:
 *<p>@author szh
 *<p>创建时间：下午3:23:12
 */
public class GZipUtils {
//    private static final Logger LOGGER = LoggerFactory.getLogger(GZipUtils.class);
    
    private GZipUtils() {
    }

    /**
     * GZip 加压
     * @param str
     * @param encoding
     * @return
     */
    public static byte[] compress(String str, String encoding) {  
        if (str == null || str.length() == 0) {  
            return null;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        GZIPOutputStream gzip = null;  
        try {  
            gzip = new GZIPOutputStream(out);  
            gzip.write(str.getBytes(encoding));  
            gzip.close();
            return out.toByteArray();  
        } catch (IOException e) {
//            LOGGER.error("gzip compress error.", e);
//            throw new SystemException("Compress Error", e, SystemException.COMPRESS_EXCEPTION);
            throw new RuntimeException(e.getMessage());
        } finally{
            IOUtils.closeQuietly(gzip);
            IOUtils.closeQuietly(out);
        }
        
    } 
    
    /** 
     *  
     * @param bytes 
     * @param encoding 
     * @return 
     */  
    public static String uncompressToString(byte[] bytes, String encoding) {  
        if (bytes == null || bytes.length == 0) {  
            return null;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);  
        GZIPInputStream ungzip = null;
        try {  
            ungzip = new GZIPInputStream(in);  
            byte[] buffer = new byte[256];  
            int n;  
            while ((n = ungzip.read(buffer)) >= 0) {  
                out.write(buffer, 0, n);  
            }  
            return out.toString(encoding);  
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
//            LOGGER.error("gzip uncompress error.", e);
//            throw new SystemException("Uncompress Error", e, SystemException.COMPRESS_EXCEPTION);
        } finally{
            IOUtils.closeQuietly(ungzip);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }  
    
    /**
     * GZip解压
     * @param bytes
     * @return
     */
    public static byte[] uncompress(byte[] bytes) {  
        if (bytes == null || bytes.length == 0) {  
            return null;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);  
        GZIPInputStream ungzip = null;
        try {  
            ungzip = new GZIPInputStream(in);  
            byte[] buffer = new byte[256];  
            int n;  
            while ((n = ungzip.read(buffer)) >= 0) {  
                out.write(buffer, 0, n);  
            }  
            return out.toByteArray();  
        } catch (IOException e) {  
//            LOGGER.error("gzip uncompress error.", e);
//            throw new SystemException("Uncompress Error", e, SystemException.COMPRESS_EXCEPTION);
            throw new RuntimeException(e.getMessage());
        }finally{
            IOUtils.closeQuietly(ungzip);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }  
}
