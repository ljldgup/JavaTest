package ThriftTest.CS;

import ThriftTest.shared.SharedService;
import ThriftTest.shared.SharedStruct;
import org.apache.thrift.TException;

import  java.lang.Math;

public class SharedServiceImpl implements SharedService.Iface {

    @Override
    public SharedStruct getStruct(int key) throws TException {
        SharedStruct ans = new SharedStruct(key,
                String.valueOf(Math.log(key)));
        return ans;
    }
}
