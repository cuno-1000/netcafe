package com.demo.test;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class Collector {
    public static void main(String[] args) {
        // 創建兩個HashMap對象
        HashMap<String, Integer> map1 = new HashMap<String, Integer>();
        HashMap<String, String> map2 = new HashMap<String, String>();

        // 向map1中添加鍵值對
        map1.put("apple", 1);
        map1.put("banana", 2);
        map1.put("orange", 3);
        map1.put("grape", 4);

        // 向map2中添加鍵值對
        map2.put("banana", "2");
        map2.put("orange", "3");

//        // 獲取兩個HashMap的補集
//        Map<String, Integer> difference = map1.entrySet().stream()
//                .filter(entry -> !map2.containsKey(entry.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//        // 打印補集
//        System.out.println(difference);
//
//        String[] keys = difference.keySet().toArray(new String[0]);
//        System.out.println(Arrays.toString(keys));

// 添加一些鍵值對到兩個HashMap中
//        Set<String> keys1 = map1.keySet();
//        Set<String> keys2 = map2.keySet();

//        Set<String> keys1 = Collections.unmodifiableSet(map1.keySet());
//        Set<String> keys2 = Collections.unmodifiableSet(map2.keySet());
        Set<String> complementKeys = map1.keySet().stream()
                .filter(key -> !map2.containsKey(key))
                .collect(Collectors.toSet()); // 獲取兩個HashMap鍵的補集

        System.out.println(new Gson().toJson(complementKeys));

        System.out.println(map1);
        System.out.println(map2);
    }
}