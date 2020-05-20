package ThriftTest.service;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import ThriftTest.service.server.HelloWorld;

/**
 * �ͻ���
 *
 * @author tang
 */
public class HelloWorldClient {
 
    public static void main(String[] args) {
        System.out.println("client started....");
        TTransport transport = null;
        try {
            // ���õ��õķ����ַΪ���أ��˿�Ϊ8080,��ʱ����Ϊ30��
            transport = new TSocket("localhost", 8080, 30000);
            // Э��Ҫ�ͷ����һ��
            TProtocol protocol = new TBinaryProtocol(transport);
            HelloWorld.Client client = new HelloWorld.Client(protocol);
            transport.open();
            // ���ýӿڷ���
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