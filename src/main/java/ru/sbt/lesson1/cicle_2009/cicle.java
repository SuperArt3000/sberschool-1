package ru.sbt.lesson1.cicle_2009;

import java.util.Scanner;

/**
 * Created by Yrwing on 11.09.2016.
 */
public class cicle {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int N = s.nextInt();
        int [] numb = new int[N];
        int sum = 0;
        for (int i = 0; i < N; i++) {
            numb[i] = s.nextInt();
        }
        for (int i = 0; i < N; i = 2 * (i + 1) - 1) {
            sum = sum + numb[i];
        }
        System.out.print(sum);
    }
}
