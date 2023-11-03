package cn.itcast.recycleview;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connect {
    public static Connection getConnection(String dbName) throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //加载驱动
            String ip = "rm-bp1676p33896qdx8r7o.mysql.rds.aliyuncs.com";
            conn = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":3306/" + dbName,
                    "guanli", "QQ456120242zqy@");//dbName是数据库名
            dengluActivity.conn_on = 1;//用于向主函数传参，判断连接是否成功
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            dengluActivity.conn_on = 2;//用于向主函数传参，判断连接是否成功
        }
        return conn;//返回Connection型变量conn用于后续连接
    }

    public static int insertIntoData(final String username, final String password,final  String name) throws SQLException {//增加数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "insert INTO login (username,password,phonename)VALUES('" + username + "','" + password + "','" + name + "')";//把用户名和密码插入到数据库中
        return stmt.executeUpdate(sql);
        //执行DML语句，返回受影响的记录条数
    }

    public static String querycol(final String id) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs = stmt.executeQuery(
                "select password from login where username='" + id + "'");//从数据库中查询用户名对应的密码并返回
        rs.first();
        a = rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }

    public static String querycoll(final String id) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs = stmt.executeQuery(
                "select username from login where username='" + id + "'");//从数据库中查询用户名对应的密码并返回
        rs.first();
        a = rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }

    public static int updateData(final String username, final String password) throws SQLException {//修改数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "update login set password='" + password + "' where username='" + username + "'";//修改的sql语句
        return stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
    }

    public static int delete(final String username) throws SQLException {   //删除数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "DELETE FROM login WHERE username='" + username + "'"; // 写删除的SQL语句
        return stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
    }

    public static String reachone(final int id) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs = stmt.executeQuery(
                "select phon from phone where id='" + id + "'");
        rs.first();
        a = rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }
    public static String reachtwo(final int id) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs = stmt.executeQuery(
                "select judge from phone where id='" + id + "'");
        rs.first();
        a = rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }
    public static int 	reviseone(final int id, final String phon) throws SQLException {//修改数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "update phone set phon='" + phon + "' where id='" + id + "'";//修改的sql语句
        return stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
    }
    public static int 	revistwo(final int id, final int judge) throws SQLException {//修改数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "update phone set judge='" + judge + "' where id='" + id + "'";//修改的sql语句
        return stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
    }

    public static int create(final String name, final String ziliao,final String  buy) throws SQLException {//增加数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "insert INTO ziliao (nameone,introduces,buy)VALUES('" + name + "','" + ziliao + "','" + buy + "')";//把用户名和密码插入到数据库中
        return stmt.executeUpdate(sql);
        //执行DML语句，返回受影响的记录条数
    }
