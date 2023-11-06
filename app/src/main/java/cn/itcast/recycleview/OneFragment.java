package cn.itcast.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by DELL on 2022/10/10.
 */

public class OneFragment extends Fragment {
    //蕉城区
    private String[] jiaoname = {"君安大酒店","宁德兰逸精品酒店","锐思特酒店宁德万达店","宁德唯依城市主题酒店","宁德市东方国际威悦大酒店","宁德沃尔假日酒店","宁德东方艾美酒店","WORD影院公寓","锦江之星风尚宁德万达广场店","宁德玖间堂泊城酒店","速8酒店宁德嘉宇汽车北站"};
    private  int[]  jiaoicons = {R.drawable.junan,R.drawable.lanyijingpin,R.drawable.ruisite,R.drawable.weiyi,R.drawable.weiyue,R.drawable.woer,R.drawable.aimei,R.drawable.word,R.drawable.dachuangfa,R.drawable.jiujian,R.drawable.suba};
    private String[] jiaobuy = {"¥124","¥194","¥133","¥102","¥123","¥137","¥114","¥136","¥137","¥110","¥163"};
    private String [] jiaointroduce = {"经过一座城，寻觅一间房，消去您满身的疲劳，我们将以热情的服务，舒适的房间，期待您的到来。我们，在这里,等您来。\n",
            "经过一座城，寻觅一间房，消去您满身的疲劳，我们将以热情的服务，舒适的房间，期待您的到来。我们，在这里,等您来。",
            "锐思特酒店起源于2006年,是逸柏酒店集团旗下个“舒适商旅型\"品牌。以追求\"高性价比的品质\"为品牌的市场定位。多年来,通过高性价比的\"投资设计\"及良好的产品体验性能，获得了众多投资者与消费者的认可。作为集团旗下精选商务酒店品牌，锐思特酒店结合家居理念精心设计，以“时尚、舒适、简约\"为主导，精致配置\"高星级床上用品\"、“时尚卫浴\"等酒店核心部件，打造出酒店产品的极致化体验。",
            "宁德唯依主题酒店位于蕉城南路,地处宁德繁华商圈，毗邻宁德市一中、宁德市建行、宁德市农行、宁德市工商银行，步行可达宁德大型购物广场――宁德万达购物广场，为您提供餐饮、娱乐、购物站式服务。",
            "宁德兰逸精品酒店位于福建省宁德市蕉城区容宏新佳坡商贸城A幢,坐拥蕉城区核心地段新佳坡步行街街口;高、中档餐饮、娱乐、购物场所比比皆是。酒店门口有多条公交线路，6路、1路、3路、7路、9路等多条线路经过，覆盖各城区，且对面为宁德汽车北站,距周边大型综合商城在2公里左右。",
            "宁德市东方国际威悦大酒店隶属于宁德东方经济发展有限公司和厦门东方艾美酒店管理有限公司战略合作的旗下酒店，拥有各类客房百余间，格调清新高雅。房内均覆盖高速Wifi，配有大型中央空调，独立电梯，且电视均为高清点播。",
            "沃尔假日酒店是一家按星级标准配置的精品旅游商务酒店，酒店位于宁德市。地理位置优越，处于市中心，交通便捷，风景优美，比邻繁华的万达广场，离福宁高速出口仅2公里，动车站8分钟。沃尔假日酒店拥有72间(套）客房。打破传统的酒店装修标准化格局，采取多种风格装修，高弹性大床，45寸高清电视，电脑等，无线网络全面覆盖，房内设施齐全，为顾客创造温馨，舒适，充满情调的起居环境。",
            "宁德东方艾美酒店（汽车南站店）位于宁德汽车南站斜对面，距离沈海高速出口仅需约500米。酒店拥有干净整洁的房问装修时尚又不乏典雅，简约却透着奢华。周边交通:距离东侨工业区2公里,距离火车站约15分钟的车程。距离汽车南站步行约2分钟，离汽车北站约25分钟，离万达广场仅需要约8分钟，沃尔玛仅需约10分钟。周边银行:农商银行，建设银行。客房内24小时提供热水、恒温恒压、冷暖空调、营养早餐，免费无线宽带，免费停车位等，酒店全体员工期待您的光临。",
            "WORD公寓，地址位于福建省宁德市天茂城市广场2号楼，装修新颖，环境宜人，1080P高清投影仪，100寸豪华幕布，给你电影院的感觉，独享电信50兆光纤高速上网，24小时热水配上豪华的沐浴设备，扫除你一天的疲劳。周边介绍:配套有偿停车位、无偿停车位，近南岸公园，天茂广场",
            "将承袭速8酒店世界一致的\"干净、友好、标准一致\"的国际化标准服务。",
            "宁德玫间堂泊城酒店是一家中高端商务酒店,时尚现代化的设计格,商务与休憩的结合,简约而不失活力，创意性的细节处处融酒店产品,为高品质的商旅人士的工作和生活带来灵感。"  };

