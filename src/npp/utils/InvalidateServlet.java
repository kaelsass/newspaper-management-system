package npp.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InvalidateServlet extends HttpServlet {


	/**
	 *
	 */
	private static final long serialVersionUID = -3336343583840666285L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gbk");
		request.setCharacterEncoding("gbk");
		PrintWriter out = response.getWriter();
		HttpSession session =request.getSession(); //得到session对象
		session.invalidate();                      //注销session  使其失效
		//然后跳转到登录页面
		System.out.println("log out!");
		response.sendRedirect("login.jsf");    //登录成功 跳入success.jsp

		//request.getRequestDispatcher("login.jsf").forward(request, response);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


	}

}
