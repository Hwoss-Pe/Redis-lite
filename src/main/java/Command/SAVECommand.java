package Command;


import HashMapControl.HashsetMap;
import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import HashMapControl.SSHashMap;
import Io.MultiWriteHandler;
import Io.OutputCheck;
import log.AppendFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SAVECommand implements Command {
//手动强制将缓存在类里面的数据执行写入
    @Override
    public void setArgs(List<String> list) {

    }

    public SAVECommand() {
    }

    @Override
    public void execute() {
        System.out.println("此时运行的是save命令");
        OutputCheck.output();
        MultiWriteHandler.setClient("保存成功");
    }
}
