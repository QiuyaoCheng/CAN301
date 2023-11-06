package cn.itcast.recycleview;

import android.content.Intent;
import android.media.Image;
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

   private String[] fuanimages1={"https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fu1.jpg",
           "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fu2.jpg",
           "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fu3.jpg",
           "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fu4.jpg",
           "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fu5.jpg",
           "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fu6.jpg"};
   private String[] fuanintroduces1 = {"        福建福安白云山，国家风景名胜区、国家4A级旅游景区、世界地质公园、国家地质公园。位于福建省福安市西北部穆云乡、晓阳镇境内，距福安市区约40公里。",
           "        景区以“冰臼壶穴博物馆”、“九龙三绝”、罕见佛光仙境等地质天象奇观为特色，更有万亩桃花园、十里葡萄沟风光旖旎，娃娃鱼、午时莲、刺桫椤繁衍生息，畲、茶、廉、古和儒释道三教文化多元交融，共同演绎出仙境白云山的瑰丽和神奇。",
           "        九龙洞景区是白云山五大园区之一，这里树茂花艳，瑶池翠山，彩画山林，冰臼自然。可谓是“冰臼垂天下，三绝驰古今”，园区内成群遍布的臼形怪石，其发布之广、规模之大、种类之全、形态之丰极为罕见。其中蟾溪游龙洞、南溪九龙洞洞中大小崩塌岩块、参差错落，洞底溪水潺潺、跌水频现、碧潭清幽、壶穴神奇，形成洞中有穴，洞洞有景的引人入胜的独特风景，被称为“地质奇观”“壶穴博物馆”。",
           "        在亿年冰臼奇观间，更是孕育了美伦美奂的“九龙三绝”，即汇集了九寨之水、黄龙之林、黄果之瀑的绝美景观。属福建人心中的“西南三绝”！",
           "        园区自秋开始，树木的换装之旅便拉开了帷幕，世外初冬，白云山却秋意正浓。五色彩林海，镶嵌于深山幽谷之中，依旧水蓝如宝石项链，镶嵌在九龙洞之中，彩林渐次变色，层林尽染，美轮美奂。",
           "        让我们走进白云山，共同呼吸大自然清新的气息，探索亿万地质奥秘。"};

    private String[] fudingimages1={"https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fuding1.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fuding2.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fuding3.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fuding4.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fuding5.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fuding6.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fuding7.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fuding8.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fuding9.jpg" ,
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E6%99%AF%E7%82%B9%E4%BB%8B%E7%BB%8D/fuding10.jpg"};
    private String[] fudingintroduces1 = {"        太姥山以花岗岩峰林岩洞为特色，融山、海、川和人文景观于一体，拥有山峻、石奇、洞异、溪秀、瀑急等众多自然景观以及古刹、碑刻等丰富的人文景观，在这里，可以登山、观海、探洞，也可以泛溪、寻古、采风……",
            "        太姥娘娘雕像造型方中有圆，以方为主，吸收了北魏石窟雕像的秀骨清相的特点，揉进少许盛唐时代人物面部的丰润，同时又从汉俑中吸取单纯简洁之韵味，使得雕像造型显得敦厚、祥和、仁慈和智慧。状为迎海风徐徐前行，头略低垂，慈目微启，时时关注着山下芸芸众生。",
            "        在景区广场游唐朝楞伽宝塔和国兴寺遗址；在龙潭湖仰观乌龙岗奇峰及其水中倒影，登观雾岗做一回腾云驾雾的活神仙；穿过挂满紫藤的绿色长廊，登上长萨公岭，沿途林木参差，山花烂漫；到涌翠亭，可近观夫妻峰，眺望元宝石，驻足欣赏“太姥无俗石”摩崖石刻和海浪石，还有素有天然空调美誉的柳杉坪。",
            "        传说中的东海诸仙每年都要来太姥山相聚，迎仙台便是太姥娘娘迎接诸仙的地方，迎仙台也是欣赏太姥山的肖形景观要在集中处，四周各式各样的肖形景观，宛若随意摆设抽象派雕像艺术大师的巨型作品，形神兼备，惟妙惟肖，令人目不暇接。",
            "        此处岩洞众多，有特色的要数素有洞中乐园之称的葫芦洞，紧接着是四通八达的将军洞，出洞时回头可赏妙趣横生的亲嘴岩。出将军洞，到V字形龙须岭，岭底可观高40余米的擎天巨柱天柱峰，两条山道通往大磐石、观海台，皆是登高望海观景好去处。",
            "        一片瓦是一处以幽取胜的佳境。挤过仅可容一人侧身而过的一线天洞，穿过白马洞，便到一片瓦。来到这里，不妨在太姥娘娘修道的鸿雪洞歇个脚，用丹井水泡一壶太姥娘娘传下的绿雪芽茶，边听太姥娘娘升天故事，边赏金龟爬壁、金鸡报晓等景观群。",
            "        香山寺始建于明朝，兰花盛开时暗香涌动，故而得名；五百罗汉堂内的罗汉均为缅甸汉白玉精雕细琢而成，为国内一绝；九鲤湖小巧玲珑，倒映着九鲤奇峰，湖光山色，是风光摄影的绝妙点。",
            "        九鲤峰以峰石峥嵘见长，集峰、岩、洞于一体，极具太姥特色。登陀九岭可直抵紫烟岭，其尽头悬崖边上的御风桥，横跨于深谷之上，可近观九鲤朝天奇景；站在两扇陡峭岩壁对峙的南天门，可远眺海市蜃楼般的香山寺；穿南天门洞可直抵南天门，洞内树藤缠绕，曲折崎岖，趣味无穷。",
            "        摩霄峰是太姥山的登高远眺的上佳去处，也可借宿赏月、观日出；峰下有唐玄宗年间建造的白云禅寺、摩尼教遗址摩尼宫，还有美女献花、石船等景观。白云禅寺周边，有摩崖石刻，流米洞、金蟾含钱树等景观。摩霄峰旁，就是覆鼎峰，建有观日台。",
            "        来到乌龙岗，可在十八罗汉头顶攀登，在曝龙背上漫步，在其他游客翘首眺望的风景里穿梭。至此，你会感觉美景看都看不过来了，时而俯瞰悬崖深谷，近观峰林耸立，体验太姥山的峰险奇观；时而放眼重峦叠嶂，时而极目长天碧海，感受山海大观的意境。"};

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
        if(name_receive.equals("福安市")){
            images1 = fuanimages1;
            introduces1 = fuanintroduces1;
            text2.setText("福安白云山风景区");
        }
        if(name_receive.equals("福鼎市")){
            images1 = fudingimages1;
            introduces1 = fudingintroduces1;
            text2.setText("太姥山风景名胜区");
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
