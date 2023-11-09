package cn.itcast.recycleview;

import android.content.Intent;
import android.content.IntentFilter;
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

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JiudianActivity extends AppCompatActivity {
    String name;
    Button open;
    String nameadd;
    private CommonDialog myDialog;
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private DialogOrderTypeFragment mFragment2=new DialogOrderTypeFragment();
    private String[] name1 = {"大床房","双","优质大床房","优质双床房","豪华套房"};
    private int[] image = {R.drawable.achuncanguang,R.drawable.shuangchuangfang,R.drawable.youzhidachuangfang,R.drawable.youzhishuangchuangfang,R.drawable.haohuataofang};
    private  String[] buy ={"¥123","¥129","¥177","¥177","¥235"};
    private  String[] introduces = {"大床  有窗  18-22平方米",
            "双床房  有窗  18-22平方米",
            "大床  有窗  24-26平方米",
            "双床  有窗  24-26平方米",
            "大床  有窗  44-46平方米"};
    int imageone;
    String nameone;
    String buyone;
    String troduceone;
    String name_receive;
    int numble=1 ;
    public static String name_one;
    public static String nametype_one;
    public static String name_numble;
    private Banner banner;
    private GlideImageLoader glideImageLoader;
    private List<String> imagePath;
    private List<String> imageTitle;
    private List<Integer> imgs;


    int f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiudian);
        Intent intent = getIntent();
        imageone = intent.getIntExtra("image",0);
        nameone = intent.getStringExtra("name1");
        buyone = intent.getStringExtra("buy1");
        troduceone = intent.getStringExtra("troduce");

        f = intent.getIntExtra("f",0);
        name = intent.getStringExtra("detail_name1");
        final String buy = intent.getStringExtra("detail_buy");
        final  String introdece = intent.getStringExtra("detail_introduce");
        name_receive= intent.getStringExtra("name");//景点地区
        final TextView menpiao = findViewById(R.id.menpiao);
        menpiao.setText(buy);
        TextView jieshouziliao = findViewById(R.id.jieshouziliao);
        jieshouziliao.setText(introdece);

        TextView jieshouname = findViewById(R.id.jieshouname);
        jieshouname.setText(name);
        nameadd = name+name_receive;
        open=(Button)findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment2.show(getFragmentManager(), "android");

            }
        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    name_one = connect.jiudianreachone(name);
                    nametype_one = connect.jiudianreachtwo(name);
                    name_numble = connect.jiudianreachthree(name);
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        mRecyclerView = findViewById(R.id.recycle_jiudian);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);

        ImageView fanhui = findViewById(R.id.image);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.putExtra("detail_iv",imageone);
                intent.putExtra("detail_name",nameone);
                intent.putExtra("detail_introduce",troduceone);
                intent.putExtra("detail_buy",buyone);
                intent.putExtra("name",name_receive);
                intent.putExtra("f",f);
                intent.setClass(JiudianActivity.this,DetailsActivity.class);
                startActivity(intent);
                finish();
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
                    finish();
                }

            }
        });
        initDate();
        initView();
    }
    private void initDate() {
        imagePath = new ArrayList<>();
        imageTitle = new ArrayList<>();
        imgs = new ArrayList<>();
        if(name.equals("君安大酒店")){
            imgs.add(R.drawable.dachuangfa);
           imagePath.add("https://cn.bing.com/images/search?view=detailV2&ccid=YzkB0Ho9&id=08F071B9C44624151A598E7EDD8FC90B35CDEBF1&thid=OIP.YzkB0Ho9GM321YoMzHQHjQHaFo&mediaurl=https%3a%2f%2fts1.cn.mm.bing.net%2fth%2fid%2fR-C.633901d07a3d18cdf6d58a0ccc74078d%3frik%3d8evNNQvJj91%252bjg%26riu%3dhttp%253a%252f%252fimg.hkwb.net%252fatt%252fsite2%252f20120308%252f633901d07a3d18cdf6d58a0ccc74078d.jpg%26ehk%3dmupUGFlfxfEilMIkHoDFPVYnfR%252bSaF86fmgOkvvU8YM%253d%26risl%3d%26pid%3dImgRaw%26r%3d0&exph=1200&expw=1580&q=%e4%b8%96%e7%95%8c%e6%97%85%e6%b8%b8%e8%83%9c%e5%9c%b0&simid=608053720556060181&FORM=IRPRST&ck=35DA4357AD6688BF3018978C17762F1E&selectedIndex=1.png");
//            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
//            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/2.png");
//            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/1.png");
//            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
//            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
//            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/6.png");
//            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/4.png");
//            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/9.png");
//            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
//            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/8.png");



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
        if(name.equals("宁德兰逸精品酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%85%B0%E9%80%B8%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/lanyijingpin.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%85%B0%E9%80%B8%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%85%B0%E9%80%B8%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%85%B0%E9%80%B8%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%85%B0%E9%80%B8%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%85%B0%E9%80%B8%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%85%B0%E9%80%B8%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%85%B0%E9%80%B8%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%85%B0%E9%80%B8%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%85%B0%E9%80%B8%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%85%B0%E9%80%B8%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/2.png");

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
        if(name.equals("锐思特酒店宁德万达店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%BA%97/ruisite.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%BA%97/10.png");

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
        if(name.equals("宁德唯依城市主题酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%94%AF%E4%BE%9D%E5%9F%8E%E5%B8%82%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/weiyi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%94%AF%E4%BE%9D%E5%9F%8E%E5%B8%82%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%94%AF%E4%BE%9D%E5%9F%8E%E5%B8%82%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%94%AF%E4%BE%9D%E5%9F%8E%E5%B8%82%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%94%AF%E4%BE%9D%E5%9F%8E%E5%B8%82%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%94%AF%E4%BE%9D%E5%9F%8E%E5%B8%82%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%94%AF%E4%BE%9D%E5%9F%8E%E5%B8%82%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%94%AF%E4%BE%9D%E5%9F%8E%E5%B8%82%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%94%AF%E4%BE%9D%E5%9F%8E%E5%B8%82%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%94%AF%E4%BE%9D%E5%9F%8E%E5%B8%82%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%94%AF%E4%BE%9D%E5%9F%8E%E5%B8%82%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/7.png");

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
        if(name.equals("宁德市东方国际威悦大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E4%B8%9C%E6%96%B9%E5%9B%BD%E9%99%85%E5%A8%81%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/weiyue.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E4%B8%9C%E6%96%B9%E5%9B%BD%E9%99%85%E5%A8%81%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E4%B8%9C%E6%96%B9%E5%9B%BD%E9%99%85%E5%A8%81%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E4%B8%9C%E6%96%B9%E5%9B%BD%E9%99%85%E5%A8%81%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E4%B8%9C%E6%96%B9%E5%9B%BD%E9%99%85%E5%A8%81%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E4%B8%9C%E6%96%B9%E5%9B%BD%E9%99%85%E5%A8%81%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E4%B8%9C%E6%96%B9%E5%9B%BD%E9%99%85%E5%A8%81%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E4%B8%9C%E6%96%B9%E5%9B%BD%E9%99%85%E5%A8%81%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E4%B8%9C%E6%96%B9%E5%9B%BD%E9%99%85%E5%A8%81%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E4%B8%9C%E6%96%B9%E5%9B%BD%E9%99%85%E5%A8%81%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E5%B8%82%E4%B8%9C%E6%96%B9%E5%9B%BD%E9%99%85%E5%A8%81%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/2.png");

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
        if(name.equals("宁德沃尔假日酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B2%83%E5%B0%94%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/woer.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B2%83%E5%B0%94%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B2%83%E5%B0%94%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B2%83%E5%B0%94%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B2%83%E5%B0%94%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B2%83%E5%B0%94%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B2%83%E5%B0%94%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B2%83%E5%B0%94%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B2%83%E5%B0%94%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B2%83%E5%B0%94%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E6%B2%83%E5%B0%94%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/7.png");

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
        if(name.equals("宁德东方艾美酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%96%B9%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/aimei.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%96%B9%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%96%B9%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%96%B9%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%96%B9%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%96%B9%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%96%B9%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%96%B9%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%96%B9%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%96%B9%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E4%B8%9C%E6%96%B9%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/10.png");

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
        if(name.equals("WORD影院公寓")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/word.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/3.png");

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
        if(name.equals("锦江之星风尚宁德万达广场店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/dachuangfa.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/3.png");

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
        if(name.equals("宁德玖间堂泊城酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/jiujian.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/2.png");

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
        if(name.equals("速8酒店宁德嘉宇汽车北站")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%80%9F8%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E5%98%89%E5%AE%87%E6%B1%BD%E8%BD%A6%E5%8C%97%E7%AB%99/suba.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%AE%81%E5%BE%B7%E7%8E%96%E9%97%B4%E5%A0%82%E6%B3%8A%E5%9F%8E%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E9%A3%8E%E5%B0%9A%E5%AE%81%E5%BE%B7%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/WORD%E5%BD%B1%E9%99%A2%E5%85%AC%E5%AF%93/3.png");

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
        if(name.equals("古田华侨酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E5%8D%8E%E4%BE%A8%E9%85%92%E5%BA%97/huaqiao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E5%8D%8E%E4%BE%A8%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E5%8D%8E%E4%BE%A8%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E5%8D%8E%E4%BE%A8%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E5%8D%8E%E4%BE%A8%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E5%8D%8E%E4%BE%A8%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E5%8D%8E%E4%BE%A8%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E5%8D%8E%E4%BE%A8%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E5%8D%8E%E4%BE%A8%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E5%8D%8E%E4%BE%A8%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E5%8D%8E%E4%BE%A8%E9%85%92%E5%BA%97/9.png");

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
        if(name.equals("宁德古田沐舍主题酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E6%B2%90%E8%88%8D%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/mushe.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E6%B2%90%E8%88%8D%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E6%B2%90%E8%88%8D%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E6%B2%90%E8%88%8D%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E6%B2%90%E8%88%8D%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E6%B2%90%E8%88%8D%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E6%B2%90%E8%88%8D%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E6%B2%90%E8%88%8D%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E6%B2%90%E8%88%8D%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E6%B2%90%E8%88%8D%E4%B8%BB%E9%A2%98%E9%85%92%E5%BA%97/2.png");

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
        if(name.equals("古田曼福酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%9B%BC%E7%A6%8F%E9%85%92%E5%BA%97/manfu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%9B%BC%E7%A6%8F%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%9B%BC%E7%A6%8F%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%9B%BC%E7%A6%8F%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%9B%BC%E7%A6%8F%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%9B%BC%E7%A6%8F%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%9B%BC%E7%A6%8F%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%9B%BC%E7%A6%8F%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E6%9B%BC%E7%A6%8F%E9%85%92%E5%BA%97/4.png");

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
        if(name.equals("古田金源大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/jinyuan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/8.png");

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
        if(name.equals("艾美酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/aimei.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/2.png");

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
        if(name.equals("宁德新世贸大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E6%96%B0%E4%B8%96%E8%B4%B8%E5%A4%A7%E9%85%92%E5%BA%97/xinshimao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E6%96%B0%E4%B8%96%E8%B4%B8%E5%A4%A7%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E6%96%B0%E4%B8%96%E8%B4%B8%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E6%96%B0%E4%B8%96%E8%B4%B8%E5%A4%A7%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E6%96%B0%E4%B8%96%E8%B4%B8%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E6%96%B0%E4%B8%96%E8%B4%B8%E5%A4%A7%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E6%96%B0%E4%B8%96%E8%B4%B8%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E6%96%B0%E4%B8%96%E8%B4%B8%E5%A4%A7%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E6%96%B0%E4%B8%96%E8%B4%B8%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E6%96%B0%E4%B8%96%E8%B4%B8%E5%A4%A7%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E6%96%B0%E4%B8%96%E8%B4%B8%E5%A4%A7%E9%85%92%E5%BA%97/6.png");

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
        if(name.equals("宁德古田好莱斯登酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%A5%BD%E8%8E%B1%E6%96%AF%E7%99%BB%E9%85%92%E5%BA%97/haolai.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%A5%BD%E8%8E%B1%E6%96%AF%E7%99%BB%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%A5%BD%E8%8E%B1%E6%96%AF%E7%99%BB%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%A5%BD%E8%8E%B1%E6%96%AF%E7%99%BB%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%A5%BD%E8%8E%B1%E6%96%AF%E7%99%BB%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%A5%BD%E8%8E%B1%E6%96%AF%E7%99%BB%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%A5%BD%E8%8E%B1%E6%96%AF%E7%99%BB%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%A5%BD%E8%8E%B1%E6%96%AF%E7%99%BB%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%A5%BD%E8%8E%B1%E6%96%AF%E7%99%BB%E9%85%92%E5%BA%97/3.png");

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
        if(name.equals("宁德古田县好望角酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%8E%BF%E5%A5%BD%E6%9C%9B%E8%A7%92%E9%85%92%E5%BA%97/haowangjiao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%8E%BF%E5%A5%BD%E6%9C%9B%E8%A7%92%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%8E%BF%E5%A5%BD%E6%9C%9B%E8%A7%92%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%8E%BF%E5%A5%BD%E6%9C%9B%E8%A7%92%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%8E%BF%E5%A5%BD%E6%9C%9B%E8%A7%92%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%8E%BF%E5%A5%BD%E6%9C%9B%E8%A7%92%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%8E%BF%E5%A5%BD%E6%9C%9B%E8%A7%92%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%8E%BF%E5%A5%BD%E6%9C%9B%E8%A7%92%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%8F%A4%E7%94%B0%E5%8E%BF%E5%A5%BD%E6%9C%9B%E8%A7%92%E9%85%92%E5%BA%97/1.png");

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
        if(name.equals("古田锦贤宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%94%A6%E8%B4%A4%E5%AE%BE%E9%A6%86/jingxian.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%94%A6%E8%B4%A4%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%94%A6%E8%B4%A4%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%94%A6%E8%B4%A4%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%94%A6%E8%B4%A4%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%94%A6%E8%B4%A4%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%94%A6%E8%B4%A4%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%94%A6%E8%B4%A4%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%94%A6%E8%B4%A4%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%94%A6%E8%B4%A4%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%8F%A4%E7%94%B0%E5%8E%BF/%E5%8F%A4%E7%94%B0%E9%94%A6%E8%B4%A4%E5%AE%BE%E9%A6%86/9.png");

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
        if(name.equals("香悦大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%A6%99%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/xiangyue.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%A6%99%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%A6%99%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%A6%99%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%A6%99%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%A6%99%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%A6%99%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%A6%99%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%A6%99%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%A6%99%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E9%A6%99%E6%82%A6%E5%A4%A7%E9%85%92%E5%BA%97/8.png");

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
        if(name.equals("屏南佳和酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E4%BD%B3%E5%92%8C%E9%85%92%E5%BA%97/jiahe.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E4%BD%B3%E5%92%8C%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E4%BD%B3%E5%92%8C%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E4%BD%B3%E5%92%8C%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E4%BD%B3%E5%92%8C%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E4%BD%B3%E5%92%8C%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E4%BD%B3%E5%92%8C%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E4%BD%B3%E5%92%8C%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E4%BD%B3%E5%92%8C%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E4%BD%B3%E5%92%8C%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E4%BD%B3%E5%92%8C%E9%85%92%E5%BA%97/taimushan.png");

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
        if(name.equals("宁德屏南凯城酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%B1%8F%E5%8D%97%E5%87%AF%E5%9F%8E%E9%85%92%E5%BA%97/kaicheng.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%B1%8F%E5%8D%97%E5%87%AF%E5%9F%8E%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%B1%8F%E5%8D%97%E5%87%AF%E5%9F%8E%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%B1%8F%E5%8D%97%E5%87%AF%E5%9F%8E%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%B1%8F%E5%8D%97%E5%87%AF%E5%9F%8E%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%B1%8F%E5%8D%97%E5%87%AF%E5%9F%8E%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%B1%8F%E5%8D%97%E5%87%AF%E5%9F%8E%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%B1%8F%E5%8D%97%E5%87%AF%E5%9F%8E%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%B1%8F%E5%8D%97%E5%87%AF%E5%9F%8E%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%B1%8F%E5%8D%97%E5%87%AF%E5%9F%8E%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%B1%8F%E5%8D%97%E5%87%AF%E5%9F%8E%E9%85%92%E5%BA%97/7.png");

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
        if(name.equals("屏南自在花时客栈")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%87%AA%E5%9C%A8%E8%8A%B1%E6%97%B6%E5%AE%A2%E6%A0%88/zizai.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%87%AA%E5%9C%A8%E8%8A%B1%E6%97%B6%E5%AE%A2%E6%A0%88/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%87%AA%E5%9C%A8%E8%8A%B1%E6%97%B6%E5%AE%A2%E6%A0%88/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%87%AA%E5%9C%A8%E8%8A%B1%E6%97%B6%E5%AE%A2%E6%A0%88/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%87%AA%E5%9C%A8%E8%8A%B1%E6%97%B6%E5%AE%A2%E6%A0%88/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%87%AA%E5%9C%A8%E8%8A%B1%E6%97%B6%E5%AE%A2%E6%A0%88/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%87%AA%E5%9C%A8%E8%8A%B1%E6%97%B6%E5%AE%A2%E6%A0%88/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%87%AA%E5%9C%A8%E8%8A%B1%E6%97%B6%E5%AE%A2%E6%A0%88/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%87%AA%E5%9C%A8%E8%8A%B1%E6%97%B6%E5%AE%A2%E6%A0%88/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%87%AA%E5%9C%A8%E8%8A%B1%E6%97%B6%E5%AE%A2%E6%A0%88/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%87%AA%E5%9C%A8%E8%8A%B1%E6%97%B6%E5%AE%A2%E6%A0%88/8.png");

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
        if(name.equals("屏南悦竹精品酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E6%82%A6%E7%AB%B9%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/yuezhu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E6%82%A6%E7%AB%B9%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E6%82%A6%E7%AB%B9%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E6%82%A6%E7%AB%B9%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E6%82%A6%E7%AB%B9%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E6%82%A6%E7%AB%B9%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E6%82%A6%E7%AB%B9%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E6%82%A6%E7%AB%B9%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E6%82%A6%E7%AB%B9%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E6%82%A6%E7%AB%B9%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E6%82%A6%E7%AB%B9%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/1.png");

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
        if(name.equals("屏南远景酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%BF%9C%E6%99%AF%E9%85%92%E5%BA%97/yuanjing.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%BF%9C%E6%99%AF%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%BF%9C%E6%99%AF%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%BF%9C%E6%99%AF%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%BF%9C%E6%99%AF%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%BF%9C%E6%99%AF%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%BF%9C%E6%99%AF%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%BF%9C%E6%99%AF%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%BF%9C%E6%99%AF%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%BF%9C%E6%99%AF%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E8%BF%9C%E6%99%AF%E9%85%92%E5%BA%97/8.png");

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
        if(name.equals("屏南白水洋舒馨宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E7%99%BD%E6%B0%B4%E6%B4%8B%E8%88%92%E9%A6%A8%E5%AE%BE%E9%A6%86/shuxing.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E7%99%BD%E6%B0%B4%E6%B4%8B%E8%88%92%E9%A6%A8%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E7%99%BD%E6%B0%B4%E6%B4%8B%E8%88%92%E9%A6%A8%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E7%99%BD%E6%B0%B4%E6%B4%8B%E8%88%92%E9%A6%A8%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E7%99%BD%E6%B0%B4%E6%B4%8B%E8%88%92%E9%A6%A8%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E7%99%BD%E6%B0%B4%E6%B4%8B%E8%88%92%E9%A6%A8%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E7%99%BD%E6%B0%B4%E6%B4%8B%E8%88%92%E9%A6%A8%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E7%99%BD%E6%B0%B4%E6%B4%8B%E8%88%92%E9%A6%A8%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E7%99%BD%E6%B0%B4%E6%B4%8B%E8%88%92%E9%A6%A8%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E7%99%BD%E6%B0%B4%E6%B4%8B%E8%88%92%E9%A6%A8%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E7%99%BD%E6%B0%B4%E6%B4%8B%E8%88%92%E9%A6%A8%E5%AE%BE%E9%A6%86/9.png");

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
        if(name.equals("屏南长兴宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E9%95%BF%E5%85%B4%E5%AE%BE%E9%A6%86/changxing.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E9%95%BF%E5%85%B4%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E9%95%BF%E5%85%B4%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E9%95%BF%E5%85%B4%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E9%95%BF%E5%85%B4%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E9%95%BF%E5%85%B4%E5%AE%BE%E9%A6%86/taimushan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E9%95%BF%E5%85%B4%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E9%95%BF%E5%85%B4%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E9%95%BF%E5%85%B4%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E9%95%BF%E5%85%B4%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%B1%8F%E5%8D%97%E5%8E%BF/%E5%B1%8F%E5%8D%97%E9%95%BF%E5%85%B4%E5%AE%BE%E9%A6%86/7.png");

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
        if(name.equals("格林豪泰酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%A0%BC%E6%9E%97%E8%B1%AA%E6%B3%B0%E9%85%92%E5%BA%97/haotai.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%A0%BC%E6%9E%97%E8%B1%AA%E6%B3%B0%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%A0%BC%E6%9E%97%E8%B1%AA%E6%B3%B0%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%A0%BC%E6%9E%97%E8%B1%AA%E6%B3%B0%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%A0%BC%E6%9E%97%E8%B1%AA%E6%B3%B0%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%A0%BC%E6%9E%97%E8%B1%AA%E6%B3%B0%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%A0%BC%E6%9E%97%E8%B1%AA%E6%B3%B0%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%A0%BC%E6%9E%97%E8%B1%AA%E6%B3%B0%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E6%A0%BC%E6%9E%97%E8%B1%AA%E6%B3%B0%E9%85%92%E5%BA%97/9.png");

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
        if(name.equals("周宁县永盛宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E5%8E%BF%E6%B0%B8%E7%9B%9B%E5%AE%BE%E9%A6%86/yongsheng.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E5%8E%BF%E6%B0%B8%E7%9B%9B%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E5%8E%BF%E6%B0%B8%E7%9B%9B%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E5%8E%BF%E6%B0%B8%E7%9B%9B%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E5%8E%BF%E6%B0%B8%E7%9B%9B%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E5%8E%BF%E6%B0%B8%E7%9B%9B%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E5%8E%BF%E6%B0%B8%E7%9B%9B%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E5%8E%BF%E6%B0%B8%E7%9B%9B%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E5%8E%BF%E6%B0%B8%E7%9B%9B%E5%AE%BE%E9%A6%86/9.png");

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
        if(name.equals("锐思特酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97/ruisitezhouning.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97/10.png");

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
        if(name.equals("周宁龙华大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E9%BE%99%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/longhua.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E9%BE%99%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E9%BE%99%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E9%BE%99%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E9%BE%99%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E9%BE%99%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E9%BE%99%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E9%BE%99%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E9%BE%99%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E9%BE%99%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E9%BE%99%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/4.png");

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
        if(name.equals("世纪金源大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B8%96%E7%BA%AA%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/jinyuanzhou.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B8%96%E7%BA%AA%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B8%96%E7%BA%AA%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B8%96%E7%BA%AA%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B8%96%E7%BA%AA%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B8%96%E7%BA%AA%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B8%96%E7%BA%AA%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B8%96%E7%BA%AA%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B8%96%E7%BA%AA%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B8%96%E7%BA%AA%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E4%B8%96%E7%BA%AA%E9%87%91%E6%BA%90%E5%A4%A7%E9%85%92%E5%BA%97/1.png");

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
        if(name.equals("周宁河畔小居")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%B2%B3%E7%95%94%E5%B0%8F%E5%B1%85/hepan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%B2%B3%E7%95%94%E5%B0%8F%E5%B1%85/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%B2%B3%E7%95%94%E5%B0%8F%E5%B1%85/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%B2%B3%E7%95%94%E5%B0%8F%E5%B1%85/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%B2%B3%E7%95%94%E5%B0%8F%E5%B1%85/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%B2%B3%E7%95%94%E5%B0%8F%E5%B1%85/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%B2%B3%E7%95%94%E5%B0%8F%E5%B1%85/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%B2%B3%E7%95%94%E5%B0%8F%E5%B1%85/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%B2%B3%E7%95%94%E5%B0%8F%E5%B1%85/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%B2%B3%E7%95%94%E5%B0%8F%E5%B1%85/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E6%B2%B3%E7%95%94%E5%B0%8F%E5%B1%85/7.png");

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
        if(name.equals("周宁东洋溪大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E4%B8%9C%E6%B4%8B%E6%BA%AA%E5%A4%A7%E9%85%92%E5%BA%97/dongxiyang.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E4%B8%9C%E6%B4%8B%E6%BA%AA%E5%A4%A7%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E4%B8%9C%E6%B4%8B%E6%BA%AA%E5%A4%A7%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E4%B8%9C%E6%B4%8B%E6%BA%AA%E5%A4%A7%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E4%B8%9C%E6%B4%8B%E6%BA%AA%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E4%B8%9C%E6%B4%8B%E6%BA%AA%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E4%B8%9C%E6%B4%8B%E6%BA%AA%E5%A4%A7%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E4%B8%9C%E6%B4%8B%E6%BA%AA%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E4%B8%9C%E6%B4%8B%E6%BA%AA%E5%A4%A7%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E4%B8%9C%E6%B4%8B%E6%BA%AA%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%91%A8%E5%AE%81%E5%8E%BF/%E5%91%A8%E5%AE%81%E4%B8%9C%E6%B4%8B%E6%BA%AA%E5%A4%A7%E9%85%92%E5%BA%97/1.png");

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
        if(name.equals("寿宁帝豪商务宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%B8%9D%E8%B1%AA%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/dihaoshangwu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%B8%9D%E8%B1%AA%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%B8%9D%E8%B1%AA%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%B8%9D%E8%B1%AA%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%B8%9D%E8%B1%AA%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%B8%9D%E8%B1%AA%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%B8%9D%E8%B1%AA%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%B8%9D%E8%B1%AA%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%B8%9D%E8%B1%AA%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%B8%9D%E8%B1%AA%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E5%B8%9D%E8%B1%AA%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/8.png");

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
        if(name.equals("大都·豪庭宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%A4%A7%E9%83%BD%C2%B7%E8%B1%AA%E5%BA%AD%E5%AE%BE%E9%A6%86/dadu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%A4%A7%E9%83%BD%C2%B7%E8%B1%AA%E5%BA%AD%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%A4%A7%E9%83%BD%C2%B7%E8%B1%AA%E5%BA%AD%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%A4%A7%E9%83%BD%C2%B7%E8%B1%AA%E5%BA%AD%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%A4%A7%E9%83%BD%C2%B7%E8%B1%AA%E5%BA%AD%E5%AE%BE%E9%A6%86/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%A4%A7%E9%83%BD%C2%B7%E8%B1%AA%E5%BA%AD%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%A4%A7%E9%83%BD%C2%B7%E8%B1%AA%E5%BA%AD%E5%AE%BE%E9%A6%86/manfu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%A4%A7%E9%83%BD%C2%B7%E8%B1%AA%E5%BA%AD%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%A4%A7%E9%83%BD%C2%B7%E8%B1%AA%E5%BA%AD%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%A4%A7%E9%83%BD%C2%B7%E8%B1%AA%E5%BA%AD%E5%AE%BE%E9%A6%86/2.png");

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
        if(name.equals("宁德寿宁柏悦宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E6%9F%8F%E6%82%A6%E5%AE%BE%E9%A6%86/boyue.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E6%9F%8F%E6%82%A6%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E6%9F%8F%E6%82%A6%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E6%9F%8F%E6%82%A6%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E6%9F%8F%E6%82%A6%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E6%9F%8F%E6%82%A6%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E6%9F%8F%E6%82%A6%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E6%9F%8F%E6%82%A6%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E6%9F%8F%E6%82%A6%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E6%9F%8F%E6%82%A6%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E6%9F%8F%E6%82%A6%E5%AE%BE%E9%A6%86/9.png");

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
        if(name.equals("寿宁裕龙宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/yuelong.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/9.png");

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
        if(name.equals("寿宁悦龙宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/yuelong.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E6%82%A6%E9%BE%99%E5%AE%BE%E9%A6%86/5.png");

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
        if(name.equals("寿宁东湖大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E4%B8%9C%E6%B9%96%E5%A4%A7%E9%85%92%E5%BA%97/donghu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E4%B8%9C%E6%B9%96%E5%A4%A7%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E4%B8%9C%E6%B9%96%E5%A4%A7%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E4%B8%9C%E6%B9%96%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E4%B8%9C%E6%B9%96%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E4%B8%9C%E6%B9%96%E5%A4%A7%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E4%B8%9C%E6%B9%96%E5%A4%A7%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E4%B8%9C%E6%B9%96%E5%A4%A7%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E4%B8%9C%E6%B9%96%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E4%B8%9C%E6%B9%96%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E4%B8%9C%E6%B9%96%E5%A4%A7%E9%85%92%E5%BA%97/1.png");

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
        if(name.equals("宁德寿宁聚源宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E8%81%9A%E6%BA%90%E5%AE%BE%E9%A6%86/juyuan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E8%81%9A%E6%BA%90%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E8%81%9A%E6%BA%90%E5%AE%BE%E9%A6%86/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E8%81%9A%E6%BA%90%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E8%81%9A%E6%BA%90%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E8%81%9A%E6%BA%90%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E8%81%9A%E6%BA%90%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E8%81%9A%E6%BA%90%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E8%81%9A%E6%BA%90%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E8%81%9A%E6%BA%90%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AE%81%E5%BE%B7%E5%AF%BF%E5%AE%81%E8%81%9A%E6%BA%90%E5%AE%BE%E9%A6%86/1.png");

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
        if(name.equals("寿宁福宁宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E7%A6%8F%E5%AE%81%E5%AE%BE%E9%A6%86/funing.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E7%A6%8F%E5%AE%81%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E7%A6%8F%E5%AE%81%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E7%A6%8F%E5%AE%81%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E7%A6%8F%E5%AE%81%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E7%A6%8F%E5%AE%81%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E7%A6%8F%E5%AE%81%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E7%A6%8F%E5%AE%81%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E7%A6%8F%E5%AE%81%E5%AE%BE%E9%A6%86/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E7%A6%8F%E5%AE%81%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E5%AF%BF%E5%AE%81%E5%8E%BF/%E5%AF%BF%E5%AE%81%E7%A6%8F%E5%AE%81%E5%AE%BE%E9%A6%86/1.png");

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
        if(name.equals("天隆商务宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E9%9A%86%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/tianlong.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E9%9A%86%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E9%9A%86%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E9%9A%86%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E9%9A%86%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E9%9A%86%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E9%9A%86%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E9%9A%86%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E9%9A%86%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E9%9A%86%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%A4%A9%E9%9A%86%E5%95%86%E5%8A%A1%E5%AE%BE%E9%A6%86/9.png");

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
        if(name.equals("宁德好乐迪假日酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%A5%BD%E4%B9%90%E8%BF%AA%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/haoledi.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%A5%BD%E4%B9%90%E8%BF%AA%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%A5%BD%E4%B9%90%E8%BF%AA%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%A5%BD%E4%B9%90%E8%BF%AA%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%A5%BD%E4%B9%90%E8%BF%AA%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%A5%BD%E4%B9%90%E8%BF%AA%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%A5%BD%E4%B9%90%E8%BF%AA%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%A5%BD%E4%B9%90%E8%BF%AA%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%A5%BD%E4%B9%90%E8%BF%AA%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%A5%BD%E4%B9%90%E8%BF%AA%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%A5%BD%E4%B9%90%E8%BF%AA%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/9.png");

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
        if(name.equals("福安南舟宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%97%E8%88%9F%E5%AE%BE%E9%A6%86/nanzhou.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%97%E8%88%9F%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%97%E8%88%9F%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%97%E8%88%9F%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%97%E8%88%9F%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%97%E8%88%9F%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%97%E8%88%9F%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%97%E8%88%9F%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%97%E8%88%9F%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%97%E8%88%9F%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%97%E8%88%9F%E5%AE%BE%E9%A6%86/9.png");

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
        if(name.equals("福安花园宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E8%8A%B1%E5%9B%AD%E5%AE%BE%E9%A6%86/huayuan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E8%8A%B1%E5%9B%AD%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E8%8A%B1%E5%9B%AD%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E8%8A%B1%E5%9B%AD%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E8%8A%B1%E5%9B%AD%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E8%8A%B1%E5%9B%AD%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E8%8A%B1%E5%9B%AD%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E8%8A%B1%E5%9B%AD%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E8%8A%B1%E5%9B%AD%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E8%8A%B1%E5%9B%AD%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E8%8A%B1%E5%9B%AD%E5%AE%BE%E9%A6%86/9.png");

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
        if(name.equals("宁德尚客快捷酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%B0%9A%E5%AE%A2%E5%BF%AB%E6%8D%B7%E9%85%92%E5%BA%97/kuaijie.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%B0%9A%E5%AE%A2%E5%BF%AB%E6%8D%B7%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%B0%9A%E5%AE%A2%E5%BF%AB%E6%8D%B7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%B0%9A%E5%AE%A2%E5%BF%AB%E6%8D%B7%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%B0%9A%E5%AE%A2%E5%BF%AB%E6%8D%B7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%B0%9A%E5%AE%A2%E5%BF%AB%E6%8D%B7%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%B0%9A%E5%AE%A2%E5%BF%AB%E6%8D%B7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%B0%9A%E5%AE%A2%E5%BF%AB%E6%8D%B7%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%B0%9A%E5%AE%A2%E5%BF%AB%E6%8D%B7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%B0%9A%E5%AE%A2%E5%BF%AB%E6%8D%B7%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E5%AE%81%E5%BE%B7%E5%B0%9A%E5%AE%A2%E5%BF%AB%E6%8D%B7%E9%85%92%E5%BA%97/9.png");

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
        if(name.equals("海鑫精品酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%B5%B7%E9%91%AB%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/haixingjingping.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%B5%B7%E9%91%AB%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%B5%B7%E9%91%AB%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%B5%B7%E9%91%AB%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%B5%B7%E9%91%AB%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%B5%B7%E9%91%AB%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%B5%B7%E9%91%AB%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%B5%B7%E9%91%AB%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%B5%B7%E9%91%AB%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%B5%B7%E9%91%AB%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E6%B5%B7%E9%91%AB%E7%B2%BE%E5%93%81%E9%85%92%E5%BA%97/9.png");

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
        if(name.equals("福安华利宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%8E%E5%88%A9%E5%AE%BE%E9%A6%86/huali.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%8E%E5%88%A9%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%8E%E5%88%A9%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%8E%E5%88%A9%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%8E%E5%88%A9%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%8E%E5%88%A9%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%8E%E5%88%A9%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%8E%E5%88%A9%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%8E%E5%88%A9%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%8E%E5%88%A9%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E5%8D%8E%E5%88%A9%E5%AE%BE%E9%A6%86/9.png");

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
        if(name.equals("福安铂晶悦己酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E9%93%82%E6%99%B6%E6%82%A6%E5%B7%B1%E9%85%92%E5%BA%97/bojingyueji.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E9%93%82%E6%99%B6%E6%82%A6%E5%B7%B1%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E9%93%82%E6%99%B6%E6%82%A6%E5%B7%B1%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E9%93%82%E6%99%B6%E6%82%A6%E5%B7%B1%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E9%93%82%E6%99%B6%E6%82%A6%E5%B7%B1%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E9%93%82%E6%99%B6%E6%82%A6%E5%B7%B1%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E9%93%82%E6%99%B6%E6%82%A6%E5%B7%B1%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E9%93%82%E6%99%B6%E6%82%A6%E5%B7%B1%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E9%93%82%E6%99%B6%E6%82%A6%E5%B7%B1%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E9%93%82%E6%99%B6%E6%82%A6%E5%B7%B1%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E5%AE%89%E5%B8%82/%E7%A6%8F%E5%AE%89%E9%93%82%E6%99%B6%E6%82%A6%E5%B7%B1%E9%85%92%E5%BA%97/9.png");

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
        if(name.equals("柘荣京鼎荣商务酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/jingding.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/10.png");

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
        if(name.equals("柘荣县富商商务酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/fushang.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/haowangjiao.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/2.png");

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
        if(name.equals("柘荣幽舍酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/youshe.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/1.png");

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
        if(name.equals("柘荣县宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/zherongxianbingguan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/taimushan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/3.png");

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
        if(name.equals("柘荣县九华洲宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E5%8D%8E%E6%B4%B2%E5%AE%BE%E9%A6%86/jiuhuazhou.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E5%8D%8E%E6%B4%B2%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E5%8D%8E%E6%B4%B2%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E5%8D%8E%E6%B4%B2%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E5%8D%8E%E6%B4%B2%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E5%8D%8E%E6%B4%B2%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E5%8D%8E%E6%B4%B2%E5%AE%BE%E9%A6%86/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E5%8D%8E%E6%B4%B2%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E5%8D%8E%E6%B4%B2%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E5%8D%8E%E6%B4%B2%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E4%B9%9D%E5%8D%8E%E6%B4%B2%E5%AE%BE%E9%A6%86/3.png");

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
        if(name.equals("柘荣东华大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%B8%9C%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/donghuadajiudian.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%B8%9C%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%B8%9C%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%B8%9C%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%B8%9C%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%B8%9C%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%B8%9C%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%B8%9C%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%B8%9C%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%B8%9C%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%B8%9C%E5%8D%8E%E5%A4%A7%E9%85%92%E5%BA%97/1.png");

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
        if(name.equals("宁德乘峰宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B9%98%E5%B3%B0%E5%AE%BE%E9%A6%86/chengfengbinguan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B9%98%E5%B3%B0%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B9%98%E5%B3%B0%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B9%98%E5%B3%B0%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B9%98%E5%B3%B0%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B9%98%E5%B3%B0%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B9%98%E5%B3%B0%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B9%98%E5%B3%B0%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B9%98%E5%B3%B0%E5%AE%BE%E9%A6%86/taimushan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B9%98%E5%B3%B0%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E5%AE%81%E5%BE%B7%E4%B9%98%E5%B3%B0%E5%AE%BE%E9%A6%86/3.png");

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
        if(name.equals("福鼎太姥大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%85%92%E5%BA%97/taimushan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%85%92%E5%BA%97/8.png");

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
        if(name.equals("福鼎艾美酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/fudingaimei.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E8%89%BE%E7%BE%8E%E9%85%92%E5%BA%97/9.png");

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
        if(name.equals("福鼎山水假日酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%B1%B1%E6%B0%B4%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/fudingshanshuijiari.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%B1%B1%E6%B0%B4%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%B1%B1%E6%B0%B4%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%B1%B1%E6%B0%B4%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%B1%B1%E6%B0%B4%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%B1%B1%E6%B0%B4%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%B1%B1%E6%B0%B4%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%B1%B1%E6%B0%B4%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%B1%B1%E6%B0%B4%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%B1%B1%E6%B0%B4%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%B1%B1%E6%B0%B4%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/9.png");

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
        if(name.equals("锦江之星福鼎太姥大道店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%81%93%E5%BA%97/jinjiangzhixin.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%81%93%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%81%93%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%81%93%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%81%93%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%81%93%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%81%93%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%81%93%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%81%93%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%81%93%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%A6%E6%B1%9F%E4%B9%8B%E6%98%9F%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%A4%A7%E9%81%93%E5%BA%97/9.png");

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
        if(name.equals("锐思特酒店福鼎南站店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E7%A6%8F%E9%BC%8E%E5%8D%97%E7%AB%99%E5%BA%97/ruisitefuding.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E7%A6%8F%E9%BC%8E%E5%8D%97%E7%AB%99%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E7%A6%8F%E9%BC%8E%E5%8D%97%E7%AB%99%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E7%A6%8F%E9%BC%8E%E5%8D%97%E7%AB%99%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E7%A6%8F%E9%BC%8E%E5%8D%97%E7%AB%99%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E7%A6%8F%E9%BC%8E%E5%8D%97%E7%AB%99%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E7%A6%8F%E9%BC%8E%E5%8D%97%E7%AB%99%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E7%A6%8F%E9%BC%8E%E5%8D%97%E7%AB%99%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E7%A6%8F%E9%BC%8E%E5%8D%97%E7%AB%99%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E7%A6%8F%E9%BC%8E%E5%8D%97%E7%AB%99%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E7%A6%8F%E9%BC%8E%E5%8D%97%E7%AB%99%E5%BA%97/9.png");

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
        if(name.equals("福鼎太姥山澳莱大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%B1%B1%E6%BE%B3%E8%8E%B1%E5%A4%A7%E9%85%92%E5%BA%97/aolaidajiudian.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%B1%B1%E6%BE%B3%E8%8E%B1%E5%A4%A7%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%B1%B1%E6%BE%B3%E8%8E%B1%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%B1%B1%E6%BE%B3%E8%8E%B1%E5%A4%A7%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%B1%B1%E6%BE%B3%E8%8E%B1%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%B1%B1%E6%BE%B3%E8%8E%B1%E5%A4%A7%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%B1%B1%E6%BE%B3%E8%8E%B1%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%B1%B1%E6%BE%B3%E8%8E%B1%E5%A4%A7%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%B1%B1%E6%BE%B3%E8%8E%B1%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%B1%B1%E6%BE%B3%E8%8E%B1%E5%A4%A7%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E7%A6%8F%E9%BC%8E%E5%B8%82/%E7%A6%8F%E9%BC%8E%E5%A4%AA%E5%A7%A5%E5%B1%B1%E6%BE%B3%E8%8E%B1%E5%A4%A7%E9%85%92%E5%BA%97/9.png");

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
        if(name.equals("霞浦县锦都宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E9%94%A6%E9%83%BD%E5%AE%BE%E9%A6%86/jindujiudian.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E9%94%A6%E9%83%BD%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E9%94%A6%E9%83%BD%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E9%94%A6%E9%83%BD%E5%AE%BE%E9%A6%86/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E9%94%A6%E9%83%BD%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E9%94%A6%E9%83%BD%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E9%94%A6%E9%83%BD%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E9%94%A6%E9%83%BD%E5%AE%BE%E9%A6%86/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E9%94%A6%E9%83%BD%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E9%94%A6%E9%83%BD%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8E%BF%E9%94%A6%E9%83%BD%E5%AE%BE%E9%A6%86/9.png");

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
        if(name.equals("城市便捷酒店宁德霞浦店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%9F%8E%E5%B8%82%E4%BE%BF%E6%8D%B7%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E9%9C%9E%E6%B5%A6%E5%BA%97/chengshibianjie.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%9F%8E%E5%B8%82%E4%BE%BF%E6%8D%B7%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E9%9C%9E%E6%B5%A6%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%9F%8E%E5%B8%82%E4%BE%BF%E6%8D%B7%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E9%9C%9E%E6%B5%A6%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%9F%8E%E5%B8%82%E4%BE%BF%E6%8D%B7%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E9%9C%9E%E6%B5%A6%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%9F%8E%E5%B8%82%E4%BE%BF%E6%8D%B7%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E9%9C%9E%E6%B5%A6%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%9F%8E%E5%B8%82%E4%BE%BF%E6%8D%B7%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E9%9C%9E%E6%B5%A6%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%9F%8E%E5%B8%82%E4%BE%BF%E6%8D%B7%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E9%9C%9E%E6%B5%A6%E5%BA%97/manfu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%9F%8E%E5%B8%82%E4%BE%BF%E6%8D%B7%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E9%9C%9E%E6%B5%A6%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%9F%8E%E5%B8%82%E4%BE%BF%E6%8D%B7%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E9%9C%9E%E6%B5%A6%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E5%9F%8E%E5%B8%82%E4%BE%BF%E6%8D%B7%E9%85%92%E5%BA%97%E5%AE%81%E5%BE%B7%E9%9C%9E%E6%B5%A6%E5%BA%97/6.png");

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
        if(name.equals("福维尔酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%A6%8F%E7%BB%B4%E5%B0%94%E9%85%92%E5%BA%97/fuweier.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%A6%8F%E7%BB%B4%E5%B0%94%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%A6%8F%E7%BB%B4%E5%B0%94%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%A6%8F%E7%BB%B4%E5%B0%94%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%A6%8F%E7%BB%B4%E5%B0%94%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%A6%8F%E7%BB%B4%E5%B0%94%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%A6%8F%E7%BB%B4%E5%B0%94%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%A6%8F%E7%BB%B4%E5%B0%94%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%A6%8F%E7%BB%B4%E5%B0%94%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%A6%8F%E7%BB%B4%E5%B0%94%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E7%A6%8F%E7%BB%B4%E5%B0%94%E9%85%92%E5%BA%97/10.png");

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
        if(name.equals("锐思特酒店霞浦山河路店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E5%B1%B1%E6%B2%B3%E8%B7%AF%E5%BA%97/ruisitejxiapuxian.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E5%B1%B1%E6%B2%B3%E8%B7%AF%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E5%B1%B1%E6%B2%B3%E8%B7%AF%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E5%B1%B1%E6%B2%B3%E8%B7%AF%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E5%B1%B1%E6%B2%B3%E8%B7%AF%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E5%B1%B1%E6%B2%B3%E8%B7%AF%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E5%B1%B1%E6%B2%B3%E8%B7%AF%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E5%B1%B1%E6%B2%B3%E8%B7%AF%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E5%B1%B1%E6%B2%B3%E8%B7%AF%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E5%B1%B1%E6%B2%B3%E8%B7%AF%E5%BA%97/taimushan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%94%90%E6%80%9D%E7%89%B9%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E5%B1%B1%E6%B2%B3%E8%B7%AF%E5%BA%97/10.png");

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
        if(name.equals("骏怡连锁酒店（霞浦店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%AA%8F%E6%80%A1%E8%BF%9E%E9%94%81%E9%85%92%E5%BA%97%EF%BC%88%E9%9C%9E%E6%B5%A6%E5%BA%97%EF%BC%89/junyiliansuojiudian.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%AA%8F%E6%80%A1%E8%BF%9E%E9%94%81%E9%85%92%E5%BA%97%EF%BC%88%E9%9C%9E%E6%B5%A6%E5%BA%97%EF%BC%89/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%AA%8F%E6%80%A1%E8%BF%9E%E9%94%81%E9%85%92%E5%BA%97%EF%BC%88%E9%9C%9E%E6%B5%A6%E5%BA%97%EF%BC%89/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%AA%8F%E6%80%A1%E8%BF%9E%E9%94%81%E9%85%92%E5%BA%97%EF%BC%88%E9%9C%9E%E6%B5%A6%E5%BA%97%EF%BC%89/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%AA%8F%E6%80%A1%E8%BF%9E%E9%94%81%E9%85%92%E5%BA%97%EF%BC%88%E9%9C%9E%E6%B5%A6%E5%BA%97%EF%BC%89/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%AA%8F%E6%80%A1%E8%BF%9E%E9%94%81%E9%85%92%E5%BA%97%EF%BC%88%E9%9C%9E%E6%B5%A6%E5%BA%97%EF%BC%89/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%AA%8F%E6%80%A1%E8%BF%9E%E9%94%81%E9%85%92%E5%BA%97%EF%BC%88%E9%9C%9E%E6%B5%A6%E5%BA%97%EF%BC%89/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%AA%8F%E6%80%A1%E8%BF%9E%E9%94%81%E9%85%92%E5%BA%97%EF%BC%88%E9%9C%9E%E6%B5%A6%E5%BA%97%EF%BC%89/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%AA%8F%E6%80%A1%E8%BF%9E%E9%94%81%E9%85%92%E5%BA%97%EF%BC%88%E9%9C%9E%E6%B5%A6%E5%BA%97%EF%BC%89/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%AA%8F%E6%80%A1%E8%BF%9E%E9%94%81%E9%85%92%E5%BA%97%EF%BC%88%E9%9C%9E%E6%B5%A6%E5%BA%97%EF%BC%89/4.png");

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
        if(name.equals("速8酒店霞浦九龙街店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%80%9F8%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E4%B9%9D%E9%BE%99%E8%A1%97%E5%BA%97/subaxiapu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%80%9F8%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E4%B9%9D%E9%BE%99%E8%A1%97%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%80%9F8%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E4%B9%9D%E9%BE%99%E8%A1%97%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%80%9F8%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E4%B9%9D%E9%BE%99%E8%A1%97%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%80%9F8%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E4%B9%9D%E9%BE%99%E8%A1%97%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%80%9F8%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E4%B9%9D%E9%BE%99%E8%A1%97%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%80%9F8%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E4%B9%9D%E9%BE%99%E8%A1%97%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%80%9F8%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E4%B9%9D%E9%BE%99%E8%A1%97%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%80%9F8%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E4%B9%9D%E9%BE%99%E8%A1%97%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%80%9F8%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E4%B9%9D%E9%BE%99%E8%A1%97%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%80%9F8%E9%85%92%E5%BA%97%E9%9C%9E%E6%B5%A6%E4%B9%9D%E9%BE%99%E8%A1%97%E5%BA%97/5.png");

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
        if(name.equals("龙云宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%BE%99%E4%BA%91%E5%AE%BE%E9%A6%86/longyun.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%BE%99%E4%BA%91%E5%AE%BE%E9%A6%86/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%BE%99%E4%BA%91%E5%AE%BE%E9%A6%86/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%BE%99%E4%BA%91%E5%AE%BE%E9%A6%86/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%BE%99%E4%BA%91%E5%AE%BE%E9%A6%86/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%BE%99%E4%BA%91%E5%AE%BE%E9%A6%86/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%BE%99%E4%BA%91%E5%AE%BE%E9%A6%86/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%BE%99%E4%BA%91%E5%AE%BE%E9%A6%86/manfu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%BE%99%E4%BA%91%E5%AE%BE%E9%A6%86/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%BE%99%E4%BA%91%E5%AE%BE%E9%A6%86/7.png");

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
        if(name.equals("霞浦千禧之家假日酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8D%83%E7%A6%A7%E4%B9%8B%E5%AE%B6%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/xiapuqianxizhijia.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8D%83%E7%A6%A7%E4%B9%8B%E5%AE%B6%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8D%83%E7%A6%A7%E4%B9%8B%E5%AE%B6%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8D%83%E7%A6%A7%E4%B9%8B%E5%AE%B6%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8D%83%E7%A6%A7%E4%B9%8B%E5%AE%B6%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/manfu.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8D%83%E7%A6%A7%E4%B9%8B%E5%AE%B6%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8D%83%E7%A6%A7%E4%B9%8B%E5%AE%B6%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8D%83%E7%A6%A7%E4%B9%8B%E5%AE%B6%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8D%83%E7%A6%A7%E4%B9%8B%E5%AE%B6%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E9%9C%9E%E6%B5%A6%E5%8E%BF/%E9%9C%9E%E6%B5%A6%E5%8D%83%E7%A6%A7%E4%B9%8B%E5%AE%B6%E5%81%87%E6%97%A5%E9%85%92%E5%BA%97/2.png");

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
//        banner.setImages(imagePath);
        banner.setImages(imgs);
        banner.start();
    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
           MyViewHolder holder = new MyViewHolder(LayoutInflater.from( JiudianActivity.this).inflate(R.layout.recycle_jiudian,parent,false));
            return holder   ;
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.nameone.setText(name1[position]);
            holder.iv.setImageResource(image[position]);
            holder.troduce.setText(introduces[position]);
            holder.buy.setText(buy[position]);
            holder.yuding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog=new CommonDialog(JiudianActivity.this,R.style.MyDialog);
                    myDialog.setTitle("提示！");
                    myDialog.setMessage("提示：您确定要预定订单吗！");
                    myDialog.setYesOnclickListener("确定", new CommonDialog.onYesOnclickListener() {
                        @Override
                        public void onYesOnclick() {
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                        connect.jiudiancreate(name,name1[position],introduces[position],buy[position],numble);
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
                            Toast.makeText(JiudianActivity.this,"恭喜你！预订成功",Toast.LENGTH_LONG).show();
                            myDialog.dismiss();
                        }
                    });

                    myDialog.setNoOnclickListener("取消", new CommonDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            Toast.makeText(JiudianActivity.this,"很抱歉！预定失败",Toast.LENGTH_LONG).show();
                            myDialog.dismiss();
                        }
                    });
                    myDialog.show();

                }
            });

        }



        @Override
        public int getItemCount() {
            return name1.length;
        }
        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView nameone;
            ImageView iv;
            TextView buy;
            TextView troduce;
            Button yuding;
            public MyViewHolder(View view){
                super(view);
                nameone = (TextView)view.findViewById(R.id.name);
                iv = (ImageView)view.findViewById(R.id.tupian);
                buy=(TextView)view.findViewById(R.id.buy);
                yuding = view.findViewById(R.id.yuding);
                troduce =(TextView) view.findViewById(R.id.chuangziliao);
            }
        }
    }
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
            Toast.makeText(JiudianActivity.this, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
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
                    "&keywords=" +nameadd+
                    "&dev=0");

            intent.setData(uri);

            //启动该页面即可
            startActivity(intent);
        } else {
            Toast.makeText(JiudianActivity.this, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
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


