package com.demo.test;

import javax.swing.*;

public class ModifyComboBoxItemTest {
    public static void main(String[] args) {
        // 創建一個JComboBox對象
        JComboBox<String> comboBox = new JComboBox<String>();

        // 添加現有項目到列表中
        comboBox.addItem("apple");
        comboBox.addItem("banana");
        comboBox.addItem("orange");

        // 刪除所有現有項目
        comboBox.removeAllItems();

        // 添加新項目到列表中
        comboBox.addItem("grape");
        comboBox.addItem("watermelon");

//        // 創建一個新的列表
//        String[] items = {"grape", "watermelon", "orange"};
//
//        // 將新列表設置為數據模型
//        comboBox.setModel(new DefaultComboBoxModel<String>(items));
    }
}