    //古田县
    private String[] guname = {"古田华侨酒店","宁德古田沐舍主题酒店","古田曼福酒店","古田金源大酒店","艾美酒店","宁德新世贸大酒店","宁德古田好莱斯登酒店","宁德古田县好望角酒店","古田锦贤宾馆"};
    private  int[]  guicons = {R.drawable.huaqiao,R.drawable.mushe,R.drawable.manfu,R.drawable.jinyuan,R.drawable.aimei,R.drawable.xinshimao,R.drawable.haolai,R.drawable.haowangjiao,R.drawable.jingxian};
    private String[] gubuy = {"¥132","¥100","¥183","¥130","¥142","¥275","¥135","¥236","¥84"};
    private String [] guintroduce = {"该店客房整洁，宽敞明亮，环境卫生，配套设施齐全，服务周到，方便入住。借鉴在欧美完善成熟的经济型酒店模式，为商务和休闲旅行等客人提供“干净、温馨\"的酒店产品，倡导“适度生活，自然自在”的生活理念。用我们的专业知识和精心规划,使我们服务和产品的效益优质，从而为我们的客户提供\"干净、温馨”经济型酒店产品。本店客房温馨舒适，竭诚欢迎您的光临。",
            "沐舍主题酒店位于古田县城西路8号，地处县城中心，主干道交汇，交通便捷,较大的人工湖翠屏湖距离酒店仅有8千米左右，湖畔分布鸟岛、蛇岛、极乐寺、临水宫等众多名胜，东临汽车东站,乘坐高铁到省会城市福州仅需37分钟，京台高速路口不足10分钟车程,侧面和楼下就有大型购物广场，是您旅游商务的理想憩息场所。",
            "古田曼福酒店座落在古田县解放路，位置优越，交通便利。拥有豪华套房、精品套房、标准套房、总统套房若干间。客房的干净整洁、待人的舒适温暖是我们的服务宗旨。一个专门为商务、休闲、旅游人士打造的温馨家园。",
            "酒店地理位置优越,交通便捷,拥有多档次客房，客房配有空调、有线电视、24小时热水系统、程控电话、互联网，是社会各界人士商务洽谈、休闲度假的理想之所。",
            "古田艾美酒店隶属艾美酒店集团旗下精品商务型酒店，酒店拥有免费停车场供客户停车。酒店以“宾客至上、品质为先”的经营理念为核心。艾美酒店集团优质的服务理念和智能化的配套设施，将带给每位宾客非凡享受和宾至如归的愉悦体验。",
            "酒店位于美丽的宁德古田食用菌之乡，地理位置优越，交通极为便利，紧邻古田翠屏湖旅游景区约6分钟，古田临水宫约25分钟，距屏南白水洋旅游景区约50公里。酒店是一家综合性商务接待为主的酒店，以其现代化设备、宽敞舒适、幽静雅致而独具一格。",
            "古田好莱斯登酒店，是一家地方风格特色酒店，位于古田县西区614路419号鑫都大厦1-5层。建筑面积约3000平方米，本酒店拥有为各界人士设身打造的各类房型共计53间，高端装修、服务周到，客房内配套有液晶电视、独立卫浴、24小时冷热水供应、环境舒适、温馨整洁，在酒店同行中性价比高，星级的客房、快捷的价格，为您提供营养早餐，免费停车场，酒店位于中心地段，购物休闲娱乐为一体，交通便利，距中心广场约200米，距长途汽车站约100米，距动车北站约13公里，周边覆盖多条公交线路。是您旅行度假、休闲商务的理想场所!",
            "古田县好望角酒店（宁德)坐洛寸风京仇夫的国教有白双r-湖畔、省级文明县城福建省古田县城区中心，酒店营业面积约1300o平方米，南楼13层、北楼16层，有国内知名设计师精心设计，设计费高达285万元，内部装修斥资5000余万元，按国际星级称准建疸，正一家集客房、餐饮、桑拿休闲、商务中心、商务会议、娱六为一体的多功能综合性商务酒店，建筑风格现代典雅。",
            "经过一座城，寻觅一间房，消去您满身的疲劳，我们将以热情的服务，舒适的房间，期待您的到来。我们，在这里,等您来。"};

