package Command;

import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class  LPOPCommand implements Command{
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public LPOPCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public LPOPCommand() {
    }

    @Override
    public void execute() {
        //套路判断参数就是加密返回的数据后进行发送给客户端
        System.out.println("此时运行的是lpop命令");
        Protocol protocol = new Protocol();
        String s ;
        if(setArgs.size()<1) {
//            参数处理
            s = protocol.encodeServer("", "401");
        }else {
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            String key = setArgs.get(0);
            LinkedList<String> linkedList = hml.get(key);
            if(linkedList==null||linkedList.size()==0){
                s = protocol.encodeServer("key错误或者该集合为空", "404");
            }else{
                String removing = linkedList.removeFirst();
                hml.put(key,linkedList);
                SLHashMap.setHml(hml);
                s = protocol.encodeServer(removing, "200");
            }
        }
        MultiWriteHandler.setClient(s);

    }
}
