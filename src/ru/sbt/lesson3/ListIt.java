package ru.sbt.lesson3;

import java.util.*;

/**
 * Created by Yrwing on 19.09.2016.
 */
public class ListIt<String> implements ListIterator<String> {
    private List<String> curList;
    private int cursor;
    private int headPointer;

    public ListIt(List<String> L){
        curList = L;
        headPointer = L.size();
        cursor =  headPointer;
    }
    @Override
    public boolean hasNext() {
        return (cursor >= 0);


    }

    @Override
    public  String next() throws NullPointerException{
        if(this.hasNext()){
            cursor--;
            return curList.get(cursor);
        }
        else throw new NullPointerException();
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
    public void set(String string) {
        curList.set(cursor, string);
    }

    @Override
    public void add(String string) {
        curList.add(string);
    }
}