    //屏南县
    private String[] pingname = {"香悦大酒店","屏南佳和酒店","宁德屏南凯城酒店","屏南自在花时客栈","屏南悦竹精品酒店","屏南远景酒店","屏南白水洋舒馨宾馆","屏南长兴宾馆"};
    private  int[]  pingicons = {R.drawable.xiangyue,R.drawable.jiahe,R.drawable.kaicheng,R.drawable.zizai,R.drawable.yuezhu,R.drawable.yuanjing,R.drawable.shuxing,R.drawable.changxing};
    private String[] pingbuy = {"¥98","¥88","¥111","¥132","¥187","¥121","¥74","¥103"};
    private String [] pingintroduce = {"香悦大酒店，建筑面积万余平方米，拥有5000平方米的停车场，酒店设有花园式套房、豪华套房、豪华单人房、标准房等各类型房间，还拥有大小会议室及多功能厅能承接各种大中小会议及宴会。",
            "酒店房内设有冷暖空调、有线电视、内线电话、有线宽带和无线wifi。以“宾客至上\"为企业理念,以“舒心满意\"为服务宗旨，竭尽全力为宾客提供优质服务，是商务、旅游理想的下榻之所。",
            "宁德屏南凯城酒店于2011年成立，总投资人民币6000多万元。占地面积4800多平方，总建筑面积18000多平方米。酒店地处屏南南大门高速公路旁，是福州、浙江等城市交通要道，二级路必经之地，交通非常便利。",
            "屏南自在花时客栈位于宁德屏南县甘棠乡涤下村，客房干净、卫生，具有基础性设施，住宿环境舒适。期待您的光临。",
            "屏南悦竹精品酒店原屏南悦竹宾馆地处古峰镇旅游集散中心，尽享通畅、便捷的交通网络。周边设施齐全，距离旅游景点白水洋、鸳鸯溪20公里。酒店拥有各式宽敞、舒适的高品应各房。方个IuMI见带及国内长途无线畅享。酒店拥有大型停车场及现代化商务配套设施，功能齐全，为您创造良好的商务活动环境。悦竹宾馆竭诚为您打造现代化精品酒店，恭候您的莅临!",
            "屏南远景酒店坐落在美丽的千年古城双溪镇,于2012年开业,楼高9层。酒店地理位置优越,交通便利。餐饮配套设施齐全，装修豪华、环境典雅、设施齐全、功能完善，服务周到。酒店从东往西可看到双溪八景的七个景点:三台隐翠、北寺秋声、印山积雪、南桥春济、文峰挺秀、钟山残霞、两涧回澜,往北看俯俸古城全景。周边景点设有白水洋、鸳鸯溪、刘公岩。屏南远景酒店交通便捷，距离县城约十多分钟路程，沿途有高速，亦可走二级路;距离白水洋、鸳鸯溪景区不到10分钟车程，是距离景区较近的酒店之一。距福州火车站180公里，距福州长乐机场196公里，距长途汽车站15公里。",
            "水洋舒馨公寓位于国家5A级景区-―(白水洋和鸳鸯溪之间的双溪镇)）公寓就在镇中心地段，距离白水洋2公里路程，距离鸳鸯溪12分钟车程。客栈周边旅游服务设施齐备，镇上有车每天来往于两个景区之间，客满即发。交通、餐饮非常方便。舒适型房设计装修，房间宽敞明亮、卫生干净，都带窗户，还具有免费的无线宽带，性价比较高。相信能为你提供一个旅游中舒适的休憩场所。(房间有独立卫生间，空调，热水器等).。。.我们本着“让各位驴友住的舒心、玩得开心\"经营理念，为各位提供服务。竭尽全力满足您的各类需求。为满足各位驴友的需求,我们开设了不同要求的房型。",
            "公寓地处县城中心地段达。宾馆装修豪华，内设中、高档客房、电脑房（宽带上网）。宾馆秉承\"兵各至上、服夯的示曰，沟欢迎您的光临。"};

