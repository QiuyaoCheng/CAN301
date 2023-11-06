package cn.itcast.recycleview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.SQLException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XiugaiyouxiangActivity extends AppCompatActivity {
    ImageView fanhui;
    EditText youxiang;
    public static String phon_user;//用于接收数据库查询的返回数据
    public static String judge_user;//用于接收数据库查询的返回数据
    public static String phone_youxiang;//用于接收数据库查询的返回数据
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiugaiyouxiang);
        fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(XiugaiyouxiangActivity.this,gerenziliaoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    phon_user = connect.reachone(1);
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
        youxiang = findViewById(R.id.xiugaiyouxiang);
        youxiang.setText(phone_youxiang);

        Button tijiao = findViewById(R.id.tijiao);
        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            if(isEmail(youxiang.getText().toString())){
                            connect.xiugaiyouxiang(phon_user,youxiang.getText().toString());
                        }

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
                if(isEmail(youxiang.getText().toString())){
                    Toast.makeText(XiugaiyouxiangActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(XiugaiyouxiangActivity.this,gerenziliaoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
