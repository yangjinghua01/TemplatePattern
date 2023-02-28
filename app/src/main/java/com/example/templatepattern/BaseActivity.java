package com.example.templatepattern;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        hookContentView();
        //1.设置布局
        setContentView();
//  2.初始化View
        initView();
//    3.初始化title
        initTitle();
//4.访问数据接口
        initData(savedInstanceState);
    }

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void setContentView();

    protected abstract void initData(Bundle savedInstanceState);
    protected void hookContentView(){


    }
}
