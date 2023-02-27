package com.example.templatepattern;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * 模版设计模式
 * 套路：
 * 必须得有继承，弗雷一般丢失流程和通用部分的封装，子类一般都是具体的功能
 *
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setContentView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}