package ThriftTest.service;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import ThriftTest.service.server.HelloWorld;

/**
 * 客户端
 *
 * @author tang
 */
public class HelloWorldClient {
 
    public static void main(String[] args) {
        System.out.println("client started....");
        TTransport transport = null;
        try {
            // 设置调用的服务地址为本地，端口为8080,超时设置为30秒
            transport = new TSocket("localhost", 8080, 30000);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            HelloWorld.Client client = new HelloWorld.Client(protocol);
            transport.open();
            // 调用接口方法
            String result = client.sendString("Hello World!");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }
 
}