    //周宁县
    private String[] zhouname = {"格林豪泰酒店","周宁县永盛宾馆","锐思特酒店","周宁龙华大酒店","世纪金源大酒店","周宁河畔小居","周宁东洋溪大酒店"};
    private  int[]  zhouicons = {R.drawable.haotai,R.drawable.yongsheng,R.drawable.ruisitezhouning,R.drawable.longhua,R.drawable.jinyuanzhou,R.drawable.hepan,R.drawable.dongxiyang};
    private String[] zhoubuy = {"¥140","¥49","¥144","¥133","¥124","¥116","¥112"};
    private String [] zhouintroduce = {"格林豪泰宁德市周宁县桥南街商务酒店属于格林酒店集团旗下连锁酒店。格林酒店集团是中国全外资连锁酒店集团之一，酒店品牌包括中高端东方经典-格林东方酒店、中高端商务-格美酒店、中高端时尚-格雅酒店、中高端休闲-格菲酒店、中档经典商务-格林豪泰酒店、中档个性组合-格盟酒店、城市绿洲-青皮树酒店、年轻多彩-贝壳酒店，为客人提供\"超健康、超舒适、超价值、超期望的广月汉务。",
            "周宁宁德永盛宾馆宾馆位于周宁县汽车站对面，交通方便，客房性价比高，住宿环境良好、通风采光较好.欢迎下榻入住",
            "锐思特酒店(周宁汽车站店)地处周宁名仕广场附近，闹中取静，出门即是繁华街市，宁静与繁华一步之遥;酒店周边有名仕广场，仙溪公园，KTV ,超市，吃住行娱乐一应俱全，便利交通。",
            "龙华大酒店位于县城桥南街，地理位置优越,临近步行街与名仕广场，周围娱乐场所与餐饮极为方便。宾馆现拥有多档次客房，客房配有空调、有线电视、24小时热水系统、提供WIFl、互联网，门口有停车场，室内车库，贴心为宾客营造了一个理想的旅游度假居停之所，更致力于精心为每位客人提供一种无尽的生活享受。酒店设施齐全，竭诚欢迎各社会团体、旅游团体及社会各界人士的下榻光临。",
            "酒店装修时尚高雅,设施齐全，环境舒适。是您商务、旅游、休闲、娱乐的理想选择。在服务上，酒店秉承着“客户至上，宾至如的宗旨，让您在住店的同时，感受到\"家\"的温暖;在各类主题体验上，酒店力求将每个房间以逼真和完美的姿态展现在您的面前，让您时时刻刻拥有一种身临其境的感觉。酒店采用了先进及高效的管理模式，让您真正的体验到酒店的与众不同。住酒店不只是简单的解决住宿问题，更在于享受住宿。",
            "周宁河畔小居位于周宁县东洋溪溪边，门口就是步行街，周边餐饮及商店等配套设施齐全，干净整洁。客房干净整洁，宽敞明亮,环境卫生，配套设施齐全，服务周到，方便入住，性价比高，住宿环境、通风采光较好。",
            "周宁宁德周东洋溪大酒店周宁县东洋溪大酒店酒店地处周宁县狮城镇商贸中心，位于狮城镇南街37号，周宁宾馆斜对面，酒店装修时尚，设施齐全，环境舒适。酒店以“宾客至上，服务\"为经营宗旨，采用了科学的经营机制和管理方法，不断追求卓越，得到了社会的认可，无论商务、休闲、娱乐，都是理想之选。"};

