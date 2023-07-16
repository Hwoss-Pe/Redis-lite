package Io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class properties {
//    专门来读取配置文件
    public static String property(String key ) throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("..\\Test444\\config.properties");
        properties.load(fileInputStream);
        fileInputStream.close();
        return properties.getProperty(key);
    }
}
