package Io;

import Time.LogPrint;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;



public class properties {
//    ר������ȡ�����ļ�
    public static String property(String key )  {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("..\\Test444\\src\\main\\resources\\config.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            LogPrint.logger.error("���������ļ��쳣",e);
        }
        return properties.getProperty(key);
    }
}
