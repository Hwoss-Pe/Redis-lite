package Command;

import HashMapControl.HashsetMap;
import Io.MultiWriteHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SREMCommand implements Command{
    private List<String> setArgs;

    public SREMCommand() {
    }

    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }
    public SREMCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }
    @Override
    public void execute() {
        System.out.println("此时运行的是srem命令");
        if(setArgs.size()<2){
            MultiWriteHandler.setClient("至少需要两个参数");
            return;
        }
        HashMap<String, HashSet<String>> hms = HashsetMap.getSetMap();
        String key = setArgs.get(0);
        String member = setArgs.get(1);
        if(hms.containsKey(key)){
            HashSet<String> hs = hms.get(key);
            if(hs.contains(member)){
                hs.remove(member);
                hms.put(key,hs);
                HashsetMap.setHms(hms);
                MultiWriteHandler.setClient("1");
            }else{
                MultiWriteHandler.setClient("0");
            }
        }else{
            MultiWriteHandler.setClient("0");
        }
    }
}
