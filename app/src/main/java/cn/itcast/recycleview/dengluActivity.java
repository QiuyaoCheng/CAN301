package cn.itcast.recycleview;

import android.content.Intent;

import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.sql.SQLException;




public class dengluActivity extends AppCompatActivity {
    public static int conn_on=0;//用于判断连接是否成功
    public static String password_receive;//用于接收数据库查询的返回数据
    public static String username_receive;
    int a =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        final EditText username = (EditText) findViewById(R.id.IDinput);//取得输入框的对象
        final EditText password = (EditText) findViewById(R.id.code_input);

        ImageView fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("two",a);
                intent.setClass(dengluActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button Register = findViewById(R.id.log_on_button);
        Register.setOnClickListener(new View.OnClickListener() {//注册
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(dengluActivity.this, zhuceActivity.class);
                startActivity(intent);
            }
        });

        final Handler handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if(username_receive ==null){
                    Toast.makeText(dengluActivity.this,"账号不存在",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!"".equals(password_receive)){
                        if (!"".equals(username.getText().toString())) {
                            if (!"".equals(password.getText().toString())) {
                                if (username_receive.equals(username.getText().toString())) {
                                    if (password_receive.equals(password.getText().toString())) {
                                        Toast.makeText(dengluActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent();
                                        intent.putExtra("two",a);
                                        intent.setClass(dengluActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(dengluActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(dengluActivity.this, "账号错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(dengluActivity.this, "密码为空,请输入", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(dengluActivity.this, "账号为空,请输入", Toast.LENGTH_SHORT).show();
                        }
                    }}

                return false;

            }
        });

        Button logon = findViewById(R.id.log_on);
        logon.setOnClickListener(new View.OnClickListener() {//登录
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {//创建多线程，分开后的任务会同步进行，无需等待更多时间，效率也会更高
                    @Override
                    public void run() {
                        password_receive=null;
                        username_receive=null;
                        Message msg = new Message();
                        try {
                            password_receive = connect.querycol(username.getText().toString());//调用查询语句，获得账号对应的密码
                            username_receive = connect.querycoll(username.getText().toString());//调用查询语句，获得账号对应的账号
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        handler2.sendMessage(msg);//跳转到handler2
                        try{
                            if(username_receive!=null){
                                if(!"".equals(password_receive)){
                                    if (!"".equals(username.getText().toString()))
                                    {
                                        if (!"".equals(password.getText().toString()))
                                        {
                                            if (username_receive.equals(username.getText().toString()))
                                            {
                                                if (password_receive.equals(password.getText().toString()))
                                                {
                                                    connect.reviseone(1,username.getText().toString());
                                                    connect.revistwo(1,1);
                                                }
                                            }
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
