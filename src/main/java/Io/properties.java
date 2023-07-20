package Io;

import log.LogPrint;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;



public class properties {
    //    专门来读取配置文件
    public static String property(String key )  {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("..\\Test444\\src\\main\\resources\\config.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            LogPrint.logger.error("读入配置文件异常",e);
        }
        return properties.getProperty(key);
    }
}
