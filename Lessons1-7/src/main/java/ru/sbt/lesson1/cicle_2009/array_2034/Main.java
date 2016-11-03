package array_2034;

import java.util.Scanner;

import static java.lang.Math.abs;



/**
 * Created by Yrwing on 15.09.2016.
 * В Res[] хранится длина почти постоянного подмассива, начиная с соотв. индекса
 * Сравниваем и выдаем наибольший Res[]
 */
public class Main {
    public static void main(String[] args) {
        int max[] = {0, 0};
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int[] Numbs = new int[n];
        int[] Res = new int[n];

        for (int i = 0; i < n; i++) {
            Numbs[i] = s.nextInt();
        }
        for (int i = 0; i < n - 1; i++) {
            Res[i] = counter(Numbs, i);
            if (abs(Res[i]) > abs(max[0] - max[1])) {
                max[0] = i;
                max[1] = Res[i] + i;
            }
        }

        max[0]++;
        max[1]++;
        System.out.println( max[0] + " " + max[1]);
    }


    static int counter(int[] Numbs, int i) {
        if (i < Numbs.length - 1) {
            int Res;
            if (abs(Numbs[i] - Numbs[i + 1]) > 1)
                Res = 0;
            else if (Numbs[i] == Numbs[i + 1]) {
                Res = counter(Numbs, i + 1) + 1;
            } else {
                int a = Numbs[i];
                int b = Numbs[i + 1];
                Res = 1;
                int j = i + 2;
                while ((j < Numbs.length) && ((Numbs[j] == a) || (Numbs[j] == b))) {
                    Res = j - i;
                    j++;
                }
            }
            return Res;
        }
        return 0;
    }
}
