package ham.andy.jikezuoye;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private Button button = null;
    private EditText user = null;
    private EditText passwd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();   去掉actionbar
        //setTitle("用户登录");

        //自定义actionbar用的
        ActionBar actionBar=getActionBar();
        actionBar.setCustomView(R.layout.action_title);   //获取自定义actionbar文件
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);   //显示出来
        setContentView(R.layout.activity_main);
        
        button = (Button)findViewById(R.id.sublime);
        user = (EditText)findViewById(R.id.user);
        passwd = (EditText)findViewById(R.id.passwd);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((user.getText().toString().equals("asd")) && (passwd.getText().toString().equals("123"))) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, Main.class);
                    startActivity(intent);
                    finish();
                } else if(!user.getText().toString().equals("asd")){
                    new AlertDialog.Builder(MainActivity.this).setTitle("登录失败")//设置对话框标题
                            .setMessage("没有该用户名")//设置显示的内容
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//响应事件
                                    // TODO Auto-generated method stub
                                    Log.i("alertdialog", " 请保存数据！");
                                }
                            }).show();//在按键响应事件中显示此对话框
                } else {
                    new AlertDialog.Builder(MainActivity.this).setTitle("登录失败")//设置对话框标题
                            .setMessage("密码错误")//设置显示的内容
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//响应事件
                                    // TODO Auto-generated method stub
                                    Log.i("alertdialog", " 请保存数据！");
                                }
                            }).show();//在按键响应事件中显示此对话框
                }
            }

        });
        
    }

}
