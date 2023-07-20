package Command;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;
import Protocolutils.Protocol;

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
        Protocol protocol = new Protocol();
        String s ;
        if(setArgs.size()<=2) {
            s = protocol.encodeServer("", "401");
        }else {
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            String key = setArgs.get(0);
            int start = Integer.parseInt(setArgs.get(1));
            int end = Integer.parseInt(setArgs.get(2));
            int length = end - start;
            LinkedList<String> linkedList = hml.get(key);
            if (linkedList == null) {
                s = protocol.encodeServer("没有链表", "404");
            }
            if (start < 0 || end < 0 || length > linkedList.size() || length < 0 || end > linkedList.size() - 1) {
                s = protocol.encodeServer("非法输入，索引不存在或者越界", "404");
            } else {
                StringBuilder str = new StringBuilder();
                for (int i = start; i <= end; i++) {
                    str.append(linkedList.get(i)).append(" ");
                    //去获取集合的对应值，然后把需要输出的存入新集合中进行返回
                }
                s = protocol.encodeServer(str.toString(), "200");
            }
        }
        MultiWriteHandler.setClient(s);
    }
}

