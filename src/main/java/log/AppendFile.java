package log;

import java.io.*;
import Command.CommandExtract;
import Io.properties;

public class AppendFile {
//
//    设计思路就是commands代表缓存，文件代表实际存储，当用保存的方法就时候就再把commands直接写入文件
//  清空缓存就是清除commands=""
//    每次手动保存和自动保存和后台保存的时候就把临时日志清空，因为数据已经持久化了
//            然后输入的命令只要符合就追加写进入
//    那么到时就会有一堆文件采用*来隔开，每次*代表一个命令，当然也可以更加复杂的加密
//    恢复的时候就从文件读取，然后用*分开每句命令最后挨个执行，清空也要清空日志

    static   String  LOGFILE;
//    默认从配置文件中读取追加日志的路径
    static {
        LOGFILE = properties.property("appendFile");
    }
    static String commands = "";
    // 清空日志文件
    public static void clearLogFile() {
        commands="";
    }

    // 将命令追加到日志文件中
    public static void saveCommand() {
        try (FileWriter writer = new FileWriter(LOGFILE, true)) {
            writer.write(commands);
            clearLogFile();
        } catch (IOException e) {
            LogPrint.logger.error("手动save保存命令出错",e);
        }
    }

    // 从日志文件中读取命令并且执行
    public static void loadCommands() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGFILE))) {
            String line;
            CommandExtract extract = new CommandExtract();
            while ((line = reader.readLine()) != null) {
                String[] commands = line.split("\\*");
                for (String command : commands) {
                    if(!command.equals("save"))
                        extract.Command(command);
                }
                System.out.println("执行恢复成功");
            }
            clearLogFile();
        } catch (IOException e) {
            LogPrint.logger.error("读取缓存数据出错",e);
        }
    }
    public  static  void tempFile(String command){
//        这里就存取那些缓存的命令
        commands = commands+command+"*";
    }
}
