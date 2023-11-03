package cn.itcast.recycleview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class JingdiandingdanActivity extends AppCompatActivity {
    private RecyclerView mlistview;
    private HomeAdapter mAdapter;
    private static String name;
    private String[] titles ;
    private String[] prices ;
    private String[] buys ;
    private String[] a1 = {};
    private CommonDialog myDialog;
    ImageView fanhui;
    public static String judge_user;//用于接收数据库查询的返回数据
    private TextView t1;
    private TextView t2;
    int a =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jingdiandingdan);
        t1=findViewById(R.id.t1);
        t2 =findViewById(R.id.t2);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    titles = connect.namequeryone();
                    prices = connect.namequerytwo();
                    buys = connect.namequerythird();
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
        fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.putExtra("two",a);
                intent.setClass(JingdiandingdanActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mlistview = findViewById(R.id.iv);
        mAdapter = new HomeAdapter();
        mlistview.setLayoutManager(new LinearLayoutManager(this));
        mlistview.setAdapter(mAdapter);

    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
           MyViewHolder holder = new MyViewHolder(LayoutInflater.from(JingdiandingdanActivity.this).inflate(R.layout.list_item,parent,false));
            return holder   ;
        }


        @Override
        public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, final int position) {
            if(judge_user.equals("1")) {

                holder.title.setText(titles[position]);
                holder.price.setText(prices[position]);
                holder.buy.setText(buys[position]);
                holder.shanchu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog=new CommonDialog(JingdiandingdanActivity.this,R.style.MyDialog);
                        myDialog.setTitle("提示！");
                        myDialog.setMessage("提示：您确定要取消订单吗！");
                        myDialog.setYesOnclickListener("确定", new CommonDialog.onYesOnclickListener() {
                            @Override
                            public void onYesOnclick() {
                                new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            connect.deleteone(titles[position]);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                                Toast.makeText(JingdiandingdanActivity.this,"恭喜你！订单取消成功",Toast.LENGTH_LONG).show();
                                myDialog.dismiss();
                                Intent i = new Intent();
                                i.setClass(JingdiandingdanActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try{
                                            titles = connect.namequeryone();
                                            prices = connect.namequerytwo();
                                            buys = connect.namequerythird();
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


                            }
                        });

                        myDialog.setNoOnclickListener("取消", new CommonDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                Toast.makeText(JingdiandingdanActivity.this,"很抱歉！订单取消失败",Toast.LENGTH_LONG).show();
                                myDialog.dismiss();
                            }
                        });
                        myDialog.show();

                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(JingdiandingdanActivity.this, DingdanActivity.class);
                        intent.putExtra("detail_titles", titles[position]);
                        intent.putExtra("detail_prices", prices[position]);
                        intent.putExtra("detail_buys", buys[position]);
                        startActivity(intent);
                        finish();
                    }
                });
            }
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

            public MyViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.title);
                price = view.findViewById(R.id.price);
                buy = view.findViewById(R.id.iv_buy);
                shanchu = view.findViewById(R.id.shanchu);

            }
        }
    }
}