    //寿宁县
    private String[] shouname = {"寿宁帝豪商务宾馆","大都·豪庭宾馆","宁德寿宁柏悦宾馆","寿宁裕龙宾馆","寿宁悦龙宾馆","寿宁东湖大酒店","宁德寿宁聚源宾馆","寿宁福宁宾馆"};
    private  int[]  shouicons = {R.drawable.dihaoshangwu,R.drawable.dadu,R.drawable.boyue,R.drawable.yulong,R.drawable.yuelong,R.drawable.donghu,R.drawable.juyuan,R.drawable.funing};
    private String[] shoubuy = {"¥112","¥110","¥63","¥69","¥58","¥256","¥120","¥90"};
    private String [] shouintroduce = {"寿宁宁德帝豪商务宾馆寿宁帝豪商务宾馆是一家主营住宿客房服务的精品酒店，位于县城东区的滨湖北路。宾馆客房装修精致、高档,设备完整、齐备。每一个房间都体现出宾馆对客人丝丝入微的贴心服务。竭诚欢迎每一位宾客的光临。",
            "大都·豪庭宾馆福建省宁德市寿宁县东区福宁街12号，大量免费停车位，临近有寿宁东区农贸批发市场，寿宁商贸城，超市便利店，商圈广场，餐饮店，休闲娱乐等等~",
            "房间配备:空调、电脑,光纤，24小时供应热水、高清电视、电风吹、拖鞋、洗浴用品、无线wifi、电热水壶、茶叶、茶杯、50兆苋带上网。宾馆客房经济实惠、方便快捷,服务热情周到、细致入微，是您商务出差,旅游观光的理想选择。",
            "寿宁裕龙宾馆环境优雅，方便快捷，宜家干净。",
            "寿宁悦龙宾宾馆位于宁德寿宁县茗溪路29号，客房整洁，宽敞明亮，环境卫生，性价比较高，欢迎期待您的光临。",
            "寿宁东湖大酒店地理位置优越、环境优美，门口有多条公交线路和到各个市、县、乡镇的班车。酒店周边生活配套齐全，是您出行、旅游、办公、娱乐的理想选择。",
            "聚缘宾馆距离寿宁新车站较近，交通便利。宾馆内设施齐全（空调，电视，电脑,吹风机等)，宾馆内房间干净安静，欢迎入住。",
            "想要游览寿宁，寿宁福宁宾馆将会是一个不错的的住宿之选。优美的环境，再搭配上细致周到的服务，酒店的休闲区定能满足您的品质需求"};

    //福安市
    private String[] funame = {"天隆商务宾馆","宁德好乐迪假日酒店","福安南舟宾馆","福安花园宾馆","宁德尚客快捷酒店","海鑫精品酒店","福安华利宾馆","福安铂晶悦己酒店"};
    private  int[]  fuicons = {R.drawable.tianlong,R.drawable.haoledi,R.drawable.nanzhou,R.drawable.huayuan,R.drawable.kuaijie,R.drawable.haixingjingping,R.drawable.huali,R.drawable.bojingyueji};
    private String[] fubuy = {"¥91","¥112","¥145","¥105","¥116","¥105","¥55","¥166"};
    private String [] fuintroduce = {"天隆商务宾馆交通便利，客房设施齐全，布置温馨舒适，服务周到。客房干净整洁，宽敞明亮，环境卫生，配套设施齐全，服务周到，方便入住，性价比高，住宿环境、通风采光较好。",
            "宁德好乐迪假日酒店是一家以提供住宿产品为王的精品酒)占，11于市中心主干道新华中路。周边有众多银行、餐饮、购物功所，定怀闲、娱乐、美食的黄金地段，地理位置优越、交通便利。酒店按高称准设计，集多功能为一体，健康舒适、典雅别致，定懋简芳公十、目助旅游下榻的理想选择。酒店始终如一地向顾客提供干净舒适的客房与贴心友好的服务。",
            "福安南舟宾馆，位于福安市区阳头街道花园路，宾馆内置设施齐全，布置合理，温馨舒适，是您理想的入住温馨家园!",
            "福安花园宾馆，位于福安市区阳头街道花园路，宾馆内置设施齐全，布置合理，温馨舒适，是您理想的入住家园式酒店!",
            "尚客快捷酒店位于福安的商业中心，在长途站对面，高速路口出口处，酒店一楼代售汽车票、火车票和飞机票，机场大巴在此停靠。旅游出行购物休闲非常便利。福安有中国较大的民间造船基地，有着较大的绿茶基地。宋理宗年间，福安籍一位官员申请建县郡写下的一首诗，可以非常准确的说明福安的情况，而宋理宗就是看到这首诗而提笔批注:赐以五福，以安一县。“福安\"因此得名。",
            "福安海鑫精品酒店位于花园路，环境优雅，出行便利，交通快捷。福安海鑫精品酒店客房干净整洁，设施完善，服务热情。酒店以“宾客至上，服务第一\"为经营宗旨，使您感到安全、舒适、方便、愉快，给您一个安静舒适的居住环境。",
            "福安宁德华利宾馆福安华利宾馆是一家提供住宿客房服务的经济型宾馆，位于汽车客运站的后面，交通异常便捷。宾馆一贯秉承“经济实惠、方便快捷\"的服务理念,竭诚欢迎您的光临。",
            "酒店位于福建省宁德市福安市鹤峰路,地处繁华闹市区和旅游中心，紧靠环城高速公路，福安市汽车站。天马山森林公园信步可达，香山寺、鹤山道观等诸多景点更是近在咫尺。这里还有金马市场、水果市场等多姿多彩的购物为您生活提供更多便捷。"};

