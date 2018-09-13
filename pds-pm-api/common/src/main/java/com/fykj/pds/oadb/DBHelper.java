package com.fykj.pds.oadb;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
  
public class DBHelper {  
    public static final String url = "jdbc:mysql://172.16.160.34:3336/td_oa";
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "oauser";  
    public static final String password = "XoCAd5Wacu75";  
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
  
    public DBHelper(String sql,List<Object> params) {  
        try {  
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
            if(params!=null && params.size()>0){
        	  for (int i = 0; i < params.size(); i++) {
        		  pst.setObject(i+1, params.get(i));
        	  }
        	  
            }
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    } 
    
    public static void main(String[] args) throws SQLException {
    	String s="8,9";
    	DBHelper dbHelper=new DBHelper("select d.DEPT_ID,d.DEPT_NAME,d.DEPT_PARENT from department d where d.DEPT_ID in("+s+")", null);
    	ResultSet ret = dbHelper.pst.executeQuery();
    	 while (ret.next()) { 
    		 System.out.println(ret.getString(1));
         }//显示数据  
    }
}  