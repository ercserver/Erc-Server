package Utilities;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Created by ohad on 21/5/2015.
 */
/* This is meant to be a regular hashmap with one liners methods*/
public class HashMapAdapter<K, T> {
    private HashMap<K, T> map;

    public HashMapAdapter() {
        map = new HashMap<K, T>();
    }

    public HashMapAdapter(HashMap other){
        map = new HashMap<>();
        map.putAll(other);
    }

    // One liners
    public HashMap<K,T> put(K key, T val){
        map.put(key, val);
        return map;
    }

    public HashMap<K,T> putAll(HashMap<K, T> other){
        map.putAll(other);
        return map;
    }

    public Object get(K key){
        return map.get(key);
    }

}
