package com.netcafe.admin.db;

import com.netcafe.common.data.Bills;
import com.netcafe.common.data.Members;
import com.netcafe.common.data.Sessions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Select {
    public Object[][] getMembers(String sql) { // 邓竣中 下同

        ResultSet resultSet = DbConn.query(sql);
        ArrayList<Members> list = new ArrayList<Members>();
        try {
            while (resultSet.next()) {
                Members m = new Members();
                m.setMemberID(resultSet.getInt(1));
                m.setPhone(resultSet.getString(2));
                m.setName(resultSet.getString(3));
                m.setSex(resultSet.getString(4));
                m.setJoinDate(resultSet.getString(5));
                m.setBalance(resultSet.getString(6));
                list.add(m);
            }
            // 用於編輯
            list.add(new Members());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] objects = new Object[list.size()][6];
        int i;
        for (i = 0; i < list.size(); i++) {
            objects[i][0] = list.get(i).getMemberID();
            objects[i][1] = list.get(i).getPhone();
            objects[i][2] = list.get(i).getName();
            objects[i][3] = list.get(i).getSex();
            objects[i][4] = list.get(i).getJoinDate();
            objects[i][5] = list.get(i).getBalance();
        }
        return objects;
    }

    public Object[][] getOnLines(String sql) {

        ResultSet resultSet = DbConn.query(sql);
        ArrayList<Sessions> list = new ArrayList<Sessions>();
        try {
            while (resultSet.next()) {
                Sessions m = new Sessions();
                m.setMemberID(resultSet.getInt(1));
                m.setPhone(resultSet.getString(2));
                m.setName(resultSet.getString(3));
                m.setOnLineAt(resultSet.getString(4));
                m.setIpAddressPort(resultSet.getString(5));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] objects = new Object[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            objects[i][0] = list.get(i).getMemberID();
            objects[i][1] = list.get(i).getPhone();
            objects[i][2] = list.get(i).getName();
            objects[i][3] = list.get(i).getOnLineAt();
            objects[i][4] = list.get(i).getIpAddressPort();
        }
        return objects;
    }

    public Object[][] getConsumes(String sql) {

        ResultSet resultSet = DbConn.query(sql);
        ArrayList<Sessions> list = new ArrayList<Sessions>();
        try {
            while (resultSet.next()) {
                Sessions m = new Sessions();
                m.setPhone(resultSet.getString(1));
                m.setIpAddressPort(resultSet.getString(2));
                m.setOnLineAt(resultSet.getString(3));
                m.setDeleteAt(resultSet.getString(4));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] objects = new Object[list.size()][4];
        for (int i = 0; i < list.size(); i++) {
            objects[i][0] = list.get(i).getPhone();
            objects[i][1] = list.get(i).getIpAddressPort();
            objects[i][2] = list.get(i).getOnLineAt();
            objects[i][3] = list.get(i).getDeleteAt();
        }
        return objects;
    }

    public Object[][] getBills(String sql) {

        ResultSet resultSet = DbConn.query(sql);
        ArrayList<Bills> list = new ArrayList<Bills>();
        try {
            while (resultSet.next()) {
                Bills m = new Bills();
                m.setPhone(resultSet.getString(1));
                m.setName(resultSet.getString(2));
                m.setTime(resultSet.getString(3));
                m.setAmount(resultSet.getString(4));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] objects = new Object[list.size()][4];
        for (int i = 0; i < list.size(); i++) {
            objects[i][0] = list.get(i).getPhone();
            objects[i][1] = list.get(i).getName();
            objects[i][2] = list.get(i).getTime();
            objects[i][3] = list.get(i).getAmount();
        }
        return objects;
    }

    public Object[][] getUserPageBills(String sql) {

        ResultSet resultSet = DbConn.query(sql);
        ArrayList<Sessions> list = new ArrayList<Sessions>();
        try {
            while (resultSet.next()) {
                Sessions m = new Sessions();
                m.setIpAddressPort(resultSet.getString(1));
                m.setOnLineAt(resultSet.getString(2));
                m.setDeleteAt(resultSet.getString(3));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] objects = new Object[list.size()][4];
        float price = 15f;
        LocalDateTime dateTime1, dateTime2;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            objects[i][0] = list.get(i).getIpAddressPort();
            objects[i][1] = list.get(i).getOnLineAt();
            objects[i][2] = list.get(i).getDeleteAt();
            if(list.get(i).getDeleteAt()!=null){
                dateTime1 = LocalDateTime.parse(list.get(i).getOnLineAt(), formatter);
                dateTime2 = LocalDateTime.parse(list.get(i).getDeleteAt(), formatter);
                Duration duration = Duration.between(dateTime1, dateTime2);
                objects[i][3] = (duration.toMinutes() + 1) * price;
            } else {
                objects[i][2] = "";
                objects[i][3] = "";
            }
        }
        return objects;
    }
}