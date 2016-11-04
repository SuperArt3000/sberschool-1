package ru.sbt.lesson1.cicle_2009.array_2025;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int length = s.nextInt();
        int a,b;
        int[] numbers = new int[length];
        for (int i = 0; i < length; i++) {
            numbers[i] = s.nextInt();
        }
        int N = s.nextInt();
        int[] result = new int[N];
        for (int i = 0; i < N; i++) {
            a = s.nextInt() - 1;
            b = s.nextInt();
            result[i] = compare(numbers, a, b);
        }
        for (int i = 0; i < N; i++) {
            System.out.println(result[i]);
        }
    }
    static int compare(int[] A, int a, int b){
        int min = A[a];
        for (int i = a; i < b; i++) {
            if(A[i] < min){
                min = A[i];
            }
        }
        return min;
    }
}
