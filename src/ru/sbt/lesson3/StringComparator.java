package ru.sbt.lesson3;

import java.util.Comparator;

/**
 * Created by Yrwing on 19.09.2016.
 */
class StringComparator implements Comparator<String>  {
    @Override
    public int compare(String a, String b) {
        if(a.length() == b.length() && a.equals(b)){
            return 0;
        }
        else if(a.length() < b.length())
            return -1;
        else return 1;
    }
}
