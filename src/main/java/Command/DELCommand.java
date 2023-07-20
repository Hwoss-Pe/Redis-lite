package Command;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.List;
public class DELCommand implements Command {
    //    删除的本质上设置key的value等于null
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public DELCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public DELCommand() {
    }

    @Override
    public void execute() {
        System.out.println("此时运行的是del命令");
        String s ;
        String value;
        Protocol protocol = new Protocol();
        if(setArgs.size()<1){
//            参数处理判断
            s = protocol.encodeServer("", "401");
        }else {
            String key = setArgs.get(0);
//            思路就是获取看看key存在，如何再去直接设置对应的key是null就可以
            HashMap<String, String> hm = SSHashMap.getSSHashMap();
            if(hm.containsKey(key)){
                value = hm.get(key);
                hm.put(key, null);
                SSHashMap.setHm(hm);
                s = protocol.encodeServer(value, "200");
            }else {
                s = protocol.encodeServer("", "401");
            }
        }
//        发送的数据是加密的
        MultiWriteHandler.setClient(s);
    }
}
