package ru.sbt.lesson3;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Yrwing on 19.09.2016.
 */
public class ListIt<String> implements ListIterator<String> {
    @Override
    public boolean hasNext() {
        if(this.hasPrevious()){return true;}else return false;

    }

    @Override
    public  String next() {
       return this.previous();
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public String previous() {
        return null;
    }

    @Override
    public int nextIndex() {
        return 0;
    }

    @Override
    public int previousIndex() {
        return 0;
    }

    @Override
    public void remove() {

    }

    @Override
    public void set(String s) {

    }

    @Override
    public void add(String s) {

    }
}
