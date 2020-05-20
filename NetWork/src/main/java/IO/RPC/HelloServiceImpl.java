package IO.RPC;

public class HelloServiceImpl implements IHello {

    @Override
    public String sayHello(String string) {
        return "ÄãºÃ:".concat ( string );
    }
}