    //柘荣县
    private String[] zhename = {"柘荣京鼎荣商务酒店","柘荣县富商商务酒店","柘荣幽舍酒店","柘荣县宾馆","柘荣县九华洲宾馆","柘荣东华大酒店","宁德乘峰宾馆"};
    private  int[]  zheicons = {R.drawable.jingding,R.drawable.fushang,R.drawable.youshe,R.drawable.zherongxianbingguan,R.drawable.jiuhuazhou,R.drawable.donghuadajiudian,R.drawable.chengfengbinguan};
    private String[] zhebuy = {"¥113","¥143","¥153","¥234","¥202","¥268","¥73"};
    private String [] zheintroduce = {"柘荣京鼎荣商务酒店:2021.6.26重装焕新，升级的是品质，提升的是服务，不变的是价格。我在京鼎荣商务酒店，恭候您的到来!",
            "柘荣县富商商务酒店位于福建省宁德市柘荣县上桥路5-10号，在柘荣县车站附近，出门就可以上104国道，交通非常方便。柘荣县富商商务酒店于2009年开业至今，在2017年1月的时候重新翻新了一遍，房间设施齐全而且新，酒店客房部设在4-6楼，2-3梭定正洽，月电梯，非常适合出差的您。希望能给您带来愉快的入住。柘荣县富商商务酒店竭诚欢迎您的入住",
            "柘荣幽舍酒店位于太子参交易中心门口(二中旁)，酒店极简设计，干净整洁。周边景区:仙屿公园.东狮山风景区.步行可到。交通便利来“柘\"享受，我\"氧\"你。",
            "柘荣县宾馆座落于双城镇河滨西路，位于县城中心位置，靠近县政府，周边配套设施齐全，方便商务人士出行。柘荣是闽浙两省边界贸易点、国务院批准对外开放的全国55个县、市之一，是生态示范区，有“中国太子参之乡”、“中国民间文化艺术之乡\"的美誉。",
            "柘荣县九华洲宾馆是一家电影主题酒店，是提供安全、舒适，让使用者得到短期的休息或睡眠空间的商业机构，按照国家星级标准兴建的集商务、会议、住宿于一体的综合性商务酒店。酒店位于柘荣县双城镇黄金地段，与仙屿公园毗邻，地理位置优越，交通便捷，附件小吃众多。",
            "东华大酒店是由柘荣县联合投资有限公司投资标准设计建造的现代化大酒店，酒店总建筑面积19000平方米，位于生态示范区，有“中具,坐落手风国太子参之乡\"、“中国民间文化艺术之乡”之称的柘荣县，坐落于风景优美的东狮山脚下，毗邻柘荣县人民政府，东依风景名胜太姥山。",
            "宁德乘峰宾馆位于宁德市柘荣县柳城东路7号，柘荣汽车站附近，东师山脚下，交通便利，出行方便，适合出门旅行的您。宁德乘峰宾馆于2013年重新翻修了一遍，房间设施齐全，而且很新，希望能给您带来愉快的入住。宁德乘峰宾馆位于柘荣县繁华的地段，出门购物，享受美食都方便。宁德乘峰宾馆竭诚欢迎您的入住。"};


