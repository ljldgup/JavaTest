package JVMTest;

public class Initialize {
    //父类静态-子类静态-父类构造代码块-父类构造方法-子类构造代码块-子类构造方法
    public static void main(String[] args) {
        C c1 = new C();
    }
}
//父类A
class A {

    A() {
        System.out.println("A构造方法");
    }

    {
        System.out.println("A构造代码块");
    }

    static {
        System.out.println("A静态码块");
    }
}

//子类C
class C extends A {
    static {
        System.out.println("C的静态代码块");
    }
    {
        System.out.println("C构造代码块");
    }
    C() {
        System.out.println("C的构造方法");
    }
}