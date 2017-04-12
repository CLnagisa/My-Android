package com.dmo1.datepickerdialogapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.widget.Button;
import android.widget.DatePicker;
import android.view.View.OnClickListener;
import android.app.DatePickerDialog;

public class MainActivity extends AppCompatActivity {

    private TextView showdate;
    private Button setdate;
    private int year;
    private int month;
    private int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showdate = (TextView) this.findViewById(R.id.showtime);
        setdate = (Button) this.findViewById(R.id.setdate);
        //初始化Calendar日历对象
        Calendar c = Calendar.getInstance(Locale.CHINA);
        final Date mydate = new Date();   //获取当前的时间
        c.setTime(mydate);  //为Calendar对象设置时间为当前日期

        year = c.get(Calendar.YEAR);  //获取Calendar对象中的年
        month = c.get(Calendar.MONDAY);  //获取Calendar对象中的月
        day = c.get(Calendar.DAY_OF_MONTH); //获取这个月的第几天
        showdate.setText("当前日期：" + year + "-" + (month + 1) + "-" + day); //显示当前的年月日

        //添加单机事件设置日期
        setdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建DatePIckerDialog对象
                DatePickerDialog my_datePickerDialog = new DatePickerDialog(MainActivity.this, Datelistener, year, month, day);


                long time = mydate.getTime();
                my_datePickerDialog.getDatePicker().setMaxDate(time);   //设置时间最大值


                my_datePickerDialog.show(); //显示DateOickerDialog组件

            }
        });
    }

    private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker v, int y, int m, int d) {
            //修改year， month，day的变量值，以便以后单机按钮时，datePickerDialog上显示上一次修改后的值
            year = y;
            month = m;
            day = d;
            //更新日期
            updateDate();
        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate() {
            //在TextView上显示日期
            showdate.setText("当前日期：" + year + "-" + (month + 1) + "-" + day);
        }
    };
}
