package Command;

import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RPOPCommand implements Command{
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public RPOPCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public RPOPCommand() {
    }

    @Override
    public void execute() {
        //套路判断参数就是加密返回的数据后进行发送给客户端
        Protocol protocol = new Protocol();
        String s ;
        System.out.println("此时运行的是rpop命令");
        if(setArgs.size()<1) {
            s = protocol.encodeServer("", "401");
        }else {
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            String key = setArgs.get(0);
            LinkedList<String> linkedList = hml.get(key);
            if(linkedList==null||linkedList.size()==0){
//                对删除的集合进行判断
                s = protocol.encodeServer("key错误或者该集合为空", "404");
            }else{
                String removing = linkedList.removeLast();
                hml.put(key,linkedList);
                SLHashMap.setHml(hml);
                s = protocol.encodeServer(removing, "200");
            }
        }
        MultiWriteHandler.setClient(s);
    }
}
