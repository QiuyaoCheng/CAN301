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

import com.bumptech.glide.Glide;

import java.sql.SQLException;

/**
 * Created by DELL on 2022/10/10.
 */

public class TwoFragment extends Fragment {
    //蕉城区
    private String[] jiaoname = {"弥生食堂日式料理（万达店）","One More 咖啡简餐","胖哥俩肉蟹煲（蕉城万达店）","七欣天（宁德万达广场店）","有拈头成都市井火锅（天茂店）","蛙小西·牛蛙堡火锅（天茂店）","桃花坞","南洋小聚（宁德万达店）"};
    private  String[]  jiaoicons = {"https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E8%95%89%E5%9F%8E%E5%8C%BA/ningsheng.jpg",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbkimg.cdn.bcebos.com%2Fpic%2F500fd9f9d72a6059980a51762634349b033bba40&refer=http%3A%2F%2Fbkimg.cdn.bcebos.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669901853&t=118288c8d1dca5c16d2d594ea1decded",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E8%95%89%E5%9F%8E%E5%8C%BA/pangeliang.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E8%95%89%E5%9F%8E%E5%8C%BA/qixintian.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E8%95%89%E5%9F%8E%E5%8C%BA/youniantou.jpg",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.kmway.com%2Fupload%2Fresources%2Fimage%2F2016%2F09%2F01%2F426236_600x600.png&refer=http%3A%2F%2Fwww.kmway.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669901853&t=66a935a8a5467901336cb5d9fec6473d",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnewoss.zhulong.com%2Ftfs%2Fpic%2Fv1%2Ftfs%2FT1cDJTBXbv1RCvBVdK.jpg&refer=http%3A%2F%2Fnewoss.zhulong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669901853&t=f08ff80c1a7f69f39b9e8670531da82a",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.shejiben.com%2Fcase%2F2014%2F02%2F19%2F20140219004824-8df1188f.jpg&refer=http%3A%2F%2Fpic.shejiben.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669901853&t=fc155bb72ea6a4f66ef4d2deefe7d150"};
    private String[] jiaobuy = {"¥91/人","¥58/人","¥72/人","¥125/人","¥80/人","¥73/人","¥82/人","¥117/人"};
    private String [] jiaointroduce = {"日本料理 万达","西餐 红星美凯龙","肉蟹煲 万达","海鲜火锅 万达","四川火锅 蕉城区","火锅 蕉城区","川菜馆 红星美凯龙","新加坡菜 万达"};
    private String[] jiaopingfen = {"4.5分","3.8分","4.0分","4.1分","4.0分","4.7分","3.8分","3.6分"};
    private String[] jiaotime = {"10:00-13:00,16:00-21:30","11:00-22:00","10:30-21:00","10:00-次日05:00","10:00-14:00,16:30-次日01:30","16:30开始营业","10:00-次日02:00","10:00-次日02:00"};

    //古田县
    private String[] guname = {"又青的店","小二烤肉（古田店）","鲜境海鲜自助餐厅（古田中央广场店）","小村烤肉（古田店）","大家食府（城东店）","菲斯牛排自助","百年鱼庄（古田大世界店）","蛙太太（古田店）"};
    private  String[]  guicons = {"https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%8F%A4%E7%94%B0%E5%8E%BF/youqingdedian.jpg",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.hytzs.com%2Fupload%2F202007%2F1595211359249024.jpg&refer=http%3A%2F%2Fwww.hytzs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669902623&t=0aa6d459434868a04a00098cd30056c4",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.cddrzs.com%2Fuploadfile%2F2017%2F1014%2F20171014032106673.jpg&refer=http%3A%2F%2Fwww.cddrzs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669902623&t=4209e7c2858b7afbcf7fd3894e94c520",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F87bc0b533f08ea9d5013cd21e06023414e4e89d931031-Qq4k7l_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669902623&t=3862c3d8a33405b02cd77257941b19f1",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.hytzs.com%2Fupload%2F202007%2F1595211332140868.jpg&refer=http%3A%2F%2Fwww.hytzs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669902623&t=31bba3bcf53350bbf931f7b6591715ac",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fq_70%2Cc_zoom%2Cw_640%2Fimages%2F20181128%2Fb66acfc6aedf46029e0b91912dd22d7c.jpeg&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669902905&t=e3ff95ec902d405c53d11887b0add768",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0111bf57884b6b0000018c1b9ef07d.jpg%401280w_1l_2o_100sh.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669902950&t=35edec797536fcf4a3a35782359b4516",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20181123%2F907f828417304aefa706808d6ea676be.jpg&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669902990&t=d2f7342aac5e814387341e2fb0345a37"};
    private String[] gubuy = {"¥47/人","¥99/人","¥123/人","95/人","¥101/人","¥72/人","¥78/人","¥82/人"};
    private String [] guintroduce = {"西餐 古田县中心城区","日式烧烤/烤肉 古田县中心城区","自助餐 古田县中心城区","日式烧烤/烤肉 古田县中心城区","福建菜 古田县其他","西餐 古田县中心城区","福建菜 古田县中心城区","其他中餐 古田县中心城区"};
    private String[] gupingfen = {"3.4分","4.0分","3.2分","3.5分","4.0分","3.6分","3.6分","3.4分"};
    private String[] gutime = {"11:00-22:00","11:00-14:00,16:00-22:00","暂无营业时间","11:00-14:00,17:00-22:30","10:00-22:00","11:00-14:00,17:00-21:30","10:00-次日01:30","10:00-次日02:00"};

    //屏南县
    private String[] pingname = {"啊咋酒楼","壹号老宅","公园壹号中餐厅","阿纯餐馆","白水洋乡味楼酒店","熙泽路餐馆","芝根芝底披萨（屏南店）"};
    private  String[]  pingicons = {"https://img2.baidu.com/it/u=2648924671,179728156&fm=253&fmt=auto&app=138&f=JPEG?w=745&h=500",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.2m2j.com%2Fuploads%2Fgs%2Fcase%2Fdesigner00_873_09.jpg&refer=http%3A%2F%2Fwww.2m2j.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903263&t=15c6f29ba91bda3911f4333b45b8f13a",
            "https://img2.baidu.com/it/u=1211977788,2532940481&fm=253&fmt=auto&app=138&f=JPEG?w=943&h=487",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.86zsw.com%2Fpics%2F20150825114879257925.jpg&refer=http%3A%2F%2Fimg.86zsw.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903357&t=a60e687e2fce37e823734aedd7c5dc74",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fdpic.tiankong.com%2F8m%2Fg5%2FQJ7123795297.jpg&refer=http%3A%2F%2Fdpic.tiankong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903394&t=f918bb9127cdb359b6a459b3b26e17b0",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zx123.cn%2FResources%2Fzx123cn%2Fuploadfile%2F2017%2F0329%2F32186372c21c0999ba5e4704cf20a8d0.jpg&refer=http%3A%2F%2Fimg.zx123.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903422&t=6d521fa1e7b4b2091916266294d48317",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.to8to.com%2Fcase%2F2018%2F06%2F16%2F20180616111527-69cb78cd.jpg&refer=http%3A%2F%2Fpic.to8to.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903453&t=47f227409d0bfd7a78058d9e9fc8b577"};
    private String[] pingbuy = {"¥180/人","¥80/人","¥104/人","¥40/人","¥32/人","¥36/人","¥27/人"};
    private String [] pingintroduce = {"福建菜 屏南县中心城区","私房菜 屏南县中心城区","其他中餐 屏南县中心城区","其他美食 屏南县其他","私房菜 屏南县其他","福建菜 屏南县其他","披萨 屏南县中心城区"};
    private String[] pingpingfen = {"3.6分","3.7分","3.5分","3.6分","3.6分","3.3分","3.7分"};
    private String[] pingtime = {"11:00-22:00","11:00-14:00,16:00-22:00","暂无营业时间","11:00-14:00,17:00-22:30","10:00-22:00","11:00-14:00,17:00-21:30","10:00-次日01:30"};

    //周宁县
    private String[] zhouname = {"福鼎海鲜大排档（周宁县）","小龙坎火锅（周宁店）","姑奶奶厨坊（周宁店）","多味美生日蛋糕（步行街店）","四叶草咖啡酒吧","友味道药膳鸡（周宁店）","美优乐(名仕广场店)"};
    private  String[]  zhouicons = {"https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20170820%2F9c23ff0cc0024b829acb0e008905e179.jpeg&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903498&t=dd36e655afce44a60d2f50525224073e",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01b8305fd73dd511013fdcc7d40ceb.jpg%403000w_1l_2o_100sh.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903548&t=df7e819b5a70c7ec7a023f1dac15a518",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fqcloud.dpfile.com%2Fpc%2FPeybzdPGGTpE4rUopi2jaz9Gbxyi4shIJJyuYnn1ASJmE5cTlkEghg4rwc6lZ2zyjoJrvItByyS4HHaWdXyO_DrXIaWutJls2xCVbatkhjUNNiIYVnHvzugZCuBITtvjski7YaLlHpkrQUr5euoQrg.jpg&refer=http%3A%2F%2Fqcloud.dpfile.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903571&t=1635809782020a2273fa459ce8de1c99",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%91%A8%E5%AE%81%E5%8E%BF/duoweimeishengri.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%91%A8%E5%AE%81%E5%8E%BF/siyecaokafei.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%91%A8%E5%AE%81%E5%8E%BF/youweidao.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%91%A8%E5%AE%81%E5%8E%BF/youlemei.jpg"};
    private String[] zhoubuy = {"¥84/人","¥105/人","¥45/人","¥13/人","¥34/人","¥23/人","¥36/人"};
    private String [] zhouintroduce = {"海鲜 周宁县中心城区","四川火锅 周宁县中心城区","福建菜 周宁县中心城区","西餐 周宁县中心城区","西餐 周宁县中心城区","火锅 周宁县中心城区","西餐 周宁县中心城区"};
    private String[] zhoupingfen = {"3.7分","3.7分","3.6分","3.7分","3.7分","3.7分","3.4分"};
    private String[] zhoutime = {"10:00-13:30，17:00-23:30","10:30-次日00:30","10:30-13:30,16:30-20:30","9:00-21:00","10:00-23:00","16:00-次日01:00","09:00-24:00"};

    //寿宁县
    private String[] shouname = {"姑奶奶（寿宁店）","佳客来（胜利街店）","芝根芝底（寿宁店）","廊桥鱼庄（寿宁店）","沙拉斯（解放店）","重庆鸡公煲"};
    private  String[]  shouicons = {"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.qqjm.com%2F201908%2F01%2F16777d09eb9.png&refer=http%3A%2F%2Fimg.qqjm.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903571&t=9ae42e6356bd0f87bc5d2eefd89c41e9",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic2.to8to.com%2Fcase%2F2014%2F07%2F28%2F20140728144121-4cd559c1.jpg&refer=http%3A%2F%2Fpic2.to8to.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669904264&t=52830c16710560a187890e0f1298d675",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2F12247280.s21i.faiusr.com%2F2%2FABUIABACGAAgjdeR_wUoxrqYzgEw4BI4mAs%21700x700.jpg&refer=http%3A%2F%2F12247280.s21i.faiusr.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903673&t=b89a81d3bb76f21afd90e76027f2f5ff",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%AF%BF%E5%AE%81%E5%8E%BF/langqiaoyuzhuang.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%AF%BF%E5%AE%81%E5%8E%BF/shalasi.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E5%AF%BF%E5%AE%81%E5%8E%BF/jigongbao.jpg"};
    private String[] shoubuy = {"¥43/人","¥56/人","¥26/人","¥33/人","¥34/人","¥20/人"};
    private String [] shouintroduce = {"本帮江浙菜 寿宁县中心城区","自助餐 寿宁县中线城区","披萨 寿宁县中线城区","鱼火锅 寿宁县中心城区","快餐简餐 寿宁县中心城区","川菜馆 寿宁县中心城区"};
    private String[] shoupingfen = {"3.3分","3.5分","3.6分","3.6分","3.6分","3.5分"};
    private String[] shoutime = {"10:30-13:30，16:30-20:30","08:30-24:00","09:00-24:00","10:00-24:00","08:00-24:00","11:00-24:00"};

    //福安市
    private String[] funame = {"小咖私厨工坊（福安店）","牛道","御源府餐厅（福安店）","江滨酒楼","味觉种子（福安市）","邦维执匠·咖餐甜点（甜点店）","福台源自助火锅·烧烤"};
    private  String[]  fuicons = {"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fdpic.tiankong.com%2F8m%2Fg5%2FQJ7123795297.jpg&refer=http%3A%2F%2Fdpic.tiankong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903422&t=7d6def729ee3b9f345c46395659cf656",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201708%2F30%2F20170830163846_cAjfZ.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903548&t=fec2e38945f6f497bbd06b06a00da00b",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E5%AE%89%E5%B8%82/yuyuanfucanting.jpg",
            "https://img1.baidu.com/it/u=3662237316,2104661046&fm=253&fmt=auto&app=138&f=JPEG?w=857&h=500",
            "https://img2.baidu.com/it/u=1158199449,1937116701&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E5%AE%89%E5%B8%82/bangweizhijiang.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E5%AE%89%E5%B8%82/futaiyuanzizhu.jpg"};
    private String[] fubuy = {"¥54/人","¥100/人","¥75/人","¥129/人","¥45/人","¥88/人","¥115/人"};
    private String [] fuintroduce = {"西餐 冠后路","潮汕牛肉火锅 福安市中心城区","福建菜 福安市中心城区","其他美食 甘棠镇","特色菜 福安市中心城区","西餐 阳头广场","自助餐 福安市中心城区"};
    private String[] fupingfen = {"3.9分","3.4分","3.7分","3.8分","3.4分","4.1分","3.4分"};
    private String[] futime = {"11:00-22:00","16:00-次日02:00","11:00-13:30,16:30-21:00","08:00-13:30,16:00-20:00","17:00-次日02:00","11:00-23:30","16:00-23:30"};

    //柘荣县
    private String[] zhename = {"晓家碧芋","聚福楼私房菜","小富春饭店","东源农家菜","乐乐锅（柘荣店）","恒昌荣饭店","九香火锅（柳城南路店）"};
    private  String[]  zheicons = {"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.shejiben.com%2Fcase%2F2016%2F02%2F15%2F20160215120845-c8cea06b.jpg&refer=http%3A%2F%2Fpic1.shejiben.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903422&t=22e328b1af7b8bd7726417446ccaaa6f",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E6%9F%98%E8%8D%A3%E5%8E%BF/jufulou.jpg",
            "https://img0.baidu.com/it/u=701376769,2403496470&fm=253&fmt=auto&app=138&f=JPEG?w=499&h=334",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fseopic.699pic.com%2Fphoto%2F50044%2F7417.jpg_wh1200.jpg&refer=http%3A%2F%2Fseopic.699pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903422&t=ecc921cb50cdd571aad5b98a574f3815",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E6%9F%98%E8%8D%A3%E5%8E%BF/leleguo.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E6%9F%98%E8%8D%A3%E5%8E%BF/hengchangrong.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E6%9F%98%E8%8D%A3%E5%8E%BF/jiuxianghuoguo.jpg"};
    private String[] zhebuy = {"¥52/人","¥37/人","¥45/人","¥33/人","¥70/人","¥70/人","¥/40人"};
    private String [] zheintroduce = {"福建 柘荣县中心城区","其他中餐 柘荣县中心城区","其他中餐 柘荣县中心城区","农家菜 柘荣县中心城区","火锅 柘荣县中心城区","家常菜 柘荣县中心城区","火锅 柘荣县中心城区"};
    private String[] zhepingfen = {"3.7分","3.7分","3.7分","3.5分","3.5分","3.5分","3.5分"};
    private String[] zhetime = {"暂无营业时间","10:00-13:00,16:00-23:00","13:30-24:00","暂无营业时间","16:30-24:00","08:30-14:00,16:00-22:00","10:00-次日01:30"};

    //福鼎市
    private String[] fudingname = {"金九龙大酒店-中餐厅","老家饭庄","闲云居","三门里逍遥林","东源大酒店","老百货自助火锅城（人本超市店）","百威火锅城（古城西路店）","山水人家休闲山庄"};
    private  String[]  fudingicons = {"https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/jinjiulongdajiudain.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/laojiafangzhuang.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/xianyunju.jpg",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhappy.online.sh.cn%2Fimages%2Fattachement%2Fjpg%2Fsite1%2F20211105%2FIMG509a4c17ec4458609894080.jpg&refer=http%3A%2F%2Fhappy.online.sh.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669901853&t=b17298d1290f6de87693bf5473080002",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbkimg.cdn.bcebos.com%2Fpic%2F8718367adab44aedb9c6fd3db11c8701a08bfb5b&refer=http%3A%2F%2Fbkimg.cdn.bcebos.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669901853&t=58b5bb1df700b36afdf9e9b15d8148b2",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/laobaihuozizhuhuoguo.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E7%A6%8F%E9%BC%8E%E5%B8%82/baiweihuoguocheng.jpg",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbkimg.cdn.bcebos.com%2Fpic%2F1e30e924b899a9014c08a1eb28da1d7b02087af45f94&refer=http%3A%2F%2Fbkimg.cdn.bcebos.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669901853&t=34b0b4cb709fec3fcb39380b7215410f"};
    private String[] fudingbuy = {"¥100/人","¥25/人","¥40/人","¥65/人","¥162/人","¥42/人","¥47/人","¥50/人"};
    private String [] fudingintroduce = {"其他中餐 福鼎市中心城区","其他中餐 福鼎市中心城区","创意菜 福鼎市其他","其他中餐 福鼎市其他","福建菜 福鼎市中心城区","自助餐 福鼎市中心城区","火锅 福鼎中心城区","其他美食 福鼎市其他"};
    private String[] fudingpingfen = {"3.7分","3.4分","3.5分","3.4分","4.0分","3.6分","3.7分","3.7分"};
    private String[] fudingtime = {"11:00-22:00","11:00-14:00,16:00-22:00","暂无营业时间","11:00-14:00,17:00-22:30","10:00-22:00","11:00-14:00,17:00-21:30","10:00-次日01:30","10:00-次日01:30"};

    //霞浦县
    private String[] xianame = {"晨曦大酒店","环海大酒楼","鑫鑫饭店","台湾欣乐园","赵妈私房菜排挡","浦天酒店-中餐厅","人民公社（霞浦店）"};
    private  String[]  xiaicons = {"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fphoto.16pic.com%2F00%2F07%2F21%2F16pic_721042_b.jpg&refer=http%3A%2F%2Fphoto.16pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903992&t=7af8384f36ea861da73044ca5e9a06f1",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbkimg.cdn.bcebos.com%2Fpic%2Fb21bb051f81986185464465044ed2e738ad4e6c7&refer=http%3A%2F%2Fbkimg.cdn.bcebos.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669904265&t=25f2705c89378d8755f363f779e7b8f5",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.to8to.com%2Fcase%2F2016%2F12%2F25%2F20161225020123-850d8c3d.jpg&refer=http%3A%2F%2Fpic.to8to.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903836&t=9bbd5797496f1ee60d8f464cd9355e74",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fx3.tuozhe8.com%2Fattachment%2Fforum%2F201512%2F23%2F005742qxrfbvw6t8mbw6mf.jpg&refer=http%3A%2F%2Fx3.tuozhe8.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669903836&t=d7ba53ef529895b2b06dd717c4f0e0ec",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E9%9C%9E%E6%B5%A6%E5%8E%BF/zhaomapaidang.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E9%9C%9E%E6%B5%A6%E5%8E%BF/putianjiudian.jpg",
            "https://recycleimage.oss-cn-hangzhou.aliyuncs.com/%E7%BE%8E%E9%A3%9F/%E9%9C%9E%E6%B5%A6%E5%8E%BF/renminggongshe.jpg"};
    private String[] xiabuy = {"¥230/人","¥115/人","¥58/人","¥21/人","¥115/人","¥50/人","¥70/人"};
    private String [] xiaintroduce = {"福建菜 松城镇","福建菜 松城镇","其他中餐 霞浦县其他","其他中餐 松城镇","其他中餐 三沙镇","海鲜 松城镇","湘菜 松城镇"};
    private String[] xiapingfen = {"3.5分","3.8分","3.5分","4.0分","3.4分","3.7分","3.8分"};
    private String[] xiatime = {"11:00-22:00","11:00-14:00,16:00-22:00","暂无营业时间","11:00-14:00,17:00-22:30","10:00-22:00","11:00-14:00,17:00-21:30","10:00-次日01:30"};

    private String[] name1 = {};
    private String[] icons = {};
    private  String[] buy ={};
    private  String[] introduces = {};
    private String[] pingfen = {};
    private String[] time = {};
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
            pingfen = jiaopingfen;
            jiaotime = time;
        }
        if(name_receive1.equals("古田县")){
            name1 = guname;
            icons = guicons;
            buy =gubuy;
            introduces = guintroduce;
            pingfen = gupingfen;
            jiaotime = gutime;
        }
        if(name_receive1.equals("屏南县")){
            name1 = pingname;
            icons = pingicons;
            buy =pingbuy;
            introduces = pingintroduce;
            pingfen = pingpingfen;
            jiaotime = pingtime;
        }
        if(name_receive1.equals("周宁县")){
            name1 = zhouname;
            icons = zhouicons;
            buy =zhoubuy;
            introduces = zhouintroduce;
            pingfen = zhoupingfen;
            jiaotime = zhoutime;
        }
        if(name_receive1.equals("寿宁县")){
            name1 = shouname;
            icons = shouicons;
            buy =shoubuy;
            introduces = shouintroduce;
            pingfen = shoupingfen;
            jiaotime = shoutime;
        }
        if(name_receive1.equals("福安市")){
            name1 = funame;
            icons = fuicons;
            buy =fubuy;
            introduces = fuintroduce;
            pingfen = fupingfen;
            jiaotime = futime;
        }
        if(name_receive1.equals("柘荣县")){
            name1 = zhename;
            icons = zheicons;
            buy =zhebuy;
            introduces = zheintroduce;
            pingfen = zhepingfen;
            jiaotime = zhetime;
        }
        if(name_receive1.equals("福鼎市")){
            name1 = fudingname;
            icons = fudingicons;
            buy =fudingbuy;
            introduces = fudingintroduce;
            pingfen = fudingpingfen;
            jiaotime = fudingtime;
        }
        if(name_receive1.equals("霞浦县")){
            name1 = xianame;
            icons = xiaicons;
            buy =xiabuy;
            introduces = xiaintroduce;
            pingfen = xiapingfen;
            jiaotime = xiatime;
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
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.twofragment,parent,false));
            return holder   ;
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            Glide.with(getActivity()).load(icons[position]).into(holder.iv);
            holder.nameone.setText(name1[position]);
            holder.buy.setText(buy[position]);
            holder.troduce.setText(introduces[position]);
            holder.pingfen.setText(pingfen[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(),MeishiActivity.class);
                    intent.putExtra("detail_iv",icons[position]);
                    intent.putExtra("detail_name1",name1[position]);
                    intent.putExtra("detail_buy",buy[position]);
                    intent.putExtra("name",name_receive1);
                    intent.putExtra("detail_introduce",introduces[position]);
                    intent.putExtra("detail_pingfen",pingfen[position]);

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
            TextView pingfen;
            public MyViewHolder(View view){
                super(view);
                nameone = (TextView)view.findViewById(R.id.name);
                iv = (ImageView)view.findViewById(R.id.iv);
                buy=(TextView)view.findViewById(R.id.buy);
                troduce =(TextView) view.findViewById(R.id.ziliao);
                pingfen = view.findViewById(R.id.pingfen);
            }
        }
    }

}
