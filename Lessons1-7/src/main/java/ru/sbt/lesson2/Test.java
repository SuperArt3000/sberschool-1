package ru.sbt.lesson2;

/**
 * Created by Yrwing on 14.09.2016.
 */
public class Test {
    public static void main(String[] args) {
        Person Larry = new Person(true, "Larry");
        Person Kerry = new Person(false, "Kerry");
        Person Tim = new Person(true, "Tim");
        Tim.marry(Kerry);
        System.out.println(Kerry.divorce());
    }
}
