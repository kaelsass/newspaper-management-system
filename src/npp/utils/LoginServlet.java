package npp.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import npp.spec.service.LoginService;


public class LoginServlet extends HttpServlet {


	/**
	 *
	 */
	private static final long serialVersionUID = -1084837795168875201L;


	@Inject
	private LoginService loginService;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if(loginService.isValid(username, password))
		{
			HttpSession session = request.getSession();
			session.setAttribute("username", username);  //用户登录加入到session中
			session.setAttribute("role", loginService.getRole(username));
			response.sendRedirect("faces/employee/employee.jsf");    //登录成功 跳入success.jsp
		}
		else
		{
			response.sendRedirect("error.jsf");
		}

		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
