package FunctionalProgram;

public class TestAction {
    public static void main(String[] args) {
    	//ע���������Ϊ�������һ������
        Action<String> action = System.out :: println;
        action.execute("Hello World!1");
        //����ʽ�ӿڣ�ֻ��һ��������������ִ��
        test(System.out :: println, "Hello World!2");
    }

    static void test(Action<String> action, String str) {
        action.execute(str);
    }
}