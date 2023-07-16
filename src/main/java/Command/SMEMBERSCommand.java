package Command;

import HashMapControl.HashsetMap;
import Io.MultiWriteHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SMEMBERSCommand implements Command{
    private List<String> setArgs;

    public SMEMBERSCommand() {
    }

    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }
    public SMEMBERSCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }
    @Override
    public void execute() {
        System.out.println("��ʱ���е���smembers����");
        if(setArgs.size()==0){
            MultiWriteHandler.setClient("������Ҫһ������");
            return;
        }
        HashMap<String, HashSet<String>> hms = HashsetMap.getSetMap();
        String key = setArgs.get(0);
        if(hms.containsKey(key)){
            String result = "";
            HashSet<String> hs = hms.get(key);
            for (String s : hs) {
                 result = result+" "+s;
            }
            MultiWriteHandler.setClient(result);

        }else{
            MultiWriteHandler.setClient("������key");
        }

    }
}
