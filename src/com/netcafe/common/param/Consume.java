package com.netcafe.common.param;

import com.netcafe.common.data.Members;
import com.netcafe.common.data.Sessions;

import java.io.Serializable;

public class Consume extends Sessions implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;

    String columnName;
    String keyWord;
    Object[][] result = null;

    public Consume(int memberId) {
        setMemberID(memberId);
    }

    public Object[][] getResult() {
        return result;
    }

    public void setResult(Object[][] result) {
        this.result = result;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

}
