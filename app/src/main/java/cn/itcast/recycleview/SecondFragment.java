package cn.itcast.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;


public class SecondFragment extends Fragment {
    TextView mingcheng;
    LinearLayout gerenziliao;
    LinearLayout jingdian;
    LinearLayout jiudian;

    public static String phon_user;//用于接收数据库查询的返回数据
    public static String judge_user;//用于接收数据库查询的返回数据
    public static String phone_name;//用于接收数据库查询的返回数据
    String  phone;
    String haoma;
    TextView t2;
    TextView t3;
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main2, container, false);
        mingcheng=view.findViewById(R.id.mingcheng);
        jingdian = view.findViewById(R.id.jingdiandingdan);
        jiudian = view.findViewById(R.id.jiudiandingdan);
        t2 = view.findViewById(R.id.t2);
        t3 = view.findViewById(R.id.t3);

//        mingcheng.setText(Result.getResult());
        haoma = Result.getResult();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    phon_user = connect.reachone(1);
                    judge_user = connect.reachtwo(1);
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

        if(!"".equals(phon_user)){
            if(!"".equals(judge_user)){
                if(judge_user.equals("1")){
                    phone = phon_user;
                    if(!"".equals(phone_name)){
                        mingcheng.setText(phone_name+"用户");
                    }

                }else {
                    t2.setText(null);
                    t3.setText("您好，尊敬的用户");
                }

            }
        }

        jiudian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),JiudiandingdanActivity.class);
                startActivity(intent);

            }
        });

        jingdian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),JingdiandingdanActivity.class);
                startActivity(intent);

            }
        });

        mingcheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equals(mingcheng.getText().toString())){
                    Intent intent = new Intent();
                    intent.putExtra("shoujihao",haoma);
                    intent.setClass(getActivity(),dengluActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else{
                    Intent intent = new Intent();
                    intent.putExtra("shoujihao",phone);
                    intent.putExtra("mingzi",mingcheng.getText().toString());
                    intent.setClass(getActivity(),gerenziliaoActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        gerenziliao = view.findViewById(R.id.gerenziliao);
        gerenziliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!"".equals(mingcheng.getText().toString())){
                    Intent intent = new Intent();
                    intent.putExtra("shoujihao",phone);
                    intent.putExtra("mingzi",mingcheng.getText().toString());
                    intent.setClass(getActivity(),gerenziliaoActivity.class);
                    startActivity(intent);}
                else{
                    Toast.makeText(getActivity(),"请先登录!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        LinearLayout tuichu = view.findViewById(R.id.tuichu);
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
        return view;
    }

    final Handler handler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Intent intent = new Intent();
            intent.setClass(getActivity(),MainActivity.class);
            startActivity(intent);
            return false;
        }
    });
}
