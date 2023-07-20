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
        System.out.println("此时运行的是ddl命令");
//       一样的封装调用
        String s ;
        Protocol protocol = new Protocol();
        if(setArgs.size()==0){
//            就是参数不够报错
            s = protocol.encodeServer("", "401");
        }else {
            String key = setArgs.get(0);
            delayHash DelayHash = new delayHash();
            long remainingTime = DelayHash.getKeyTtl(key);
//            返回的东西就直接data就是所需要的时间秒
            s = protocol.encodeServer(remainingTime + "", "200");
        }
        MultiWriteHandler.setClient(s);
    }
}