    //福鼎市
    private String[] fudingname = {"福鼎太姥大酒店","福鼎艾美酒店","福鼎山水假日酒店","锦江之星福鼎太姥大道店","锐思特酒店福鼎南站店","福鼎太姥山澳莱大酒店"};
    private  int[]  fudingicons = {R.drawable.tailaoshan,R.drawable.fudingaimei,R.drawable.fudingshanshuijiari,R.drawable.jinjiangzhixin,R.drawable.ruisitefuding,R.drawable.aolaidajiudian};
    private String[] fudingbuy = {"¥126","¥96","¥133","¥132","¥161","¥122"};
    private String [] fudingintroduce = {"福鼎太姥大酒店是希瑞连锁旗下一家分店，集客房、会议、商务、旅游为一体的多功能,智能化管理的商务连锁酒店。",
            "福鼎艾美酒店店地处福鼎市位于繁华市中心老街海口街,交通便利，出行方便，毗邻福鼎市汽车南站、汽车北站，门口就有公交站，也能快速到达福鼎动车站;周边特色餐饮景点多，福鼎著名的海鲜一条街就在旁边，走路即可到达，著名的风景名胜区太姥山风景区有动车可以直达。福鼎艾美酒店是您商务出行、旅游住宿的选择。",
            "本酒店位于太姥山旅游集散中心斜对面，临近秦川汽车站，有停车位，电梯，交通便利。客房温馨舒适，服务周到热情，是您出差旅行的优选之地。",
            "锦江之星福鼎太姥大道店座落于闽浙交界处的海滨城市及旅游城市福鼎市，酒店位于市中心繁华商业区的太姥大道上，临近城市绿肺风景旅游区-太姥山。酒店周边有着令人流连忘返的美食一亲街,2贸商城、温州商城、茶叶街、福鼎特产等，步行即可直接到达。",
            "锐思特酒店起源于2006年,是逸柏酒店集团旗下个“舒适商旅型\"品牌。以追求“高性价比的品质\"为品牌的市场定位。多年来，通过高性价比的“投资设计\"及良好的产品体验性能，获得了众多投资者与消费者的认可。作为集团旗下精选商务酒店品牌，锐思特酒店结合家居理念精心设计，以“时尚、舒适、简约\"为主导，精致配置“高星级床上用品”、“时尚卫浴\"等酒店核心部件，打造出酒店产品的极致化体验。",
            "座落在风景秀丽的宁德福鼎太姥山脚下，现代化的装修风格，智能化的客房，人性化的服务，带给您星级版的品质体验，酒店一楼设澳莱庄园，主营武夷岩茶、福鼎白茶、澳洲原装原瓶进口葡萄酒,是您品茗、品酒商务洽谈的绝佳选择!"};
    //霞浦县
    private String[] xianame = {"霞浦县锦都宾馆","城市便捷酒店宁德霞浦店","福维尔酒店","锐思特酒店霞浦山河路店","骏怡连锁酒店（霞浦店）","速8酒店霞浦九龙街店","龙云宾馆","霞浦千禧之家假日酒店"};
    private  int[]  xiaicons = {R.drawable.jindujiudian,R.drawable.chengshibianjie,R.drawable.fuweier,R.drawable.ruisitejxiapuxian,R.drawable.junyiliansuojiudian,R.drawable.subaxiapu,R.drawable.longyun,R.drawable.xiapuqianxizhijia};
    private String[] xiabuy = {"¥60","¥148","¥164","¥117","¥160","¥175","¥79","¥129"};
    private String [] xiaintroduce = {"酒店位于霞浦县县中心，周边交通便利，特色小吃街，娱乐场所，该店客房整洁，宽敞明亮，环境卫生，配套设施齐全，服务周到，方便入住",
            "城市便捷酒店（霞浦店）位于东吾路，近福宁大道，地段繁华，交通便捷。酒店周边旅游资源丰富，海国桃源―杨家溪及被誉为中国夫丽滩涂的霞浦风光摄影地均只需30分钟车程城市便捷酒店（霞浦店）是城市便捷酒店集团旗下的一家经济快捷精品酒店。",
            "艾美福维尔酒店是福建艾美商业集团有限公司旗下精品酒店位于中国滩涂摄影圣地\"霞浦”，毗邻霞浦客运站、福宁文化公园。酒店整体装饰简约现代，客房布置舒适洁净，多款房型内均配以优质床上用品、豪华淋浴设备、舒适办公桌椅、丰富的电视节目，是出差、旅游的理想选择。",
            "锐思特酒店起源于2006年,是逸柏酒店集团旗下个“舒适商旅型\"品牌。以追求“高性价比的品质\"为品牌的市场定位。多年来，通过高性价比的“投资设计\"及良好的产品体验性能，获得了众多投资者与消费者的认可。作为集团旗下精选商务酒店品牌，锐思特酒店结合家居理念精心设计，以\"时尚、舒适、简约\"为主导，精致配置“高星级床上用品\"、“时尚卫浴\"等酒店核心部件，打造出酒店产品的极致化体验。",
            "骏怡连锁酒店位于霞浦县松城街道龙首路102号，专为商务旅客量身设计，为经常在外旅行并追求舒适、便利住宿的人设计的时尚酒店。酒店紧临龙首山公园、南峰山等景点,便于休闲;同时它又处于美食、购物、文化的中心地带，便于交流。酒店距霞浦火车站5么里，距霞浦汽车站2公里，交通便利。酒店拥有各式客房:标准大床，标准双床，商务大床，豪华套房、商务双人房等50间",
            "速8酒店霞浦九龙街店位于霞浦县新城区商业街中心地带。地处长溪路九龙街商业区，毗邻动车站、福宁长途汽车站、沈海高速路霞浦站进出口，交通便捷。酒店共有70间设施齐全、清新、简雅、明亮的客房。",
            "龙云宾馆地理位置优越，交通便利，出门就可以坐上公交车，亦或者有店家提供电动车，让你在各个景点间自由穿梭，附近还有篮球场、小型电影院、咖啡店、超市等让你的旅行更加便利。客房干净整洁，周边知名小吃一条街，宾馆重新装修，价格优惠，提供景点向导咨询。",
            "霞浦千禧之家假日酒店地处霞浦县城中心地段东吾路与福宁大道交汇处，邻近王龙广场、月波公园，旅游出行便利。这里以精品客房为主，房间设计风格时尚大方，配设空调、液晶电视、国内长途电话、24小时热水、独立卫浴等设施，无线网络让你与外界沟通不中断。"};

