  package Command;
import HashMapControl.SLHashMap;
import Io.MultiWriteHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

    public class RPUSHCommand implements Command{
        private List<String> setArgs;
        @Override
        public void setArgs(List<String> list) {
            this.setArgs = list;
        }

        public RPUSHCommand(List<String> setArgs) {
            this.setArgs = setArgs;
        }

        public RPUSHCommand() {
        }

        @Override
        public void execute() {
            if(setArgs.size()<=1){
                MultiWriteHandler.setClient("至少需要两个参数");
                System.out.println("此时运行的是rpush命令");
                HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
                String key = setArgs.get(0);
                String value = setArgs.get(1);
                LinkedList<String> linkedList = hml.get(key);
                if(linkedList==null){
                    linkedList = new LinkedList<>();
                    linkedList.add(value);
                }else{
                    linkedList.addLast(value);
                }
                hml.put(key,linkedList);
                SLHashMap.setHml(hml);
            }
        }
    }

