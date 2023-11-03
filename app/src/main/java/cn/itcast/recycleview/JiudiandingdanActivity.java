package cn.itcast.recycleview;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JiudiandingdanActivity extends AppCompatActivity {
    private RecyclerView mRecycleview;
    private HomeAdapter mAdapter;
    private String[] titles={} ;
    private String[] prices={} ;
    private String[] buys ={};
    private String[] introduce={};

    private String[] a1 = {};
    String n1;
    ImageView fanhui;
    public static String judge_user;//用于接收数据库查询的返回数据
    private CommonDialog myDialog;
    int a =2;
    TextView t1 ,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiudiandingdan);
        t1=findViewById(R.id.t1);
        t2 =findViewById(R.id.t2);
        fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.putExtra("two",a);
                intent.setClass(JiudiandingdanActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    titles = connect.jiudianqueryone();
                    prices = connect.jiudianquerythree();
                    buys = connect.jiudianqueryfour();
                    introduce = connect.jiudianquerytwo();
                    judge_user = connect.reachtwo(1);
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
        if(judge_user.equals("0")){
            titles  = a1;
            t2.setText("");
            t1.setText("请先登录后查看全部订单");
        }

        mRecycleview = findViewById(R.id.iv);
        mAdapter = new HomeAdapter();
        mRecycleview.setLayoutManager(new LinearLayoutManager(this));
        mRecycleview.setAdapter(mAdapter);
    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
       MyViewHolder holder = new MyViewHolder(LayoutInflater.from(JiudiandingdanActivity.this).inflate(R.layout.recycle_jiudaindingdan,parent,false));
            return holder   ;
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

                holder.title.setText(titles[position]);
                holder.price.setText(prices[position]);
                holder.buy.setText(buys[position]);
                holder.type.setText(introduce[position]);
                n1 = titles[position];
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(JiudiandingdanActivity.this, JiudiandingdanxiangqingActivity.class);
                    intent.putExtra("detail_titles", titles[position]);
                    intent.putExtra("detail_prices", prices[position]);
                    intent.putExtra("detail_buys", buys[position]);
                    intent.putExtra("detail_introduce",introduce[position]);
                    startActivity(intent);

                }
            });
            holder.shanchu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog=new CommonDialog(JiudiandingdanActivity.this,R.style.MyDialog);
                    myDialog.setTitle("提示！");
                    myDialog.setMessage("提示：您确定要取消订单吗！");
                    myDialog.setYesOnclickListener("确定", new CommonDialog.onYesOnclickListener() {
                        @Override
                        public void onYesOnclick() {
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        connect.shanchujiudian(titles[position],introduce[position]);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                            Toast.makeText(JiudiandingdanActivity.this,"恭喜你！订单取消成功",Toast.LENGTH_LONG).show();
                            myDialog.dismiss();

                            Intent i = new Intent();
                            i.putExtra("two",a);
                            i.setClass(JiudiandingdanActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();


                        }
                    });

                    myDialog.setNoOnclickListener("取消", new CommonDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            Toast.makeText(JiudiandingdanActivity.this,"很抱歉！订单取消失败",Toast.LENGTH_LONG).show();
                            myDialog.dismiss();
                        }
                    });
                    myDialog.show();

                }
    });

}

        @Override
        public int getItemCount() {
            return titles.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView price;
            TextView buy;
            Button shanchu;
            TextView type;
            Button dingwei;
            public MyViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.title);
                price = view.findViewById(R.id.price);
                buy = view.findViewById(R.id.iv_buy);
                shanchu = view.findViewById(R.id.shanchu);
                type= view.findViewById(R.id.type);

            }
        }}
  }

