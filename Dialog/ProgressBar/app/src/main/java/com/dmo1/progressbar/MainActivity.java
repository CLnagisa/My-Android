package com.dmo1.progressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar firstBar = null;
    private ProgressBar secondBar = null;
    private Button myButton= null;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstBar = (ProgressBar) findViewById(R.id.firstBar);
        secondBar = (ProgressBar) findViewById(R.id.secondBar);
        myButton = (Button) findViewById(R.id.myButton);
        myButton.setOnClickListener(new ButtonListener());

    }

    class ButtonListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if(i == 0) {
                //setVisibility()这个函数是设置控件的显示和隐藏的，有三个属性，分别是visible可见，invisible不可见，占据空间，gone隐藏，不占据空间
                firstBar.setVisibility(View.VISIBLE);
                secondBar.setVisibility(View.VISIBLE);
                firstBar.setMax(150);      //设置长度，每次增加的长度
            } else if(i < firstBar.getMax()) {
                firstBar.setProgress(i);     //设置进度条的进度
                firstBar.setSecondaryProgress(i + 10);       //用播放器来讲，setProgress就是播放进度，secondaryProgress就是缓冲进度
            } else {
                firstBar.setVisibility(View.GONE);
                secondBar.setVisibility(View.GONE);
            }
            i = i + 10;
        }
    }
}
