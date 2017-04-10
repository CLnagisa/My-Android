package com.dmo1.submenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.SubMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu sub = menu.addSubMenu("搜索");
        sub.setIcon(android.R.drawable.ic_menu_search);
        sub.add(0, 1, 1, "本地");
        sub.add(0, 1, 1, "网络");
        return super.onCreateOptionsMenu(menu);
    }
}
