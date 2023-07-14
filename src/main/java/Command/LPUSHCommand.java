package Command;

import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;

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
        if(setArgs.size()<=1) {
            MultiWriteHandler.setClient("至少需要两个参数");
            return;
        }
            System.out.println("此时运行的是lpush命令");
            HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
            String key = setArgs.get(0);
            String value = setArgs.get(1);
            LinkedList<String> linkedList = hml.get(key);
            if(linkedList==null){
                linkedList = new LinkedList<>();
                linkedList.add(value);
            }else{
                linkedList.addFirst(value);
            }
            hml.put(key,linkedList);
            SLHashMap.setHml(hml);
            MultiWriteHandler.setClient("1");
    }
}
