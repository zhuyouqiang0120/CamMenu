package com.zens.cam.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @2016年3月1日 下午3:09:58
 *
 */

public class DBCamUtils {
	// private static final String tbl_column = "tbl_column";
	private static final String tbl_userinfo = "t_menu";

	public List<Record> getCamMenu(String ip) {
		String sql = "select * from " +tbl_userinfo+" where device = '"+ip+"'";
		return Db.find(sql);
	}
	
	public static void main(String[] args) throws IOException {
		String result = "";
        BufferedReader in = null;
		URL realUrl = new URL("http://172.16.244.103/u1/GetFolderContents?client=8002003789220717&account=7543109&assetId=GDZX0920180327000005&folderType=1&resultType=json");
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("gw.httpproxy.local", 80));
		URLConnection connection = realUrl.openConnection(proxy);
		System.out.println(proxy.toString());
		connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.connect();
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        
        System.out.println(result);

	}
}
