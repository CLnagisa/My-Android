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
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MainActivity extends Activity {
    private Button button = null;
    private EditText user = null;
    private EditText passwd = null;
    private SQLiteDatabase db = null;
    private Context mContext = null;
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

        mContext = this;
        getShujuku();   //打开数据库
        db.close();

        /**************************************检查登录状态**************************************/
        getShujuku();
        Cursor cursor = db.rawQuery("Select state From denglu Where state=1", null);        //查找是否存在状态为1的，有那就是登录过，直接跳转，没有那就继续下面的操作
        cursor.moveToNext();
        if(cursor.getCount() > 0) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Main.class);
            startActivity(intent);
            finish();
        }


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
                if(cursor.getCount() <= 0) {     //没有查到数据的情况
                    new AlertDialog.Builder(MainActivity.this).setTitle("登录失败")//设置对话框标题
                            .setMessage("没有填写用户名或者没有该用户")//设置显示的内容
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//响应事件
                                    // TODO Auto-generated method stub
                                    Log.i("alertdialog", " 请保存数据！");
                                }
                            }).show();//在按键响应事件中显示此对话框
                } else {
                    //这里的比较要用到qeuals才可以正常判断
                    if(!cursor.getString(1).equals(usepasswd)){     //判断密码
                        new AlertDialog.Builder(MainActivity.this).setTitle("登录失败")//设置对话框标题
                                .setMessage("密码为空或密码错误")//设置显示的内容
                                .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {//响应事件
                                        // TODO Auto-generated method stub
                                        Log.i("alertdialog", " 请保存数据！");
                                    }
                                }).show();//在按键响应事件中显示此对话框
                    } else {     //都正确，实现跳转
                        cursor = db.rawQuery("Select userName From denglu Where userName='" + user1 + "'", null);
                        cursor.moveToNext();
                        if(cursor.getCount() <= 0) {
                            db.execSQL("INSERT INTO denglu VALUES (?, ?)", new Object[]{user.getText().toString(), 1});
                        } else {
                            db.execSQL("UPDATE denglu SET state=1 WHERE userName='" + user1 + "'");
                        }
                        cursor.close();
                        db.close();
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, Main.class);
                        startActivity(intent);
                        finish();
                    }
                }

                cursor.close();
                db.close();
            }

        });

        /********************************************跳转到注册页面**********************************/
        TextView zhuce = (TextView)findViewById(R.id.zhuce);
        zhuce.setText(Html.fromHtml("<u>" + "注册" + "</>" ));        //添加下划线
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, regist.class);
                startActivity(intent);
            }
        });


    }

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
        }
        db = SQLiteDatabase.openOrCreateDatabase(databaseFile, null);
    }
    /********************************************判断和打开数据库文件函数结束*************************************/
    /********************************************判断和打开数据库文件函数结束*************************************/

}
