package language;

public class ByteTest {
    public static void main(String[] args) {
        byte[] bytes = {0x6c, 0x6a, 0x6c, 0x6c, 0x6a, 0x6c, 0x00, 0x00, 0x02, 0x01, 0x00};
        int sum = 0;
        for (byte aByte : bytes) {
            sum += aByte;
        }

        //大端排序
        byte[] src = new byte[4];
        src[0] = (byte) ((sum >> 24) & 0xFF);
        src[1] = (byte) ((sum >> 16) & 0xFF);
        src[2] = (byte) ((sum >> 8) & 0xFF);
        src[3] = (byte) (sum & 0xFF);
        System.out.println((byte) ~src[3]);

        //byte直接转int高位导致异常
        byte a = (byte) 0xe1;
        System.out.println((int) a);

        //正确
        System.out.println((int) (a & 0xFF));

        //java的char是两字节
        char b = 1 << 12;
        System.out.println((int) b);
    }
}
