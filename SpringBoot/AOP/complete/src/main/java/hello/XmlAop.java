package hello;

//xml配置实现,不需要实例化
public class XmlAop
{
    public void LogBefore()
    {
        System.out.println("xml aop before method");
    }
    
    public void LogAfter()
    {
        System.out.println("xml aop after method");
    }
}