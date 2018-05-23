package qhw.wechat.util;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyPlaceholder extends
		PropertyPlaceholderConfigurer {

	private static Map<String, String> tokenMap;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		tokenMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			tokenMap.put(keyStr, value);
		}
	}

	public static String getContextProperty(String name) {
		return tokenMap.get(name);
	}

	public static void update(String key, String value){
		tokenMap.put(key, value);
	}
}