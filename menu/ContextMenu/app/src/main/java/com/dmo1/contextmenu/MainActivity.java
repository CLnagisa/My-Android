package com.dmo1.contextmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //没有下面的内容点击是无反应的

        TextView tv = new TextView(this);

        registerForContextMenu(tv);
        setContentView(tv);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch (item.getItemId()) {
            case 1: //做新建的事情
                break;
            case 2: //做打开的事情
                break;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 1, 1, "新建");
        menu.add(0, 1, 1, "打开");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
