package Command;

import Io.MultiWriteHandler;
import log.LogPrint;
import log.AppendFile;

import java.util.ArrayList;
import java.util.List;

public class CommandExtract {
    //    执行把输入的东西解析
    public Command Extract(String userInput)  {
//        注意编码的问题，如果编码出错这里也会失效的
        String[] split = userInput.split("说：");
        Command command = null;
        try {
            command = Command(split[1]);
        } catch (Exception e) {
            LogPrint.logger.error("命令读入出现错误",e);
        }
        return command;
//        注意返回的指令对象可能不存在，也就是错误的指令的时候，如果不存在就直接输出日志
    }

    public Command Command (String commandStr) {
        List<String> argList = new ArrayList<>();
        String[] s = commandStr.split(" ");
//        这里就是获取命令比如set
        String newString = s[0].trim();
//        这个是获取参数
        String[] strings =commandStr.split("\\s+");
//        这里从一开始保证命令不进去
        for (int i = 1; i < strings.length; i++) {
            argList.add(strings[i]);
        }
        newString = newString.toUpperCase();
        newString = "Command."+newString+"Command";
        Class<?> aClass = null;
        try {
            aClass = Class.forName(newString);
            if(Command.class.isAssignableFrom(aClass)){
                try {
//        如果是有效的命令，那么解析来就是创建对象并且传入参数，并且写入追加文件
                    AppendFile.tempFile(commandStr);
                    //命令合法就把命令存到追加文件
                    Command command = (Command) aClass.getDeclaredConstructor().newInstance();
//                    设置对应的参数和命令的执行
                    command.setArgs(argList);
                    command.execute();
                    return command;
                } catch (Exception e) {
                    LogPrint.logger.error("命令执行失败",e);
                }
            }
        } catch (ClassNotFoundException e) {
            MultiWriteHandler.setClient("输入的命令错误");
//            这里输出日志，而是返回给就客户端说是输入问题，程序正常运行
        }
        return null;
    }
}
