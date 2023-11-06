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

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MeishiActivity extends AppCompatActivity {
    String name;
    Button open;
    String nameadd;
    private CommonDialog myDialog;
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private DialogOrderTypeFragment mFragment2=new DialogOrderTypeFragment();
    private String[] name1 = {"总汇三明治","精致鸡肉沙拉配千岛酱","牛肉汉堡","总汇三明治配薯条","黑松露培根奶油汁面"};
    private String[] image = {"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbkimg.cdn.bcebos.com%2Fpic%2F810a19d8bc3eb1354e4da1e3a81ea8d3fc1f44db&refer=http%3A%2F%2Fbkimg.cdn.bcebos.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1670032852&t=875928d8450303f558eb1a5dff5621ff",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbkimg.cdn.bcebos.com%2Fpic%2Fc995d143ad4bd1131e80f0f654afa40f4afb05a0&refer=http%3A%2F%2Fbkimg.cdn.bcebos.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1670032889&t=60c296d2e0242088f2e746f7beb76533",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/niurohanbao.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/zonghuisanmingzhipeishutiao.jpg",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fqcloud.dpfile.com%2Fpc%2F3cF1dqt9QPBGkGcXbi3222DaT8OsX2GjaKEh_gtCJ1bmNvxHXc5fTmTa-7OA_59gjoJrvItByyS4HHaWdXyO_DrXIaWutJls2xCVbatkhjUNNiIYVnHvzugZCuBITtvjski7YaLlHpkrQUr5euoQrg.jpg&refer=http%3A%2F%2Fqcloud.dpfile.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1670032921&t=be873bddaecabc3b15847d9ee61d0473"};
    private  String[] buy ={"¥88","¥8","¥30","¥38","¥18"};
    private  String[] introduces = {"料很足，两个人分刚好",
            "量很足，里面的东西很丰盛，完美",
            "牛肉汉堡份量刚刚好，肉馅很新鲜",
            "我点了里面有火腿的版本，三明治酥酥的，里面配料也很足",
            "我觉得这个面很好吃，奶油敲到好处"};
    int imageone;
    String nameone;
    String buyone;
    String troduceone;
    String name_receive;
    String pingfen;
    int f;
    int numble=1 ;
    public static String name_one;
    public static String nametype_one;
    public static String name_numble;
    private Banner banner;
    private GlideImageLoader glideImageLoader;
    private List<String> imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meishi);
        Intent intent = getIntent();
        f = intent.getIntExtra("f",0);
        imageone = intent.getIntExtra("image",0);
        nameone = intent.getStringExtra("name1");
        buyone = intent.getStringExtra("buy1");
        troduceone = intent.getStringExtra("troduce");

        pingfen = intent.getStringExtra("detail_pingfen");
        name = intent.getStringExtra("detail_name1");
        final String buy = intent.getStringExtra("detail_buy");
        final  String introdece = intent.getStringExtra("detail_introduce");
        name_receive= intent.getStringExtra("name");//景点地区
        final TextView menpiao = findViewById(R.id.menpiao);
        menpiao.setText(buy);
        TextView jieshouziliao = findViewById(R.id.jieshouziliao);
        jieshouziliao.setText(pingfen+"  "+introdece);

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
                intent.setClass(MeishiActivity.this,DetailsActivity.class);
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
        if(name.equals("弥生食堂日式料理（万达店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E8%95%89%E5%9F%8E%E5%8C%BA/ningsheng.jpg");
        }
        if(name.equals("One More 咖啡简餐")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E8%95%89%E5%9F%8E%E5%8C%BA/onemore.jpg");
        }
        if(name.equals("胖哥俩肉蟹煲（蕉城万达店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E8%95%89%E5%9F%8E%E5%8C%BA/pangeliang.jpg");
        }
        if(name.equals("七欣天（宁德万达广场店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E8%95%89%E5%9F%8E%E5%8C%BA/qixintian.jpg");
        }
        if(name.equals("有拈头成都市井火锅（天茂店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E8%95%89%E5%9F%8E%E5%8C%BA/youniantou.jpg");
        }
        if(name.equals("蛙小西·牛蛙堡火锅（天茂店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E8%95%89%E5%9F%8E%E5%8C%BA/waxiaoxi.jpg");
        }
        if(name.equals("桃花坞")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E8%95%89%E5%9F%8E%E5%8C%BA/taohuawu.jpg");
        }
        if(name.equals("南洋小聚（宁德万达店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E8%95%89%E5%9F%8E%E5%8C%BA/nanyang.jpg");
        }

        if(name.equals("又青的店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%8F%A4%E7%94%B0%E5%8E%BF/youqingdedian.jpg");
        }
        if(name.equals("小二烤肉（古田店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%8F%A4%E7%94%B0%E5%8E%BF/xiaoerkaorou.jpg");
        }
        if(name.equals("鲜境海鲜自助餐厅（古田中央广场店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%8F%A4%E7%94%B0%E5%8E%BF/xianjing.jpg");
        }
        if(name.equals("小村烤肉（古田店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%8F%A4%E7%94%B0%E5%8E%BF/xiaocunkaorou.jpg");
        }
        if(name.equals("大家食府（城东店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%8F%A4%E7%94%B0%E5%8E%BF/dajia.jpg");
        }
        if(name.equals("菲斯牛排自助")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%8F%A4%E7%94%B0%E5%8E%BF/feilizizhu.jpg");
        }
        if(name.equals("百年鱼庄（古田大世界店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%8F%A4%E7%94%B0%E5%8E%BF/bainianyuzhuang.jpg");
        }
        if(name.equals("蛙太太（古田店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%8F%A4%E7%94%B0%E5%8E%BF/wataitai.jpg");
        }

        if(name.equals("啊咋酒楼")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%B1%8F%E5%8D%97%E5%8E%BF/azajiulou.jpg");
        }
        if(name.equals("壹号老宅")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%B1%8F%E5%8D%97%E5%8E%BF/yihaolaozhai.jpg");
        }
        if(name.equals("公园壹号中餐厅")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%B1%8F%E5%8D%97%E5%8E%BF/gongyuanyihao.jpg");
        }
        if(name.equals("阿纯餐馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%B1%8F%E5%8D%97%E5%8E%BF/achuncanguang.jpg");
        }
        if(name.equals("白水洋乡味楼酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%B1%8F%E5%8D%97%E5%8E%BF/baishuiyangxiangwei.jpg");
        }
        if(name.equals("熙泽路餐馆")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%B1%8F%E5%8D%97%E5%8E%BF/xizelu.jpg");
        }
        if(name.equals("芝根芝底披萨（屏南店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%B1%8F%E5%8D%97%E5%8E%BF/zhigenzhidi.jpg");
        }

        if(name.equals("福鼎海鲜大排档（周宁县）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%91%A8%E5%AE%81%E5%8E%BF/haixiangdapaidang.jpg");
        }
        if(name.equals("小龙坎火锅（周宁店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%91%A8%E5%AE%81%E5%8E%BF/xiaolonghuoguo.jpg");
        }

        if(name.equals("姑奶奶厨坊（周宁店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%91%A8%E5%AE%81%E5%8E%BF/gunainaizhouning.jpg");
        }

        if(name.equals("多味美生日蛋糕（步行街店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%91%A8%E5%AE%81%E5%8E%BF/duoweimeishengri.jpg");
        }

        if(name.equals("四叶草咖啡酒吧")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%91%A8%E5%AE%81%E5%8E%BF/siyecaokafei.jpg");
        }
        if(name.equals("友味道药膳鸡（周宁店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%91%A8%E5%AE%81%E5%8E%BF/youweidao.jpg");
        }
        if(name.equals("美优乐(名仕广场店)")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%91%A8%E5%AE%81%E5%8E%BF/youlemei.jpg");
        }

        if(name.equals("姑奶奶（寿宁店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%AF%BF%E5%AE%81%E5%8E%BF/gunainaishouning.jpg");
        }
        if(name.equals("佳客来（胜利街店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%AF%BF%E5%AE%81%E5%8E%BF/jiakelai.jpg");
        }
        if(name.equals("芝根芝底（寿宁店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%AF%BF%E5%AE%81%E5%8E%BF/zhigenzhidishounin.jpg");
        }
        if(name.equals("廊桥鱼庄（寿宁店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%AF%BF%E5%AE%81%E5%8E%BF/langqiaoyuzhuang.jpg");
        }
        if(name.equals("沙拉斯（解放店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%AF%BF%E5%AE%81%E5%8E%BF/shalasi.jpg");
        }
        if(name.equals("重庆鸡公煲")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%AF%BF%E5%AE%81%E5%8E%BF/jigongbao.jpg");
        }

        if(name.equals("小咖私厨工坊（福安店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E5%AE%89%E5%B8%82/xiaokasichu.jpg");
        }
        if(name.equals("牛道")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E5%AE%89%E5%B8%82/niudao.jpg");
        }
        if(name.equals("御源府餐厅（福安店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E5%AE%89%E5%B8%82/yuyuanfucanting.jpg");
        }
        if(name.equals("江滨酒楼")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E5%AE%89%E5%B8%82/jiangbingjiulou.jpg");
        }
        if(name.equals("味觉种子（福安市）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E5%AE%89%E5%B8%82/weijuezhongzi.jpg");
        }
        if(name.equals("邦维执匠·咖餐甜点（甜点店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E5%AE%89%E5%B8%82/bangweizhijiang.jpg");
        }
        if(name.equals("福台源自助火锅·烧烤")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E5%AE%89%E5%B8%82/futaiyuanzizhu.jpg");
        }

        if(name.equals("晓家碧芋")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E6%9F%98%E8%8D%A3%E5%8E%BF/xiaojiabiyu.jpg");
        }
        if(name.equals("聚福楼私房菜")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E6%9F%98%E8%8D%A3%E5%8E%BF/jufulou.jpg");
        }
        if(name.equals("小富春饭店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E6%9F%98%E8%8D%A3%E5%8E%BF/xiaofuchunfangdian.jpg");
        }
        if(name.equals("东源农家菜")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E6%9F%98%E8%8D%A3%E5%8E%BF/dongyuannongjiacai.jpg");
        }
        if(name.equals("乐乐锅（柘荣店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E6%9F%98%E8%8D%A3%E5%8E%BF/leleguo.jpg");
        }
        if(name.equals("恒昌荣饭店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E6%9F%98%E8%8D%A3%E5%8E%BF/hengchangrong.jpg");
        }
        if(name.equals("九香火锅（柳城南路店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E6%9F%98%E8%8D%A3%E5%8E%BF/jiuxianghuoguo.jpg");
        }

        if(name.equals("金九龙大酒店-中餐厅")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/jinjiulongdajiudain.jpg");
        }
        if(name.equals("老家饭庄")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/laojiafangzhuang.jpg");
        }
        if(name.equals("闲云居")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/xianyunju.jpg");
        }
        if(name.equals("三门里逍遥林")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/sanmengli.jpg");
        }
        if(name.equals("东源大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/dongyuandajiudian.jpg");
        }
        if(name.equals("老百货自助火锅城（人本超市店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/laobaihuozizhuhuoguo.jpg");
        }
        if(name.equals("百威火锅城（古城西路店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/baiweihuoguocheng.jpg");
        }
        if(name.equals("山水人家休闲山庄")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/shanshuirenjia.jpg");
        }

        if(name.equals("晨曦大酒店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E9%9C%9E%E6%B5%A6%E5%8E%BF/chenxidajiudian.jpg");
        }
        if(name.equals("环海大酒楼")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E9%9C%9E%E6%B5%A6%E5%8E%BF/huanhaidajiulou.jpg");
        }

        if(name.equals("鑫鑫饭店")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E9%9C%9E%E6%B5%A6%E5%8E%BF/xinxinfangdian.jpg");
        }
        if(name.equals("台湾欣乐园")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E9%9C%9E%E6%B5%A6%E5%8E%BF/taiwanxinleyuan.jpg");
        }
        if(name.equals("赵妈私房菜排挡")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E9%9C%9E%E6%B5%A6%E5%8E%BF/zhaomapaidang.jpg");
        }
        if(name.equals("浦天酒店-中餐厅")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E9%9C%9E%E6%B5%A6%E5%8E%BF/putianjiudian.jpg");
        }
        if(name.equals("人民公社（霞浦店）")){
            imagePath.add("https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E9%9C%9E%E6%B5%A6%E5%8E%BF/renminggongshe.jpg");
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
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
          MyViewHolder holder = new MyViewHolder(LayoutInflater.from( MeishiActivity.this).inflate(R.layout.recycle_meishi,parent,false));
            return holder  ;
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.nameone.setText(name1[position]);
            Glide.with(MeishiActivity.this).load(image[position]).into(holder.iv);
            holder.troduce.setText(introduces[position]);
            holder.buy.setText(buy[position]);

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
            Toast.makeText(MeishiActivity.this, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
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
            Toast.makeText(MeishiActivity.this, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
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
