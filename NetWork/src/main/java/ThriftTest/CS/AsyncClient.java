package ThriftTest.CS;

import ThriftTest.shared.SharedService;
import ThriftTest.shared.SharedStruct;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AsyncClient {

        private  static  class myAsyncMethodCallback implements AsyncMethodCallback<SharedStruct> {
            // 处理服务返回的结果值O
            public void onComplete(SharedStruct sharedStruct) {
                long t;
                t = Math.abs(new Random().nextInt() % 8 ) + 2;

                System.out.println(String.format("%d, sleep for %d", sharedStruct.getKey(), t));
                try {
                    TimeUnit.SECONDS.sleep(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(String.format("log(%d)=%s", sharedStruct.getKey(), sharedStruct.getValue()));
            }

            @Override
            public void onError(Exception exception) {

            }
        }

        public static void main(String[] args) throws IOException, TException, InterruptedException {
        // 异步调用管理器
        TAsyncClientManager clientManager = new TAsyncClientManager();
        // 客户端应该使用非阻塞 IO
        TNonblockingTransport transport = new TNonblockingSocket("localhost",8181);
        // 协议与服务端需要一致

        TProtocolFactory tProtocolFactory = new TBinaryProtocol.Factory();
        // 异步调用
        SharedService.AsyncClient asyncClient = new SharedService.AsyncClient(tProtocolFactory,
                clientManager, transport);
        asyncClient.getStruct(123, new myAsyncMethodCallback());
        //同一个client无法执行多个请求,新建的貌似也不行。。
        //asyncClient.getStruct(456, new myAsyncMethodCallback());
        System.out.println("Client call finished");
        TimeUnit.SECONDS.sleep(10);
    }
}
