package com.huai.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huai.utils.RoleUtil;

public class LoginFilter implements Filter{

	protected FilterConfig filterConfig = null;
	
	@Override
	public void destroy() {
		filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		String path = req.getRequestURI();
		
		System.out.println("path = "+path);
		
		// 关于登录的都无需过滤
		if (path.endsWith(".css")||
				path.indexOf("images") > -1||
				path.endsWith(".js")) {
			chain.doFilter(request, response);
			return;
		}
		if (path.indexOf("login.html") > -1||
				path.indexOf("index.html") > -1) {
			chain.doFilter(request, response);
			return;
		}
		if(path.indexOf("login") > -1){
			chain.doFilter(request, response);
			return;
		}
		
		Object teacher = req.getSession().getAttribute(RoleUtil.TEACHER_ROLE_NAME);
		if(teacher == null){
			resp.sendRedirect(request.getServletContext().getContextPath()+"/html/index.html");
			return ;
		}
		
		
		System.out.println("--->teacher loginning filter<----");
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

}
