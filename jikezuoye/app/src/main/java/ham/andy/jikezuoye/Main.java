package ham.andy.jikezuoye;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Main extends Activity {
    private PopupWindow popupWindow;  //从右出来的弹出框
    private Context mContext = null;   //上下文
    private List<Map<String, Object>> myData;  //listitem用
    //List<Map<String, Object>> items = new ArrayList<Map<String, Object>>(); 是定义一个List类型的变量，list里面存放的是一个Map，而Map的key是一个String类型，Map的value是Object类型,Map是一个接口 代表一个key-value 键值对
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();      //用全局来保存，实现增加和删除
    private Map<String, Object> map = new HashMap<String, Object>();   //基于哈希表的map，用来存取数据，
    private SQLiteDatabase db = null;
    private MyAdapter adapter = null;
    String now_use = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();   去掉actionbar
        //setTitle("用户登录");
        //自定义actionbar用的
        ActionBar actionBar=getActionBar();
        actionBar.setCustomView(R.layout.main_title);   //获取自定义actionbar文件
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);   //显示出来
        setContentView(R.layout.main);

        mContext = this;    //获取主Main的this，有几个地方会用到
        adapter = new MyAdapter(this);

        getShujuku();
        Cursor cursor = db.rawQuery("Select userName From denglu Where state=1", null);        //查找是否存在状态为1的，有那就是登录过，直接跳转，没有那就继续下面的操作
        cursor.moveToNext();
        now_use = cursor.getString(0).toString();
        cursor.close();
        db.close();

        /********点击头像右边弹出框的实现***********/
        ImageView touxiang = (ImageView)findViewById(R.id.touxiang);
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupWindow();  //初始化类
            }
        });



        /********点击右边的十字架出现操作按钮*******/
        final ImageView caozuo = (ImageView)findViewById(R.id.caozuo);
        caozuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(caozuo);
            }
        });


        /********点击用户跳转到不同的聊天页面*******/
        ListView listview = null;              //生成设配器，数组===》listItem
        listview = (ListView)findViewById(R.id.MyListView);

        /*        SimpleAdapter adapter = new SimpleAdapter(this, getData(),   //数据来源
                                                  R.layout.my_listitem,   //ListItem的xml实现
                                                  new String[]{"title", "img"},   //动态数组与ListItem对应的子项
                                                  new int[]{R.id.title, R.id.img});  //ListItem的XML文件里面的id
        listview.setAdapter(adapter);//启动并显示设配器*/
        //用BaseAdapter并重写BaseAdapter类来实现
        myData = getData();
        MyAdapter adapter = new MyAdapter(this);
        listview.setAdapter(adapter);


    }





    /**********************************************************右边弹出框相关的函数开始***************************************************************/
    /**********************************************************右边弹出框相关的函数开始***************************************************************/
    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     */
    class popupDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }
    //右弹出框的初始化代码。
    protected void initPopupWindow(){
        View popupWindowView = getLayoutInflater().inflate(R.layout.youtanchukuang, null);  //LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化,因此如果你的Activity里如果用到别的layout,比如对话框上的layout,你还要设置对话框上的layout里的组件(像图片ImageView,文字TextView)上的内容,你就必须用inflate()先将对话框上的layout找出来,然后再用这个layout对象去找到它上面的组件
        //内容，高度，宽度
        //4个参数分别为要显示的view，width和height为宽和高，值为像素值，是否获取焦点。View的职责，根据测量模式和ViewGroup给出的建议的宽和高，计算出自己的宽和高；同时还有个更重要的职责是：在ViewGroup为其指定的区域内绘制自己的形态。
        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT, true);
        //动画效果，在style文件里面
        popupWindow.setAnimationStyle(R.style.AnimationLeftFade);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
        //宽度
        popupWindow.setWidth(650);
        //高度
        //popupWindow.setHeight(LayoutParams.FILL_PARENT);
        //显示位置
        popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_main, null), Gravity.LEFT, 0, 500);  //设置弹出框，gravity是弹出的方向
        //设置背景半透明
        backgroundAlpha(0.5f);


        //获取数据更改用户名
        final TextView yonghuming = (TextView)popupWindowView.findViewById(R.id.yonghuming);
        yonghuming.setText(now_use);
        db.close();
        popupWindowView.setFocusableInTouchMode(true);    //获取淡出框的焦点
        /*注销按钮，右弹出框的监听时间，如果只是一般的用inflate来获取的话是不行的，inflate的作用就是获取到xml，
        这里是获取别的xml文件，取出对应的id，但是inflate就但存是获取对应的xml，是不能对其操作的，所以用setFocusableInTouchMode方法，
        先获取到这个弹出框的焦点，然后再做监听事件*/
        TextView zhuxiao = (TextView)popupWindowView.findViewById(R.id.zhanghaozhuxiao);
        zhuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注销的时候把登录的状态改为0
                getShujuku();
                db.execSQL("UPDATE denglu SET state=0 WHERE userName='" + yonghuming.getText().toString() + "'");
                db.close();
                Intent logoutIntent = new Intent(Main.this, MainActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutIntent);
                finish();
            }
        });
        TextView tuichu = (TextView)popupWindowView.findViewById(R.id.tuichu);
        tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });



        //关闭事件
        popupWindow.setOnDismissListener(new Main.popupDismissListener());  //将透明度改回来
        popupWindowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*if( popupWindow!=null && popupWindow.isShowing()){
                 popupWindow.dismiss();
                 popupWindow=null;
                }*/
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });

    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();  //得到透明属性值
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);    //setAttributes设置透明属性
    }
    /**********************************************************右边弹出框相关的函数结束***************************************************************/
    /**********************************************************右边弹出框相关的函数结束***************************************************************/

    /**********************************************************右边十字架弹出框的函数开始*************************************************************/
    /**********************************************************右边十字架弹出框的函数开始*************************************************************/
    /*右边操作按钮的弹出框*/
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.caozuo, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //创建一个带输入框的弹出框
                final EditText inputServer = new EditText(mContext);
                inputServer.setBackgroundResource(R.drawable.shape);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("请输入要添加的用户名").setView(inputServer)
                        .setNegativeButton("取消", null);                                  //取消就取消
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //点确定的相应事件
                    public void onClick(DialogInterface dialog, int which) {
                        String yonghuming = null;
                        yonghuming = inputServer.getText().toString();     //获取输入的数据
                        Toast toast = null;
                        getShujuku();
                        Cursor cursor = db.rawQuery("Select userName From " + now_use +" Where userName = '" + yonghuming + "'", null);
                        cursor.moveToNext();
                        if(cursor.getCount() <= 0) {
                            cursor = db.rawQuery("Select userName From user Where userName = '" + yonghuming + "'", null);
                            cursor.moveToNext();
                            if(cursor.getCount() <= 0) {
                                toast = Toast.makeText(getApplicationContext(), "没有该用户", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, -200);
                                toast.show();
                                cursor.close();
                                db.close();
                            } else {
                                if(cursor.getString(0).equals(now_use)) {
                                    toast = Toast.makeText(getApplicationContext(), "不能添加自己", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, -200);
                                    toast.show();
                                    cursor.close();
                                    db.close();
                                } else {
                                    db.execSQL("Insert into " + now_use + " Values(null, '" + yonghuming + "')");
                                    map = new HashMap<String, Object>();
                                    map.put("title", yonghuming);
                                    map.put("img", R.drawable.a);
                                    list.add(map);
                                    toast = Toast.makeText(getApplicationContext(), "好友添加成功", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, -200);
                                    toast.show();
                                    adapter.notifyDataSetChanged();
                                    cursor.close();
                                    db.close();
                                }
                            }

                        } else {
                            toast = Toast.makeText(getApplicationContext(), "该用户已在你的好友列表里面", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, -200);
                            toast.show();
                            cursor.close();
                            db.close();
                        }

                    }
                });
                builder.show();
                return false;
            }
        });
        popupMenu.show();
    }
    /**********************************************************右边十字架弹出框的函数结束*************************************************************/
    /**********************************************************右边十字架弹出框的函数结束*************************************************************/

    /**********************************************************主页面的数据显示和listview应用函数开始*************************************************/
    /**********************************************************主页面的数据显示和listview应用函数开始*************************************************/
    /* 主页数据显示，listitem的函数实现*/
       private List<Map<String, Object>> getData() {
        //获取数据用的，一个哈希map
        getShujuku();
        Cursor cursor = db.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='" + now_use + "'", null);
        //判断数据库的存在，这里一定是有数据返回的，存在的话是1，不存在的话是0，所以要用geiInt(0)获取这个值来判断
        cursor.moveToNext();
        if(cursor.getInt(0) == 0) {
            String sql = "CREATE TABLE " + now_use + " (\"id\"  INTEGER PRIMARY KEY AUTOINCREMENT, \"userName\"  TEXT(20), CONSTRAINT \"userName\" FOREIGN KEY (\"userName\") REFERENCES \"user\" (\"userName\"))";
            db.execSQL(sql);
            cursor.close();
            db.close();
        }  else {
            //查找出该用户的好友表，遍历显示出来
            cursor = db.rawQuery("Select userName From " + now_use, null);
            while(cursor.moveToNext()) {
                map = new HashMap<String, Object>();
                map.put("title", cursor.getString(0));
                map.put("img", R.drawable.a);
                list.add(map);
            }
            cursor.close();
            db.close();
        }
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
            //获取一个在数据集中指定索引的视图来显示数据
            ViewHolder holder = null;
            //如果缓存converView为空，则需创建View
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = yonghu.inflate(R.layout.my_listitem, null);
                holder.img = (ImageView)convertView.findViewById(R.id.img);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.button = (Button)convertView.findViewById(R.id.view_btn);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便取出Tag
                convertView.setTag(holder);
            } else {
                //缓存有，直接取出
                holder = (ViewHolder)convertView.getTag();
            }
            //设置创建的img数据为myData的第一个数据
            holder.img.setBackgroundResource((Integer)myData.get(position).get("img"));
            holder.title.setText((String)myData.get(position).get("title"));
            final String aaa = (String)myData.get(position).get("title");
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除对应的view，list.remove(position)是删除哈希表的数据，notifyDataSetChanged()是告知数据被改变需要更新。
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
            //监视每一个listview，点击的时候跳转到对应的activity
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //传递参数
                    intent.putExtra("title", aaa);
                    intent.setClass(Main.this, chat.class);
                    startActivity(intent);
                }
            });
            //监视每一个listview，点击的时候跳转到对应的activity
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //传递参数
                    intent.putExtra("title", aaa);
                    intent.setClass(Main.this, chat.class);
                    startActivity(intent);
                }
            });
            return convertView;
        }


    }
    /**********************************************************主页面的数据显示和listview应用函数结束*************************************************/
    /**********************************************************主页面的数据显示和listview应用函数结束*************************************************/

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
















