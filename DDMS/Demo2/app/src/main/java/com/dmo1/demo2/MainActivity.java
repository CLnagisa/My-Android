package com.dmo1.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button myButton;
    static final String LIFT_TAG = "LogActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {                //第一个页面的oncreate周期
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(MainActivity.LIFT_TAG, "FirstActivity --> onCreate");
        Button myButton = (Button) findViewById(R.id.button1);
        myButton.setText("打开第二个页面");
        myButton.setOnClickListener(new ButtonOnClickListener());
    }

    class ButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, secondActivity.class);
            MainActivity.this.startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        //第一个页面的start页面
        Log.v(MainActivity.LIFT_TAG, "FirstAcvity --->onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        //第一个页面的restart周期
        Log.v(MainActivity.LIFT_TAG, "FirstActivity --> onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        //第一个页面的resume
        Log.v(MainActivity.LIFT_TAG, "FirstAcvity --->onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        //第一个页面的pause周期
        Log.v(MainActivity.LIFT_TAG, "FirstActivity --> onPause");
        super.onPause();
    }


    @Override
    protected void onStop() {
        //第一个页面的stop页面
        Log.v(MainActivity.LIFT_TAG, "FirstAcvity --->onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //第一个页面的destroy周期
        Log.v(MainActivity.LIFT_TAG, "FirstActivity --> onDestory");
        super.onDestroy();
    }
    //以上的声明周期按顺序排列下来
}
