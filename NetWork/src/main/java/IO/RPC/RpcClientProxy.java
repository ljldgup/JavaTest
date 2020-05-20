package IO.RPC;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RpcClientProxy<T> implements InvocationHandler {

    private  Class<T> serviceInterface;
    private InetSocketAddress addr;

    public RpcClientProxy(Class<T> serviceInterface, String ip,String port) {
        this.serviceInterface = serviceInterface;
        this.addr = new InetSocketAddress(ip, Integer.parseInt ( port ));
    }

    @SuppressWarnings("unchecked")
	public T getClientIntance(){
        return (T) Proxy.newProxyInstance (serviceInterface.getClassLoader(),new Class<?>[]{serviceInterface},this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Socket socket = null;
        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        try {
            // 2.����Socket�ͻ��ˣ�����ָ����ַ����Զ�̷����ṩ��
            socket = new Socket();
            socket.connect(addr);

            // 3.��Զ�̷����������Ľӿ��ࡢ�������������б�ȱ�����͸������ṩ��
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeUTF(serviceInterface.getName());
            output.writeUTF(method.getName());
            output.writeObject(method.getParameterTypes());
            output.writeObject(args);

            // 4.ͬ�������ȴ�����������Ӧ�𣬻�ȡӦ��󷵻�
            input = new ObjectInputStream(socket.getInputStream());
            return input.readObject();
        } finally {
            if (socket != null) socket.close();
            if (output != null) output.close();
            if (input != null) input.close();
        }
    }

    public static void main(String[] args) {
    	//Class<T> serviceInterface ����ľ����࣬�˴����;������Ͳ���
        RpcClientProxy client = new RpcClientProxy<>(IHello.class,"localhost","6666");
        IHello hello = (IHello) client.getClientIntance ();
        System.out.println (hello.sayHello ( "socket rpc" ));
    }
}