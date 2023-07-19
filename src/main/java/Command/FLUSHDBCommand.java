package Command;

import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import HashMapControl.SSHashMap;
import Io.MultiWriteHandler;
import Protocolutils.Protocol;
import log.AppendFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class FLUSHDBCommand implements Command{
    @Override
    public void setArgs(List<String> list) {

    }

    @Override
    public void execute() {
//    ��ջ���������¶�ȡȻ�󸲸�������ݣ�ֱ�Ӷ�����һ�ε�����,����ҲҪ���������־
        String s ;
        Protocol protocol = new Protocol();
        HashMap<String, String> hm = SSHashMap.input();
        HashMap<String, LinkedList<String>> hml = SLHashMap.input();
        HashMap<String, HashMap<String, String>> hmh = SHHashMap.input();
        SSHashMap.setHm(hm);
        SLHashMap.setHml(hml);
        SHHashMap.setHmh(hmh);
        AppendFile.clearLogFile();
        s = protocol.encodeServer("�������ɹ�", "200");
        MultiWriteHandler.setClient(s);
//                �����ʱ׷����־�ļ�
    }
}
