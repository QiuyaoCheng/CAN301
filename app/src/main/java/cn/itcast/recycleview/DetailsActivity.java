package cn.itcast.recycleview;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    Button open;
    private DialogOrderTypeFragment mFragment2=new DialogOrderTypeFragment();
    String name;
    RelativeLayout r1;
    public static String judge_user;//用于接收数据库查询的返回数据
    private CommonDialog myDialog;
    private Button button;
//    private RecyclerView mRecyclerView;
//    private HomeAdapter mAdapter;

    private Banner banner;
    private GlideImageLoader glideImageLoader;
    private List<String> imagePath;
    private List<String> imageTitle;

    private OneFragment fg1;
    private TwoFragment fg2;
    private TextView firstText;
    private TextView secondText;
    private RelativeLayout firstLayout;
    private RelativeLayout secondLayout;
    private FragmentManager fragmentManager;
    private int whirt = 0xFFFFFFFF;
    private int gray = 0xFF7597B3;
    private int dark = 0xff000000;
    int img;
    int f;
   String ziliao;
     String buy1;
   String name_receive1;
    private int a = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_details);
        fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();

        f = intent.getIntExtra("f",0);
        name = intent.getStringExtra("detail_name");
        ziliao = intent.getStringExtra("detail_introduce");
        buy1 = intent.getStringExtra("detail_buy");
         img =intent.getIntExtra("detail_iv",0);
       name_receive1 = intent.getStringExtra("name");//景点地区


        final TextView menpiao = findViewById(R.id.menpiao);
        menpiao.setText(buy1);

        TextView jieshouname = findViewById(R.id.jieshouname);
        jieshouname.setText(name);

        TextView jieshouintroduce = findViewById(R.id.jieshouintroduce);
        jieshouintroduce.setText(ziliao);

        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialog=new CommonDialog(DetailsActivity.this,R.style.MyDialog);
                myDialog.setTitle("提示！");
                myDialog.setMessage("提示：您确定要购买吗！");

                myDialog.setYesOnclickListener("确定", new CommonDialog.onYesOnclickListener() {
                    @Override
                    public void onYesOnclick() {

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{

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
                        if(judge_user.equals("1")){
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        connect.create(name,ziliao,buy1);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                            Toast.makeText(getApplicationContext(),"恭喜你！购票成功",Toast.LENGTH_LONG).show();
                        }
                      if(judge_user.equals("0")){
                          Toast.makeText(getApplicationContext(),"请先登录后再进行购票!",Toast.LENGTH_LONG).show();
                      }
                        myDialog.dismiss();
                    }
                });
                myDialog.setNoOnclickListener("取消", new CommonDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        Toast.makeText(getApplicationContext(),"很抱歉！购票失败",Toast.LENGTH_LONG).show();
                        myDialog.dismiss();
                    }
                });
               myDialog.show();
            }
        });
        open=(Button)findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment2.show(getFragmentManager(), "android");

            }
        });

        ImageView fanhui = findViewById(R.id.image);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f==0){
                    Intent intent =new Intent();
                    intent.setClass(DetailsActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(f==1){
                    Intent intent =new Intent();
                    intent.setClass(DetailsActivity.this,ZhujiemianActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        mFragment2.setOnDialogListener(new DialogOrderTypeFragment.OnDialogListener() {
            @Override
            public void onDialogClick(String person,String code) {

                if (code.equals("0"))
                {
                    openMap1();

                }else if(code.equals("1"))
                {
                    openMap2();
                }else
                {

                }

            }
        });

        r1 = findViewById(R.id.R1);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.putExtra("image",img);
                intent.putExtra("name1",name);
                intent.putExtra("buy1",buy1);
                intent.putExtra("troduce",ziliao);
                intent.putExtra("name",name_receive1);
                intent.setClass(DetailsActivity.this,JianshaoActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        mRecyclerView = findViewById(R.id.recycle);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mAdapter = new HomeAdapter();
//        mRecyclerView.setAdapter(mAdapter);

        initDate();
        initView();
        initView1();
        setChioceItem(a); // 初始化页面加载时显示第一个选项卡
    }
    private void initView1() {
        firstText = (TextView) findViewById(R.id.first_text);
        secondText = (TextView) findViewById(R.id.second_text);
        firstLayout = (RelativeLayout) findViewById(R.id.first_layout);
        secondLayout = (RelativeLayout) findViewById(R.id.second_layout);
        firstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChioceItem(0);
            }
        });
        secondLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChioceItem(1);
            }
        });


    }
    private void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        hideFragments(fragmentTransaction);
        switch (index) {
            case 0:
// firstImage.setImageResource(R.drawable.XXXX); 需要的话自行修改
                firstText.setTextColor(dark);
                firstLayout.setBackgroundColor(gray);
// 如果fg1为空，则创建一个并添加到界面上
                if (fg1 == null) {
                    fg1 = new OneFragment();
                    fragmentTransaction.add(R.id.content, fg1);
                } else {
// 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(fg1);
                }
                break;
            case 1:
                secondText.setTextColor(dark);
                secondLayout.setBackgroundColor(gray);
                if (fg2 == null) {
                    fg2 = new TwoFragment();
                    fragmentTransaction.add(R.id.content, fg2);
                } else {
                    fragmentTransaction.show(fg2);
                }
                break;
        }
        fragmentTransaction.commit(); // 提交
    }
    private void clearChioce() {
// firstImage.setImageResource(R.drawable.XXX);
        firstText.setTextColor(gray);
        firstLayout.setBackgroundColor(whirt);
// secondImage.setImageResource(R.drawable.XXX);


        secondText.setTextColor(gray);
        secondLayout.setBackgroundColor(whirt);

    }
    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) {
            fragmentTransaction.hide(fg1);
        }
        if (fg2 != null) {
            fragmentTransaction.hide(fg2);
        }}
    private void initDate() {
        imagePath = new ArrayList<>();
        imageTitle = new ArrayList<>();
        if(name.equals("三都澳")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E4%B8%89%E9%83%BD%E6%BE%B3/sanduao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E4%B8%89%E9%83%BD%E6%BE%B3/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E4%B8%89%E9%83%BD%E6%BE%B3/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E4%B8%89%E9%83%BD%E6%BE%B3/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E4%B8%89%E9%83%BD%E6%BE%B3/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E4%B8%89%E9%83%BD%E6%BE%B3/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E4%B8%89%E9%83%BD%E6%BE%B3/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E4%B8%89%E9%83%BD%E6%BE%B3/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E4%B8%89%E9%83%BD%E6%BE%B3/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E4%B8%89%E9%83%BD%E6%BE%B3/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E4%B8%89%E9%83%BD%E6%BE%B3/9.png");


            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");

        }
        if(name.equals("霍童古镇")){

            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%9C%8D%E7%AB%A5%E5%8F%A4%E9%95%87/huotongguzheng.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%9C%8D%E7%AB%A5%E5%8F%A4%E9%95%87/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%9C%8D%E7%AB%A5%E5%8F%A4%E9%95%87/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%9C%8D%E7%AB%A5%E5%8F%A4%E9%95%87/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%9C%8D%E7%AB%A5%E5%8F%A4%E9%95%87/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%9C%8D%E7%AB%A5%E5%8F%A4%E9%95%87/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%9C%8D%E7%AB%A5%E5%8F%A4%E9%95%87/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%9C%8D%E7%AB%A5%E5%8F%A4%E9%95%87/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%9C%8D%E7%AB%A5%E5%8F%A4%E9%95%87/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%9C%8D%E7%AB%A5%E5%8F%A4%E9%95%87/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%9C%8D%E7%AB%A5%E5%8F%A4%E9%95%87/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
       if(name.equals("宁德东湖水利风景区")){
           imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/shuili.png");
           imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/1.png");
           imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/10.png");
           imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/11.png");
           imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/2.png");
           imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/3.png");
           imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/4.png");
           imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/5.png");
           imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/6.png");
           imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/7.png");
           imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/8.png");
           imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/9.png");

           imageTitle.add("景点图片");
           imageTitle.add("景点图片");
           imageTitle.add("景点图片");
           imageTitle.add("景点图片");
           imageTitle.add("景点图片");
           imageTitle.add("景点图片");
           imageTitle.add("景点图片");
           imageTitle.add("景点图片");
           imageTitle.add("景点图片");
           imageTitle.add("景点图片");
           imageTitle.add("景点图片");
           imageTitle.add("景点图片");
       }
        if(name.equals("福建支提山国家森林公园")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E7%A6%8F%E5%BB%BA%E6%94%AF%E6%8F%90%E5%B1%B1%E5%9B%BD%E5%AE%B6%E6%A3%AE%E6%9E%97%E5%85%AC%E5%9B%AD/senglin.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E7%A6%8F%E5%BB%BA%E6%94%AF%E6%8F%90%E5%B1%B1%E5%9B%BD%E5%AE%B6%E6%A3%AE%E6%9E%97%E5%85%AC%E5%9B%AD/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E7%A6%8F%E5%BB%BA%E6%94%AF%E6%8F%90%E5%B1%B1%E5%9B%BD%E5%AE%B6%E6%A3%AE%E6%9E%97%E5%85%AC%E5%9B%AD/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E7%A6%8F%E5%BB%BA%E6%94%AF%E6%8F%90%E5%B1%B1%E5%9B%BD%E5%AE%B6%E6%A3%AE%E6%9E%97%E5%85%AC%E5%9B%AD/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E7%A6%8F%E5%BB%BA%E6%94%AF%E6%8F%90%E5%B1%B1%E5%9B%BD%E5%AE%B6%E6%A3%AE%E6%9E%97%E5%85%AC%E5%9B%AD/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E7%A6%8F%E5%BB%BA%E6%94%AF%E6%8F%90%E5%B1%B1%E5%9B%BD%E5%AE%B6%E6%A3%AE%E6%9E%97%E5%85%AC%E5%9B%AD/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E7%A6%8F%E5%BB%BA%E6%94%AF%E6%8F%90%E5%B1%B1%E5%9B%BD%E5%AE%B6%E6%A3%AE%E6%9E%97%E5%85%AC%E5%9B%AD/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E7%A6%8F%E5%BB%BA%E6%94%AF%E6%8F%90%E5%B1%B1%E5%9B%BD%E5%AE%B6%E6%A3%AE%E6%9E%97%E5%85%AC%E5%9B%AD/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E7%A6%8F%E5%BB%BA%E6%94%AF%E6%8F%90%E5%B1%B1%E5%9B%BD%E5%AE%B6%E6%A3%AE%E6%9E%97%E5%85%AC%E5%9B%AD/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E7%A6%8F%E5%BB%BA%E6%94%AF%E6%8F%90%E5%B1%B1%E5%9B%BD%E5%AE%B6%E6%A3%AE%E6%9E%97%E5%85%AC%E5%9B%AD/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E7%A6%8F%E5%BB%BA%E6%94%AF%E6%8F%90%E5%B1%B1%E5%9B%BD%E5%AE%B6%E6%A3%AE%E6%9E%97%E5%85%AC%E5%9B%AD/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("宁德洋中水利风景区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B4%8B%E4%B8%AD%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/yangzhong.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B4%8B%E4%B8%AD%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B4%8B%E4%B8%AD%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B4%8B%E4%B8%AD%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B4%8B%E4%B8%AD%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B4%8B%E4%B8%AD%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B4%8B%E4%B8%AD%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B4%8B%E4%B8%AD%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B4%8B%E4%B8%AD%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B4%8B%E4%B8%AD%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B4%8B%E4%B8%AD%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("宁德蕉城区上金贝中华畲家寨")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E8%95%89%E5%9F%8E%E5%8C%BA%E4%B8%8A%E9%87%91%E8%B4%9D%E4%B8%AD%E5%8D%8E%E7%95%B2%E5%AE%B6%E5%AF%A8/shangjinbei.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E8%95%89%E5%9F%8E%E5%8C%BA%E4%B8%8A%E9%87%91%E8%B4%9D%E4%B8%AD%E5%8D%8E%E7%95%B2%E5%AE%B6%E5%AF%A8/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E8%95%89%E5%9F%8E%E5%8C%BA%E4%B8%8A%E9%87%91%E8%B4%9D%E4%B8%AD%E5%8D%8E%E7%95%B2%E5%AE%B6%E5%AF%A8/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E8%95%89%E5%9F%8E%E5%8C%BA%E4%B8%8A%E9%87%91%E8%B4%9D%E4%B8%AD%E5%8D%8E%E7%95%B2%E5%AE%B6%E5%AF%A8/11.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E8%95%89%E5%9F%8E%E5%8C%BA%E4%B8%8A%E9%87%91%E8%B4%9D%E4%B8%AD%E5%8D%8E%E7%95%B2%E5%AE%B6%E5%AF%A8/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E8%95%89%E5%9F%8E%E5%8C%BA%E4%B8%8A%E9%87%91%E8%B4%9D%E4%B8%AD%E5%8D%8E%E7%95%B2%E5%AE%B6%E5%AF%A8/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E8%95%89%E5%9F%8E%E5%8C%BA%E4%B8%8A%E9%87%91%E8%B4%9D%E4%B8%AD%E5%8D%8E%E7%95%B2%E5%AE%B6%E5%AF%A8/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E8%95%89%E5%9F%8E%E5%8C%BA%E4%B8%8A%E9%87%91%E8%B4%9D%E4%B8%AD%E5%8D%8E%E7%95%B2%E5%AE%B6%E5%AF%A8/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E8%95%89%E5%9F%8E%E5%8C%BA%E4%B8%8A%E9%87%91%E8%B4%9D%E4%B8%AD%E5%8D%8E%E7%95%B2%E5%AE%B6%E5%AF%A8/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E8%95%89%E5%9F%8E%E5%8C%BA%E4%B8%8A%E9%87%91%E8%B4%9D%E4%B8%AD%E5%8D%8E%E7%95%B2%E5%AE%B6%E5%AF%A8/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E8%95%89%E5%9F%8E%E5%8C%BA%E4%B8%8A%E9%87%91%E8%B4%9D%E4%B8%AD%E5%8D%8E%E7%95%B2%E5%AE%B6%E5%AF%A8/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E8%95%89%E5%9F%8E%E5%8C%BA%E4%B8%8A%E9%87%91%E8%B4%9D%E4%B8%AD%E5%8D%8E%E7%95%B2%E5%AE%B6%E5%AF%A8/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("洪口水库")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E6%B4%AA%E5%8F%A3%E6%B0%B4%E5%BA%93/hongshui.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E6%B4%AA%E5%8F%A3%E6%B0%B4%E5%BA%93/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E6%B4%AA%E5%8F%A3%E6%B0%B4%E5%BA%93/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E6%B4%AA%E5%8F%A3%E6%B0%B4%E5%BA%93/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E6%B4%AA%E5%8F%A3%E6%B0%B4%E5%BA%93/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E6%B4%AA%E5%8F%A3%E6%B0%B4%E5%BA%93/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E6%B4%AA%E5%8F%A3%E6%B0%B4%E5%BA%93/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E6%B4%AA%E5%8F%A3%E6%B0%B4%E5%BA%93/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E6%B4%AA%E5%8F%A3%E6%B0%B4%E5%BA%93/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E6%B4%AA%E5%8F%A3%E6%B0%B4%E5%BA%93/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E6%B4%AA%E5%8F%A3%E6%B0%B4%E5%BA%93/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("宁德鹤林宫")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E9%B9%A4%E6%9E%97%E5%AE%AB/helin.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E9%B9%A4%E6%9E%97%E5%AE%AB/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E9%B9%A4%E6%9E%97%E5%AE%AB/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E9%B9%A4%E6%9E%97%E5%AE%AB/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E9%B9%A4%E6%9E%97%E5%AE%AB/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E9%B9%A4%E6%9E%97%E5%AE%AB/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E9%B9%A4%E6%9E%97%E5%AE%AB/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E9%B9%A4%E6%9E%97%E5%AE%AB/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E9%B9%A4%E6%9E%97%E5%AE%AB/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E9%B9%A4%E6%9E%97%E5%AE%AB/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E9%B9%A4%E6%9E%97%E5%AE%AB/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }

        if(name.equals("宁德市博物馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/bowuguan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("古田钱来山景区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%92%B1%E6%9D%A5%E5%B1%B1%E6%99%AF%E5%8C%BA/gutianxian.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%92%B1%E6%9D%A5%E5%B1%B1%E6%99%AF%E5%8C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%92%B1%E6%9D%A5%E5%B1%B1%E6%99%AF%E5%8C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%92%B1%E6%9D%A5%E5%B1%B1%E6%99%AF%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%92%B1%E6%9D%A5%E5%B1%B1%E6%99%AF%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%92%B1%E6%9D%A5%E5%B1%B1%E6%99%AF%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%92%B1%E6%9D%A5%E5%B1%B1%E6%99%AF%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%92%B1%E6%9D%A5%E5%B1%B1%E6%99%AF%E5%8C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%92%B1%E6%9D%A5%E5%B1%B1%E6%99%AF%E5%8C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%92%B1%E6%9D%A5%E5%B1%B1%E6%99%AF%E5%8C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%92%B1%E6%9D%A5%E5%B1%B1%E6%99%AF%E5%8C%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("翠屏湖")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E7%BF%A0%E5%B1%8F%E6%B9%96/cuipinghu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E7%BF%A0%E5%B1%8F%E6%B9%96/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E7%BF%A0%E5%B1%8F%E6%B9%96/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E7%BF%A0%E5%B1%8F%E6%B9%96/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E7%BF%A0%E5%B1%8F%E6%B9%96/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E7%BF%A0%E5%B1%8F%E6%B9%96/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E7%BF%A0%E5%B1%8F%E6%B9%96/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E7%BF%A0%E5%B1%8F%E6%B9%96/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E7%BF%A0%E5%B1%8F%E6%B9%96/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E7%BF%A0%E5%B1%8F%E6%B9%96/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E7%BF%A0%E5%B1%8F%E6%B9%96/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("齐云寺")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%BD%90%E4%BA%91%E5%AF%BA/qiyunshi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%BD%90%E4%BA%91%E5%AF%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%BD%90%E4%BA%91%E5%AF%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%BD%90%E4%BA%91%E5%AF%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%BD%90%E4%BA%91%E5%AF%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%BD%90%E4%BA%91%E5%AF%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%BD%90%E4%BA%91%E5%AF%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%BD%90%E4%BA%91%E5%AF%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%BD%90%E4%BA%91%E5%AF%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%BD%90%E4%BA%91%E5%AF%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%BD%90%E4%BA%91%E5%AF%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("古田溪山书画院")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%BA%AA%E5%B1%B1%E4%B9%A6%E7%94%BB%E9%99%A2/gutianxishan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%BA%AA%E5%B1%B1%E4%B9%A6%E7%94%BB%E9%99%A2/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%BA%AA%E5%B1%B1%E4%B9%A6%E7%94%BB%E9%99%A2/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%BA%AA%E5%B1%B1%E4%B9%A6%E7%94%BB%E9%99%A2/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%BA%AA%E5%B1%B1%E4%B9%A6%E7%94%BB%E9%99%A2/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%BA%AA%E5%B1%B1%E4%B9%A6%E7%94%BB%E9%99%A2/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%BA%AA%E5%B1%B1%E4%B9%A6%E7%94%BB%E9%99%A2/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%BA%AA%E5%B1%B1%E4%B9%A6%E7%94%BB%E9%99%A2/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%BA%AA%E5%B1%B1%E4%B9%A6%E7%94%BB%E9%99%A2/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%BA%AA%E5%B1%B1%E4%B9%A6%E7%94%BB%E9%99%A2/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%BA%AA%E5%B1%B1%E4%B9%A6%E7%94%BB%E9%99%A2/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("长洋徐氏古民居群")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%95%BF%E6%B4%8B%E5%BE%90%E6%B0%8F%E5%8F%A4%E6%B0%91%E5%B1%85%E7%BE%A4/changyangxunshi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%95%BF%E6%B4%8B%E5%BE%90%E6%B0%8F%E5%8F%A4%E6%B0%91%E5%B1%85%E7%BE%A4/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%95%BF%E6%B4%8B%E5%BE%90%E6%B0%8F%E5%8F%A4%E6%B0%91%E5%B1%85%E7%BE%A4/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%95%BF%E6%B4%8B%E5%BE%90%E6%B0%8F%E5%8F%A4%E6%B0%91%E5%B1%85%E7%BE%A4/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%95%BF%E6%B4%8B%E5%BE%90%E6%B0%8F%E5%8F%A4%E6%B0%91%E5%B1%85%E7%BE%A4/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%95%BF%E6%B4%8B%E5%BE%90%E6%B0%8F%E5%8F%A4%E6%B0%91%E5%B1%85%E7%BE%A4/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%95%BF%E6%B4%8B%E5%BE%90%E6%B0%8F%E5%8F%A4%E6%B0%91%E5%B1%85%E7%BE%A4/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%95%BF%E6%B4%8B%E5%BE%90%E6%B0%8F%E5%8F%A4%E6%B0%91%E5%B1%85%E7%BE%A4/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%95%BF%E6%B4%8B%E5%BE%90%E6%B0%8F%E5%8F%A4%E6%B0%91%E5%B1%85%E7%BE%A4/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%95%BF%E6%B4%8B%E5%BE%90%E6%B0%8F%E5%8F%A4%E6%B0%91%E5%B1%85%E7%BE%A4/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%95%BF%E6%B4%8B%E5%BE%90%E6%B0%8F%E5%8F%A4%E6%B0%91%E5%B1%85%E7%BE%A4/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("金钟湖山庄")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%87%91%E9%92%9F%E6%B9%96%E5%B1%B1%E5%BA%84/jinzhonghushanzhuan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%87%91%E9%92%9F%E6%B9%96%E5%B1%B1%E5%BA%84/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%87%91%E9%92%9F%E6%B9%96%E5%B1%B1%E5%BA%84/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%87%91%E9%92%9F%E6%B9%96%E5%B1%B1%E5%BA%84/11.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%87%91%E9%92%9F%E6%B9%96%E5%B1%B1%E5%BA%84/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%87%91%E9%92%9F%E6%B9%96%E5%B1%B1%E5%BA%84/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%87%91%E9%92%9F%E6%B9%96%E5%B1%B1%E5%BA%84/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%87%91%E9%92%9F%E6%B9%96%E5%B1%B1%E5%BA%84/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%87%91%E9%92%9F%E6%B9%96%E5%B1%B1%E5%BA%84/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%87%91%E9%92%9F%E6%B9%96%E5%B1%B1%E5%BA%84/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%87%91%E9%92%9F%E6%B9%96%E5%B1%B1%E5%BA%84/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E9%87%91%E9%92%9F%E6%B9%96%E5%B1%B1%E5%BA%84/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("凤林祠")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0/fenglinshi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("凤林祠坐")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0%E5%9D%90/fenglinshizuo.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0%E5%9D%90/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0%E5%9D%90/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0%E5%9D%90/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0%E5%9D%90/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0%E5%9D%90/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0%E5%9D%90/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0%E5%9D%90/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0%E5%9D%90/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0%E5%9D%90/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%87%A4%E6%9E%97%E7%A5%A0%E5%9D%90/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("蝉林祠")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%9D%89%E6%9E%97%E7%A5%A0/changlinshi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%9D%89%E6%9E%97%E7%A5%A0/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%9D%89%E6%9E%97%E7%A5%A0/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%9D%89%E6%9E%97%E7%A5%A0/11.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%9D%89%E6%9E%97%E7%A5%A0/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%9D%89%E6%9E%97%E7%A5%A0/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%9D%89%E6%9E%97%E7%A5%A0/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%9D%89%E6%9E%97%E7%A5%A0/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%9D%89%E6%9E%97%E7%A5%A0/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%9D%89%E6%9E%97%E7%A5%A0/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%9D%89%E6%9E%97%E7%A5%A0/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%9D%89%E6%9E%97%E7%A5%A0/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("圆瑛故居")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%9C%86%E7%91%9B%E6%95%85%E5%B1%85/yuanyinguju.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%9C%86%E7%91%9B%E6%95%85%E5%B1%85/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%9C%86%E7%91%9B%E6%95%85%E5%B1%85/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%9C%86%E7%91%9B%E6%95%85%E5%B1%85/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%9C%86%E7%91%9B%E6%95%85%E5%B1%85/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%9C%86%E7%91%9B%E6%95%85%E5%B1%85/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%9C%86%E7%91%9B%E6%95%85%E5%B1%85/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%9C%86%E7%91%9B%E6%95%85%E5%B1%85/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%9C%86%E7%91%9B%E6%95%85%E5%B1%85/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%9C%86%E7%91%9B%E6%95%85%E5%B1%85/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%9C%86%E7%91%9B%E6%95%85%E5%B1%85/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("古田临水宫")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E4%B8%B4%E6%B0%B4%E5%AE%AB/gutianlinshuigong.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E4%B8%B4%E6%B0%B4%E5%AE%AB/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E4%B8%B4%E6%B0%B4%E5%AE%AB/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E4%B8%B4%E6%B0%B4%E5%AE%AB/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E4%B8%B4%E6%B0%B4%E5%AE%AB/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E4%B8%B4%E6%B0%B4%E5%AE%AB/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E4%B8%B4%E6%B0%B4%E5%AE%AB/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E4%B8%B4%E6%B0%B4%E5%AE%AB/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E4%B8%B4%E6%B0%B4%E5%AE%AB/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E4%B8%B4%E6%B0%B4%E5%AE%AB/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E4%B8%B4%E6%B0%B4%E5%AE%AB/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("白水洋")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BD%E6%B0%B4%E6%B4%8B/baishuiyang.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BD%E6%B0%B4%E6%B4%8B/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BD%E6%B0%B4%E6%B4%8B/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BD%E6%B0%B4%E6%B4%8B/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BD%E6%B0%B4%E6%B4%8B/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BD%E6%B0%B4%E6%B4%8B/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BD%E6%B0%B4%E6%B4%8B/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BD%E6%B0%B4%E6%B4%8B/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BD%E6%B0%B4%E6%B4%8B/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BD%E6%B0%B4%E6%B4%8B/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BD%E6%B0%B4%E6%B4%8B/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("鸳鸯溪")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%B8%B3%E9%B8%AF%E6%BA%AA/yuanyangxi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%B8%B3%E9%B8%AF%E6%BA%AA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%B8%B3%E9%B8%AF%E6%BA%AA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%B8%B3%E9%B8%AF%E6%BA%AA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%B8%B3%E9%B8%AF%E6%BA%AA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%B8%B3%E9%B8%AF%E6%BA%AA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%B8%B3%E9%B8%AF%E6%BA%AA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%B8%B3%E9%B8%AF%E6%BA%AA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%B8%B3%E9%B8%AF%E6%BA%AA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("大棠滑草场")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%A4%A7%E6%A3%A0%E6%BB%91%E8%8D%89%E5%9C%BA/datang.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%A4%A7%E6%A3%A0%E6%BB%91%E8%8D%89%E5%9C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%A4%A7%E6%A3%A0%E6%BB%91%E8%8D%89%E5%9C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%A4%A7%E6%A3%A0%E6%BB%91%E8%8D%89%E5%9C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%A4%A7%E6%A3%A0%E6%BB%91%E8%8D%89%E5%9C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%A4%A7%E6%A3%A0%E6%BB%91%E8%8D%89%E5%9C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%A4%A7%E6%A3%A0%E6%BB%91%E8%8D%89%E5%9C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%A4%A7%E6%A3%A0%E6%BB%91%E8%8D%89%E5%9C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%A4%A7%E6%A3%A0%E6%BB%91%E8%8D%89%E5%9C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%A4%A7%E6%A3%A0%E6%BB%91%E8%8D%89%E5%9C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%A4%A7%E6%A3%A0%E6%BB%91%E8%8D%89%E5%9C%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("际头耕读文化大观园")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%99%85%E5%A4%B4%E8%80%95%E8%AF%BB%E6%96%87%E5%8C%96%E5%A4%A7%E8%A7%82%E5%9B%AD/daguangyuan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%99%85%E5%A4%B4%E8%80%95%E8%AF%BB%E6%96%87%E5%8C%96%E5%A4%A7%E8%A7%82%E5%9B%AD/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%99%85%E5%A4%B4%E8%80%95%E8%AF%BB%E6%96%87%E5%8C%96%E5%A4%A7%E8%A7%82%E5%9B%AD/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%99%85%E5%A4%B4%E8%80%95%E8%AF%BB%E6%96%87%E5%8C%96%E5%A4%A7%E8%A7%82%E5%9B%AD/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%99%85%E5%A4%B4%E8%80%95%E8%AF%BB%E6%96%87%E5%8C%96%E5%A4%A7%E8%A7%82%E5%9B%AD/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%99%85%E5%A4%B4%E8%80%95%E8%AF%BB%E6%96%87%E5%8C%96%E5%A4%A7%E8%A7%82%E5%9B%AD/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%99%85%E5%A4%B4%E8%80%95%E8%AF%BB%E6%96%87%E5%8C%96%E5%A4%A7%E8%A7%82%E5%9B%AD/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%99%85%E5%A4%B4%E8%80%95%E8%AF%BB%E6%96%87%E5%8C%96%E5%A4%A7%E8%A7%82%E5%9B%AD/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("漈头古村")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E6%BC%88%E5%A4%B4%E5%8F%A4%E6%9D%91/jitoucun.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E6%BC%88%E5%A4%B4%E5%8F%A4%E6%9D%91/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E6%BC%88%E5%A4%B4%E5%8F%A4%E6%9D%91/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E6%BC%88%E5%A4%B4%E5%8F%A4%E6%9D%91/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E6%BC%88%E5%A4%B4%E5%8F%A4%E6%9D%91/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E6%BC%88%E5%A4%B4%E5%8F%A4%E6%9D%91/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E6%BC%88%E5%A4%B4%E5%8F%A4%E6%9D%91/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E6%BC%88%E5%A4%B4%E5%8F%A4%E6%9D%91/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E6%BC%88%E5%A4%B4%E5%8F%A4%E6%9D%91/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E6%BC%88%E5%A4%B4%E5%8F%A4%E6%9D%91/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("纱帽岩")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%BA%B1%E5%B8%BD%E5%B2%A9/shamaoyan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%BA%B1%E5%B8%BD%E5%B2%A9/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%BA%B1%E5%B8%BD%E5%B2%A9/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%BA%B1%E5%B8%BD%E5%B2%A9/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%BA%B1%E5%B8%BD%E5%B2%A9/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%BA%B1%E5%B8%BD%E5%B2%A9/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%BA%B1%E5%B8%BD%E5%B2%A9/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%BA%B1%E5%B8%BD%E5%B2%A9/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%BA%B1%E5%B8%BD%E5%B2%A9/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%BA%B1%E5%B8%BD%E5%B2%A9/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%BA%B1%E5%B8%BD%E5%B2%A9/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("万安桥")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%B8%87%E5%AE%89%E6%A1%A5/wanganqiao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%B8%87%E5%AE%89%E6%A1%A5/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%B8%87%E5%AE%89%E6%A1%A5/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%B8%87%E5%AE%89%E6%A1%A5/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%B8%87%E5%AE%89%E6%A1%A5/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%B8%87%E5%AE%89%E6%A1%A5/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%B8%87%E5%AE%89%E6%A1%A5/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%B8%87%E5%AE%89%E6%A1%A5/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%B8%87%E5%AE%89%E6%A1%A5/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%B8%87%E5%AE%89%E6%A1%A5/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%B8%87%E5%AE%89%E6%A1%A5/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("百丈漈瀑布")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BE%E4%B8%88%E6%BC%88%E7%80%91%E5%B8%83/baishangpubu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BE%E4%B8%88%E6%BC%88%E7%80%91%E5%B8%83/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BE%E4%B8%88%E6%BC%88%E7%80%91%E5%B8%83/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BE%E4%B8%88%E6%BC%88%E7%80%91%E5%B8%83/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BE%E4%B8%88%E6%BC%88%E7%80%91%E5%B8%83/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BE%E4%B8%88%E6%BC%88%E7%80%91%E5%B8%83/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BE%E4%B8%88%E6%BC%88%E7%80%91%E5%B8%83/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BE%E4%B8%88%E6%BC%88%E7%80%91%E5%B8%83/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BE%E4%B8%88%E6%BC%88%E7%80%91%E5%B8%83/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BE%E4%B8%88%E6%BC%88%E7%80%91%E5%B8%83/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E7%99%BE%E4%B8%88%E6%BC%88%E7%80%91%E5%B8%83/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("仙山牧场")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%B1%B1%E7%89%A7%E5%9C%BA/xianshanmuchang.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%B1%B1%E7%89%A7%E5%9C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%B1%B1%E7%89%A7%E5%9C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%B1%B1%E7%89%A7%E5%9C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%B1%B1%E7%89%A7%E5%9C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%B1%B1%E7%89%A7%E5%9C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%B1%B1%E7%89%A7%E5%9C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%B1%B1%E7%89%A7%E5%9C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%B1%B1%E7%89%A7%E5%9C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%B1%B1%E7%89%A7%E5%9C%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("鼎潭仙宴谷")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%BC%8E%E6%BD%AD%E4%BB%99%E5%AE%B4%E8%B0%B7/dingtanxianyangu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%BC%8E%E6%BD%AD%E4%BB%99%E5%AE%B4%E8%B0%B7/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%BC%8E%E6%BD%AD%E4%BB%99%E5%AE%B4%E8%B0%B7/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%BC%8E%E6%BD%AD%E4%BB%99%E5%AE%B4%E8%B0%B7/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%BC%8E%E6%BD%AD%E4%BB%99%E5%AE%B4%E8%B0%B7/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%BC%8E%E6%BD%AD%E4%BB%99%E5%AE%B4%E8%B0%B7/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%BC%8E%E6%BD%AD%E4%BB%99%E5%AE%B4%E8%B0%B7/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%BC%8E%E6%BD%AD%E4%BB%99%E5%AE%B4%E8%B0%B7/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%BC%8E%E6%BD%AD%E4%BB%99%E5%AE%B4%E8%B0%B7/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%BC%8E%E6%BD%AD%E4%BB%99%E5%AE%B4%E8%B0%B7/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%BC%8E%E6%BD%AD%E4%BB%99%E5%AE%B4%E8%B0%B7/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("国家地质公园")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%9B%BD%E5%AE%B6%E5%9C%B0%E8%B4%A8%E5%85%AC%E5%9B%AD/guojiagongyuan.png");
            imagePath.add("hhttps://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%9B%BD%E5%AE%B6%E5%9C%B0%E8%B4%A8%E5%85%AC%E5%9B%AD/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%9B%BD%E5%AE%B6%E5%9C%B0%E8%B4%A8%E5%85%AC%E5%9B%AD/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%9B%BD%E5%AE%B6%E5%9C%B0%E8%B4%A8%E5%85%AC%E5%9B%AD/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%9B%BD%E5%AE%B6%E5%9C%B0%E8%B4%A8%E5%85%AC%E5%9B%AD/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%9B%BD%E5%AE%B6%E5%9C%B0%E8%B4%A8%E5%85%AC%E5%9B%AD/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%9B%BD%E5%AE%B6%E5%9C%B0%E8%B4%A8%E5%85%AC%E5%9B%AD/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%9B%BD%E5%AE%B6%E5%9C%B0%E8%B4%A8%E5%85%AC%E5%9B%AD/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%9B%BD%E5%AE%B6%E5%9C%B0%E8%B4%A8%E5%85%AC%E5%9B%AD/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%9B%BD%E5%AE%B6%E5%9C%B0%E8%B4%A8%E5%85%AC%E5%9B%AD/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%9B%BD%E5%AE%B6%E5%9C%B0%E8%B4%A8%E5%85%AC%E5%9B%AD/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("观景台")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E8%A7%82%E6%99%AF%E5%8F%B0/guanjingtai.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E8%A7%82%E6%99%AF%E5%8F%B0/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E8%A7%82%E6%99%AF%E5%8F%B0/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E8%A7%82%E6%99%AF%E5%8F%B0/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E8%A7%82%E6%99%AF%E5%8F%B0/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E8%A7%82%E6%99%AF%E5%8F%B0/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E8%A7%82%E6%99%AF%E5%8F%B0/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E8%A7%82%E6%99%AF%E5%8F%B0/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E8%A7%82%E6%99%AF%E5%8F%B0/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E8%A7%82%E6%99%AF%E5%8F%B0/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E8%A7%82%E6%99%AF%E5%8F%B0/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("仙女瀑")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%A5%B3%E7%80%91/xiannvpu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%A5%B3%E7%80%91/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%A5%B3%E7%80%91/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%A5%B3%E7%80%91/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%A5%B3%E7%80%91/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%A5%B3%E7%80%91/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%A5%B3%E7%80%91/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%A5%B3%E7%80%91/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%A5%B3%E7%80%91/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%A5%B3%E7%80%91/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%A5%B3%E7%80%91/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E4%BB%99%E5%A5%B3%E7%80%91/baishangpubu.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("鲤鱼溪")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%B2%A4%E9%B1%BC%E6%BA%AA/liyuxi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%B2%A4%E9%B1%BC%E6%BA%AA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%B2%A4%E9%B1%BC%E6%BA%AA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%B2%A4%E9%B1%BC%E6%BA%AA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%B2%A4%E9%B1%BC%E6%BA%AA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%B2%A4%E9%B1%BC%E6%BA%AA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%B2%A4%E9%B1%BC%E6%BA%AA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%B2%A4%E9%B1%BC%E6%BA%AA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%B2%A4%E9%B1%BC%E6%BA%AA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%B2%A4%E9%B1%BC%E6%BA%AA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%B2%A4%E9%B1%BC%E6%BA%AA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("九龙漈风景名胜区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E6%BC%88%E9%A3%8E%E6%99%AF%E5%90%8D%E8%83%9C%E5%8C%BA/jiulongfengjingmingshengqu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E6%BC%88%E9%A3%8E%E6%99%AF%E5%90%8D%E8%83%9C%E5%8C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E6%BC%88%E9%A3%8E%E6%99%AF%E5%90%8D%E8%83%9C%E5%8C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E6%BC%88%E9%A3%8E%E6%99%AF%E5%90%8D%E8%83%9C%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E6%BC%88%E9%A3%8E%E6%99%AF%E5%90%8D%E8%83%9C%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E6%BC%88%E9%A3%8E%E6%99%AF%E5%90%8D%E8%83%9C%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E6%BC%88%E9%A3%8E%E6%99%AF%E5%90%8D%E8%83%9C%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E6%BC%88%E9%A3%8E%E6%99%AF%E5%90%8D%E8%83%9C%E5%8C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E6%BC%88%E9%A3%8E%E6%99%AF%E5%90%8D%E8%83%9C%E5%8C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E6%BC%88%E9%A3%8E%E6%99%AF%E5%90%8D%E8%83%9C%E5%8C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E6%BC%88%E9%A3%8E%E6%99%AF%E5%90%8D%E8%83%9C%E5%8C%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("陈峭古村")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%99%88%E5%B3%AD%E5%8F%A4%E6%9D%91/chenxiaogucun.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%99%88%E5%B3%AD%E5%8F%A4%E6%9D%91/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%99%88%E5%B3%AD%E5%8F%A4%E6%9D%91/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%99%88%E5%B3%AD%E5%8F%A4%E6%9D%91/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%99%88%E5%B3%AD%E5%8F%A4%E6%9D%91/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%99%88%E5%B3%AD%E5%8F%A4%E6%9D%91/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%99%88%E5%B3%AD%E5%8F%A4%E6%9D%91/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%99%88%E5%B3%AD%E5%8F%A4%E6%9D%91/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%99%88%E5%B3%AD%E5%8F%A4%E6%9D%91/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("周宁滴水岩")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%BB%B4%E6%B0%B4%E5%B2%A9/zhouningdishuiyan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%BB%B4%E6%B0%B4%E5%B2%A9/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%BB%B4%E6%B0%B4%E5%B2%A9/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%BB%B4%E6%B0%B4%E5%B2%A9/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%BB%B4%E6%B0%B4%E5%B2%A9/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%BB%B4%E6%B0%B4%E5%B2%A9/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%BB%B4%E6%B0%B4%E5%B2%A9/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%BB%B4%E6%B0%B4%E5%B2%A9/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%BB%B4%E6%B0%B4%E5%B2%A9/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%BB%B4%E6%B0%B4%E5%B2%A9/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%BB%B4%E6%B0%B4%E5%B2%A9/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("林公忠平王祖殿")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%9E%97%E5%85%AC%E5%BF%A0%E5%B9%B3%E7%8E%8B%E7%A5%96%E6%AE%BF/zudian.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%9E%97%E5%85%AC%E5%BF%A0%E5%B9%B3%E7%8E%8B%E7%A5%96%E6%AE%BF/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%9E%97%E5%85%AC%E5%BF%A0%E5%B9%B3%E7%8E%8B%E7%A5%96%E6%AE%BF/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%9E%97%E5%85%AC%E5%BF%A0%E5%B9%B3%E7%8E%8B%E7%A5%96%E6%AE%BF/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%9E%97%E5%85%AC%E5%BF%A0%E5%B9%B3%E7%8E%8B%E7%A5%96%E6%AE%BF/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%9E%97%E5%85%AC%E5%BF%A0%E5%B9%B3%E7%8E%8B%E7%A5%96%E6%AE%BF/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%9E%97%E5%85%AC%E5%BF%A0%E5%B9%B3%E7%8E%8B%E7%A5%96%E6%AE%BF/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%9E%97%E5%85%AC%E5%BF%A0%E5%B9%B3%E7%8E%8B%E7%A5%96%E6%AE%BF/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%9E%97%E5%85%AC%E5%BF%A0%E5%B9%B3%E7%8E%8B%E7%A5%96%E6%AE%BF/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%9E%97%E5%85%AC%E5%BF%A0%E5%B9%B3%E7%8E%8B%E7%A5%96%E6%AE%BF/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%9E%97%E5%85%AC%E5%BF%A0%E5%B9%B3%E7%8E%8B%E7%A5%96%E6%AE%BF/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("周宁般若寺")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E8%88%AC%E8%8B%A5%E5%AF%BA/zhouningbanruosi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E8%88%AC%E8%8B%A5%E5%AF%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E8%88%AC%E8%8B%A5%E5%AF%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E8%88%AC%E8%8B%A5%E5%AF%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E8%88%AC%E8%8B%A5%E5%AF%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E8%88%AC%E8%8B%A5%E5%AF%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E8%88%AC%E8%8B%A5%E5%AF%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E8%88%AC%E8%8B%A5%E5%AF%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E8%88%AC%E8%8B%A5%E5%AF%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E8%88%AC%E8%8B%A5%E5%AF%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E8%88%AC%E8%8B%A5%E5%AF%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("九龙石窟")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E7%9F%B3%E7%AA%9F/jiulongshiku.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E7%9F%B3%E7%AA%9F/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E7%9F%B3%E7%AA%9F/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E7%9F%B3%E7%AA%9F/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E7%9F%B3%E7%AA%9F/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E7%9F%B3%E7%AA%9F/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E7%9F%B3%E7%AA%9F/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E7%9F%B3%E7%AA%9F/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E7%9F%B3%E7%AA%9F/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E7%9F%B3%E7%AA%9F/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B9%9D%E9%BE%99%E7%9F%B3%E7%AA%9F/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("高山明珠")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%AB%98%E5%B1%B1%E6%98%8E%E7%8F%A0/gaoshanmingzhu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%AB%98%E5%B1%B1%E6%98%8E%E7%8F%A0/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%AB%98%E5%B1%B1%E6%98%8E%E7%8F%A0/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%AB%98%E5%B1%B1%E6%98%8E%E7%8F%A0/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%AB%98%E5%B1%B1%E6%98%8E%E7%8F%A0/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%AB%98%E5%B1%B1%E6%98%8E%E7%8F%A0/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%AB%98%E5%B1%B1%E6%98%8E%E7%8F%A0/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%AB%98%E5%B1%B1%E6%98%8E%E7%8F%A0/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%AB%98%E5%B1%B1%E6%98%8E%E7%8F%A0/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%AB%98%E5%B1%B1%E6%98%8E%E7%8F%A0/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%AB%98%E5%B1%B1%E6%98%8E%E7%8F%A0/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("浦源郑氏宗祠")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%B5%A6%E6%BA%90%E9%83%91%E6%B0%8F%E5%AE%97%E7%A5%A0/puyuanzongsi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%B5%A6%E6%BA%90%E9%83%91%E6%B0%8F%E5%AE%97%E7%A5%A0/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%B5%A6%E6%BA%90%E9%83%91%E6%B0%8F%E5%AE%97%E7%A5%A0/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%B5%A6%E6%BA%90%E9%83%91%E6%B0%8F%E5%AE%97%E7%A5%A0/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%B5%A6%E6%BA%90%E9%83%91%E6%B0%8F%E5%AE%97%E7%A5%A0/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%B5%A6%E6%BA%90%E9%83%91%E6%B0%8F%E5%AE%97%E7%A5%A0/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%B5%A6%E6%BA%90%E9%83%91%E6%B0%8F%E5%AE%97%E7%A5%A0/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%B5%A6%E6%BA%90%E9%83%91%E6%B0%8F%E5%AE%97%E7%A5%A0/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%B5%A6%E6%BA%90%E9%83%91%E6%B0%8F%E5%AE%97%E7%A5%A0/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%B5%A6%E6%BA%90%E9%83%91%E6%B0%8F%E5%AE%97%E7%A5%A0/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%B5%A6%E6%BA%90%E9%83%91%E6%B0%8F%E5%AE%97%E7%A5%A0/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("犀溪漂流")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA%E6%BC%82%E6%B5%81/xixi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA%E6%BC%82%E6%B5%81/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA%E6%BC%82%E6%B5%81/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA%E6%BC%82%E6%B5%81/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA%E6%BC%82%E6%B5%81/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA%E6%BC%82%E6%B5%81/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA%E6%BC%82%E6%B5%81/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA%E6%BC%82%E6%B5%81/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA%E6%BC%82%E6%B5%81/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA%E6%BC%82%E6%B5%81/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA%E6%BC%82%E6%B5%81/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("西浦")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%A5%BF%E6%B5%A6/xipu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%A5%BF%E6%B5%A6/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%A5%BF%E6%B5%A6/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%A5%BF%E6%B5%A6/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%A5%BF%E6%B5%A6/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%A5%BF%E6%B5%A6/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%A5%BF%E6%B5%A6/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%A5%BF%E6%B5%A6/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%A5%BF%E6%B5%A6/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%A5%BF%E6%B5%A6/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%A5%BF%E6%B5%A6/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("灵峰禅寺")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%81%B5%E5%B3%B0%E7%A6%85%E5%AF%BA/lingfeng.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%81%B5%E5%B3%B0%E7%A6%85%E5%AF%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%81%B5%E5%B3%B0%E7%A6%85%E5%AF%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%81%B5%E5%B3%B0%E7%A6%85%E5%AF%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%81%B5%E5%B3%B0%E7%A6%85%E5%AF%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%81%B5%E5%B3%B0%E7%A6%85%E5%AF%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%81%B5%E5%B3%B0%E7%A6%85%E5%AF%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%81%B5%E5%B3%B0%E7%A6%85%E5%AF%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%81%B5%E5%B3%B0%E7%A6%85%E5%AF%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%81%B5%E5%B3%B0%E7%A6%85%E5%AF%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%81%B5%E5%B3%B0%E7%A6%85%E5%AF%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("南山风景区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%8D%97%E5%B1%B1%E9%A3%8E%E6%99%AF%E5%8C%BA/nanshan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%8D%97%E5%B1%B1%E9%A3%8E%E6%99%AF%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%8D%97%E5%B1%B1%E9%A3%8E%E6%99%AF%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%8D%97%E5%B1%B1%E9%A3%8E%E6%99%AF%E5%8C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%8D%97%E5%B1%B1%E9%A3%8E%E6%99%AF%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%8D%97%E5%B1%B1%E9%A3%8E%E6%99%AF%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%8D%97%E5%B1%B1%E9%A3%8E%E6%99%AF%E5%8C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%8D%97%E5%B1%B1%E9%A3%8E%E6%99%AF%E5%8C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%8D%97%E5%B1%B1%E9%A3%8E%E6%99%AF%E5%8C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%8D%97%E5%B1%B1%E9%A3%8E%E6%99%AF%E5%8C%BA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%8D%97%E5%B1%B1%E9%A3%8E%E6%99%AF%E5%8C%BA/1.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("下党村")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%8B%E5%85%9A%E6%9D%91/dongyuangujianzhu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%8B%E5%85%9A%E6%9D%91/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%8B%E5%85%9A%E6%9D%91/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%8B%E5%85%9A%E6%9D%91/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%8B%E5%85%9A%E6%9D%91/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%8B%E5%85%9A%E6%9D%91/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%8B%E5%85%9A%E6%9D%91/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%8B%E5%85%9A%E6%9D%91/xiadangcun.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%8B%E5%85%9A%E6%9D%91/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%8B%E5%85%9A%E6%9D%91/2.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("小托水库水利风景区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%B0%8F%E6%89%98%E6%B0%B4%E5%BA%93%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/shuikufengjingqu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%B0%8F%E6%89%98%E6%B0%B4%E5%BA%93%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%B0%8F%E6%89%98%E6%B0%B4%E5%BA%93%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%B0%8F%E6%89%98%E6%B0%B4%E5%BA%93%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%B0%8F%E6%89%98%E6%B0%B4%E5%BA%93%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%B0%8F%E6%89%98%E6%B0%B4%E5%BA%93%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%B0%8F%E6%89%98%E6%B0%B4%E5%BA%93%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%B0%8F%E6%89%98%E6%B0%B4%E5%BA%93%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%B0%8F%E6%89%98%E6%B0%B4%E5%BA%93%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%B0%8F%E6%89%98%E6%B0%B4%E5%BA%93%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%B0%8F%E6%89%98%E6%B0%B4%E5%BA%93%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/1.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("寿宁县生态茶园")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%8E%BF%E7%94%9F%E6%80%81%E8%8C%B6%E5%9B%AD/shengtaichayuan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%8E%BF%E7%94%9F%E6%80%81%E8%8C%B6%E5%9B%AD/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%8E%BF%E7%94%9F%E6%80%81%E8%8C%B6%E5%9B%AD/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%8E%BF%E7%94%9F%E6%80%81%E8%8C%B6%E5%9B%AD/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%8E%BF%E7%94%9F%E6%80%81%E8%8C%B6%E5%9B%AD/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%8E%BF%E7%94%9F%E6%80%81%E8%8C%B6%E5%9B%AD/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%8E%BF%E7%94%9F%E6%80%81%E8%8C%B6%E5%9B%AD/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%8E%BF%E7%94%9F%E6%80%81%E8%8C%B6%E5%9B%AD/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%8E%BF%E7%94%9F%E6%80%81%E8%8C%B6%E5%9B%AD/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%8E%BF%E7%94%9F%E6%80%81%E8%8C%B6%E5%9B%AD/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%8E%BF%E7%94%9F%E6%80%81%E8%8C%B6%E5%9B%AD/1.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("犀溪")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA/xi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E7%8A%80%E6%BA%AA/hongshui.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("中共闽东特委含溪旧址")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%AD%E5%85%B1%E9%97%BD%E4%B8%9C%E7%89%B9%E5%A7%94%E5%90%AB%E6%BA%AA%E6%97%A7%E5%9D%80/mindongjiuzhi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%AD%E5%85%B1%E9%97%BD%E4%B8%9C%E7%89%B9%E5%A7%94%E5%90%AB%E6%BA%AA%E6%97%A7%E5%9D%80/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%AD%E5%85%B1%E9%97%BD%E4%B8%9C%E7%89%B9%E5%A7%94%E5%90%AB%E6%BA%AA%E6%97%A7%E5%9D%80/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%AD%E5%85%B1%E9%97%BD%E4%B8%9C%E7%89%B9%E5%A7%94%E5%90%AB%E6%BA%AA%E6%97%A7%E5%9D%80/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%AD%E5%85%B1%E9%97%BD%E4%B8%9C%E7%89%B9%E5%A7%94%E5%90%AB%E6%BA%AA%E6%97%A7%E5%9D%80/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%AD%E5%85%B1%E9%97%BD%E4%B8%9C%E7%89%B9%E5%A7%94%E5%90%AB%E6%BA%AA%E6%97%A7%E5%9D%80/6.png");
            imagePath.add(" https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%AD%E5%85%B1%E9%97%BD%E4%B8%9C%E7%89%B9%E5%A7%94%E5%90%AB%E6%BA%AA%E6%97%A7%E5%9D%80/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%AD%E5%85%B1%E9%97%BD%E4%B8%9C%E7%89%B9%E5%A7%94%E5%90%AB%E6%BA%AA%E6%97%A7%E5%9D%80/fengqiwushidazhai.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%AD%E5%85%B1%E9%97%BD%E4%B8%9C%E7%89%B9%E5%A7%94%E5%90%AB%E6%BA%AA%E6%97%A7%E5%9D%80/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E4%B8%AD%E5%85%B1%E9%97%BD%E4%B8%9C%E7%89%B9%E5%A7%94%E5%90%AB%E6%BA%AA%E6%97%A7%E5%9D%80/3.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("车岭古道")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/chelinggudao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/8.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("福安白云山")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/baiyunshan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("兰谷温泉度假村")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%85%B0%E8%B0%B7%E6%B8%A9%E6%B3%89%E5%BA%A6%E5%81%87%E6%9D%91/langu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E8%BD%A6%E5%B2%AD%E5%8F%A4%E9%81%93/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("天池草场")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E6%B1%A0%E8%8D%89%E5%9C%BA/tianchi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E6%B1%A0%E8%8D%89%E5%9C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E6%B1%A0%E8%8D%89%E5%9C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E6%B1%A0%E8%8D%89%E5%9C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E6%B1%A0%E8%8D%89%E5%9C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E6%B1%A0%E8%8D%89%E5%9C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E6%B1%A0%E8%8D%89%E5%9C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E6%B1%A0%E8%8D%89%E5%9C%BA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E6%B1%A0%E8%8D%89%E5%9C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E6%B1%A0%E8%8D%89%E5%9C%BA/8.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("金山大峡谷")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%87%91%E5%B1%B1%E5%A4%A7%E5%B3%A1%E8%B0%B7/jinshan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%87%91%E5%B1%B1%E5%A4%A7%E5%B3%A1%E8%B0%B7/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%87%91%E5%B1%B1%E5%A4%A7%E5%B3%A1%E8%B0%B7/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%87%91%E5%B1%B1%E5%A4%A7%E5%B3%A1%E8%B0%B7/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%87%91%E5%B1%B1%E5%A4%A7%E5%B3%A1%E8%B0%B7/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%87%91%E5%B1%B1%E5%A4%A7%E5%B3%A1%E8%B0%B7/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%87%91%E5%B1%B1%E5%A4%A7%E5%B3%A1%E8%B0%B7/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%87%91%E5%B1%B1%E5%A4%A7%E5%B3%A1%E8%B0%B7/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%87%91%E5%B1%B1%E5%A4%A7%E5%B3%A1%E8%B0%B7/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%87%91%E5%B1%B1%E5%A4%A7%E5%B3%A1%E8%B0%B7/8.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("狮峰寺")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%8B%AE%E5%B3%B0%E5%AF%BA/shifengshi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%8B%AE%E5%B3%B0%E5%AF%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%8B%AE%E5%B3%B0%E5%AF%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%8B%AE%E5%B3%B0%E5%AF%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%8B%AE%E5%B3%B0%E5%AF%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%8B%AE%E5%B3%B0%E5%AF%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%8B%AE%E5%B3%B0%E5%AF%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%8B%AE%E5%B3%B0%E5%AF%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%8B%AE%E5%B3%B0%E5%AF%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%8B%AE%E5%B3%B0%E5%AF%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%8B%AE%E5%B3%B0%E5%AF%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("廉村")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%BB%89%E6%9D%91/liancun.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%BB%89%E6%9D%91/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%BB%89%E6%9D%91/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%BB%89%E6%9D%91/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%BB%89%E6%9D%91/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%BB%89%E6%9D%91/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%BB%89%E6%9D%91/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%BB%89%E6%9D%91/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%BB%89%E6%9D%91/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%BB%89%E6%9D%91/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("穆云畲族乡")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A9%86%E4%BA%91%E7%95%B2%E6%97%8F%E4%B9%A1/shezu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A9%86%E4%BA%91%E7%95%B2%E6%97%8F%E4%B9%A1/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A9%86%E4%BA%91%E7%95%B2%E6%97%8F%E4%B9%A1/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A9%86%E4%BA%91%E7%95%B2%E6%97%8F%E4%B9%A1/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A9%86%E4%BA%91%E7%95%B2%E6%97%8F%E4%B9%A1/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A9%86%E4%BA%91%E7%95%B2%E6%97%8F%E4%B9%A1/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A9%86%E4%BA%91%E7%95%B2%E6%97%8F%E4%B9%A1/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A9%86%E4%BA%91%E7%95%B2%E6%97%8F%E4%B9%A1/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A9%86%E4%BA%91%E7%95%B2%E6%97%8F%E4%B9%A1/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("闽东革命纪念馆vmt")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%97%BD%E4%B8%9C%E9%9D%A9%E5%91%BD%E7%BA%AA%E5%BF%B5%E9%A6%86/mindonggeming.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%97%BD%E4%B8%9C%E9%9D%A9%E5%91%BD%E7%BA%AA%E5%BF%B5%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%97%BD%E4%B8%9C%E9%9D%A9%E5%91%BD%E7%BA%AA%E5%BF%B5%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%97%BD%E4%B8%9C%E9%9D%A9%E5%91%BD%E7%BA%AA%E5%BF%B5%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%97%BD%E4%B8%9C%E9%9D%A9%E5%91%BD%E7%BA%AA%E5%BF%B5%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%97%BD%E4%B8%9C%E9%9D%A9%E5%91%BD%E7%BA%AA%E5%BF%B5%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%97%BD%E4%B8%9C%E9%9D%A9%E5%91%BD%E7%BA%AA%E5%BF%B5%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%97%BD%E4%B8%9C%E9%9D%A9%E5%91%BD%E7%BA%AA%E5%BF%B5%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%97%BD%E4%B8%9C%E9%9D%A9%E5%91%BD%E7%BA%AA%E5%BF%B5%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%97%BD%E4%B8%9C%E9%9D%A9%E5%91%BD%E7%BA%AA%E5%BF%B5%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E9%97%BD%E4%B8%9C%E9%9D%A9%E5%91%BD%E7%BA%AA%E5%BF%B5%E9%A6%86/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("福安文化馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/baiyunshan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E7%99%BD%E4%BA%91%E5%B1%B1/8.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("柏柱洋红色旅游景区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%9F%8F%E6%9F%B1%E6%B4%8B%E7%BA%A2%E8%89%B2%E6%97%85%E6%B8%B8%E6%99%AF%E5%8C%BA/yanghongse.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%9F%8F%E6%9F%B1%E6%B4%8B%E7%BA%A2%E8%89%B2%E6%97%85%E6%B8%B8%E6%99%AF%E5%8C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%9F%8F%E6%9F%B1%E6%B4%8B%E7%BA%A2%E8%89%B2%E6%97%85%E6%B8%B8%E6%99%AF%E5%8C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%9F%8F%E6%9F%B1%E6%B4%8B%E7%BA%A2%E8%89%B2%E6%97%85%E6%B8%B8%E6%99%AF%E5%8C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%9F%8F%E6%9F%B1%E6%B4%8B%E7%BA%A2%E8%89%B2%E6%97%85%E6%B8%B8%E6%99%AF%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%9F%8F%E6%9F%B1%E6%B4%8B%E7%BA%A2%E8%89%B2%E6%97%85%E6%B8%B8%E6%99%AF%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%9F%8F%E6%9F%B1%E6%B4%8B%E7%BA%A2%E8%89%B2%E6%97%85%E6%B8%B8%E6%99%AF%E5%8C%BA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%9F%8F%E6%9F%B1%E6%B4%8B%E7%BA%A2%E8%89%B2%E6%97%85%E6%B8%B8%E6%99%AF%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%9F%8F%E6%9F%B1%E6%B4%8B%E7%BA%A2%E8%89%B2%E6%97%85%E6%B8%B8%E6%99%AF%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%9F%8F%E6%9F%B1%E6%B4%8B%E7%BA%A2%E8%89%B2%E6%97%85%E6%B8%B8%E6%99%AF%E5%8C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%9F%8F%E6%9F%B1%E6%B4%8B%E7%BA%A2%E8%89%B2%E6%97%85%E6%B8%B8%E6%99%AF%E5%8C%BA/7.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("福安市博物馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/fuanbowuguan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%B8%82%E5%8D%9A%E7%89%A9%E9%A6%86/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("柘荣鸳鸯草场")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E9%B8%B3%E9%B8%AF%E8%8D%89%E5%9C%BA/zherong.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E9%B8%B3%E9%B8%AF%E8%8D%89%E5%9C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E9%B8%B3%E9%B8%AF%E8%8D%89%E5%9C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E9%B8%B3%E9%B8%AF%E8%8D%89%E5%9C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E9%B8%B3%E9%B8%AF%E8%8D%89%E5%9C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E9%B8%B3%E9%B8%AF%E8%8D%89%E5%9C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E9%B8%B3%E9%B8%AF%E8%8D%89%E5%9C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E9%B8%B3%E9%B8%AF%E8%8D%89%E5%9C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E9%B8%B3%E9%B8%AF%E8%8D%89%E5%9C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E9%B8%B3%E9%B8%AF%E8%8D%89%E5%9C%BA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E9%B8%B3%E9%B8%AF%E8%8D%89%E5%9C%BA/8.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("九龙井")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B9%9D%E9%BE%99%E4%BA%95/jiulongjing.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B9%9D%E9%BE%99%E4%BA%95/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B9%9D%E9%BE%99%E4%BA%95/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B9%9D%E9%BE%99%E4%BA%95/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B9%9D%E9%BE%99%E4%BA%95/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B9%9D%E9%BE%99%E4%BA%95/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B9%9D%E9%BE%99%E4%BA%95/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B9%9D%E9%BE%99%E4%BA%95/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B9%9D%E9%BE%99%E4%BA%95/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B9%9D%E9%BE%99%E4%BA%95/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B9%9D%E9%BE%99%E4%BA%95/7.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("宁德东狮山")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B8%9C%E7%8B%AE%E5%B1%B1/dongshishan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B8%9C%E7%8B%AE%E5%B1%B1/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B8%9C%E7%8B%AE%E5%B1%B1/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B8%9C%E7%8B%AE%E5%B1%B1/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B8%9C%E7%8B%AE%E5%B1%B1/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B8%9C%E7%8B%AE%E5%B1%B1/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B8%9C%E7%8B%AE%E5%B1%B1/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B8%9C%E7%8B%AE%E5%B1%B1/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B8%9C%E7%8B%AE%E5%B1%B1/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B8%9C%E7%8B%AE%E5%B1%B1/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B8%9C%E7%8B%AE%E5%B1%B1/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("仙都胜境景区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%BB%99%E9%83%BD%E8%83%9C%E5%A2%83%E6%99%AF%E5%8C%BA/xiandujingqu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%BB%99%E9%83%BD%E8%83%9C%E5%A2%83%E6%99%AF%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%BB%99%E9%83%BD%E8%83%9C%E5%A2%83%E6%99%AF%E5%8C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%BB%99%E9%83%BD%E8%83%9C%E5%A2%83%E6%99%AF%E5%8C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%BB%99%E9%83%BD%E8%83%9C%E5%A2%83%E6%99%AF%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%BB%99%E9%83%BD%E8%83%9C%E5%A2%83%E6%99%AF%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%BB%99%E9%83%BD%E8%83%9C%E5%A2%83%E6%99%AF%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%BB%99%E9%83%BD%E8%83%9C%E5%A2%83%E6%99%AF%E5%8C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%BB%99%E9%83%BD%E8%83%9C%E5%A2%83%E6%99%AF%E5%8C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%BB%99%E9%83%BD%E8%83%9C%E5%A2%83%E6%99%AF%E5%8C%BA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%BB%99%E9%83%BD%E8%83%9C%E5%A2%83%E6%99%AF%E5%8C%BA/8.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("柘荣县九龙井水利风景区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E9%BE%99%E4%BA%95%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/zherongjiulong.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E9%BE%99%E4%BA%95%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E9%BE%99%E4%BA%95%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E9%BE%99%E4%BA%95%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E9%BE%99%E4%BA%95%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E9%BE%99%E4%BA%95%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E9%BE%99%E4%BA%95%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E9%BE%99%E4%BA%95%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E9%BE%99%E4%BA%95%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E9%BE%99%E4%BA%95%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E9%BE%99%E4%BA%95%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/8.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("东源古建筑群")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B8%9C%E6%BA%90%E5%8F%A4%E5%BB%BA%E7%AD%91%E7%BE%A4/dongyuangujianzhu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B8%9C%E6%BA%90%E5%8F%A4%E5%BB%BA%E7%AD%91%E7%BE%A4/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B8%9C%E6%BA%90%E5%8F%A4%E5%BB%BA%E7%AD%91%E7%BE%A4/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B8%9C%E6%BA%90%E5%8F%A4%E5%BB%BA%E7%AD%91%E7%BE%A4/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B8%9C%E6%BA%90%E5%8F%A4%E5%BB%BA%E7%AD%91%E7%BE%A4/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B8%9C%E6%BA%90%E5%8F%A4%E5%BB%BA%E7%AD%91%E7%BE%A4/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B8%9C%E6%BA%90%E5%8F%A4%E5%BB%BA%E7%AD%91%E7%BE%A4/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B8%9C%E6%BA%90%E5%8F%A4%E5%BB%BA%E7%AD%91%E7%BE%A4/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E4%B8%9C%E6%BA%90%E5%8F%A4%E5%BB%BA%E7%AD%91%E7%BE%A4/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("凤岐吴氏大宅")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%87%A4%E5%B2%90%E5%90%B4%E6%B0%8F%E5%A4%A7%E5%AE%85/fengqiwushidazhai.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%87%A4%E5%B2%90%E5%90%B4%E6%B0%8F%E5%A4%A7%E5%AE%85/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%87%A4%E5%B2%90%E5%90%B4%E6%B0%8F%E5%A4%A7%E5%AE%85/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%87%A4%E5%B2%90%E5%90%B4%E6%B0%8F%E5%A4%A7%E5%AE%85/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%87%A4%E5%B2%90%E5%90%B4%E6%B0%8F%E5%A4%A7%E5%AE%85/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%87%A4%E5%B2%90%E5%90%B4%E6%B0%8F%E5%A4%A7%E5%AE%85/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%87%A4%E5%B2%90%E5%90%B4%E6%B0%8F%E5%A4%A7%E5%AE%85/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%87%A4%E5%B2%90%E5%90%B4%E6%B0%8F%E5%A4%A7%E5%AE%85/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%87%A4%E5%B2%90%E5%90%B4%E6%B0%8F%E5%A4%A7%E5%AE%85/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("百丈岩八仙洞")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E7%99%BE%E4%B8%88%E5%B2%A9%E5%85%AB%E4%BB%99%E6%B4%9E/baizhangyanbaxiandong.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E7%99%BE%E4%B8%88%E5%B2%A9%E5%85%AB%E4%BB%99%E6%B4%9E/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E7%99%BE%E4%B8%88%E5%B2%A9%E5%85%AB%E4%BB%99%E6%B4%9E/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E7%99%BE%E4%B8%88%E5%B2%A9%E5%85%AB%E4%BB%99%E6%B4%9E/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E7%99%BE%E4%B8%88%E5%B2%A9%E5%85%AB%E4%BB%99%E6%B4%9E/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E7%99%BE%E4%B8%88%E5%B2%A9%E5%85%AB%E4%BB%99%E6%B4%9E/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E7%99%BE%E4%B8%88%E5%B2%A9%E5%85%AB%E4%BB%99%E6%B4%9E/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E7%99%BE%E4%B8%88%E5%B2%A9%E5%85%AB%E4%BB%99%E6%B4%9E/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E7%99%BE%E4%B8%88%E5%B2%A9%E5%85%AB%E4%BB%99%E6%B4%9E/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E7%99%BE%E4%B8%88%E5%B2%A9%E5%85%AB%E4%BB%99%E6%B4%9E/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("蟠桃映翠")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E8%9F%A0%E6%A1%83%E6%98%A0%E7%BF%A0/pantao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E8%9F%A0%E6%A1%83%E6%98%A0%E7%BF%A0/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E8%9F%A0%E6%A1%83%E6%98%A0%E7%BF%A0/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E8%9F%A0%E6%A1%83%E6%98%A0%E7%BF%A0/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E8%9F%A0%E6%A1%83%E6%98%A0%E7%BF%A0/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E8%9F%A0%E6%A1%83%E6%98%A0%E7%BF%A0/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E8%9F%A0%E6%A1%83%E6%98%A0%E7%BF%A0/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E8%9F%A0%E6%A1%83%E6%98%A0%E7%BF%A0/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E8%9F%A0%E6%A1%83%E6%98%A0%E7%BF%A0/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E8%9F%A0%E6%A1%83%E6%98%A0%E7%BF%A0/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E8%9F%A0%E6%A1%83%E6%98%A0%E7%BF%A0/4.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("青岚湖水利风景区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E9%9D%92%E5%B2%9A%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/qinglanfengjingqu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E9%9D%92%E5%B2%9A%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E9%9D%92%E5%B2%9A%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E9%9D%92%E5%B2%9A%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E9%9D%92%E5%B2%9A%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E9%9D%92%E5%B2%9A%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E9%9D%92%E5%B2%9A%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E9%9D%92%E5%B2%9A%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E9%9D%92%E5%B2%9A%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E9%9D%92%E5%B2%9A%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E9%9D%92%E5%B2%9A%E6%B9%96%E6%B0%B4%E5%88%A9%E9%A3%8E%E6%99%AF%E5%8C%BA/6.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("太姥山")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%AA%E5%A7%A5%E5%B1%B1/tailaoshan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%AA%E5%A7%A5%E5%B1%B1/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%AA%E5%A7%A5%E5%B1%B1/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%AA%E5%A7%A5%E5%B1%B1/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%AA%E5%A7%A5%E5%B1%B1/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%AA%E5%A7%A5%E5%B1%B1/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%AA%E5%A7%A5%E5%B1%B1/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%AA%E5%A7%A5%E5%B1%B1/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%AA%E5%A7%A5%E5%B1%B1/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%AA%E5%A7%A5%E5%B1%B1/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%AA%E5%A7%A5%E5%B1%B1/baizhangyanbaxiandong.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("牛郎岗海滨景区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%89%9B%E9%83%8E%E5%B2%97%E6%B5%B7%E6%BB%A8%E6%99%AF%E5%8C%BA/niulang.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%89%9B%E9%83%8E%E5%B2%97%E6%B5%B7%E6%BB%A8%E6%99%AF%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%89%9B%E9%83%8E%E5%B2%97%E6%B5%B7%E6%BB%A8%E6%99%AF%E5%8C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%89%9B%E9%83%8E%E5%B2%97%E6%B5%B7%E6%BB%A8%E6%99%AF%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%89%9B%E9%83%8E%E5%B2%97%E6%B5%B7%E6%BB%A8%E6%99%AF%E5%8C%BA/baizhangyanbaxiandong.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%89%9B%E9%83%8E%E5%B2%97%E6%B5%B7%E6%BB%A8%E6%99%AF%E5%8C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%89%9B%E9%83%8E%E5%B2%97%E6%B5%B7%E6%BB%A8%E6%99%AF%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%89%9B%E9%83%8E%E5%B2%97%E6%B5%B7%E6%BB%A8%E6%99%AF%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%89%9B%E9%83%8E%E5%B2%97%E6%B5%B7%E6%BB%A8%E6%99%AF%E5%8C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%89%9B%E9%83%8E%E5%B2%97%E6%B5%B7%E6%BB%A8%E6%99%AF%E5%8C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%89%9B%E9%83%8E%E5%B2%97%E6%B5%B7%E6%BB%A8%E6%99%AF%E5%8C%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("翠郊古民居")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%BF%A0%E9%83%8A%E5%8F%A4%E6%B0%91%E5%B1%85/cuijiao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%BF%A0%E9%83%8A%E5%8F%A4%E6%B0%91%E5%B1%85/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%BF%A0%E9%83%8A%E5%8F%A4%E6%B0%91%E5%B1%85/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%BF%A0%E9%83%8A%E5%8F%A4%E6%B0%91%E5%B1%85/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%BF%A0%E9%83%8A%E5%8F%A4%E6%B0%91%E5%B1%85/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%BF%A0%E9%83%8A%E5%8F%A4%E6%B0%91%E5%B1%85/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%BF%A0%E9%83%8A%E5%8F%A4%E6%B0%91%E5%B1%85/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%BF%A0%E9%83%8A%E5%8F%A4%E6%B0%91%E5%B1%85/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%BF%A0%E9%83%8A%E5%8F%A4%E6%B0%91%E5%B1%85/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%BF%A0%E9%83%8A%E5%8F%A4%E6%B0%91%E5%B1%85/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%BF%A0%E9%83%8A%E5%8F%A4%E6%B0%91%E5%B1%85/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("九鲤溪瀑")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E4%B9%9D%E9%B2%A4%E6%BA%AA%E7%80%91/jiulixipu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E4%B9%9D%E9%B2%A4%E6%BA%AA%E7%80%91/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E4%B9%9D%E9%B2%A4%E6%BA%AA%E7%80%91/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E4%B9%9D%E9%B2%A4%E6%BA%AA%E7%80%91/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E4%B9%9D%E9%B2%A4%E6%BA%AA%E7%80%91/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E4%B9%9D%E9%B2%A4%E6%BA%AA%E7%80%91/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E4%B9%9D%E9%B2%A4%E6%BA%AA%E7%80%91/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E4%B9%9D%E9%B2%A4%E6%BA%AA%E7%80%91/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E4%B9%9D%E9%B2%A4%E6%BA%AA%E7%80%91/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E4%B9%9D%E9%B2%A4%E6%BA%AA%E7%80%91/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E4%B9%9D%E9%B2%A4%E6%BA%AA%E7%80%91/baizhangyanbaxiandong.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("小白鹭海滨度假村")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%B0%8F%E7%99%BD%E9%B9%AD%E6%B5%B7%E6%BB%A8%E5%BA%A6%E5%81%87%E6%9D%91/xiaobailu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%B0%8F%E7%99%BD%E9%B9%AD%E6%B5%B7%E6%BB%A8%E5%BA%A6%E5%81%87%E6%9D%91/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%B0%8F%E7%99%BD%E9%B9%AD%E6%B5%B7%E6%BB%A8%E5%BA%A6%E5%81%87%E6%9D%91/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%B0%8F%E7%99%BD%E9%B9%AD%E6%B5%B7%E6%BB%A8%E5%BA%A6%E5%81%87%E6%9D%91/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%B0%8F%E7%99%BD%E9%B9%AD%E6%B5%B7%E6%BB%A8%E5%BA%A6%E5%81%87%E6%9D%91/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%B0%8F%E7%99%BD%E9%B9%AD%E6%B5%B7%E6%BB%A8%E5%BA%A6%E5%81%87%E6%9D%91/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%B0%8F%E7%99%BD%E9%B9%AD%E6%B5%B7%E6%BB%A8%E5%BA%A6%E5%81%87%E6%9D%91/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%B0%8F%E7%99%BD%E9%B9%AD%E6%B5%B7%E6%BB%A8%E5%BA%A6%E5%81%87%E6%9D%91/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%B0%8F%E7%99%BD%E9%B9%AD%E6%B5%B7%E6%BB%A8%E5%BA%A6%E5%81%87%E6%9D%91/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%B0%8F%E7%99%BD%E9%B9%AD%E6%B5%B7%E6%BB%A8%E5%BA%A6%E5%81%87%E6%9D%91/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%B0%8F%E7%99%BD%E9%B9%AD%E6%B5%B7%E6%BB%A8%E5%BA%A6%E5%81%87%E6%9D%91/baizhangyanbaxiandong.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("大嵛山岛")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/dayushan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/1.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("嵛山岛")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/dayushan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A7%E5%B5%9B%E5%B1%B1%E5%B2%9B/4.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("赤溪村")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%A4%E6%BA%AA%E6%9D%91/chixicun.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%A4%E6%BA%AA%E6%9D%91/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%A4%E6%BA%AA%E6%9D%91/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%A4%E6%BA%AA%E6%9D%91/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%A4%E6%BA%AA%E6%9D%91/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%A4%E6%BA%AA%E6%9D%91/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%A4%E6%BA%AA%E6%9D%91/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%A4%E6%BA%AA%E6%9D%91/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%A4%E6%BA%AA%E6%9D%91/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%A4%E6%BA%AA%E6%9D%91/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%A4%E6%BA%AA%E6%9D%91/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("石兰村")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%9F%B3%E5%85%B0%E6%9D%91/shilancun.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%9F%B3%E5%85%B0%E6%9D%91/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%9F%B3%E5%85%B0%E6%9D%91/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%9F%B3%E5%85%B0%E6%9D%91/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%9F%B3%E5%85%B0%E6%9D%91/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%9F%B3%E5%85%B0%E6%9D%91/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%9F%B3%E5%85%B0%E6%9D%91/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%9F%B3%E5%85%B0%E6%9D%91/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%9F%B3%E5%85%B0%E6%9D%91/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%9F%B3%E5%85%B0%E6%9D%91/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%9F%B3%E5%85%B0%E6%9D%91/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("潋城城堡")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/chengbao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("点头妈祖宫")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%82%B9%E5%A4%B4%E5%A6%88%E7%A5%96%E5%AE%AB/diantoumazu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%82%B9%E5%A4%B4%E5%A6%88%E7%A5%96%E5%AE%AB/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%82%B9%E5%A4%B4%E5%A6%88%E7%A5%96%E5%AE%AB/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%82%B9%E5%A4%B4%E5%A6%88%E7%A5%96%E5%AE%AB/daguangyuan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%82%B9%E5%A4%B4%E5%A6%88%E7%A5%96%E5%AE%AB/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%82%B9%E5%A4%B4%E5%A6%88%E7%A5%96%E5%AE%AB/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%82%B9%E5%A4%B4%E5%A6%88%E7%A5%96%E5%AE%AB/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E6%BD%8B%E5%9F%8E%E5%9F%8E%E5%A0%A1/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%82%B9%E5%A4%B4%E5%A6%88%E7%A5%96%E5%AE%AB/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%82%B9%E5%A4%B4%E5%A6%88%E7%A5%96%E5%AE%AB/1.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("福鼎香山寺游览区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E9%A6%99%E5%B1%B1%E5%AF%BA%E6%B8%B8%E8%A7%88%E5%8C%BA/fudingxiangshansi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E9%A6%99%E5%B1%B1%E5%AF%BA%E6%B8%B8%E8%A7%88%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E9%A6%99%E5%B1%B1%E5%AF%BA%E6%B8%B8%E8%A7%88%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E9%A6%99%E5%B1%B1%E5%AF%BA%E6%B8%B8%E8%A7%88%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E9%A6%99%E5%B1%B1%E5%AF%BA%E6%B8%B8%E8%A7%88%E5%8C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E9%A6%99%E5%B1%B1%E5%AF%BA%E6%B8%B8%E8%A7%88%E5%8C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E9%A6%99%E5%B1%B1%E5%AF%BA%E6%B8%B8%E8%A7%88%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E9%A6%99%E5%B1%B1%E5%AF%BA%E6%B8%B8%E8%A7%88%E5%8C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E9%A6%99%E5%B1%B1%E5%AF%BA%E6%B8%B8%E8%A7%88%E5%8C%BA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E9%A6%99%E5%B1%B1%E5%AF%BA%E6%B8%B8%E8%A7%88%E5%8C%BA/chenxiaogucun.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("天门岭游览区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A9%E9%97%A8%E5%B2%AD%E6%B8%B8%E8%A7%88%E5%8C%BA/tianmenlingyouqu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A9%E9%97%A8%E5%B2%AD%E6%B8%B8%E8%A7%88%E5%8C%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A9%E9%97%A8%E5%B2%AD%E6%B8%B8%E8%A7%88%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A9%E9%97%A8%E5%B2%AD%E6%B8%B8%E8%A7%88%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A9%E9%97%A8%E5%B2%AD%E6%B8%B8%E8%A7%88%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A9%E9%97%A8%E5%B2%AD%E6%B8%B8%E8%A7%88%E5%8C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A9%E9%97%A8%E5%B2%AD%E6%B8%B8%E8%A7%88%E5%8C%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A9%E9%97%A8%E5%B2%AD%E6%B8%B8%E8%A7%88%E5%8C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A9%E9%97%A8%E5%B2%AD%E6%B8%B8%E8%A7%88%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A9%E9%97%A8%E5%B2%AD%E6%B8%B8%E8%A7%88%E5%8C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%A4%A9%E9%97%A8%E5%B2%AD%E6%B8%B8%E8%A7%88%E5%8C%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("冷城古堡位")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%86%B7%E5%9F%8E%E5%8F%A4%E5%A0%A1%E4%BD%8D/gubao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%86%B7%E5%9F%8E%E5%8F%A4%E5%A0%A1%E4%BD%8D/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%86%B7%E5%9F%8E%E5%8F%A4%E5%A0%A1%E4%BD%8D/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%86%B7%E5%9F%8E%E5%8F%A4%E5%A0%A1%E4%BD%8D/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%86%B7%E5%9F%8E%E5%8F%A4%E5%A0%A1%E4%BD%8D/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%86%B7%E5%9F%8E%E5%8F%A4%E5%A0%A1%E4%BD%8D/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%86%B7%E5%9F%8E%E5%8F%A4%E5%A0%A1%E4%BD%8D/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%86%B7%E5%9F%8E%E5%8F%A4%E5%A0%A1%E4%BD%8D/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%86%B7%E5%9F%8E%E5%8F%A4%E5%A0%A1%E4%BD%8D/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%86%B7%E5%9F%8E%E5%8F%A4%E5%A0%A1%E4%BD%8D/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%86%B7%E5%9F%8E%E5%8F%A4%E5%A0%A1%E4%BD%8D/6.png");


            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("灵峰寺")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%81%B5%E5%B3%B0%E5%AF%BA/lingfengshi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%81%B5%E5%B3%B0%E5%AF%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%81%B5%E5%B3%B0%E5%AF%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%81%B5%E5%B3%B0%E5%AF%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%81%B5%E5%B3%B0%E5%AF%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%81%B5%E5%B3%B0%E5%AF%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%81%B5%E5%B3%B0%E5%AF%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%81%B5%E5%B3%B0%E5%AF%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%81%B5%E5%B3%B0%E5%AF%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%81%B5%E5%B3%B0%E5%AF%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%81%B5%E5%B3%B0%E5%AF%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("国兴寺")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%9B%BD%E5%85%B4%E5%AF%BA/guoxingshi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%9B%BD%E5%85%B4%E5%AF%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%9B%BD%E5%85%B4%E5%AF%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%9B%BD%E5%85%B4%E5%AF%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%9B%BD%E5%85%B4%E5%AF%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%9B%BD%E5%85%B4%E5%AF%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%9B%BD%E5%85%B4%E5%AF%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%9B%BD%E5%85%B4%E5%AF%BA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E5%9B%BD%E5%85%B4%E5%AF%BA/daguangyuan.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("西阳老人桥")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%A5%BF%E9%98%B3%E8%80%81%E4%BA%BA%E6%A1%A5/xiyanglaorenqiao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%A5%BF%E9%98%B3%E8%80%81%E4%BA%BA%E6%A1%A5/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%A5%BF%E9%98%B3%E8%80%81%E4%BA%BA%E6%A1%A5/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%A5%BF%E9%98%B3%E8%80%81%E4%BA%BA%E6%A1%A5/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%A5%BF%E9%98%B3%E8%80%81%E4%BA%BA%E6%A1%A5/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%A5%BF%E9%98%B3%E8%80%81%E4%BA%BA%E6%A1%A5/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%A5%BF%E9%98%B3%E8%80%81%E4%BA%BA%E6%A1%A5/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%A5%BF%E9%98%B3%E8%80%81%E4%BA%BA%E6%A1%A5/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%A5%BF%E9%98%B3%E8%80%81%E4%BA%BA%E6%A1%A5/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%A5%BF%E9%98%B3%E8%80%81%E4%BA%BA%E6%A1%A5/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%A5%BF%E9%98%B3%E8%80%81%E4%BA%BA%E6%A1%A5/8.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("瑶列岛国家级海洋公园")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%91%B6%E5%88%97%E5%B2%9B%E5%9B%BD%E5%AE%B6%E7%BA%A7%E6%B5%B7%E6%B4%8B%E5%85%AC%E5%9B%AD/fuyao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%91%B6%E5%88%97%E5%B2%9B%E5%9B%BD%E5%AE%B6%E7%BA%A7%E6%B5%B7%E6%B4%8B%E5%85%AC%E5%9B%AD/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%91%B6%E5%88%97%E5%B2%9B%E5%9B%BD%E5%AE%B6%E7%BA%A7%E6%B5%B7%E6%B4%8B%E5%85%AC%E5%9B%AD/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%91%B6%E5%88%97%E5%B2%9B%E5%9B%BD%E5%AE%B6%E7%BA%A7%E6%B5%B7%E6%B4%8B%E5%85%AC%E5%9B%AD/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%91%B6%E5%88%97%E5%B2%9B%E5%9B%BD%E5%AE%B6%E7%BA%A7%E6%B5%B7%E6%B4%8B%E5%85%AC%E5%9B%AD/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%91%B6%E5%88%97%E5%B2%9B%E5%9B%BD%E5%AE%B6%E7%BA%A7%E6%B5%B7%E6%B4%8B%E5%85%AC%E5%9B%AD/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%91%B6%E5%88%97%E5%B2%9B%E5%9B%BD%E5%AE%B6%E7%BA%A7%E6%B5%B7%E6%B4%8B%E5%85%AC%E5%9B%AD/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%91%B6%E5%88%97%E5%B2%9B%E5%9B%BD%E5%AE%B6%E7%BA%A7%E6%B5%B7%E6%B4%8B%E5%85%AC%E5%9B%AD/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%91%B6%E5%88%97%E5%B2%9B%E5%9B%BD%E5%AE%B6%E7%BA%A7%E6%B5%B7%E6%B4%8B%E5%85%AC%E5%9B%AD/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%91%B6%E5%88%97%E5%B2%9B%E5%9B%BD%E5%AE%B6%E7%BA%A7%E6%B5%B7%E6%B4%8B%E5%85%AC%E5%9B%AD/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%91%B6%E5%88%97%E5%B2%9B%E5%9B%BD%E5%AE%B6%E7%BA%A7%E6%B5%B7%E6%B4%8B%E5%85%AC%E5%9B%AD/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("资国寺")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%84%E5%9B%BD%E5%AF%BA/ziguo.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%84%E5%9B%BD%E5%AF%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%84%E5%9B%BD%E5%AF%BA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%84%E5%9B%BD%E5%AF%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%84%E5%9B%BD%E5%AF%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%84%E5%9B%BD%E5%AF%BA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%84%E5%9B%BD%E5%AF%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%84%E5%9B%BD%E5%AF%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%84%E5%9B%BD%E5%AF%BA/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%84%E5%9B%BD%E5%AF%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E8%B5%84%E5%9B%BD%E5%AF%BA/5.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("杨家溪")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E6%9D%A8%E5%AE%B6%E6%BA%AA/yangjiaxi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E6%9D%A8%E5%AE%B6%E6%BA%AA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E6%9D%A8%E5%AE%B6%E6%BA%AA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E6%9D%A8%E5%AE%B6%E6%BA%AA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E6%9D%A8%E5%AE%B6%E6%BA%AA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E6%9D%A8%E5%AE%B6%E6%BA%AA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E6%9D%A8%E5%AE%B6%E6%BA%AA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E6%9D%A8%E5%AE%B6%E6%BA%AA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E6%9D%A8%E5%AE%B6%E6%BA%AA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E6%9D%A8%E5%AE%B6%E6%BA%AA/fengqiwushidazhai.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("罗汉溪景区")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%BD%97%E6%B1%89%E6%BA%AA%E6%99%AF%E5%8C%BA/luohanxi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%BD%97%E6%B1%89%E6%BA%AA%E6%99%AF%E5%8C%BA/fengqiwushidazhai.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%BD%97%E6%B1%89%E6%BA%AA%E6%99%AF%E5%8C%BA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%BD%97%E6%B1%89%E6%BA%AA%E6%99%AF%E5%8C%BA/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%BD%97%E6%B1%89%E6%BA%AA%E6%99%AF%E5%8C%BA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%BD%97%E6%B1%89%E6%BA%AA%E6%99%AF%E5%8C%BA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%BD%97%E6%B1%89%E6%BA%AA%E6%99%AF%E5%8C%BA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%BD%97%E6%B1%89%E6%BA%AA%E6%99%AF%E5%8C%BA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%BD%97%E6%B1%89%E6%BA%AA%E6%99%AF%E5%8C%BA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%BD%97%E6%B1%89%E6%BA%AA%E6%99%AF%E5%8C%BA/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("赤岸村")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/chianqiao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/baishuiyang.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("霞浦松山天后圣母行宫")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E6%9D%BE%E5%B1%B1%E5%A4%A9%E5%90%8E%E5%9C%A3%E6%AF%8D%E8%A1%8C%E5%AE%AB/shengmuxinggong.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E6%9D%BE%E5%B1%B1%E5%A4%A9%E5%90%8E%E5%9C%A3%E6%AF%8D%E8%A1%8C%E5%AE%AB/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E6%9D%BE%E5%B1%B1%E5%A4%A9%E5%90%8E%E5%9C%A3%E6%AF%8D%E8%A1%8C%E5%AE%AB/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E6%9D%BE%E5%B1%B1%E5%A4%A9%E5%90%8E%E5%9C%A3%E6%AF%8D%E8%A1%8C%E5%AE%AB/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E6%9D%BE%E5%B1%B1%E5%A4%A9%E5%90%8E%E5%9C%A3%E6%AF%8D%E8%A1%8C%E5%AE%AB/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E6%9D%BE%E5%B1%B1%E5%A4%A9%E5%90%8E%E5%9C%A3%E6%AF%8D%E8%A1%8C%E5%AE%AB/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E6%9D%BE%E5%B1%B1%E5%A4%A9%E5%90%8E%E5%9C%A3%E6%AF%8D%E8%A1%8C%E5%AE%AB/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E6%9D%BE%E5%B1%B1%E5%A4%A9%E5%90%8E%E5%9C%A3%E6%AF%8D%E8%A1%8C%E5%AE%AB/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E6%9D%BE%E5%B1%B1%E5%A4%A9%E5%90%8E%E5%9C%A3%E6%AF%8D%E8%A1%8C%E5%AE%AB/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E6%9D%BE%E5%B1%B1%E5%A4%A9%E5%90%8E%E5%9C%A3%E6%AF%8D%E8%A1%8C%E5%AE%AB/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E6%9D%BE%E5%B1%B1%E5%A4%A9%E5%90%8E%E5%9C%A3%E6%AF%8D%E8%A1%8C%E5%AE%AB/6.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("空海大师纪念堂")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%A9%BA%E6%B5%B7%E5%A4%A7%E5%B8%88%E7%BA%AA%E5%BF%B5%E5%A0%82/jiniantang.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E8%B5%A4%E5%B2%B8%E6%9D%91/baishuiyang.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("霞蒲滩涂")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E8%92%B2%E6%BB%A9%E6%B6%82/xiaputantu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E8%92%B2%E6%BB%A9%E6%B6%82/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E8%92%B2%E6%BB%A9%E6%B6%82/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E8%92%B2%E6%BB%A9%E6%B6%82/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E8%92%B2%E6%BB%A9%E6%B6%82/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E8%92%B2%E6%BB%A9%E6%B6%82/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E8%92%B2%E6%BB%A9%E6%B6%82/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E8%92%B2%E6%BB%A9%E6%B6%82/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E8%92%B2%E6%BB%A9%E6%B6%82/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E8%92%B2%E6%BB%A9%E6%B6%82/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E8%92%B2%E6%BB%A9%E6%B6%82/8.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("北岐滩涂")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%8C%97%E5%B2%90%E6%BB%A9%E6%B6%82/beiqitangtu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%8C%97%E5%B2%90%E6%BB%A9%E6%B6%82/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%8C%97%E5%B2%90%E6%BB%A9%E6%B6%82/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%8C%97%E5%B2%90%E6%BB%A9%E6%B6%82/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%8C%97%E5%B2%90%E6%BB%A9%E6%B6%82/fengqiwushidazhai.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%8C%97%E5%B2%90%E6%BB%A9%E6%B6%82/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%8C%97%E5%B2%90%E6%BB%A9%E6%B6%82/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%8C%97%E5%B2%90%E6%BB%A9%E6%B6%82/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%8C%97%E5%B2%90%E6%BB%A9%E6%B6%82/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%8C%97%E5%B2%90%E6%BB%A9%E6%B6%82/5.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("霞浦县城")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E5%9F%8E/xiapuxiancheng.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E5%9F%8E/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E5%9F%8E/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E5%9F%8E/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E5%9F%8E/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E5%9F%8E/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E5%9F%8E/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E5%9F%8E/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E5%9F%8E/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E5%9F%8E/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E5%9F%8E/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("小皓海滩")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%B0%8F%E7%9A%93%E6%B5%B7%E6%BB%A9/xiaohaohait.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%B0%8F%E7%9A%93%E6%B5%B7%E6%BB%A9/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%B0%8F%E7%9A%93%E6%B5%B7%E6%BB%A9/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%B0%8F%E7%9A%93%E6%B5%B7%E6%BB%A9/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%B0%8F%E7%9A%93%E6%B5%B7%E6%BB%A9/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%B0%8F%E7%9A%93%E6%B5%B7%E6%BB%A9/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%B0%8F%E7%9A%93%E6%B5%B7%E6%BB%A9/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%B0%8F%E7%9A%93%E6%B5%B7%E6%BB%A9/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%B0%8F%E7%9A%93%E6%B5%B7%E6%BB%A9/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%B0%8F%E7%9A%93%E6%B5%B7%E6%BB%A9/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%B0%8F%E7%9A%93%E6%B5%B7%E6%BB%A9/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }
        if(name.equals("七都溪")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E4%B8%83%E9%83%BD%E6%BA%AA/qiduxi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E4%B8%83%E9%83%BD%E6%BA%AA/cuipinghu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E4%B8%83%E9%83%BD%E6%BA%AA/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E4%B8%83%E9%83%BD%E6%BA%AA/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E4%B8%83%E9%83%BD%E6%BA%AA/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E4%B8%83%E9%83%BD%E6%BA%AA/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E4%B8%83%E9%83%BD%E6%BA%AA/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E4%B8%83%E9%83%BD%E6%BA%AA/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E4%B8%83%E9%83%BD%E6%BA%AA/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E4%B8%83%E9%83%BD%E6%BA%AA/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E4%B8%83%E9%83%BD%E6%BA%AA/7.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
        }

    }
    private void initView() {
        glideImageLoader = new GlideImageLoader();
        banner = findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(glideImageLoader);
        banner.setDelayTime(3000);
        banner.isAutoPlay(true);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImages(imagePath);
        banner.start();

    }


//    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
//          MyViewHolder holder = new MyViewHolder(LayoutInflater.from( DetailsActivity.this).inflate(R.layout.recycle_itemone,parent,false));
//            return holder   ;
//        }
//        @Override
//        public void onBindViewHolder(MyViewHolder holder, final int position) {
//
//            holder.iv.setImageResource(icons[position]);
//            holder.nameone.setText(name1[position]);
//            holder.buy.setText(buy[position]);
//            holder.troduce.setText(introduces[position]);
////            int img =intent.getIntExtra("detail_iv",0);
////            final int f = intent.getIntExtra("f",0);
////            name = intent.getStringExtra("detail_name");
////            final String ziliao = intent.getStringExtra("detail_introduce");
////            final String buy = intent.getStringExtra("detail_buy");
////            final  String name_receive = intent.getStringExtra("name");//景点地区
//
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.setClass(DetailsActivity.this,JiudianActivity.class);
//                    intent.putExtra("detail_iv",icons[position]);
//                    intent.putExtra("detail_name1",name1[position]);
//                    intent.putExtra("detail_buy",buy[position]);
//                    intent.putExtra("name",name_receive1);
//                    intent.putExtra("detail_introduce",introduces[position]);
//                    intent.putExtra("image",img);
//                    intent.putExtra("name1",name);
//                    intent.putExtra("buy1",buy1);
//                    intent.putExtra("troduce",ziliao);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//        }
//
//
//
//        @Override
//        public int getItemCount() {
//            return name1.length;
//        }
//        class MyViewHolder extends RecyclerView.ViewHolder{
//            TextView nameone;
//            ImageView iv;
//            TextView buy;
//            TextView troduce;
//            public MyViewHolder(View view){
//                super(view);
//                nameone = (TextView)view.findViewById(R.id.name);
//                iv = (ImageView)view.findViewById(R.id.iv);
//                buy=(TextView)view.findViewById(R.id.buy);
//                troduce =(TextView) view.findViewById(R.id.ziliao);
//            }
//        }
//    }

    private void openMap1(){
        if (isAvilible("com.baidu.BaiduMap")) {//传入指定应用包名
            try {

                //有经纬度的情况
//                Intent intent = Intent.getIntent("intent://map/direction?" +
//                        "destination=latlng:" + "34.264642646862" + "," + "108.95108518068" + "|name:我的目的地" +    //终点
//                        "&mode=driving&" +
//                        "&src=appname#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
//                startActivity(intent); //启动调用
                Intent intent = Intent.getIntent("intent://map/direction?" +
                        "destination=name"+
                        "&mode=driving&" +
                        "&src=appname#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                startActivity(intent); //启动调用

            } catch (URISyntaxException e) {
                Log.e("intent", e.getMessage());
            }
        } else {
            //market为路径，id为包名
            //显示手机上所有的market商店
            Toast.makeText(DetailsActivity.this, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }
    }

    private void openMap2()
    {
        if (isAvilible("com.autonavi.minimap")) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            //将功能Scheme以URI的方式传入data   有经纬度的情况
//            Uri uri = Uri.parse("androidamap://navi?sourceApplication=appname&poiname=fangheng&lat=" +
//                    "34.264642646862" + "&lon=" + "108.95108518068" + "&dev=1&style=2");
            Uri uri = Uri.parse("androidamap://poi?sourceApplication=softname" +
                    "&keywords=" +name+
                    "&dev=0");

            intent.setData(uri);

            //启动该页面即可
            startActivity(intent);
        } else {
            Toast.makeText(DetailsActivity.this, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }
    }

    public boolean isAvilible(String packageName){
        //获取packagemanager

        final PackageManager packageManager = this.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
    }



