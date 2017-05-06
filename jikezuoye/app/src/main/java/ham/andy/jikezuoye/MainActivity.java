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
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {
    private Button button = null;
    private EditText user = null;
    private EditText passwd = null;
    private SQLiteDatabase db = null;
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



        /**************************************登录事件*****************************************/


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getShujuku();   //打开数据库
                String user1 = user.getText().toString();   //获取写入的用户名
                String usepasswd = passwd.getText().toString();
                //db.execSQL("Select userName From user Where userName='" + user1 + "'");
                Cursor cursor = db.rawQuery("Select userName, userPasswd From user Where userName='" + user1 + "'", null);     //查询用rawQuery，增加修改删除用execsql
                /*在要读取的列不存在的时候该方法会返回值“-1”。所以可知，以上报错可能是因为要get的列不存在，也可能是因为游标位置不对。后来发现，
                因为我在执行这个语句前没有执行“Cursor.moveToNext();”这个函数，导致游标还位于第一位置的前面，所以索引显示为“-1”,前面加上这句就没错了。*/
                cursor.moveToNext();       //取下一条数据，也就是下一行
                new AlertDialog.Builder(MainActivity.this).setTitle(cursor)//设置对话框标题
                        .setMessage("没有填写或者没有改用户")//设置显示的内容
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//响应事件
                                // TODO Auto-generated method stub
                                Log.i("alertdialog", " 请保存数据！");
                            }
                        }).show();//在按键响应事件中显示此对话框
                /*if(cursor == null) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("登录失败")//设置对话框标题
                            .setMessage("没有填写或者没有改用户")//设置显示的内容
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//响应事件
                                    // TODO Auto-generated method stub
                                    Log.i("alertdialog", " 请保存数据！");
                                }
                            }).show();//在按键响应事件中显示此对话框
                } else {
                    if(cursor.getString(1) == usepasswd){
                        new AlertDialog.Builder(MainActivity.this).setTitle("登录失败")//设置对话框标题
                                .setMessage("密码错误")//设置显示的内容
                                .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {//响应事件
                                        // TODO Auto-generated method stub
                                        Log.i("alertdialog", " 请保存数据！");
                                    }
                                }).show();//在按键响应事件中显示此对话框
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, Main.class);
                        startActivity(intent);
                        finish();
                    }
                }*/

                cursor.close();
                db.close();
            }

        });

        /********************************************注册事件**********************************/
        TextView zhuce = (TextView)findViewById(R.id.zhuce);
        zhuce.setText(Html.fromHtml("<u>" + "注册" + "</>" ));        //添加下划线


    }


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
        now.delete();           //暂时测试用，记得删掉
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


}
