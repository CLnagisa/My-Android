package com.dmo1.optionsmenu;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button)findViewById(R.id.hello);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                openOptionsMenu();  //必须在activity这个继承下才有效果，不让没效果

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //当用户点击菜单按钮的时候弹出
        menu.add(0, 2, 1, "保存");
        menu.add(0, 2, 2, R.string.exit);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //当用户点击弹出的菜单的选项的时候执行
        if(item.getItemId() == 2) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
