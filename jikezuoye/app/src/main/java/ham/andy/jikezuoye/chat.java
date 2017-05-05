package ham.andy.jikezuoye;


import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CL on 2017/5/4.
 */

public class chat extends Activity{

    private Context mContext = null;   //上下文

    private List<Map<String, Object>> myData;  //listitem用
    //List<Map<String, Object>> items = new ArrayList<Map<String, Object>>(); 是定义一个List类型的变量，list里面存放的是一个Map，而Map的key是一个String类型，Map的value是Object类型,Map是一个接口 代表一个key-value 键值对
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();      //用全局来保存，实现增加和删除
    private Map<String, Object> map = new HashMap<String, Object>();   //基于哈希表的map，用来存取数据，



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        getActionBar().hide();  //不显示actionBar

        mContext = null;

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




        ListView listview = null;              //生成设配器，数组===》listItem
        listview = (ListView)findViewById(R.id.chat_list);
        //用BaseAdapter并重写BaseAdapter类来实现
        myData = getData();
        MyAdapter adapter = new MyAdapter(this);
        listview.setAdapter(adapter);


    }



    /********************************************显示数据的函数开始*******************************************/
    /********************************************显示数据的函数开始*******************************************/
    /* 主页数据显示，listitem的函数实现*/
    private List<Map<String, Object>> getData() {
        //获取数据用的，一个哈希map

        map = new HashMap<String, Object>();
        map.put("mysely", "1");
        map.put("chat_list_right_text", "自己第一");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("mysely", "0");
        map.put("chat_list_left_text", "他人第一");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("mysely", "1");
        map.put("chat_list_right_text", "自己第二");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("mysely", "0");
        map.put("chat_list_left_text", "他人第二");
        list.add(map);
        return list;
    }
    //继承BaseAdapter， 写自己的adapter
    static class ViewHolder {
        public ImageView img;
        public TextView title;
        public Button button;
    }  //创建一个静态类，后面要用
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater yonghu;

        public MyAdapter(Context context) {
            this.yonghu = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            //在此适配器中所代表的数据集中的条目数
            return myData.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            //获取数据集中与指定索引对应的数据项
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            //取在列表中与指定索引对应的行id
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //设置是对面发来的还是自己发的
            ViewHolder holder = null;

            if(myData.get(position).get("mysely")  == "1") {
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = yonghu.inflate(R.layout.chat_listview_right, null);
                    holder.title = (TextView) convertView.findViewById(R.id.chat_list_right_text);
                    //将设置好的布局保存到缓存中，并将其设置在Tag里，以便取出Tag
                    convertView.setTag(holder);
                } else {
                    //缓存有，直接取出
                    holder = (ViewHolder) convertView.getTag();
                }
                //设置创建的img数据为myData的第一个数据
                holder.title.setText((String) myData.get(position).get("chat_list_right_text"));
                return convertView;
            } else {
                //如果缓存converView为空，则需创建View
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = yonghu.inflate(R.layout.chat_listview_left, null);
                    holder.title = (TextView) convertView.findViewById(R.id.chat_list_left_text);
                    //将设置好的布局保存到缓存中，并将其设置在Tag里，以便取出Tag
                    convertView.setTag(holder);
                } else {
                    //缓存有，直接取出
                    holder = (ViewHolder) convertView.getTag();
                }
                //设置创建的img数据为myData的第一个数据
                holder.title.setText((String) myData.get(position).get("chat_list_left_text"));
                return convertView;
            }
        }
    }
    /********************************************显示数据的函数结束*******************************************/
    /********************************************显示数据的函数结束*******************************************/



}
