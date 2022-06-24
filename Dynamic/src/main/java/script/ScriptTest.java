package script;

import javax.script.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ScriptTest {
    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        //允许执行的脚本
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        for (ScriptEngineFactory factory : factories) {
            System.out.println(factory.getNames());
        }

        //执行js
        ScriptEngine engine = manager.getEngineByName("js");
        String js = "1 + 2";
        Integer result1 = (Integer) engine.eval(js);
        System.out.println("js: " + js + " -> " + result1);

        //定义调用函数
        js = "function welcome(name){return 'welcom ' + name;}";
        engine.eval(js);
        Invocable invocable = (Invocable) engine;
        String result2 = (String) invocable.invokeFunction("welcome", "hhhhh");
        System.out.println("js: " + js + " -> " + result2);

        //外部赋值，使用默认环境
        Date date = new Date();
        System.out.println(date.getTime());
        engine.put("date", date);
        js = "function getTime(){return date.getTime();}";
        engine.eval(js);
        Long result3 = (Long) invocable.invokeFunction("getTime");
        System.out.println("js: " + js + " -> " + result3);



        Bindings binds1 = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        binds1.put("foo",123);
        js = "foo = foo + \"123\"";
        engine.eval(js);

        //javascript脚本不是线程安全的,共享了一个环境
        String env = binds1.keySet().stream().map(k->String.join("=",k, binds1.get(k).toString()))
                .collect(Collectors.joining("\n"));
        System.out.println(env);
        System.out.println("-----------------------------------");

        //创建要给新环境
        Bindings bindings = engine.createBindings();//Local级别的Binding
        bindings.put("goo", 42.0);
        js = "goo = goo + 123";
        engine.eval(js,bindings);
        System.out.println(bindings.get("goo"));

        //编译成js再执行
        CompiledScript compiledScript = ((Compilable)engine).compile(js);


    }

}
