package cn.itcast.recycleview;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.SQLException;



public class zhuceActivity extends AppCompatActivity {
    public static String username_receive;
    public static String password_receive;
    String mingzi = "修改名称";
    int a;
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
        setContentView(R.layout.activity_zhuce);

        ImageView fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(zhuceActivity.this,dengluActivity.class);
                startActivity(intent);
                finish();
            }
        });
        final EditText username = (EditText)findViewById(R.id.phone);
        final EditText password = (EditText)findViewById(R.id.password);

        final Handler handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {

                if(!"".equals(username.getText().toString())){
                    if(!"".equals(password.getText().toString()))
                    {
                        if(isMobileNO(username.getText().toString())){
                            if (username_receive ==null || !username_receive.equals(username.getText().toString())) {
                                Toast.makeText(zhuceActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setClass(zhuceActivity.this, dengluActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(zhuceActivity.this, "账号已注册过", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(zhuceActivity.this, "手机号码格式错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(zhuceActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(zhuceActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        Button zhuce = (Button)findViewById(R.id.zhuce);
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try {
                            password_receive= connect.querycol(username.getText().toString());//调用查询语句，获得账号对应的密码
                            username_receive=connect.querycoll(username.getText().toString());

                        }catch (SQLException e) {
                            e.printStackTrace();
                        }
                        handler2.sendMessage(msg);//跳转到handler2
                        try{
                            if(!"".equals(username.getText().toString())){
                                if(!"".equals(password.getText().toString())) {
                                    if (isMobileNO(username.getText().toString())) {
                                        if (username_receive ==null || !username_receive.equals(username.getText().toString())) {
                                            connect.insertIntoData(username.getText().toString(), password.getText().toString(),mingzi);
                                        }
                                    }
                                }
                            }
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }
                ).start();

            }
        });
    }
}
