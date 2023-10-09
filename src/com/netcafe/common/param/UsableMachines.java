package com.netcafe.common.param;

import com.google.gson.Gson;

import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class UsableMachines {
    public Socket socket = null;
    public HashMap<String, Socket> clientSockets = null;
    public HashMap<String, Integer> usedMachines = null;
}
