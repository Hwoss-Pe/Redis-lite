package Command;

import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LPUSHCommand implements Command{
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public LPUSHCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public LPUSHCommand() {
    }

    @Override
    public void execute() {
        //套路判断参数就是加密返回的数据后进行发送给客户端
        Protocol protocol = new Protocol();
        String s ;
        System.out.println("此时运行的是lpush命令");
        if(setArgs.size()<=1) {
            s = protocol.encodeServer("", "401");
        }else {
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            String key = setArgs.get(0);
            String value = setArgs.get(1);
            LinkedList<String> linkedList = hml.get(key);
            if(linkedList==null){
//                对添加的集合进行一个判断是否存在
                linkedList = new LinkedList<>();
                linkedList.add(value);
            }else{
                linkedList.addFirst(value);
            }
            hml.put(key,linkedList);
//            每次取出来更新后还要去设置回去类里面的数据更新
            SLHashMap.setHml(hml);
            s = protocol.encodeServer("", "200");
        }
        MultiWriteHandler.setClient(s);
    }
}
