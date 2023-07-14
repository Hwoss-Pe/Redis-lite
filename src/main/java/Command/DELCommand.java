package Command;
        import Io.MultiWriteHandler;
        import HashMapControl.SSHashMap;
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
        if(setArgs.size()<1){
            MultiWriteHandler.setClient("至少需要两个参数");
            return;
        }
        String key = setArgs.get(0);
        System.out.println("此时运行的是del命令");
        HashMap<String, String> hm = SSHashMap.getSSHashMap();
        hm.put(key, null);
        MultiWriteHandler.setClient("删除该key的value成功");
        SSHashMap.setHm(hm);
    }
}
