package com.zens.cam.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.plugin.quartz.QuartzPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.zens.cam.controller.CamController;

/**
 * 
 * zyq zhuyq@zensvision.com 2016年3月18日 上午11:16:29
 */
public class CamConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
	}

	// 路由
	@Override
	public void configRoute(Routes me) {

		me.add("/", CamController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		loadPropertyFile("jdbc.properties"); // load配置文件
		C3p0Plugin c3p0 = new C3p0Plugin(getProperty("jdbc.url"), getProperty("jdbc.username"),
				getProperty("jdbc.password"));
		me.add(c3p0); // 添加插件

		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0);
		me.add(arp);
		arp.setDialect(new MysqlDialect());

	}

	@Override
	public void configInterceptor(Interceptors me) {

	}

	@Override
	public void configHandler(Handlers me) {
	}

	
	
}