//    public static String namequery(final int id) throws SQLException {//读取某一行
//        String a;
//        //加载数据库驱动
//        Connection conn = null;
//        conn = getConnection("person");//异步连接  同步运行会出现空指针
//        //使用DriverManager获取数据库连接
//        Statement stmt = conn.createStatement();
//        //使用Connection来创建一个Statment对象
//        ResultSet rs =  stmt.executeQuery("select nameone from ziliao where id='" + id + "'");
//        rs.first();
//        a = rs.getString(1);
//        rs.close();
//        return a;
//    //把查询结果输出来
//        }
    public static String[] namequeryone() throws SQLException {//读取某一行
            String a;
            //加载数据库驱动
            Connection conn = null;
            conn = getConnection("person");//异步连接  同步运行会出现空指针
            //使用DriverManager获取数据库连接
            Statement stmt = conn.createStatement();
            //使用Connection来创建一个Statment对象
            ResultSet rs =  stmt.executeQuery("select nameone from ziliao ");
            rs.last(); //移到最后一行
            int rowCount = rs.getRow(); //得到当前行号，也就是记录数
            rs.beforeFirst(); //还要用到记录集，就把指针再移到初始化的位置
            String[] array=new String[rowCount];
            int i = 0;
            while(rs.next()){
                String uname = rs.getString("nameone");
                array[i] = uname;
                i++;
            }
        return array;
        //把查询结果输出来
    }
    public static String[] namequerytwo() throws SQLException {//读取某一行
        String a;
        //加载数据库驱动
        Connection conn = null;
        conn = getConnection("person");//异步连接  同步运行会出现空指针
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs =  stmt.executeQuery("select introduces from ziliao ");
        rs.last(); //移到最后一行
        int rowCount = rs.getRow(); //得到当前行号，也就是记录数
        rs.beforeFirst(); //还要用到记录集，就把指针再移到初始化的位置
        String[] array=new String[rowCount];
        int i = 0;
        while(rs.next()){
            String uname = rs.getString("introduces");
            array[i] = uname;
            i++;
        }
        return array;
        //把查询结果输出来
    }
    public static String[] namequerythird() throws SQLException {//读取某一行
        String a;
        //加载数据库驱动
        Connection conn = null;
        conn = getConnection("person");//异步连接  同步运行会出现空指针
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs =  stmt.executeQuery("select buy from ziliao ");
        rs.last(); //移到最后一行
        int rowCount = rs.getRow(); //得到当前行号，也就是记录数
        rs.beforeFirst(); //还要用到记录集，就把指针再移到初始化的位置
        String[] array=new String[rowCount];
        int i = 0;
        while(rs.next()){
            String uname = rs.getString("buy");
            array[i] = uname;
            i++;
        }
        return array;
        //把查询结果输出来
    }
    public static String[] dingdianreach() throws SQLException{
        Connection conn = null;
        conn = getConnection("person");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select didian from quyu");
        rs.last();
        int rowCount = rs.getRow();
        rs.beforeFirst();
        String[] array = new String[rowCount];
        int i =0;
        while (rs.next()){
            String uname = rs.getString("didian");
            array[i] = uname;
            i++;
        }
        return array;
    }
    public static int 	dizhione(final int id, final String name) throws SQLException {//修改数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "update dizhi set name='" + name + "' where id='" + id + "'";//修改的sql语句
        return stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
    }
    public static String dizhiname(final int id) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs = stmt.executeQuery(
                "select name from dizhi where id='" + id + "'");
        rs.first();
        a = rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }
    public static int deleteone(final String username) throws SQLException {   //删除数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "DELETE FROM ziliao WHERE nameone='" + username + "'"; // 写删除的SQL语句
        return stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
    }
    public static String reachthird(final String id) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs = stmt.executeQuery(
                "select phonename from login where username='" + id + "'");
        rs.first();
        a = rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }
    public static int xiugainingcheng(final String username, final String phonename) throws SQLException {//修改数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "update login set phonename='" + phonename + "' where username='" + username + "'";//修改的sql语句
        return stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
    }
    public static int xiugaishoujihao(final String username, final String password) throws SQLException {//修改数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "update login set username='" + password + "' where username='" + username + "'";//修改的sql语句
        return stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
    }

    public static int xiugaiyouxiang(final String username, final String password) throws SQLException {//修改数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "update login set phoneyouxiang='" + password + "' where username='" + username + "'";//修改的sql语句
        return stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
    }
    public static String reachform(final String id) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs = stmt.executeQuery(
                "select phoneyouxiang from login where username='" + id + "'");
        rs.first();
        a = rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }
    public static int jiudiancreate(final String name, final String name_type,final  String name_introduce,final String name_buy,final int numble) throws SQLException {//增加数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "insert INTO jiudian (name,name_type,name_introduce,name_buy,numble)VALUES('" + name + "','" + name_type + "','" + name_introduce + "','" + name_buy + "','" + numble + "')";//把用户名和密码插入到数据库中
        return stmt.executeUpdate(sql);
        //执行DML语句，返回受影响的记录条数
    }
    public static String jiudianreachone(final String id) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs = stmt.executeQuery(
                "select name from login where name='" + id + "'");
        rs.first();
        a = rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }
    public static String jiudianreachtwo(final String id) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs = stmt.executeQuery(
                "select name_type from jiudian where name='" + id + "'");
        rs.first();
        a = rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }
//    public static int jiudianxiugai(final String name, final String numble) throws SQLException {//修改数据
//        Connection conn = null;
//        conn = getConnection("person");
//        //使用DriverManager获取数据库连接
//        Statement stmt = conn.createStatement();
//        //使用Connection来创建一个Statment对象
//        String sql = "update jiudian set numble='" + numble + "' where name='" + name + "'";//修改的sql语句
//        return stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
//    }
    public static String jiudianreachthree(final String id) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs = stmt.executeQuery(
                "select numble from jiudian where name='" + id + "'");
        rs.first();
        a = rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }
    public static String[] jiudianqueryone() throws SQLException {//读取某一行
        String a;
        //加载数据库驱动
        Connection conn = null;
        conn = getConnection("person");//异步连接  同步运行会出现空指针
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs =  stmt.executeQuery("select name from jiudian ");
        rs.last(); //移到最后一行
        int rowCount = rs.getRow(); //得到当前行号，也就是记录数
        rs.beforeFirst(); //还要用到记录集，就把指针再移到初始化的位置
        String[] array=new String[rowCount];
        int i = 0;
        while(rs.next()){
            String uname = rs.getString("name");
            array[i] = uname;
            i++;
        }
        return array;
        //把查询结果输出来
    }
    public static String[] jiudianquerytwo() throws SQLException {//读取某一行
        String a;
        //加载数据库驱动
        Connection conn = null;
        conn = getConnection("person");//异步连接  同步运行会出现空指针
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs =  stmt.executeQuery("select name_type from jiudian ");
        rs.last(); //移到最后一行
        int rowCount = rs.getRow(); //得到当前行号，也就是记录数
        rs.beforeFirst(); //还要用到记录集，就把指针再移到初始化的位置
        String[] array=new String[rowCount];
        int i = 0;
        while(rs.next()){
            String uname = rs.getString("name_type");
            array[i] = uname;
            i++;
        }
        return array;
        //把查询结果输出来
    }
    public static String[] jiudianquerythree() throws SQLException {//读取某一行
        String a;
        //加载数据库驱动
        Connection conn = null;
        conn = getConnection("person");//异步连接  同步运行会出现空指针
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs =  stmt.executeQuery("select name_introduce from jiudian ");
        rs.last(); //移到最后一行
        int rowCount = rs.getRow(); //得到当前行号，也就是记录数
        rs.beforeFirst(); //还要用到记录集，就把指针再移到初始化的位置
        String[] array=new String[rowCount];
        int i = 0;
        while(rs.next()){
            String uname = rs.getString("name_introduce");
            array[i] = uname;
            i++;
        }
        return array;
        //把查询结果输出来
    }
    public static String[] jiudianqueryfour() throws SQLException {//读取某一行
        String a;
        //加载数据库驱动
        Connection conn = null;
        conn = getConnection("person");//异步连接  同步运行会出现空指针
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs =  stmt.executeQuery("select name_buy from jiudian ");
        rs.last(); //移到最后一行
        int rowCount = rs.getRow(); //得到当前行号，也就是记录数
        rs.beforeFirst(); //还要用到记录集，就把指针再移到初始化的位置
        String[] array=new String[rowCount];
        int i = 0;
        while(rs.next()){
            String uname = rs.getString("name_buy");
            array[i] = uname;
            i++;
        }
        return array;
        //把查询结果输出来
    }
    public static int shanchujiudian(final String name,final String name_type ) throws SQLException {   //删除数据
        Connection conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "DELETE FROM jiudian WHERE name='" + name + "'and name_type='" + name_type + "'";// 写删除的SQL语句
        return stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
    }
//    public static String[] tuijianone() throws SQLException {//读取全部数据
//        String a;
//        //加载数据库驱动
//        Connection conn = null;
//        conn = getConnection("person");//异步连接  同步运行会出现空指针
//        //使用DriverManager获取数据库连接
//        Statement stmt = conn.createStatement();
//        //使用Connection来创建一个Statment对象
//        ResultSet rs =  stmt.executeQuery("select fuding from tuijian ");
//        rs.last(); //移到最后一行
//        int rowCount = rs.getRow(); //得到当前行号，也就是记录数
//        rs.beforeFirst(); //还要用到记录集，就把指针再移到初始化的位置
//        String[] array=new String[rowCount];
//        int i = 0;
//        while(rs.next()){
//            String uname = rs.getString("fuding");
//            array[i] = uname;
//            i++;
//        }
//        return array;
//        //把查询结果输出来
//    }

