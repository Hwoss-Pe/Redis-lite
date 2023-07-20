package Command;

import java.util.HashMap;
import java.util.List;

public interface Command {
    void setArgs(List<String> list);
    //    保存输入的参数
    void execute();
//    执行相对的命令的方法
}
