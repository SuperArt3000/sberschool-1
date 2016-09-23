package ru.sbt.lesson4_Generic;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yrwing on 22.09.2016.
 */
public class RealCountMap<K> implements CountMap<K> {
    private final HashMap<K, Integer> NodeMap = new HashMap<>();

    @Override
    public int size(){
        return NodeMap.size();
    }

    @Override
    public void add(K key) {
        if (NodeMap.containsKey(key))
            NodeMap.put(key, (NodeMap.get(key) + 1));
        else
            NodeMap.put(key, 1);
    }

    @Override
    public int remove(K key) {
        return (NodeMap.containsKey(key)) ? NodeMap.remove(key): 0;
    }

    @Override
    public  int getCount(K key) {
        return (NodeMap.containsKey(key)) ? NodeMap.get(key) : 0;
    }

    @Override
    public void addAll(CountMap<K> source) {
        Map<K, Integer> curMap = new HashMap<>(source.toMap());
        for (K key : curMap.keySet()){
            if(curMap.containsKey(key) && (NodeMap.containsKey(key)))
                NodeMap.put(key, (NodeMap.get(key) + curMap.get(key)));
            else if (curMap.containsKey(key))
                NodeMap.put(key, curMap.get(key));
        }
    }

    @Override
    public  HashMap<K, Integer> toMap() {
        return NodeMap;
    }

    @Override
    public  void toMap(Map<K, Integer> destination) {
        destination = NodeMap;
    }
}
