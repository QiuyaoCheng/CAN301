package cn.itcast.recycleview;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JiudiandingdanxiangqingActivity extends AppCompatActivity {
    String title;
    String ziliao;
    String buy;
     String introduce;
    int a =1;
    Button open;
    private DialogOrderTypeFragment mFragment2=new DialogOrderTypeFragment();
    private CommonDialog myDialog;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiudiandingdanxiangqing);
        Intent intent = getIntent();
        title = intent.getStringExtra("detail_titles");
        ziliao = intent.getStringExtra("detail_prices");
        buy = intent.getStringExtra("detail_buys");
        introduce = intent.getStringExtra("detail_introduce");
        TextView name = findViewById(R.id.name);
        name.setText(title);

        TextView prices = findViewById(R.id.introduce);
        prices.setText(introduce+"  "+ziliao);

        TextView buys = findViewById(R.id.buy);
        buys.setText(buy);
        ImageView fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("two",a);
                intent1.setClass(JiudiandingdanxiangqingActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });


        button = (Button) findViewById(R.id.tuiding);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialog=new CommonDialog(JiudiandingdanxiangqingActivity.this,R.style.MyDialog);
                myDialog.setTitle("提示！");
                myDialog.setMessage("提示：您确定要取消订单吗！");

                myDialog.setYesOnclickListener("确定", new CommonDialog.onYesOnclickListener() {
                    @Override
                    public void onYesOnclick() {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    connect.shanchujiudian(title,introduce);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        Toast.makeText(getApplicationContext(),"恭喜你！订单取消成功",Toast.LENGTH_LONG).show();
                        myDialog.dismiss();
                        Intent i = new Intent();
                        i.putExtra("two",a);
                        i.setClass(JiudiandingdanxiangqingActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                myDialog.setNoOnclickListener("取消", new CommonDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        Toast.makeText(getApplicationContext(),"很抱歉！订单取消失败",Toast.LENGTH_LONG).show();
                        myDialog.dismiss();
                    }
                });
                myDialog.show();
            }
        });

        open=findViewById(R.id.daohang);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment2.show(getFragmentManager(), "android");
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
            Toast.makeText(JiudiandingdanxiangqingActivity.this, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
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
                    "&keywords=" +title+
                    "&dev=0");

            intent.setData(uri);

            //启动该页面即可
            startActivity(intent);
        } else {
            Toast.makeText(JiudiandingdanxiangqingActivity.this, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
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