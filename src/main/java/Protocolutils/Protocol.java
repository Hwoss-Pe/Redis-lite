package Protocolutils;

import log.LogPrint;

import java.util.HashMap;

public class Protocol {

   static HashMap<Integer,String > hm = new HashMap<>();
   static{
        hm.put(501,"key找不到");
        hm.put(200,"OK");
        hm.put(502,"value找不到");
        hm.put(505,"服务器出现异常");
        hm.put(404,"客户端出现异常");
        hm.put(401,"请求参数有误");
    }
    //    protocol协议的具体：
//    message             code
//    请求成功就返回数据     状态码200
//    key找不到            状态码501
//    value找不到          状态码502
//    服务器出现异常        状态码505
//    客户端出现异常        状态码404
//    请求参数有误          状态码401




//    请求报文,客户端加密的格式
//    get /users protocol
//    Content-type: text/string
//    Accept-Language ：en
//    data
//
//
//    响应报文，服务端加密的格式
//    protocol  200
//    Content-type: text/string
//    Content-length:19
//    message


    //客户端数据转换成请求报文的方法
    public  String encodeClient(String data)
    {
        String request = "get /users protocol\n" +
                "Content-type:text/string\n" +
                "Accept-Language:en\n"+
                "data:";
        return  request+data;
}
//    服务端请求报文转换成数据的方法
    public  String decodeServer(String request){
        String[] split = request.split("data:");
        String data = null;
        try {
            data = split[1];
        } catch (Exception e) {
            LogPrint.logger.error("协议出现解析错误");
        }
        return data;
    }
//服务端数据转换响应报文的方法
    public  String encodeServer(String data, String codeStr){
        int code = Integer.parseInt(codeStr);
        String message = hm.get(code);
        if(message == null) {
            message = "未处理的状态码";
        }
        String dataLength = "0";
        if(data!=null){
           dataLength = data.length()+"";
        }
        String reply = "protocol "+codeStr+" \n"+"Content-type:text/string\n" +
                "Content-length:"+dataLength+'\n'+"message:"+message+"data:"+data;
        return reply;
    }
//    //客户端处理响应报文的方法
    public  String decodeClient(String reply) {
        String codeStr = "";
        String message = "";
        String data  = "";
        try {
            codeStr = reply.split(" ")[1];
            message = reply.split("message:")[1].split("data:")[0];
            data = reply.split("data:")[1];

        } catch (Exception e) {
            LogPrint.logger.error("协议出现解析错误");
        }
        if(!data.equals("")) {
            data = "\n"+data;
        }
        if(codeStr.equals("")&&data.equals("")&&message.equals("")){
            return "命令读取失败，客户端收到此信息";
        }
        return codeStr+" "+message+" "+data;
    }


}
