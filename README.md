

# Readme

## 功能介绍

这是一个模仿redis数据库的实现，能够进行客户端和服务端进行交互，并且读取命令支持持久化和配置文件

可以提供API包，把当前项目打包成jar包后在命令环境里面运行就可以

支持的命令：

set， get ，del，lpush，rpush，lget，ldel，hget，hset，hdel，ddl（ttl），expire，len，save，bgsave

flushdb , sadd , smembers , sismember , srem

采用多线程主从Reactor模型，NIO非阻塞通道连接客户端和服务端，可以作为一个基本使用，需要自己封装Handler

## 运行和开发环境

采用的是支持maven框架，用idea集成环境。

项目字节码版本1.8

依赖包括

![]([https://pic.imgdb.cn/item/64bbebd71ddac507ccbf3002.jpg](https://pic.imgdb.cn/item/64bbf4da1ddac507cce1393d.jpg))

包括slf4j，logback集成，还有junit测试模块可以省略

maven设置的版本采用和项目一样，而不是在配置环境中采用原本的java1.2

![](https://pic.imgdb.cn/item/64bbec541ddac507ccc10392.jpg)

这里的报错是正常的，保证Maven能够install

项目编码为UTF-8

## 快速开始

![](https://pic.imgdb.cn/item/64bbed0f1ddac507ccc3c6a7.jpg)

先运行maven的install，然后需要先运行ProgramServer再运行ProgramClient就可以，如果需要打开多个客户端就在Client类里面设置允许多个实例同时运行

config.properties是关于配置文件的，里面可以进行设置也进行了说明

Data文件表示本地持久化文件，并没有做清空操作，当然也可以删除运行后会自动创建

appendFile是追加日志文件储存命令的

logback.xml是关于日志框架将日志写入和调整日志处理等级的



## 运行流程



服务端开启的界面：

![](https://pic.imgdb.cn/item/64bbf0041ddac507ccce495b.jpg)

10s后会进行自动保存每过1分钟就保存数据，在服务端run方法里面可以设置

客户端开启的界面：![](https://pic.imgdb.cn/item/64bbf0281ddac507cccec347.jpg)

直接输入命令就可以,这里会出现端口的改变，到时输出的就是配置文件里面的端口，第一个是用来记录客户端ip的

![](https://pic.imgdb.cn/item/64bbf0961ddac507ccd03adf.jpg)

返回的格式是

状态码 回复 

数据

![](https://pic.imgdb.cn/item/64bbf0e91ddac507ccd1acae.jpg)

命令是可以正确运行的，输入出错会按照提示参数错误，这里面同时多个命令只会执行第一个

![](https://pic.imgdb.cn/item/64bbf1be1ddac507ccd4cc40.jpg)

这里如果命令错误会有反馈

```
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
```



这是报文格式，可以在Protovol里面进行调试查看，遵循此加密格式

```
    //    protocol协议的具体：
//    message             code
//    请求成功就返回数据     状态码200
//    key找不到            状态码501
//    value找不到          状态码502
//    服务器出现异常        状态码505
//    客户端出现异常        状态码404
//    请求参数有误          状态码401
```

## 关于客户端读取信息乱码

如果在接收的返回信息出现乱码，在MultiWriteHandler类的64行中更改传输给客户端的编码格式

![](https://pic.imgdb.cn/item/64bbf23c1ddac507ccd6ca79.jpg)

这里相对注释代码就可以了

## 运行思路

主体流程就是获取命令后通过反射到对应的类里面然后执行方法，然后进行数据临时存储在类里面，然后把类的数据到时一起放在数据持久化里面，这里的选择通过配置文件修改，默认是在二进制文件持久化方法执行，然后一些异步保存机制都在里面可以去设置。

