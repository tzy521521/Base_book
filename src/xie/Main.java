package xie;

import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * Created by tzy on 2017/9/21.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String string=scanner.nextLine();
            System.out.println(getNoRe(string));
        }
    }
    public static String getNoRe(String string){
        LinkedHashSet<Character> linkedHashSet=new LinkedHashSet<>();
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i <string.length() ; i++) {
            linkedHashSet.add(string.charAt(i));
        }
        for (Character ch:linkedHashSet) {
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }
}
