package cn.itcast.recycleview;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;



public class anquanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anquan);
        LinearLayout xiugai1 = findViewById(R.id.xiugai1);

        xiugai1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setClass(anquanActivity.this,xiugaimimaActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout zhuxiao = findViewById(R.id.zhuxiao);
        zhuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setClass(anquanActivity.this,zhuxiaoActivity.class);
                startActivity(intent);
            }
        });
        ImageView fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(anquanActivity.this,gerenziliaoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    }

