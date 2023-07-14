package Command;
import HashMapControl.SHHashMap;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;
import java.util.HashMap;
import java.util.List;
public class HDELCommand implements Command {
    //    ɾ���ı���������key��value����null
    private List<String> setArgs;
    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    public HDELCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    public HDELCommand() {
    }

    @Override
    public void execute() {
        System.out.println("��ʱ���е���del����");
        HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
        if(setArgs.size()<1){
            MultiWriteHandler.setClient("������Ҫһ������");
            return;
        }
         else if(setArgs.size()==1){
            String key = setArgs.get(0);
//            ɾ������������ϣ
            HashMap<String, String> hm = hmh.get(key);
            hm.clear();
            hmh.put(key,hm);
            MultiWriteHandler.setClient("1");
        }
         else if(setArgs.size()==2){
            String key1 = setArgs.get(0);
            String key2 = setArgs.get(1);
//             ɾ�����ǹ�ϣ�����value
            HashMap<String, String> hm = hmh.get(key1);
            hm.put(key2,null);
            MultiWriteHandler.setClient("1");
        }
        SHHashMap.setHmh(hmh);
    }
}
