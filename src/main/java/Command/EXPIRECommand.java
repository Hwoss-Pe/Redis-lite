package Command;

import Io.MultiWriteHandler;
import Protocolutils.Protocol;
import Time.delayHash;

import java.util.List;

public class EXPIRECommand implements Command{
    private List<String> setArgs;

    public EXPIRECommand() {
    }

    public EXPIRECommand(List<String> setArgs) {
        this.setArgs = setArgs;
    }

    @Override
    public void setArgs(List<String> list ) {
        this.setArgs = list;
    }

    @Override
    public void execute() {
        System.out.println("此时运行的是expire命令");
        String s ;
        Protocol protocol = new Protocol();
        if(setArgs.size()<=1){
//            参数出处理
            s = protocol.encodeServer("", "401");
        }else{
            delayHash delayHash = new delayHash();
            // 设置键的过期时间
            String key = setArgs.get(0);
            String expire = setArgs.get(1);
//            设置的方法
            delayHash.setKeyExpiration(key, Long.parseLong(expire));
            s = protocol.encodeServer("", "200");
        }
        MultiWriteHandler.setClient(s);

    }
}
