package cn.itcast.recycleview;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2022/8/12.
 */

public class ThirdFragment extends Fragment {
    private RecyclerView mlistview;
    private HomeAdapter mAdapter;
    private RecyclerView listview;
    private HomeAdaptertwo madaptertwo;
    private static String name;
    private String[] titles ;
    private String[] prices ;
    private String[] buys ;
    private String[] a1 = {};
    private String[] jiudiantitles ;
    private String[] jiudianprices ;
    private String[] jiudianbuys;
    private String[] jiudianintroduce;
    String n1;

    private CommonDialog myDialog;
    public static String judge_user;//用于接收数据库查询的返回数据
    private TextView t1;
    private TextView t2;
    private TextView jingdian;
    private TextView jiudian;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_third, container, false);
        t1=view.findViewById(R.id.t1);
        t2 = view.findViewById(R.id.t2);
        jingdian = view.findViewById(R.id.jingdian);
        jiudian = view.findViewById(R.id.jiudian);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                jiudiantitles = null;
                jiudianbuys = null;
                jiudianintroduce = null;
                jiudianprices =null;
                try{
                    titles = connect.namequeryone();
                    prices = connect.namequerytwo();
                    buys = connect.namequerythird();
                    judge_user = connect.reachtwo(1);

                    jiudiantitles = connect.jiudianqueryone();
                    jiudianprices = connect.jiudianquerythree();
                    jiudianbuys = connect.jiudianqueryfour();
                    jiudianintroduce = connect.jiudianquerytwo();

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
            jiudiantitles = a1;
            t2.setText("");
            t1.setText("请先登录后查看全部订单");
            jingdian.setText("");
            jiudian.setText("");
        }
        //                try {
//                    for (int i = 1; i < 5; i++) {
//                        name = connect.namequery(i);
//                        titles[i - 1] = name;
//                        if("".equals(name)){
//                            break;
//                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }

        listview = view.findViewById(R.id.iv2);
        madaptertwo = new HomeAdaptertwo();
        listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        listview.setAdapter(madaptertwo);

        mlistview = view.findViewById(R.id.iv);
        mAdapter = new HomeAdapter();
        mlistview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mlistview.setAdapter(mAdapter);
        return view;
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.list_item,parent,false));
            return holder   ;
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            if(judge_user.equals("1")) {

                holder.title.setText(titles[position]);
                holder.price.setText(prices[position]);
                holder.buy.setText(buys[position]);
                holder.shanchu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog=new CommonDialog(getActivity(),R.style.MyDialog);
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
                                Toast.makeText(getActivity(),"恭喜你！订单取消成功",Toast.LENGTH_LONG).show();
                                myDialog.dismiss();
                                Intent i = new Intent();
                                i.setClass(getActivity(),MainActivity.class);
                                startActivity(i);
                                getActivity().finish();
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
                                Toast.makeText(getActivity(),"很抱歉！订单取消失败",Toast.LENGTH_LONG).show();
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
                        intent.setClass(getActivity(), DingdanActivity.class);
                        intent.putExtra("detail_titles", titles[position]);
                        intent.putExtra("detail_prices", prices[position]);
                        intent.putExtra("detail_buys", buys[position]);
                        startActivity(intent);
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
    class HomeAdaptertwo extends RecyclerView.Adapter<HomeAdaptertwo.MyViewHolder>{
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.recycle_jiudaindingdan,parent,false));
            return holder   ;
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.title.setText(jiudiantitles[position]);
            holder.price.setText(jiudianprices[position]);
            holder.buy.setText(jiudianbuys[position]);
            holder.type.setText(jiudianintroduce[position]);
            n1 = jiudiantitles[position];
            holder.shanchu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog=new CommonDialog(getActivity(),R.style.MyDialog);
                    myDialog.setTitle("提示！");
                    myDialog.setMessage("提示：您确定要取消订单吗！");
                    myDialog.setYesOnclickListener("确定", new CommonDialog.onYesOnclickListener() {
                        @Override
                        public void onYesOnclick() {
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        connect.shanchujiudian(jiudiantitles[position],jiudianintroduce[position]);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                            Toast.makeText(getActivity(),"恭喜你！订单取消成功",Toast.LENGTH_LONG).show();
                            myDialog.dismiss();

                            Intent i = new Intent();
                            i.setClass(getActivity(),MainActivity.class);
                            startActivity(i);



                        }
                    });

                    myDialog.setNoOnclickListener("取消", new CommonDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            Toast.makeText(getActivity(),"很抱歉！订单取消失败",Toast.LENGTH_LONG).show();
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
                    intent.setClass(getActivity(), JiudiandingdanxiangqingActivity.class);
                    intent.putExtra("detail_titles", jiudiantitles[position]);
                    intent.putExtra("detail_prices", jiudianprices[position]);
                    intent.putExtra("detail_buys", jiudianbuys[position]);
                    intent.putExtra("detail_introduce",jiudianintroduce[position]);
                    startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return jiudiantitles.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView price;
            TextView buy;
            Button shanchu;
            TextView type;

            public MyViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.title);
                price = view.findViewById(R.id.price);
                buy = view.findViewById(R.id.iv_buy);
                shanchu = view.findViewById(R.id.shanchu);
                type= view.findViewById(R.id.type);
            }
        }
    }

}
