package com.example.templatepattern;

public class Request implements Runnable,Comparable<Request>{
    @Override
    public int compareTo(Request o) {
        return 0;
    }

    @Override
    public void run() {
        System.out.println("run");
    }
}
