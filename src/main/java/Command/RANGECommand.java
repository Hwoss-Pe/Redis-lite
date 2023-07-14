package Command;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RANGECommand implements Command{
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public RANGECommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public RANGECommand() {
    }

    @Override
    public void execute() {
        System.out.println("此时运行的是range命令");
        if(setArgs.size()<=2) {
            MultiWriteHandler.setClient("至少需要三个参数");
            return ;
        }
        HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
        String key = setArgs.get(0);
        int start = Integer.parseInt(setArgs.get(1));
        int end = Integer.parseInt(setArgs.get(2));
        int length = end-start ;
        LinkedList<String> linkedList = hml.get(key);
        if(linkedList==null) {
            MultiWriteHandler.setClient("没有链表");
        }
        if (start<0||end<0||length > linkedList.size()||length<0||end > linkedList.size()-1) {
            MultiWriteHandler.setClient("非法输入，索引不存在或者越界");//考虑索引非法
        } else {
            for (int i = start; i <= end; i++) {
                MultiWriteHandler.setClient(linkedList.get(i)+" ");
                //去获取集合的对应值，然后把需要输出的存入新集合中进行返回
            }
        }
    }
}

