package FunctionalProgram;

public class TestAction {
    public static void main(String[] args) {
    	//注意这里理解为传入的是一个函数
        Action<String> action = System.out :: println;
        action.execute("Hello World!1");
        //函数式接口，只有一个函数，传入后就执行
        test(System.out :: println, "Hello World!2");
    }

    static void test(Action<String> action, String str) {
        action.execute(str);
    }
}