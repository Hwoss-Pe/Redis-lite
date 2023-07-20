package Command;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RPUSHCommand implements Command{
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public RPUSHCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public RPUSHCommand() {
    }

    @Override
    public void execute() {
        //套路判断参数就是加密返回的数据后进行发送给客户端
        Protocol protocol = new Protocol();
        String s ;
        System.out.println("此时运行的是rpush命令");
        if(setArgs.size()<=1) {
            s = protocol.encodeServer("", "401");
        }else {
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            String key = setArgs.get(0);
            String value = setArgs.get(1);
            LinkedList<String> linkedList = hml.get(key);
            if(linkedList==null){
                linkedList = new LinkedList<>();
                linkedList.add(value);
            }else{
                linkedList.addLast(value);
            }
            hml.put(key,linkedList);
            SLHashMap.setHml(hml);
            s = protocol.encodeServer("", "200");
//            思路和lpush一样，调用现成的方法
        }
        MultiWriteHandler.setClient(s);
    }
}

