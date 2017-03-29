package com.dmo1.demo2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by 43715 on 2017/3/29.
 */

public class secondActivity extends AppCompatActivity{
    private static final String LIFT_TAG = "LogActivity";
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {                //第一个页面的oncreate周期
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        Button back = (Button) findViewById(R.id.button2);
        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondActivity.this.setResult(RESULT_OK, intent);   //返回上一个activity
                secondActivity.this.finish();                       //结束这个activity
            }
        });
    }

    @Override
    protected void onStart() {
        // 第一个页面的start
        Log.v(secondActivity.LIFT_TAG, "SecondActivity --->onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        //第二个页面的restant
        Log.v(secondActivity.LIFT_TAG, "SecondActivity --->onRestart");
        super.onRestart();
    }


    @Override
    protected void onResume() {
        //第三个页面的resume
        Log.v(secondActivity.LIFT_TAG, "SecondActivity --->onResume");
        super.onResume();
    }


    @Override
    protected void onPause() {
        //第二个页面的pause
        Log.v(secondActivity.LIFT_TAG, "SecondActivity --->onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        //第二个页面的stop
        Log.v(secondActivity.LIFT_TAG, "SecondActivity --->onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        Log.v(secondActivity.LIFT_TAG,"SecondActivity --->onDestory");
        super.onDestroy();
    }

}
