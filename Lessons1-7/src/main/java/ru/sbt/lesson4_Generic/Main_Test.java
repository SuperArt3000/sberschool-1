package ru.sbt.lesson4_Generic;

import java.util.ArrayList;
import java.util.List;

public class Main_Test {
    public static void main(String[] args) {
        CountMap<String> hcm = new RealCountMap<>();
        hcm.add("Hello");
        hcm.add("Welcome");
        hcm.add("Hello");
        System.out.println(hcm.getCount("Hello"));
        CountMap<String> hcm_2 = new RealCountMap<>();
        hcm_2.add("Victory");
        hcm_2.add("Hello");
        hcm.addAll(hcm_2);
        for (String key : hcm.toMap().keySet()) {
            System.out.println(key + "..." + hcm.getCount(key));
        }
        System.out.println(hcm.size());
        System.out.println(hcm.remove("Hello"));
        System.out.println(hcm.remove("Hello"));
        List<Integer> s1 = new ArrayList<>();
        List<Integer> s2 = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            s1.add(i);
            s2.add(2*i);
        }
        System.out.println(CollectionUtils.containsAny(s2, s1));
    }
}
