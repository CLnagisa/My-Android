package com.demo1.demo1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by 43715 on 2017/3/22.
 */

public class OtherActivity extends AppCompatActivity {
    private Intent intent;
    private Bundle bunde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_activity);

        //取得Intent中的Bundle对象
        Bundle bunde = this.getIntent().getExtras();

        //取得Bundle对象中的数据
        String ans = bunde.getString("ans");

        //判断所选答案
        String sexText = "";
        if(ans.equals("655")) {
            sexText = "正确";
        } else {
            sexText = "错误";
        }
        TextView tv1 = (TextView) findViewById(R.id.back1);
        tv1.setText("您选择的答案是" + sexText);

        //以findViewById(0取得Button对象，并添加onClickListener
        Button btn3_back = (Button) findViewById(R.id.back);
        btn3_back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回resule回上一个activity
                OtherActivity.this.setResult(RESULT_OK, intent);
                //结束这个activity
                OtherActivity.this.finish();
            }
        });
    }
}
