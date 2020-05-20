package ThriftTest.service;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import ThriftTest.service.server.HelloWorld;

/**
 * �����

 */

 
public class HelloWorldServiceServer {
 
    public static void main(String[] args) throws TTransportException {
        System.out.println("server started ");
 
        // ����������
        TProcessor tProcessor = new HelloWorld.Processor<HelloWorld.Iface>(new HelloWorldServiceImpl());
 
        // ���÷���˿�Ϊ 8080
        TServerSocket serverSocket = new TServerSocket(8080);
 
        // �򵥵ĵ��̷߳���ģ��
        TServer.Args tArgs = new TServer.Args(serverSocket);
        tArgs.processor(tProcessor);
        // ����Э�鹤��Ϊ TBinaryProtocol.Factory
        tArgs.protocolFactory(new TBinaryProtocol.Factory());
        TServer server = new TSimpleServer(tArgs);
        // ��������
        server.serve();
    }
 
}