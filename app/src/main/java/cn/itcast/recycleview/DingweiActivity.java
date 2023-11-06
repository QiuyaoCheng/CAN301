package cn.itcast.recycleview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;

public class DingweiActivity extends AppCompatActivity {
    private ListView mlistview;
    private String[] name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingwei);
        ImageView fanhui = findViewById(R.id.jiantou);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(DingweiActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                 name = connect.dingdianreach();
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
        mlistview = findViewById(R.id.name);
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        mlistview.setAdapter(mAdapter);
    }
    class MyBaseAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return name[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(DingweiActivity.this,R.layout.list_itemtwo,null);
            TextView di = view.findViewById(R.id.title);
            di.setText(name[position]);
            mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                connect.dizhione(1,name[position]);
                            }catch (SQLException e ){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    Intent intent = new Intent();
                    intent.setClass(DingweiActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            return view;
        }
    }
}
