package io.RPC;

public class HelloServiceImpl implements IHello {

    @Override
    public String sayHello(String string) {
        return "你好:".concat ( string );
    }
}
