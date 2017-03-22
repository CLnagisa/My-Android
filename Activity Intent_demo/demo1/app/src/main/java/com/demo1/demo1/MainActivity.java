package com.demo1.demo1;

import android.content.Intent;
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
    RadioGroup radioGroup = null;    //实例化一个radiogroup组件.radioGroup选中组，只能一个被选中，
    //RadioButton currentRadioButton = null;


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
        //radioGroup.setOnCheckedChangeListener(mChangeRadio);

        //实例化两个按钮
        Button btn1_sure = (Button) findViewById(R.id.sure);
        Button btn2_cancel = (Button) findViewById(R.id.cancel);

        //监听两个按钮
        btn1_sure.setOnClickListener(new btn1_sure());
        btn2_cancel.setOnClickListener(new btn2_cancel());
    }

    class btn1_sure implements View.OnClickListener {   //按钮事件监听器类，实现OnClickListener接口
        @Override
        public void onClick(View v) {             //实现OnClickListener接口中的onClick方法
            String ans = "";
            if(r1.isChecked()) {     //isChecked()复选框是否被选中，选中放回true，没有则返回false
                ans = "355";
            } else if(r2.isChecked()) {
                ans = "455";
            } else if(r3.isChecked()) {
                ans = "555";
            } else if(r4.isChecked()) {
                ans = "655";
            }

            //new一个Intent对象，并指定class    在Android中提供了Intent机制来协助应用间的交互与通讯
            Intent intent = new Intent();

            //设置Intent对象要启动的Activity
            intent.setClass(MainActivity.this, OtherActivity.class);   //从MainActivity这个页面跳转到OtherActivity

            //new 一个Bundle对象，并酱要传递的数据传入
            Bundle bundle = new Bundle();
            bundle.putString("ans", ans);    //bundle是一个键值对，两个activity之间的通讯可以通过bundle类来实现,第一个参数为键名，第二个为对应的值

            //将Bundle对象assign给Intent
            intent.putExtras(bundle);       //putExtras就是用来传递参数的，里面的参数是一个键值对，即putExtras("A", abc);

            //通过Intent对象启动另外一个Activity
            startActivityForResult(intent, 0);
        }
    }

    class btn2_cancel implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            radioGroup.clearCheck();          //清除按钮组的选择
            setTitle("");
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //当requestCode、resultCode同时为0，也就是处理待定的结果
        if(requestCode == 0 && resultCode == 0) {
                //取得来自Activity2的数据，并显示在画面上
                Bundle bunde = data.getExtras();
                String ans = bunde.getString("ans");
        }
    }

    /*  一般的复选框运用，在没有多页面切换的时候用另外的方法

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
    */
}

