package ham.andy.jikezuoye;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by CL on 2017/5/6.
 */

public class regist extends Activity {

    private SQLiteDatabase db = null;
    private Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        getActionBar().hide();  //不显示actionBar
        mContext = this;

        /*****************************返回登录界面**********************/
        ImageView fanhui_img = (ImageView)findViewById(R.id.regist_img);
        fanhui_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fanghui();
            }
        });
        TextView fanhui_text = (TextView)findViewById(R.id.regist_fanhui);
        fanhui_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fanghui();
            }
        });

        /*****************************注册事件****************************/
        final EditText user = (EditText)findViewById(R.id.user);      //获取帐号名
        final EditText passwd = (EditText)findViewById(R.id.passwd);  //获取密码
        final EditText passwd1 = (EditText)findViewById(R.id.passwd1);//获取确认密码
        Button sublime = (Button)findViewById(R.id.sublime);    //获取确认按钮

        sublime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = null;
                getShujuku();
                final Cursor cursor = db.rawQuery("Select userName From user Where userName='" + user.getText() + "'", null);
                if(cursor.getCount() > 0) {
                    toast = Toast.makeText(getApplicationContext(), "账户已存在，请重新填写", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, -200);
                    toast.show();
                } else {
                    if(passwd.getText().toString().equals("")) {
                        toast = Toast.makeText(getApplicationContext(), "密码为空", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, -200);
                        toast.show();
                        passwd.requestFocus();       //获取焦点
                        cursor.close();
                        db.close();
                    } else if(!passwd1.getText().toString().equals(passwd.getText().toString())) {
                        toast = Toast.makeText(getApplicationContext(), "输入的两次密码不一样", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, -200);
                        toast.show();
                        passwd.requestFocus();       //获取焦点
                        cursor.close();
                        db.close();
                    } else {
                        db.execSQL("INSERT INTO user VALUES (?, ?)", new Object[]{user.getText().toString(), passwd.getText().toString()});
                        cursor.close();
                        db.close();
                        final TextView inputServer = new TextView(mContext);
                        inputServer.setText("注册成功！");
                        inputServer.setPadding(10, 50, 0, 0);
                        inputServer.setGravity(Gravity.CENTER);
                        inputServer.setTextSize(20);
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setView(inputServer);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //点确定的相应事件
                            public void onClick(DialogInterface dialog, int which) {
                                fanghui();
                            }
                        });
                        builder.show();
                    }
                }
            }
        });


    }

    /********************************************返回主页面函数开始*************************************/
    /********************************************返回主页面函数开始*************************************/
    public void fanghui() {
        Intent logoutIntent = new Intent(regist.this, MainActivity.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logoutIntent);
        finish();
    }
    /********************************************返回主页面函数结束*************************************/
    /********************************************返回主页面函数结束*************************************/


    /********************************************判断和打开数据库文件函数开始*************************************/
    /********************************************判断和打开数据库文件函数开始*************************************/
    private void getShujuku(){
        final String DATABASE_PATH="data/data/"+ this.getPackageName() + "/databases/";
        String databaseFile=DATABASE_PATH+"mysqlite.db";
        //创建databases目录（不存在时）
        File file=new File(DATABASE_PATH);
        if(!file.exists()){
            file.mkdirs();
        }
        //判断数据库是否存在

        File now = new File(databaseFile);
        if (!now.exists()) {
            //把数据库拷贝到/data/data/<package_name>/databases目录下
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(databaseFile);       //创建写入流文件
                //数据库放res/raw目录下
                InputStream inputStream = getResources().openRawResource(R.raw.mysqlite);
                byte[] buffer = new byte[1024];  //缓存二进制数据
                int readBytes = 0;
                while ((readBytes = inputStream.read(buffer)) != -1)     //读取流数据，每次1024个比特
                    fileOutputStream.write(buffer, 0, readBytes);   //用流方式写
                inputStream.close();                  //关闭打开的流文件
                fileOutputStream.close();             //关闭可写入的流文件
            } catch (IOException e) {
            }
        } else {

        }
        db = SQLiteDatabase.openOrCreateDatabase(databaseFile, null);
    }
    /********************************************判断和打开数据库文件函数结束*************************************/
    /********************************************判断和打开数据库文件函数结束*************************************/
}
