package Command;

import Io.MultiWriteHandler;
import Protocolutils.Protocol;
import Time.delayHash;

import java.util.List;

public class DDLCommand implements Command{
    private List<String> setArgs;

    public DDLCommand() {
    }

    public DDLCommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    @Override
    public void setArgs(List<String> list) {
        this.setArgs = list;
    }

    @Override
    public void execute() {
        System.out.println("��ʱ���е���ddl����");
        String s ;
        Protocol protocol = new Protocol();
        if(setArgs.size()==0){
            s = protocol.encodeServer("", "401");
        }else {
            String key = setArgs.get(0);
            delayHash DelayHash = new delayHash();
            long remainingTime = DelayHash.getKeyTtl(key);
            s = protocol.encodeServer(remainingTime + "", "200");
        }
        MultiWriteHandler.setClient(s);
    }
}
