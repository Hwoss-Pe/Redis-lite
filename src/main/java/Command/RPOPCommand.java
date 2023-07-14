package Command;

import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;

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
        if(setArgs.size()<1) {
            MultiWriteHandler.setClient("至少需要一个参数");
            return;
        }
        System.out.println("此时运行的是rpop命令");
        HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
        String key = setArgs.get(0);
        LinkedList<String> linkedList = hml.get(key);
        if(linkedList==null||linkedList.size()==0){
            System.out.println("key错误或者该集合为空");
            System.out.println("0表示删除失败");
        }else{
            String removing = linkedList.removeLast();
            hml.put(key,linkedList);
            SLHashMap.setHml(hml);
            MultiWriteHandler.setClient(removing);
            System.out.println("1表示删除成功");
        }
    }
}
