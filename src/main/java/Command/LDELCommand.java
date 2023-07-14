package Command;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
public class LDELCommand implements Command {
    //    删除的本质上设置key的value等于null
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public LDELCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public LDELCommand() {
    }

    @Override
    public void execute() {
        if(setArgs.size()<1){
            MultiWriteHandler.setClient("至少需要一个参数");
            return;
        }
        String key = setArgs.get(0);
        System.out.println("此时运行的是ldel命令");
        HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
        if(hml.containsKey(key)){
            LinkedList<String> linkedList = hml.get(key);
            if(linkedList!=null){
                linkedList.clear();
                MultiWriteHandler.setClient("1");
            }else{
                MultiWriteHandler.setClient("0");
            }
        }else{
            MultiWriteHandler.setClient("0");
        }

        System.out.println("清空成功");
        SLHashMap.setHml(hml);
    }
}
