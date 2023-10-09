package com.netcafe.common.param;

import com.netcafe.common.data.Members;

import java.io.Serializable;
import java.util.Queue;

public class Call extends Members implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;

    public Queue<String> callingQueue;
    public String content;
}
