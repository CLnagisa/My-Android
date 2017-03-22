package com.demo1.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    //实例化4个RadioButton组件
    RadioButton r1 = null;
    RadioButton r2 = null;
    RadioButton r3 = null;
    RadioButton r4 = null;
    RadioGroup radioGroup = null;    //实例化一个radiogroup组件
    RadioButton currentRadioButton = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获得单选按钮组
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        //获得单选按钮,RadioButton是强制类型装欢，findViewById函数是通过id找到对应的组件
        r1 = (RadioButton) findViewById(R.id.a);
        r2 = (RadioButton) findViewById(R.id.b);
        r3 = (RadioButton) findViewById(R.id.c);
        r4 = (RadioButton) findViewById(R.id.d);
        
        //监听单选按钮组
        radioGroup.setOnCheckedChangeListener(mChangeRadio);

        //实例化两个按钮
        Button btn1_sure = (Button) findViewById(R.id.sure);
        Button btn2_cancel = (Button) findViewById(R.id.cancel);
        //监听两个按钮
        btn1_sure.setOnClickListener(new btn1_sure());
        btn2_cancel.setOnClickListener(new btn2_cancel());
    }

    class btn1_sure implements View.OnClickListener {   //实现onclicklistener接口和实现onclick方法，implements实现定义的抽象接口
        @Override
        public void onClick(View v) {
            if(currentRadioButton.getText().equals("655")) {   //判断，选择的等于655吗？
                setTitle("你选择的答案是正确的！");
            } else {
                setTitle("你选择的答案是错误的");
            }
        }
    }

    class btn2_cancel implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            radioGroup.clearCheck();          //清除按钮组的选择
            setTitle("");
        }
    }

    private RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener() {   //OnCheckedChangeListener单选复选的一个选择性的类
        @Override
        public void onCheckedChanged(RadioGroup group, int checkId) {
            if(checkId == r1.getId()) {
                //获得按钮的名称
                currentRadioButton = r1;
            } else if(checkId == r2.getId()) {
                currentRadioButton = r2;
            } else if(checkId == r3.getId()) {
                currentRadioButton = r3;
            } else if(checkId == r4.getId()) {
                currentRadioButton = r4;
            }
        }
    };
}

