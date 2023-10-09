package com.netcafe.admin.db;

public class Updata {
    //添加数据
    public int addData(String sql) {
        return DbConn.updataInfo(sql);
    }
}