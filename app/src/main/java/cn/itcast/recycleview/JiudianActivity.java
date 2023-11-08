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
    private String[] name1 = {"Standard single room","Standard twin room","Business single room","Deluxe twin room","Deluxe suite"};
    private int[] image = {R.drawable.danrenchuang,R.drawable.shuangrenchuang,R.drawable.busdanren,R.drawable.busshuangren,R.drawable.delsuite};
//    private int[] image = {R.drawable.achuncanguang,R.drawable.shuangchuangfang,R.drawable.youzhidachuangfang,R.drawable.youzhishuangchuangfang,R.drawable.haohuataofang};

    private  String[] buy ={"¥123","¥129","¥177","¥177","¥235"};
    private  String[] introduces = {
            "single bed  18-22 square meters",
            "double bed  18-22 square meters",
            "single bed  24-26 square meters",
            "double bed  24-26 square meters",
            "single bed  44-46 square meters"};
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
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/3.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/2.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/1.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/10.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/5.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/4.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/9.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/7.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E8%95%89%E5%9F%8E%E5%8C%BA/%E5%90%9B%E5%AE%89%E5%A4%A7%E9%85%92%E5%BA%97/8.png");



            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
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
        if(name.equals("Huaguoshan Hotel")){
            imagePath.add("https://cf2.bstatic.com/xdata/images/hotel/max1280x900/109834211.jpg?k=fe2d35607e558136791f19dfe304f45ce21c2b292851fa7318957f27ae503faf&o=&hp=1");
            imagePath.add("https://cf2.bstatic.com/xdata/images/hotel/max1280x900/283808630.jpg?k=90f0e2345066774b56c53aa6f532d41723307b26355d2061e86206f4b76a963d&o=&hp=1");
            imagePath.add("https://cf2.bstatic.com/xdata/images/hotel/max1280x900/109833877.jpg?k=f580d8c2e7e5f9105738dfadfe856074c265df44ca5167d746e5339574dade6b&o=&hp=1");
            imagePath.add("https://ts1.cn.mm.bing.net/th/id/R-C.20dfc8d06909e83cccd818be2f8670b1?rik=e1lBw78gSY60%2bA&riu=http%3a%2f%2fimg1n.soufunimg.com%2fzxb%2f201609%2f13%2f530%2faa824db6dd5f6d568a9a3508dd3c4f92.jpeg&ehk=i%2fNotuidt81PD7ZqNLuBJakA0qZrrucqJ%2fZ3wQgxHoI%3d&risl=&pid=ImgRaw&r=0");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");

        }
        if(name.equals("Sotitel Lianyungang Suning")){
            imagePath.add("http://sofitel-lianyungang-suning.31td.com/UploadFiles/2.jpg");
            imagePath.add("http://sofitel-lianyungang-suning.31td.com/UploadFiles/3.jpg");
            imagePath.add("http://sofitel-lianyungang-suning.31td.com/UploadFiles/4.jpg");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");
        }

        if(name.equals("Zhongyin Mingdu Hotel")){
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/72159141.jpg?k=b2d8b51f0a59f40443313c85b4c3b657de46aa8e486b0fa501c0f8501a5bc7ad&o=");
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/72159147.jpg?k=ad6a5f9a684cb9c3765fc670efeb69312059d2d1ca2ccf3f04fd83f09308c703&o=");
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/72159127.jpg?k=5b34fafe88106dbf449a155233df3c9492a4bf5ae10361a5b3d10219472bafc0&o=");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");

        }
        if(name.equals("Atour Hotel")){
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/402255430.jpg?k=39b75ae81641eb97505f0c8ba17f73f6e56b660cebea6c70b39815e65077671f&o=");
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/402255425.jpg?k=f4fa6ab8456d9045a9467ad973560424bda3cc6483c7675244231410c7f48d4b&o=");
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/402255442.jpg?k=1f87822907dfe7af804b35e3db86069f67fd6732f384248c430afc79cbbeaa6a&o=");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");

        }
        if(name.equals("Jinjiang Inn")){
            imagePath.add("http://dimg04.c-ctrip.com/images//200t080000003b04dB4C8_R_550_412.jpg");
            imagePath.add("http://dimg04.c-ctrip.com/images//20050700000025dgxA18C_R_550_412.jpg");
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/78782917.jpg?k=04d4effb7e9a09830f72e005bb1caecd045404844b9a242467669059783eb6d2&o=");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");

        }
        if(name.equals("Home Inn")){
            imagePath.add("https://images.bthhotels.com/homeinns/020073_a.jpg");
            imagePath.add("https://media-cdn.tripadvisor.com/media/daodao/photo-s/04/51/ff/d2/caption.jpg");
            imagePath.add("https://images.bthhotels.com/image/25c3b950-5cd6-4b0c-bc84-a3a5f833250c.png");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");

        }
        if(name.equals("Hanting Hotel")){
            imagePath.add("https://cdn-img.readytotrip.com/t/1024x768/content/1d/df/1ddf603dde686f438178c0b1180c9de8505d3a2d.jpeg");
            imagePath.add("http://dimg04.c-ctrip.com/images//200e080000003db75C21D_R_550_412.jpg");
            imagePath.add("https://cdn-img.readytotrip.com/t/1024x768/content/11/df/11dfab27475b757d6d90e1ef64e9bf747d996d0a.jpeg");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");
        }

        if(name.equals("柘荣京鼎荣商务酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/jingding.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/6.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E4%BA%AC%E9%BC%8E%E8%8D%A3%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/5.png");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");

        }
        if(name.equals("柘荣县富商商务酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/fushang.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AF%8C%E5%95%86%E5%95%86%E5%8A%A1%E9%85%92%E5%BA%97/9.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");

        }
        if(name.equals("柘荣幽舍酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/youshe.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%B9%BD%E8%88%8D%E9%85%92%E5%BA%97/3.png");

            imageTitle.add("景点图片");
            imageTitle.add("景点图片");
            imageTitle.add("景点图片");

        }
        if(name.equals("柘荣县宾馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/zherongxianbingguan.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/8.png");
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E9%85%92%E5%BA%97/%E6%9F%98%E8%8D%A3%E5%8E%BF/%E6%9F%98%E8%8D%A3%E5%8E%BF%E5%AE%BE%E9%A6%86/6.png");

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
        if(name.equals("Atour")) {
            imagePath.add("https://i.travelapi.com/lodging/38000000/37670000/37666700/37666651/79ba584e_z.jpg");
            imagePath.add("https://i.travelapi.com/lodging/38000000/37670000/37666700/37666651/6a293fbf_z.jpg");
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1024x720/207694728.jpg?k=d54f02e3c34a4874516ca0220781bbfaf730a4a9f319ce9668aa714a8c7ce95b&o=");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");
        }

        if(name.equals("InterContinental Nantong")){
            imagePath.add("https://tse2-mm.cn.bing.net/th?id=OLC.behAZ8gW/s+pfQ480x360&rs=1&pid=ImgDet");
            imagePath.add("http://dimg04.c-ctrip.com/images//200o0k000000buolg48CD_R_550_412.jpg");
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/324802757.jpg?k=5c740981a119db9703ed2b8a934e90647e76543f3ae5a52834c1403518ee6ce4&o=");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");
        }
        if(name.equals("the modern Holiday Inn Nantong Oasis International")){
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1024x720/263334048.jpg?k=d5eab3fec85d95b0e8113a6a882c0f62a546c28e692013be9d38e4741086d0ad&o=");
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1024x720/263041325.jpg?k=afc487633b0a33e96924c55f4012f489ff07a7081454d9cfcf07794228305b7b&o=");
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1024x720/263334063.jpg?k=97126129e6f47d523154c6e24500d09d1944adede0dc62239db8ef1af0924fe6&o=");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");

        }
        if(name.equals("Jinshi International Hotel")){
            imagePath.add("http://dimg04.c-ctrip.com/images//200w050000000cx2hF8B5_R_550_412.jpg");
            imagePath.add("http://dimg04.c-ctrip.com/images//200b0r000000hga3kDCC1_R_550_412.jpg");
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/840x460/134270222.jpg?k=9d66e0a951477d57d6d0311127163eeaaf8cc9045ff13e714aac4504b4d903f8&o=");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");
        }

        if(name.equals("Jinjiang Inn")){
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/78769417.jpg?k=ffb2fbf99138d6f288835208effa75ebff7e2b2e3b9a4c0904dd268333b5e8d9&o=");
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/78769424.jpg?k=059b88928afa76bac9850804b34da98cee109cc4675f73404d124db4342bb3ef&o=");
            imagePath.add("https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/78769419.jpg?k=e9efbfa64297990d081a2b99ad98f33e11bfad1fa30d42ae2afe35a1dccfa7d7&o=");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");
        }

        if(name.equals("Hilton Garden Inn Nantong Xinghu")){
            imagePath.add("https://tse1-mm.cn.bing.net/th/id/OLC.AIC5tp0ASaQ9dA480x360?&rs=1&pid=ImgDet");
            imagePath.add("https://tse3-mm.cn.bing.net/th/id/OLC.gJffZVCM29UZ4A480x360?&rs=1&pid=ImgDet");
            imagePath.add("https://tse2-mm.cn.bing.net/th/id/OLC.s4KuzwjDPB30Wg480x360?&rs=1&pid=ImgDet");

            imageTitle.add("picture");
            imageTitle.add("picture");
            imageTitle.add("picture");
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
        banner.setImages(imagePath);
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


