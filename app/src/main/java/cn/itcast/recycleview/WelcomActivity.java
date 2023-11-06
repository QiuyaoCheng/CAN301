package cn.itcast.recycleview;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.graphics.Bitmap;
import android.os.Handler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
public class WelcomActivity extends AppCompatActivity {
    private TextView textView;
    //声明时间有多少;
    private int count = 5;
    int a =0;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcom);

// 初始化控件对象textView

        textView = (TextView) findViewById(R.id.textView);
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_text);//透明度  加载动画效果
        handler.sendEmptyMessageDelayed(0, 1000);

    }


    private int getCount() {
        Button tiaoguo = findViewById(R.id.tiaoguo);

        tiaoguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(WelcomActivity.this, MainActivity.class);
                startActivity(intent);
                a=1;
            }
        });

        if(a==0){
            count--;
        if (count == 0) {

            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);

            finish();
        }
        }




        return count;

    }

//进行一个消息的处理

    @SuppressLint("HandlerLeak")

    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {

            if (msg.what == 0) {

                textView.setText(getCount()+"");

                handler.sendEmptyMessageDelayed(0, 1000);

                animation.reset();

                textView.startAnimation(animation);

            }

        };

    };

}

