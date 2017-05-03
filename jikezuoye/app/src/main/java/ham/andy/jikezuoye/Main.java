package ham.andy.jikezuoye;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import org.w3c.dom.Text;

public class Main extends Activity {
    private PopupWindow popupWindow;  //从右出来的弹出框
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

        /*点击头像右边弹出框的实现*/
        ImageView touxiang = (ImageView)findViewById(R.id.touxiang);
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupWindow();  //初始化类
            }
        });
        /*点击右边的十字架出现操作按钮*/
        final ImageView caozuo = (ImageView)findViewById(R.id.caozuo);
        caozuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(caozuo);
            }
        });
        /*点击用户跳转到不同的聊天页面*/
        LinearLayout linear = null;
        linear = (LinearLayout)findViewById(R.id.linear);
        TextView[] text = new TextView[5];
        TextView fengexian = new TextView(this);
        fengexian.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
        fengexian.setBackgroundColor(Color.GRAY);
        linear.addView(fengexian);
        for(int i=0; i < 5; i++) {
            // 第一个参数为宽的设置，第二个参数为高的设置。
            text[i] = new TextView(this);
            text[i].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 60));
            text[i].setText("第一项");
            //设置居中
            text[i].setGravity(Gravity.CENTER_VERTICAL);
            linear.addView(text[i]);
            //插入分割线

        }
    }



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
                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        popupMenu.show();
    }
}