package ham.andy.jikezuoye;


import android.app.Activity;


import android.content.Context;

import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CL on 2017/5/4.
 */

public class chat extends Activity{

    private Context mContext = null;   //上下文
    String now_use = null;
    private SQLiteDatabase db = null;
    private String aaa = null;     //保存和谁聊天

    ArrayList<HashMap<String, Object>> chatList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        getActionBar().hide();  //不显示actionBar

        mContext = this;

        /*********************************获取当前的登录用户名*****************************/
        getShujuku();
        Cursor cursor = db.rawQuery("Select userName From denglu Where state=1", null);        //查找是否存在状态为1的，有那就是登录过，直接跳转，没有那就继续下面的操作
        cursor.moveToNext();
        now_use = cursor.getString(0).toString();
        cursor.close();
        db.close();


        /**********更改标题*********/
        Intent intent = getIntent();    //获取传过来的数据
        aaa = intent.getStringExtra("title");
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

        /****************************************聊天记录显示****************************************/
        chatList = new ArrayList<HashMap<String, Object>>();
        addTextToList();     //执行显示函数


        final EditText editText = (EditText)findViewById(R.id.chat_edittext);
        final ListView chatListView = (ListView)findViewById(R.id.chat_list);
        final MyChatAdapter adapter = new MyChatAdapter(this, chatList);

        /****************************************发送事件*******************************************/
        Button send = (Button)findViewById(R.id.chat_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取到输入框的值
                String a = editText.getText().toString();
                if(a.length()==0)
                    return;
                editText.setText("");
                //添加到哈希表
                //addTextToList(a, 0);
                //通知说数据更改了，
                adapter.notifyDataSetChanged();
                //定位到到listview中的view中的最后一个
                chatListView.setSelection(chatList.size()-1);
            }
        });
        chatListView.setAdapter(adapter);

    }




    /********************************************显示数据的函数开始*******************************************/
    /********************************************显示数据的函数开始*******************************************/
    //添加数据进哈希表
    protected void addTextToList(){
        getShujuku();
        String sql = "Select message, sendName, receiveName From message where sendName='" + now_use + "' and receiveName='" + aaa + "' OR sendName='" + aaa + "' and receiveName='" + now_use + "'";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()) {
            if(cursor.getString(1).equals(now_use)) {
                HashMap<String,Object> map=new HashMap<String,Object>();
                map.put("person", 0);
                map.put("text", cursor.getString(0));
                chatList.add(map);
            } else {
                HashMap<String,Object> map=new HashMap<String,Object>();
                map.put("person", 1);
                map.put("text", cursor.getString(0));
                chatList.add(map);
            }
        }
    }
    //继承BaseAdapter， 写自己的adapter
    public class MyChatAdapter extends BaseAdapter {
        Context context = null;
        ArrayList<HashMap<String, Object>> chatList = null;
        public MyChatAdapter(Context context, ArrayList<HashMap<String, Object>> chatList) {
            super();
            this.chatList = chatList;
            this.context = context;
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            //在此适配器中所代表的数据集中的条目数
            return chatList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            //获取数据集中与指定索引对应的数据项
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            //取在列表中与指定索引对应的行id
            return position;
        }

        class ViewHolder {
            public TextView textView = null;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //设置是对面发来的还是自己发的
            /* 这里不能加缓存，找了一晚上的错误。。。。因为像第二个一样加了缓存的话，不能准确的判断是要用哪一个
               convertView = LayoutInflater.from(context).inflate(layout[a==0?0:1], null);造成错误    */
            ViewHolder holder = null;
            //两个页面
            int[] layout = {R.layout.chat_listview_right,R.layout.chat_listview_left};
            //两个页面对应的不同的text名字
            int[] to = {R.id.chatlist_text_me, R.id.chatlist_text_other};
            String[] from = {"chat_list_right_text", "chat_list_left_text"};
            int a = (Integer)chatList.get(position).get("person");
            //用person来判断，是0的话就是自己发的了
            convertView = LayoutInflater.from(context).inflate(layout[a==0?0:1], null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(to[a]);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便取出Tag
            convertView.setTag(holder);

            //设置创建的img数据为myData的第一个数据
            holder.textView.setText((String) chatList.get(position).get("text"));
            return convertView;
        }
    }
    /********************************************显示数据的函数结束*******************************************/
    /********************************************显示数据的函数结束*******************************************/


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
