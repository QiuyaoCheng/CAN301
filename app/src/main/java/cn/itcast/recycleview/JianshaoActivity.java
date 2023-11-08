package cn.itcast.recycleview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class JianshaoActivity extends AppCompatActivity {
    int imageone;
    String nameone;
    String buyone;
    String troduceone;
    String name_receive;
    private RecyclerView mRecycleview;
    private HomeAdapter mAdapter;
    private String[] images1 ;

    private String[] introduces1;

    private String[] jiaoimages1={"https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/1.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/2.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/3.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/4.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/5.jpg"};
    private String[] jiaointroduces1 ={"        三都澳以其独特的景致、秀美的风姿闻名遐迩，素有“海上天湖，神仙港湾”之称。澳内有许多形态各异的礁石岸坞，如“金龟驮珠”、“鲲鹏展翅”、“烈马回首”、“非洲大象”、“鸡笼屿”、“古猿人”、“笔架山”等栩栩如生。峰奇石怪，景色优美，每年吸引众多游人前去观光。",
            "        三都澳福海关遗址掩映在苍松翠柏之中，沉寂于海岛山岗，蝉鸣秋意，海景旷远，是探幽寻古的妙境。",
            "        青山畔，军港悠悠，伴着海浪，一幅宁静祥和的美景：军舰出海，巡洋归航，一声鸣笛，回荡在清幽的山水间，好一派威严壮丽的景象。",
            "        三都澳斗姆风景区坐落斗帽岛，荟萃三都澳美景，岛上奇石遍布，有名扬海内的中国沿海一大奇观——螺壳岩，有迷宫洞、斗姆石城、犀牛望月，个个鬼斧神工，惟妙惟肖。凿于悬崖绝壁上的海滨栈道，是一处赏海绝境。斗帽岛还是斗姆娘娘的圣地，那里有许多古老而神奇的传说，让你仿佛置身神话般的世界。",
            "        这里渔户相连，绵延数十公里，被称为“东方威尼斯”。海上渔城有街巷、门牌，有社区管理机构、警察、移动通信营业厅，有百货批发部、酒楼、卡拉OK厅等。坐在随波荡漾的渔排上，可以把酒临风、品尝即捞即烹的海中奇珍，还可以垂钓、赏鱼、娱乐休闲，真是奇趣天成，一种妙不可言的感受。"};

   private String[] lianimages1 ={
           "https://bkimg.cdn.bcebos.com/pic/6a600c338744ebf899ff903fdef9d72a6059a720?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxMTY=,g_7,xp_5,yp_5/format,f_auto",
           "https://bkimg.cdn.bcebos.com/pic/faf2b2119313b07e5433a4080bd7912397dd8c20?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxMTY=,g_7,xp_5,yp_5/format,f_auto",
           "https://bkimg.cdn.bcebos.com/pic/77094b36acaf2edd0c7fbde98a1001e939019320?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2U4MA==,g_7,xp_5,yp_5/format,f_auto"
   };
   private String[] lianintroduces1 = {
           "        Huaguo Mountain, located in the middle foot of Yuntai Mountain in the south of Lianyungang City, Jiangsu Province, was called Cangwu Mountain in the Tang and Song dynasties, also known as Qingfeng Peak, the main peak of Yuntai Mountain Range, and the highest peak of Jiangsu Mountains. Huaguo Mountain has 136 peaks, and the main peak of Huaguo Mountain is the Jade Girl Peak, with an altitude of 624.4 meters.",
           "        The geological formation of Huaguo Mountain is closely related to the tectonic movement and erosion of the earth's crust, and has experienced a long geological history and the influence of natural action, forming a unique landform landscape, and the landform of Huaguo Mountain in the scenic area is composed of red granite, with steep mountains and peculiar shapes.",
           "        The scenic area where Huaguo Mountain is located has been named as a national key scenic spot, a national AAAAA tourist area, and a national geological park."};

    private String[] nantongimages1={
            "https://bkimg.cdn.bcebos.com/pic/0b46f21fbe096b6303d724fc0e338744eaf8ac54?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2U4MA==,g_7,xp_5,yp_5/format,f_auto",
            "https://bkimg.cdn.bcebos.com/pic/2934349b033b5bb550adafd635d3d539b600bca4?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2U4MA==,g_7,xp_5,yp_5/format,f_auto" ,
            "https://bkimg.cdn.bcebos.com/pic/6c224f4a20a4462392b30d489b22720e0cf3d79b?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2U4MA==,g_7,xp_5,yp_5/format,f_auto"};
    private String[] nantongintroduces1 = {
            "        The formation of the five Mountains has been 350 million to 400 million years ago, [2] The Han Dynasty \"Literature General Examination\" record: Hailing, Han County, there is Wolf Mountain. [3] Huangnishan (29.3 meters), Ma 'anshan (49.4 meters), Wolf Mountain (104.8 meters), Jianshan (80.5 meters) and Junshan (108.5 meters) cover an area of 0.728 square kilometers.。",
            "        The five mountains in the Wolf Mountain Scenic Area were formed by strong crustal changes and sea and land changes, which have a history of 350 million to 400 million years, 1 million years earlier than the Himalayas. 7,500 years ago, Wolf Mountain was still a small island in the vast sea.",
            "        The cliff on the north slope of Wolf Mountain was formed during the Yanshan Movement 150 million to 70 million years ago. Nanding is a single mountain, with rock strata inclined to the southeast and an inclination of about 15°."};

    private String[] guimages1={"https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/gu1.jpg"};
    private String[] guintroduces1 = {"        翠屏湖位于古田县城东郊，翠屏湖距城关3公里，翠屏湖属亚热带气候，翠屏湖中烟波浩淼，空气清新，四季如春，翠屏湖水域面积达37.1平方公里，翠屏湖蓄水量为6.41亿立方米，水质碧澄（达到国家二级标准）。三十六个大小岛屿，隔水相峙，沿翠屏湖有被省人民政府列为省级文物保护单位的海内外公认的\"顺天圣母\"陈靖姑祖庙临水宫。"};

    private String[] pingimages1={"https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/ping1.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/ping2.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/ping3.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/ping4.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/ping5.jpg"};
    private String[] pingintroduces1 = {"        白水洋犹如一丘刚刚耙平的巨大农田，平展展地铺呈在崇山峻岭之中。在三大浅水广场白水洋上可骑自行车，有宽近百米的折水弧瀑，有近百米的水上滑道。奇特的地质景观，让他有了与众不同的亲水体验。",
            "        这里有极具特色的天然地质景观，平坦的岩石河床一石而就，净无沙砾，登高俯瞰，其形状犹如一丘刚刚耙平的巨大农田，平展展地铺呈在崇山峻岭之中。",
            "        折水弧瀑有近百米宽，缓缓从洞口飘洒到洋面上，让人感觉如一道漫长的白布，分隔了山色与水色，又能让人感到清凉舒爽。",
            "        由于白水洋天然独特的水质构造，在这里冲浪有十分独特的亲水体验。有近百米的水上滑道上，只要身着泳衣，赤身冲浪也不伤肌肤，给游客天然的亲水体验。",
            "        阳光、白水、水蚀波痕，形成了色彩斑斓的河床。人们或踏水、或冲浪、或开展赛跑、拔河、武术、骑车、舞狮等别具一格的水上运动，尽享大自然赐予的清凉。"};

    private String[] shouimages1={"https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/shou1.jpg"};
    private String[] shouintroduces1 = {"        横石玻璃天桥，2020年横空出世，景点位于福建省宁德市寿宁县犀溪镇横石旅游区，比邻犀溪漂流，为宁德全新靓丽风景线，这里有浪漫的玻璃天桥、震撼人心的高空滑索、还有惊险刺激的玻璃水滑道，孔雀园，集万千宠爱于一身，打卡横石玻璃天桥，有你真甜。"};

    private String[] xiaimages1={"https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/xia1.jpg"};
    private String[] xiaintroduces1 = {"        罗汉溪，源于福建省霞浦县柏洋乡洋里土勃头村，呈西北—东南流向。溪流经横江、溪西、洋沙溪，于下村汇集桐油溪支流，经百步溪，出水磨坑、大桥头入后港海。其主要支流桐油溪，源于水门乡百笕村，流域面积42平方公里，河道长度17公里。罗汉溪是霞浦县三大河流之一，蕴含丰富的水资源，是霞浦县最重要的集储水发电、农业灌溉、城市饮用水为一体的水源河流。罗汉溪又是具有独特山水景色的河流，第四纪冰川遗迹冰臼、巨石滩、峡谷、岩崖瀑布、两岸山景，绘就秀丽的山水画卷，有着重要的旅游观光价值。"};

    private String[] zheimages1={"https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/zhe1.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/zhe2.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/zhe3.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/zhe4.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/zhe5.jpg"};
    private String[] zheintroduces1 = {"        柘荣鸳鸯头草场位于省级风景名胜区东狮山南侧，海拔980米至1110米之间，距柘荣县城约5公里。",
            "        这是一片面积约5000亩，四周被阔叶和针叶混交林包围的草场。周边的山峰巍峨挺拔，充满阳刚之壮美。草场核心区的山岚远远望去，又如人工泥塑的微型盆景。草山低矮，绵延起伏;山脊走势，富有韵律，节奏中蕴含着温文典雅之美妙!",
            "        站在草原的高处\"鸳鸯峰\"顶环顾四周，向东可望见太姥山与茫茫东海水天相接。如果遇到天朗气清的早晨，还可以看到旭日从海平面冉冉升起的难得景象。向南可探视柘荣与霞浦交界的高峰\"目海尖\"(海拔1192.4米)的全貌，再远处则是台湾海峡浩渺的烟波。向西望去，近可观柘荣南山(海拔1301.7米)，远能眺望群山绵延之中的世界地公园\"白云山\"(海拔1450.2米)。向北可见太姥山脉主峰\"东狮山\"(海拔1479米)云蒸霞蔚景象。",
            "        鸳鸯头草原四季风景各具魅力。春天，绵延的草丛中绽放着淡紫色、红色和白色的杜鹃，与漫山遍野的野花竞相争艳，引来众多的蜜蜂和彩蝶尽情飞舞。置身其中，能使人心花怒放。夏天，翠绿的山岗与蓝天白云融为一体，放眼望去，处处都是天然画卷，令人心旷神怡。秋天，此起彼伏的草甸开着银白色的草花，在微风吹拂下，摆动着修颀窈窕的身姿。冬天，泛红的草山与四周绿色的阔叶和针叶混交林带，形成了鲜明的色彩对比。如果降下一场瑞雪，还会使草原披上银装，其乐更无穷。",
            ""};
    TextView text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jianshao);
        Intent intent = getIntent();
        imageone = intent.getIntExtra("image",0);
        nameone = intent.getStringExtra("name1");
        buyone = intent.getStringExtra("buy1");
        troduceone = intent.getStringExtra("troduce");
        name_receive= intent.getStringExtra("name");//景点地区
        text2 = findViewById(R.id.text2);
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
                intent.setClass(JianshaoActivity.this,DetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if(name_receive.equals("蕉城区")){
            images1 = jiaoimages1;
            introduces1 = jiaointroduces1;
            text2.setText("三都澳斗姆岛景区");
        }
        if(name_receive.equals("Lianyungang")){
            images1 = lianimages1;
            introduces1 = lianintroduces1;
            text2.setText("Mount Huaguoshan");
        }
        if(name_receive.equals("Nantong")){
            images1 = nantongimages1;
            introduces1 = nantongintroduces1;
            text2.setText("the Wolf Mountain");
        }
        if(name_receive.equals("古田县")){
            images1 = guimages1;
            introduces1 = guintroduces1;
            text2.setText("翠屏湖景区");
        }
        if(name_receive.equals("屏南县")){
            images1 = pingimages1;
            introduces1 = pingintroduces1;
            text2.setText("白水洋风景区");
        }
        if(name_receive.equals("寿宁县")){
            images1 = shouimages1;
            introduces1 = shouintroduces1;
            text2.setText("横石玻璃天桥");
        }
        if(name_receive.equals("霞浦县")){
            images1 = xiaimages1;
            introduces1 = xiaintroduces1;
            text2.setText("罗汉溪风景区");
        }
        if(name_receive.equals("柘荣县")){
            images1 = zheimages1;
            introduces1 = zheintroduces1;
            text2.setText("柘荣鸳鸯草场景区");
        }
        if(name_receive.equals("周宁县")){
            images1 = jiaoimages1;
            introduces1 = jiaointroduces1;
            text2.setText("九龙漈风景区");
        }
        mRecycleview = findViewById(R.id.recycle);
        mRecycleview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HomeAdapter();
        mRecycleview.setAdapter(mAdapter);
    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           MyViewHolder holder = new MyViewHolder(LayoutInflater.from(JianshaoActivity.this).inflate(R.layout.jianshao_recycle,parent,false));

            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Glide.with(JianshaoActivity.this).load(images1[position]).into(holder.iv);
            holder.introduces1.setText(introduces1[position]);
        }

        @Override
        public int getItemCount() {
            return images1.length;
        }
        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView introduces1;
            ImageView iv;
            public MyViewHolder(View view){
                super(view);
                iv = (ImageView) view.findViewById(R.id.iv);
                introduces1 = view.findViewById(R.id.introduce);
            }
        }
    }
}