//    public static String[] tuijiantwo() throws SQLException {//读取全部数据
//        String a;
//        //加载数据库驱动
//        Connection conn = null;
//        conn = getConnection("person");//异步连接  同步运行会出现空指针
//        //使用DriverManager获取数据库连接
//        Statement stmt = conn.createStatement();
//        //使用Connection来创建一个Statment对象
//        ResultSet rs =  stmt.executeQuery("select fudingoicons from tuijian ");
//        rs.last(); //移到最后一行
//        int rowCount = rs.getRow(); //得到当前行号，也就是记录数
//        rs.beforeFirst(); //还要用到记录集，就把指针再移到初始化的位置
//        String[] array=new String[rowCount];
//        int i = 0;
//        while(rs.next()){
//            String uname = rs.getString("fudingoicons");
//            array[i] = uname;
//            i++;
//        }
//        return array;
//        //把查询结果输出来
//    }
//    public static String[] tuijianthree() throws SQLException {//读取全部数据
//        String a;
//        //加载数据库驱动
//        Connection conn = null;
//        conn = getConnection("person");//异步连接  同步运行会出现空指针
//        //使用DriverManager获取数据库连接
//        Statement stmt = conn.createStatement();
//        //使用Connection来创建一个Statment对象
//        ResultSet rs =  stmt.executeQuery("select fudingbuy from tuijian ");
//        rs.last(); //移到最后一行
//        int rowCount = rs.getRow(); //得到当前行号，也就是记录数
//        rs.beforeFirst(); //还要用到记录集，就把指针再移到初始化的位置
//        String[] array=new String[rowCount];
//        int i = 0;
//        while(rs.next()){
//            String uname = rs.getString("fudingbuy");
//            array[i] = uname;
//            i++;
//        }
//        return array;
//        //把查询结果输出来
//    }
//    public static String[] tuijianfour() throws SQLException {//读取全部数据
//        String a;
//        //加载数据库驱动
//        Connection conn = null;
//        conn = getConnection("person");//异步连接  同步运行会出现空指针
//        //使用DriverManager获取数据库连接
//        Statement stmt = conn.createStatement();
//        //使用Connection来创建一个Statment对象
//        ResultSet rs =  stmt.executeQuery("select fudingpingfen from tuijian ");
//        rs.last(); //移到最后一行
//        int rowCount = rs.getRow(); //得到当前行号，也就是记录数
//        rs.beforeFirst(); //还要用到记录集，就把指针再移到初始化的位置
//        String[] array=new String[rowCount];
//        int i = 0;
//        while(rs.next()){
//            String uname = rs.getString("fudingpingfen");
//            array[i] = uname;
//            i++;
//        }
//        return array;
//        //把查询结果输出来
//    }
//    public static String[] tuijianfive() throws SQLException {//读取全部数据
//        String a;
//        //加载数据库驱动
//        Connection conn = null;
//        conn = getConnection("person");//异步连接  同步运行会出现空指针
//        //使用DriverManager获取数据库连接
//        Statement stmt = conn.createStatement();
//        //使用Connection来创建一个Statment对象
//        ResultSet rs =  stmt.executeQuery("select fudingintroduce from tuijian ");
//        rs.last(); //移到最后一行
//        int rowCount = rs.getRow(); //得到当前行号，也就是记录数
//        rs.beforeFirst(); //还要用到记录集，就把指针再移到初始化的位置
//        String[] array=new String[rowCount];
//        int i = 0;
//        while(rs.next()){
//            String uname = rs.getString("fudingintroduce");
//            array[i] = uname;
//            i++;
//        }
//        return array;
//        //把查询结果输出来
//    }
}
