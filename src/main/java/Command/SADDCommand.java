package Command;

import HashMapControl.HashsetMap;
import Io.MultiWriteHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SADDCommand implements Command{
    private List<String> setArgs;

    public SADDCommand() {
    }

    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }
    public SADDCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }
    @Override
    public void execute() {
        System.out.println("��ʱ���е���sadd����");
        if(setArgs.size()==0){
            MultiWriteHandler.setClient("������Ҫ��������");
            return;
        }
        HashMap<String, HashSet<String>> hms = HashsetMap.getSetMap();
        String key = setArgs.get(0);
//        �и�˼·������ԭ���Ĳ�����������ȥ����һ������Ϳ�����ʣ����Ϊ����
         setArgs.remove(0);
        HashSet<String> hs = new HashSet<String>(setArgs);
        hms.put(key, hs);
        HashsetMap.setHms(hms);
        MultiWriteHandler.setClient("1");
    }
}
