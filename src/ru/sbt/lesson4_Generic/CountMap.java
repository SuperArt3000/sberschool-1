package ru.sbt.lesson4_Generic;

import java.util.Map;

/**
 * Created by Yrwing on 21.09.2016.
 */
public interface CountMap<K> {

    // добавляет элемент в этот контейнер.
    void add(K key);

    //Возвращает количество добавлений данного элемента
    int getCount(K key);

    //Удаляет элемент и контейнера и возвращает количество его добавлений(до удаления)
    int remove(K key);

    //количество разных элементов
    int size();

    //Добавить все элементы из source в текущий контейнер, при совпадении ключей, суммировать значения
    void addAll(CountMap<K> source);

    //Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений
    Map<K, Integer> toMap();

    //Тот же самый контракт как и toMap(), только всю информацию записать в destination
    void toMap(Map<K, Integer> destination);

}
