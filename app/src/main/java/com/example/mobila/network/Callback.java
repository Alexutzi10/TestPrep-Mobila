package com.example.mobila.network;

public interface Callback <R>{
    void runResultOnUIThread(R result);
}
