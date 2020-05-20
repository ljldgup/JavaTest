package ThriftTest.service;

import org.apache.thrift.TException;

import ThriftTest.service.server.HelloWorld;


/**
 * �����ʵ��
 */
public class HelloWorldServiceImpl implements HelloWorld.Iface {
 
    @Override
    public String sendString(String para) throws TException {
        System.out.println("receive from client: " + para);
        String result = "server received successfully";
        return result;
    }
 
}