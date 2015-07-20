package npp.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SalesFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) servletRequest;
	    HttpServletResponse res = (HttpServletResponse) servletResponse;
		HttpSession session = req.getSession();

		String username = (String)session.getAttribute("username");
		String role = (String)session.getAttribute("role");

		if (username != null && !username.equals("")) {
			if(role != null && !role.equals("") && (role.equals("admin") || role.equals("salesman")))
			// 如果现在存在了session，则请求向下继续传递
				filterChain.doFilter(servletRequest, servletResponse);
			else
				res.sendRedirect("../../accessDenied.jsf");
		} else {
			// 跳转到提示登陆页面
			res.sendRedirect("../../error.jsf");
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
