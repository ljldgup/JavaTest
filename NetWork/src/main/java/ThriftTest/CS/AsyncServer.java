package ThriftTest.CS;


import ThriftTest.shared.SharedService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;

public class AsyncServer {
    public static void main(String[] args) throws Exception {
        TProcessor processor = new SharedService.Processor<SharedService.Iface>(new SharedServiceImpl());
        TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(8181);
        TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory();
        TThreadedSelectorServer.Args serverArgs = new TThreadedSelectorServer.Args(serverSocket)
                .processor(processor)
                .protocolFactory(protocolFactory);
        TServer server = new TThreadedSelectorServer(serverArgs);
        System.out.println("start server on :8181");
        server.serve();
    }
}
