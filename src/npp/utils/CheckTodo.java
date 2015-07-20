package npp.utils;
// Import required java libraries
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import npp.dto.MessageDto;

import com.google.gson.Gson;

// Extend HttpServlet class
public class CheckTodo extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
//	@Inject
//	private SessionManager sessionManager;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();

		Gson gson = new Gson();
		List<MessageDto> messages = new ArrayList<MessageDto>();
		MessageDto message = new MessageDto();
		message.setSummary("summary");
		message.setDetail("detail");
		message.setSeverity("info");
		messages.add(message);

		System.out.println(gson.toJson(message));

		out.println(gson.toJson(message));
		// sessionManager.addGlobalMessageFatal("here", "here");
		out.close();
	}

	public void destroy() {
		// do nothing. -by www.yiibai.com
	}
}