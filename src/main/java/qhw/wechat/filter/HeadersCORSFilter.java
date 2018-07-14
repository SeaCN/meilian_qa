package qhw.wechat.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HeadersCORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setHeader("Access-Control-Allow-Origin", "http://web.devqz.club");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		HttpServletRequest hrequest = (HttpServletRequest)request;
		String path = hrequest.getServletPath();
		String to = hrequest.getParameter("to");
		if ("/meten/getCode".endsWith(path) || "/meten/index".endsWith(path) || "/meten/redirect_uri".endsWith(path)
				|| "/meten/registPage".endsWith(path) || "/meten/regist".endsWith(path)) {
			chain.doFilter(request, servletResponse);
		} else {	
			chain.doFilter(request, servletResponse);
		}
	}

	@Override
	public void destroy() {

	}
}