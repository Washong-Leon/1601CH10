package cn.bdqn.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigManager {

	/**
	 * 静态属性
	 * 内存中只会分配一个空间
	 *
	 */
	private static Properties properties;

	private ConfigManager(){
		properties=new Properties();
		InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream("database.properties");
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static ConfigManager getInstance(){

		return ConfigManagerHelper.configManager;
	}

	public String getValue(String key){
		return properties.getProperty(key);
	}

	static class ConfigManagerHelper{
		private static ConfigManager configManager= new ConfigManager();
	}
}
