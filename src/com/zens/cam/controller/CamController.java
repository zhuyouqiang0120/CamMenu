package com.zens.cam.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.zens.cam.db.DBCamUtils;


/**
 * 
 * @author zyq
 * @e-mail zhuyq@zensvision.com
 * @date 2016年6月23日 上午10:11:18
 */

public class CamController extends Controller {

	DBCamUtils db = new DBCamUtils();

	public void getData() throws IOException, Exception {
		String device = getPara("device");
		List<Record> records = db.getCamMenu(device);
		String data = records.get(0).getStr("date");
		data = data.replace("'", "\"");
		data = "{"+data+"}";
		
		if(getPara("callback") == null){
			renderJson(data);
		}else{
			renderJavascript(getPara("callback") + "(" + data + ")");
		}
	}

	public void startWebSocket() throws IOException, InterruptedException {
		int port = 8080;
        ChatServer s = new ChatServer("localhost", port );
        s.start();
        System.out.println( "ChatServer started on port: " + s.getPort() );

        BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
        while ( true ) {
            String in = sysin.readLine();
            s.sendToAll( in );
        }
	}
}
