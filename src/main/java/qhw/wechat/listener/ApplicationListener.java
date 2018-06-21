package qhw.wechat.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class ApplicationListener extends ContextLoaderListener {
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		String webAppRootKey = event.getServletContext().getRealPath("/");  
        System.setProperty("webAppRootKey" , webAppRootKey);  
		super.contextInitialized(event);
	}
}
