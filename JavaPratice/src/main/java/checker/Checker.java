package checker;

public class Checker {
    static class Node {
        Node next;
    }

    static boolean checkCycle(Node root) {
        Node tNode = root;
        for (int i = 0; i < 100000; i++) {
            if (tNode == null)
                return true;
            tNode = tNode.next;
        }
        return false;
    }

    public static void main(String[] args) {
        Node root = new Node();
        Node next = new Node();
        root.next = next;
        next.next = root;
        //Vm option参数加 -ea
        assert checkCycle(root) : "有环";
    }
}
