package com.Dmo1.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "LogDemo";
    private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View V) {
                Log.v(MainActivity.ACTIVITY_TAG, "This is Verbose.");   //Log.v调试的颜色为黑色的，任何信息都会输出，这的v代表verbose冗长的意思，平时使用就是Log.v("","");例如;Log.v(TAG, "onStrat").
                Log.d(MainActivity.ACTIVITY_TAG, "This is Debug.");     //Log.d输出颜色是蓝色的，仅输出debug调试的意思，但他会输出上层的信息，过滤起来可以通过DDMS的Logcat标签来选择。
                Log.i(MainActivity.ACTIVITY_TAG, "This is Information");//Log.i输出为绿色，一般提示性的消息information，他不会输出Log.v和Log.d的消息，但会显示i、w和e的信息
                Log.w(MainActivity.ACTIVITY_TAG, "This is Warnning.");  //Log.w输出为橙色，可以看作是warning警告，一般需要我们注意优化android代码，同时选择他后还会输出Log.e信息
                Log.e(MainActivity.ACTIVITY_TAG, "This is Error.");     //Log.e输出为红色，是error错误，这里仅显示红的的错误信息，这些错误就需要我们认真分析，查看栈的信息了。
                                                                        //以上Log的级别一次升高，VERBOSE、DEBUG信息应当只存在开发中，INFO、WARN，ERROR这三种Log将出现在发布版本中
            }
        });
    }
}
