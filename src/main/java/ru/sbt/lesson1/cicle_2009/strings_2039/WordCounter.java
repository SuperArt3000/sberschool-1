package ru.sbt.lesson1.cicle_2009.strings_2039;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String str = in.readLine();
        String[] Words = str.split("([^a-zA-Z]+)");
        Pattern p = Pattern.compile("([^a-zA-Z]+)");
        Matcher m = p.matcher(str);
        if(m.lookingAt()) {
            System.out.println(Words.length - 1);
        }
        else{
            System.out.println(Words.length);
        }
    }
}
