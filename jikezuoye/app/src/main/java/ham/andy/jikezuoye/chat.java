package ham.andy.jikezuoye;


import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by CL on 2017/5/4.
 */

public class chat extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        getActionBar().hide();  //不显示actionBar

        /**********更改标题*********/
        Intent intent = getIntent();    //获取传过来的数据
        String aaa = intent.getStringExtra("title");
        TextView chat_title = (TextView)findViewById(R.id.chat_title);//根据传过来的数据更改标题
        chat_title.setText(aaa);

        /**********放回按钮*********/
        TextView fanghui = (TextView)findViewById(R.id.chat_fanhui);
        fanghui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView fanghui1 = (ImageView)findViewById(R.id.chat_img);
        fanghui1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
