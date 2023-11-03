package cn.itcast.recycleview;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.SQLException;


public class zhuxiaoActivity extends AppCompatActivity {

    public static String username_receive;
    int a=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuxiao);
        final Intent intent = getIntent();
        final String haoma = intent.getStringExtra("shoujihao");
        final EditText username = (EditText)findViewById(R.id.phone);

        final Handler handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if(!"".equals(username_receive)){
                    if(!"".equals(username.getText().toString()))
                    {
                        if(username_receive.equals(username.getText().toString()))
                        {
                            Toast.makeText(zhuxiaoActivity.this,"注销成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            Result.setResult(username.getText().toString());
                            intent.putExtra("two",a);
                            intent.setClass(zhuxiaoActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(zhuxiaoActivity.this,"账号不正确，请重新输入!",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(zhuxiaoActivity.this,"账号为空，请重新输入!",Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
//        因为服务端和数据库不是实时同步的，有些数据是缓存在服务端或者客户端本地的，所以一段时间内仍然是可以查询到的！
        Button zhuxiao = (Button)findViewById(R.id.zhuxiao);
        zhuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try {
                            username_receive = connect.querycoll(username.getText().toString());//调用查询语句，获得账号对应的账号
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        handler2.sendMessage(msg);//跳转到handler2
                        try{
                            if(username_receive!=null){
                                if(!"".equals(username.getText().toString()))
                                {
                                    if(username_receive.equals(username.getText().toString()))
                                    {
                                        connect.revistwo(1,0);
                                        connect.reviseone(1,null);
                                        connect.delete(username.getText().toString());
                                    }
                                }
                            }
                        }catch (SQLException e){
                        e.printStackTrace();
                    }}
                }).start();

            }
        });
        ImageView fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("shoujihao",haoma);
                intent.setClass(zhuxiaoActivity.this,anquanActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
