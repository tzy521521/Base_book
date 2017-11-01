package chapter11;

/**
 * Created by tzy on 2017/9/26.
 */
public class Main {
    public static void main(String[] args) {
        new Students();
        System.out.println(Runtime.getRuntime());
        System.out.println("~~~~~~`");
    }
    static class Person{
        public Person() {
            System.out.println("Person constructor");
        }
        static {
            System.out.println("static Person");
        }
        {
            System.out.println("no-static Person");
        }
    }
    static class Students extends Person{
        public Students() {
            System.out.println("Student constructor");
        }
        static {
            System.out.println("static Students");
        }
        {
            System.out.println("no-static Students");
        }
    }
}
