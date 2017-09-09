package chapter9;

import java.util.Date;

/**
 * Created by tzy on 2017/4/18.
 */
public class TestDate {
    public static void main(String[] args) {
        Date date=new Date();
        System.out.println("The elapsed time since Jan 1,1970 is "+date.getTime()+" milliseconds");
        System.out.println(date.toString());
    }
}
