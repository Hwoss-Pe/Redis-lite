package Command;

import HashMapControl.HashsetMap;
import Io.MultiWriteHandler;

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
        System.out.println("��ʱ���е���sismember����");
        if(setArgs.size()<2){
            MultiWriteHandler.setClient("������Ҫ��������");
            return;
        }
        HashMap<String, HashSet<String>> hms = HashsetMap.getSetMap();
        String key = setArgs.get(0);
        String member =  setArgs.get(1);
        if(hms.containsKey(key)){
            HashSet<String> hs = hms.get(key);
            if(hs.contains(member)){
                MultiWriteHandler.setClient("�������иó�Ա");
            }else{
                MultiWriteHandler.setClient("������û�иó�Ա");
            }
        }else{
            MultiWriteHandler.setClient("������key");
        }

    }
}
