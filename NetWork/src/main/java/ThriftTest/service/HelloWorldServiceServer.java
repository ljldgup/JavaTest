package ThriftTest.service;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import ThriftTest.service.server.HelloWorld;

/**
 * 服务端

 */

 
public class HelloWorldServiceServer {
 
    public static void main(String[] args) throws TTransportException {
        System.out.println("server started ");
 
        // 关联处理器
        TProcessor tProcessor = new HelloWorld.Processor<HelloWorld.Iface>(new HelloWorldServiceImpl());
 
        // 设置服务端口为 8080
        TServerSocket serverSocket = new TServerSocket(8080);
 
        // 简单的单线程服务模型
        TServer.Args tArgs = new TServer.Args(serverSocket);
        tArgs.processor(tProcessor);
        // 设置协议工厂为 TBinaryProtocol.Factory
        tArgs.protocolFactory(new TBinaryProtocol.Factory());
        TServer server = new TSimpleServer(tArgs);
        // 启动服务
        server.serve();
    }
 
}