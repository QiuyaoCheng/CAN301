package cn.itcast.recycleview;

import android.content.Intent;

import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;


public class gerenziliaoActivity extends AppCompatActivity {
   final String i = null;
    int a =2;
    public static String phon_user;//用于接收数据库查询的返回数据
    public static String judge_user;//用于接收数据库查询的返回数据
    public static String phone_name;//用于接收数据库查询的返回数据
    public static String phone_youxiang;//用于接收数据库查询的返回数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenziliao);
        final  TextView mingzi = findViewById(R.id.xiugaimingzi);
        final TextView shoujihao = findViewById(R.id.shoujihao);
        final TextView youxiangming = findViewById(R.id.xiugaiyouxiang);
        Intent intent = getIntent();
        final String haoma = intent.getStringExtra("shoujihao");
        final String mingcheng = intent.getStringExtra("mingzi");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    phon_user = connect.reachone(1);
                    judge_user = connect.reachtwo(1);
                    phone_name = connect.reachthird(phon_user);
                    phone_youxiang = connect.reachform(phon_user);
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        shoujihao.setText(phon_user);
        mingzi.setText(phone_name);
        youxiangming.setText(phone_youxiang);

        ImageView fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Result.setResult(haoma);
                intent.putExtra("two",a);
                intent.setClass(gerenziliaoActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        LinearLayout ningcheng = findViewById(R.id.ningcheng);
        ningcheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setClass(gerenziliaoActivity.this,XiugaimingchengActivity.class);
                startActivity(intent);
                finish();
            }
        });
        LinearLayout shouji = findViewById(R.id.shouji);
        shouji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setClass(gerenziliaoActivity.this,XiugaishoujihaoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        LinearLayout zhanghao = findViewById(R.id.zhanghao);
        zhanghao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setClass(gerenziliaoActivity.this,anquanActivity.class);
                startActivity(intent);
                finish();
            }
        });
        LinearLayout youxiang  = findViewById(R.id.youxiang);
        youxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(gerenziliaoActivity.this,XiugaiyouxiangActivity.class);
                startActivity(intent);
                finish();
            }
        });
        LinearLayout tuichu = findViewById(R.id.tuichu);
        tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try{
                        connect.revistwo(1,0);
                    }catch (SQLException e){
                            e.printStackTrace();
                        }
                        handler2.sendMessage(msg);//跳转到handler2
                    }
                }).start();

            }
        });

    }
    final Handler handler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Intent intent = new Intent();
            intent.putExtra("two",a);
            intent.setClass(gerenziliaoActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return false;
        }
    });

}
