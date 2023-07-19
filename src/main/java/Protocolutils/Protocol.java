package Protocolutils;

import log.LogPrint;

import java.util.HashMap;

public class Protocol {

   static HashMap<Integer,String > hm = new HashMap<>();
   static{
        hm.put(501,"key�Ҳ���");
        hm.put(200,"OK");
        hm.put(502,"value�Ҳ���");
        hm.put(505,"�����������쳣");
        hm.put(404,"�ͻ��˳����쳣");
        hm.put(401,"�����������");
    }
    //    protocolЭ��ľ��壺
//    message             code
//    ����ɹ��ͷ�������     ״̬��200
//    key�Ҳ���            ״̬��501
//    value�Ҳ���          ״̬��502
//    �����������쳣        ״̬��505
//    �ͻ��˳����쳣        ״̬��404
//    �����������          ״̬��401




//    ������,�ͻ��˼��ܵĸ�ʽ
//    get /users protocol
//    Content-type: text/string
//    Accept-Language ��en
//    data
//
//
//    ��Ӧ���ģ�����˼��ܵĸ�ʽ
//    protocol  200
//    Content-type: text/string
//    Content-length:19
//    message


    //�ͻ�������ת���������ĵķ���
    public  String encodeClient(String data)
    {
        String request = "get /users protocol\n" +
                "Content-type:text/string\n" +
                "Accept-Language:en\n"+
                "data:";
        return  request+data;
}
//    �����������ת�������ݵķ���
    public  String decodeServer(String request){
        String[] split = request.split("data:");
        String data = null;
        try {
            data = split[1];
        } catch (Exception e) {
            LogPrint.logger.error("Э����ֽ�������");
        }
        return data;
    }
//���������ת����Ӧ���ĵķ���
    public  String encodeServer(String data, String codeStr){
        int code = Integer.parseInt(codeStr);
        String message = hm.get(code);
        if(message == null) {
            message = "δ�����״̬��";
        }
        String dataLength = "0";
        if(data!=null){
           dataLength = data.length()+"";
        }
        String reply = "protocol "+codeStr+" \n"+"Content-type:text/string\n" +
                "Content-length:"+dataLength+'\n'+"message:"+message+"data:"+data;
        return reply;
    }
//    //�ͻ��˴�����Ӧ���ĵķ���
    public  String decodeClient(String reply) {
        String codeStr = "";
        String message = "";
        String data  = "";
        try {
            codeStr = reply.split(" ")[1];
            message = reply.split("message:")[1].split("data:")[0];
            data = reply.split("data:")[1];

        } catch (Exception e) {
            LogPrint.logger.error("Э����ֽ�������");
        }
        if(!data.equals("")) {
            data = "\n"+data;
        }
        if(codeStr.equals("")&&data.equals("")&&message.equals("")){
            return "�����ȡʧ�ܣ��ͻ����յ�����Ϣ";
        }
        return codeStr+" "+message+" "+data;
    }


}
