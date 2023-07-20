package Command;

import HashMapControl.HashsetMap;
import Io.MultiWriteHandler;
import Protocolutils.Protocol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SISMEMBERCommand implements Command{
    private List<String> setArgs;

    public SISMEMBERCommand() {
    }

    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }
    public SISMEMBERCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }
    @Override
    public void execute() {
        Protocol protocol = new Protocol();
        String s ;
        System.out.println("此时运行的是sismember命令");
        if(setArgs.size()<2){
            s = protocol.encodeServer("", "401");
        }else{
            HashMap<String, HashSet<String>> hms = HashsetMap.getSetMap();
            String key = setArgs.get(0);
            String member =  setArgs.get(1);
            if(hms.containsKey(key)){
                HashSet<String> hs = hms.get(key);
                if(hs.contains(member)){
                    s = protocol.encodeServer("", "200");
                }else{
                    s = protocol.encodeServer("集合中没有该成员", "404");
                }
            }else{
                s = protocol.encodeServer("", "501");
            }
        }
        MultiWriteHandler.setClient(s);
    }
}