    private String[] name1 = {};
    private int[] icons = {};
    private  String[] buy ={};
    private  String[] introduces = {};
    String name_receive1;
    int img;
    int f;
    String ziliao;
    String buy1;
    String name;
    private RecyclerView mRecyclerView;
        private HomeAdapter mAdapter;
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onefragment, container, false);

        Intent intent = getActivity().getIntent();
        f = intent.getIntExtra("f",0);
        name_receive1 = intent.getStringExtra("name");//景点地区
        name = intent.getStringExtra("detail_name");
        ziliao = intent.getStringExtra("detail_introduce");
        buy1 = intent.getStringExtra("detail_buy");
        img =intent.getIntExtra("detail_iv",0);

        if(name_receive1.equals("蕉城区")){
            name1 = jiaoname;
            icons = jiaoicons;
            buy =jiaobuy;
            introduces = jiaointroduce;
        }
        if(name_receive1.equals("古田县")){
            name1 = guname;
            icons = guicons;
            buy =gubuy;
            introduces = guintroduce;
        }
        if(name_receive1.equals("屏南县")){
            name1 = pingname;
            icons = pingicons;
            buy =pingbuy;
            introduces = pingintroduce;
        }
        if(name_receive1.equals("周宁县")){
            name1 = zhouname;
            icons = zhouicons;
            buy =zhoubuy;
            introduces = zhouintroduce;
        }
        if(name_receive1.equals("寿宁县")){
            name1 = shouname;
            icons = shouicons;
            buy =shoubuy;
            introduces = shouintroduce;
        }
        if(name_receive1.equals("福安市")){
            name1 = funame;
            icons = fuicons;
            buy =fubuy;
            introduces = fuintroduce;
        }
        if(name_receive1.equals("柘荣县")){
            name1 = zhename;
            icons = zheicons;
            buy =zhebuy;
            introduces = zheintroduce;
        }
        if(name_receive1.equals("福鼎市")){
            name1 = fudingname;
            icons = fudingicons;
            buy =fudingbuy;
            introduces = fudingintroduce;
        }
        if(name_receive1.equals("霞浦县")){
            name1 = xianame;
            icons = xiaicons;
            buy =xiabuy;
            introduces = xiaintroduce;
        }
        mRecyclerView = view.findViewById(R.id.recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return view;


    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
          MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.recycle_itemone,parent,false));
            return holder   ;
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.iv.setImageResource(icons[position]);
            holder.nameone.setText(name1[position]);
            holder.buy.setText(buy[position]);
            holder.troduce.setText(introduces[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(),JiudianActivity.class);
                    intent.putExtra("detail_iv",icons[position]);
                    intent.putExtra("detail_name1",name1[position]);
                    intent.putExtra("detail_buy",buy[position]);
                    intent.putExtra("name",name_receive1);
                    intent.putExtra("detail_introduce",introduces[position]);

                    intent.putExtra("f",f);
                    intent.putExtra("image",img);
                    intent.putExtra("name1",name);
                    intent.putExtra("buy1",buy1);
                    intent.putExtra("troduce",ziliao);
                    startActivity(intent);
                    getActivity().finish();
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
            public MyViewHolder(View view){
                super(view);
                nameone = (TextView)view.findViewById(R.id.name);
                iv = (ImageView)view.findViewById(R.id.iv);
                buy=(TextView)view.findViewById(R.id.buy);
                troduce =(TextView) view.findViewById(R.id.ziliao);
            }
        }
    }

    }
