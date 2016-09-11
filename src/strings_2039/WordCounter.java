package strings_2039;

import java.util.Scanner;
/**
 * Created by Yrwing on 11.09.2016.
 */
public class WordCounter {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String[] Words = s.nextLine().split("([^a-zA-Z]+)");
        int n = Words.length;
        System.out.println(n);
    }
}
