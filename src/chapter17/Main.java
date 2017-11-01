package chapter17;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by tzy on 2017/10/13.
 * 将temp.dat中的第10个字节存到c中。
 */
public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("temp.dat");
        try (
                FileInputStream stream = new java.io.FileInputStream(file);
             ){
            int pos = 9;
            stream.skip(pos); //跳过包含pos的字节数/*

             byte[] b=new byte[1];
            stream.read(b);
            System.out.println(new String(b));

            /*
            int c=stream.read();
            System.out.print(c);
             */


        }
    }
}
