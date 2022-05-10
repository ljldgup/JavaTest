package language;

import test.Weekdays;

import static test.Weekdays.SUNDAY;

public class EnumTest {
    public static void main(String[] args) {
        System.out.println(SUNDAY.equals(Weekdays.SUNDAY));
    }
}
