package Utilities;

import java.util.HashMap;

/**
 * Created by naor on 21/5/2015.
 */
/* This is meant to be a regular hashmap with one liners methods*/
public class HashMapBuilder<K, T> {
    private HashMap<K, T> map;

    public HashMapBuilder() {
        map = new HashMap<K, T>();
    }


    /*
    public HashMapBuilder(HashMap other){
        map = new HashMap<>();
        map.putAll(other);
    }
*/
    // One liners
    public HashMapBuilder<K,T> put(K key, T val){
        map.put(key, val);
        return this;
    }

    public HashMapBuilder<K,T> putAll(HashMap<K, T> other){
        map.putAll(other);
        return this;
    }

    public HashMap<K, T> build(){
        return map;
    }

}
