package Command;


import HashMapControl.HashsetMap;
import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import HashMapControl.SSHashMap;
import Io.MultiWriteHandler;
import Io.OutputCheck;
import log.AppendFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SAVECommand implements Command {
//�ֶ�ǿ�ƽ������������������ִ��д��
    @Override
    public void setArgs(List<String> list) {

    }

    public SAVECommand() {
    }

    @Override
    public void execute() {
        System.out.println("��ʱ���е���save����");
        OutputCheck.output();
        MultiWriteHandler.setClient("����ɹ�");
    }
}
