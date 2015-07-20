package npp.utils;
// Import required java libraries
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import npp.dto.EventDto;
import npp.dto.MessageDto;
import npp.spec.service.ScheduleService;

import com.google.gson.Gson;

// Extend HttpServlet class
public class SendMail extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ScheduleService scheduleService;
	private List<EventDto> notifyList = null;
	private final String username = "nppillar@gmail.com";
	private final String password = "stmspass1";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();

		List<EventDto> list = scheduleService.getNotificationEvents();
		List<EventDto> newEvents = new ArrayList<EventDto>();
		List<MessageDto> messages = new ArrayList<MessageDto>();
		StringBuffer sb = new StringBuffer();
		boolean hasNewEvent = false;
		if (notifyList == null)
			notifyList = new ArrayList<EventDto>();
		for (EventDto dto : list) {
			if (!notifyList.contains(dto)) {
				hasNewEvent = true;
				newEvents.add(dto);
				sb.append(dto.getFormatDate() + " " + dto.getTitle()).append("\n");
				if(dto.isEmailNotification())
					sendEmail(dto);
			}
		}
		if (hasNewEvent) {
			MessageDto message = new MessageDto();
			message.setSummary("Notification Event");
			message.setDetail(sb.toString());
			message.setSeverity("info");
			messages.add(message);
			Gson gson = new Gson();
			out.println(gson.toJson(messages));
			// sessionManager.addGlobalMessageFatal("here", "here");
			out.close();
		}
		notifyList = list;
	}

	private void sendEmail(EventDto dto) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("nppillar@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(dto.getEmail()));
			message.setSubject("Event Notification: " + dto.getFormatDate() + " " + dto.getTitle());
			System.out.println("note:" + dto.getNote());
			message.setText(dto.getNote());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public void destroy() {
		// do nothing. -by www.yiibai.com
	}
}