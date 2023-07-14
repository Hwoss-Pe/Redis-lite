package Command;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;
import HashMapControl.SSHashMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
public class LDELCommand implements Command {
    //    ɾ���ı���������key��value����null
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
            MultiWriteHandler.setClient("������Ҫһ������");
            return;
        }
        String key = setArgs.get(0);
        System.out.println("��ʱ���е���ldel����");
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

        System.out.println("��ճɹ�");
        SLHashMap.setHml(hml);
    }
}
