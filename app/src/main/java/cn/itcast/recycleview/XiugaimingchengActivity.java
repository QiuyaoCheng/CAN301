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

public class XiugaimingchengActivity extends AppCompatActivity {
    public static String phon_user;//用于接收数据库查询的返回数据
    public static String judge_user;//用于接收数据库查询的返回数据
    public static String phone_name;//用于接收数据库查询的返回数据
    private int gray = 0xFF7597B3;
     EditText ningcheng;
     Button tijiao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiugaimingcheng);
        tijiao = findViewById(R.id.tijiao);
        ningcheng = findViewById(R.id.xiugainicheng);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    phon_user = connect.reachone(1);
                    phone_name = connect.reachthird(phon_user);
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
        ningcheng.setText(phone_name);
        if("修改名称".equals(ningcheng.getText().toString())){
            tijiao.setTextColor(gray);

        }

            tijiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(!"修改名称".equals(ningcheng.getText().toString())){
                                try{
                                    connect.xiugainingcheng(phon_user,ningcheng.getText().toString());
                                }catch (SQLException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(!"修改名称".equals(ningcheng.getText().toString())){
                    Toast.makeText(XiugaimingchengActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent();
                    intent.setClass(XiugaimingchengActivity.this,gerenziliaoActivity.class);
                    startActivity(intent);
                    finish();}

                }
            });

        ImageView fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(XiugaimingchengActivity.this,gerenziliaoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        }
    }

