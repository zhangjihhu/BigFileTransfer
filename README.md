# BigFileTransfer
利用Netty进行大文件传输(zero-copy)

本例子是利用Netty在client和server之间传输超大文件，基于自定义的编解码器，其中数据包由指令(数据包类型)，数据长度，数据组成

|指令|数据长度|数据|
|:----:|:-------:|:----:|
|1字节|4字节|N字节|

>该项目操作流程：
1. 开启服务端(FileUploadServer)
2. 开启客户端(FileUploadClient)，完成与服务端连接
3. 从客户端输入文件路径，完成从client->server的文件发送



有问题可以联系1325626881@qq.com交流学习
