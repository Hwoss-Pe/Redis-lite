package Command;

import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import HashMapControl.SSHashMap;
import Io.MultiWriteHandler;
import log.AppendFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class FLUSHDBCommand implements Command{
    @Override
    public void setArgs(List<String> list) {

    }

    @Override
    public void execute() {
//    清空缓存就是重新读取然后覆盖类的数据，直接读回上一次的数据,这里也要进行清除日志
        try {
            HashMap<String, String> hm = SSHashMap.input();
            HashMap<String, LinkedList<String>> hml = SLHashMap.input();
            HashMap<String, HashMap<String, String>> hmh = SHHashMap.input();
            SSHashMap.setHm(hm);
            SLHashMap.setHml(hml);
            SHHashMap.setHmh(hmh);
            AppendFile.clearLogFile();
            MultiWriteHandler.setClient("清除缓存成功");
//                清空临时追加日志文件
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
