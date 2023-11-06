package cn.itcast.recycleview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.SQLException;

public class XiugaishoujihaoActivity extends AppCompatActivity {
    EditText shoujihao ;
    Button tijiao;
    public static String phon_user;//用于接收数据库查询的返回数据
    public static String username_receive;
    public static String password_receive;

    public static boolean isMobileNO(String mobiles) {

        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)){
            return false;
        }
        else return mobiles.matches(telRegex);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiugaishoujihao);
        shoujihao = findViewById(R.id.xiugaishoujihao);
        tijiao = findViewById(R.id.tijiao);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    phon_user = connect.reachone(1);
                    username_receive=connect.querycoll(shoujihao.getText().toString());
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

        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            if(isMobileNO(shoujihao.getText().toString())){
                                        connect.reviseone(1,shoujihao.getText().toString());
                                        connect.xiugaishoujihao(phon_user,shoujihao.getText().toString());
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


                    if(isMobileNO(shoujihao.getText().toString())){

                            Toast.makeText(XiugaishoujihaoActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(XiugaishoujihaoActivity.this,gerenziliaoActivity.class);
                            startActivity(intent);
                            finish();

            }}
        });
        ImageView fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(XiugaishoujihaoActivity.this,gerenziliaoActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
