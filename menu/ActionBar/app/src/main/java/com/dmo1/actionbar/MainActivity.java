package com.dmo1.actionbar;
        import android.app.Activity;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.telephony.SmsManager;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.ViewConfiguration;
        import android.widget.Toast;

        import java.lang.reflect.Field;
        import java.sql.Date;
        import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            // 弹出当前时间操作
            case R.id.action_calendar:
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
                String str = formatter.format(curDate);
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                break;
            // 拨打电话给10086
            case R.id.action_call:
                Uri uri = Uri.parse("tel:10086");
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
                break;
            case R.id.action_msm:
                //给10086发送内容为10086的信息
                Uri uri1 = Uri.parse("smsto:10086");
                Intent intent1 = new Intent(Intent.ACTION_SENDTO, uri1);
                intent1.putExtra("sms_body", "Hello");
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
