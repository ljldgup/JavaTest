package ws;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class Hello 
{
    public String sayHello(@WebParam(name="description")String name)
    {
        return "Hello, " + name + "!";
    }
}
