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



public class xiugaimimaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiugaimima);
        final EditText username = (EditText)findViewById(R.id.phone);
        final EditText password = (EditText)findViewById(R.id.password);
        Intent intent = getIntent();
        final String haoma = intent.getStringExtra("shoujihao");
        final Handler handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if(haoma.equals(username.getText().toString())){
                    if(!"".equals(password.getText().toString())){
                        Toast.makeText(xiugaimimaActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("shoujihao",haoma);
                        intent.setClass(xiugaimimaActivity.this,anquanActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(xiugaimimaActivity.this,"密码为空，请重新输入！",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(xiugaimimaActivity.this,"账号不正确，请重新输入",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        Button xiugai = (Button)findViewById(R.id.xiugai);
        xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        handler2.sendMessage(msg);//跳转到handler2
                        try{
                            if(haoma.equals(username.getText().toString())) {
                                if(!"".equals(password.getText().toString())){
                                connect.updateData(username.getText().toString(), password.getText().toString());
                            }}
                        }catch (SQLException e){
                            e.printStackTrace();

                        }
                    }
                }).start();
            }
        });

        ImageView fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("shoujihao",haoma);
                intent.setClass(xiugaimimaActivity.this,anquanActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
