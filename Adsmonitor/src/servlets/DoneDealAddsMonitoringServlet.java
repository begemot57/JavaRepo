package servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.MonitoringJsonObject;
import controllers.AddsMonitorController;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/DoneDealAddsMonitoringServlet")
public class DoneDealAddsMonitoringServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String SUPER_USER_EMAIL = "leoio1953@gmail.com";

	/**
	 * Default constructor.
	 */
	public DoneDealAddsMonitoringServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns all current monitors.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userEmail = request.getParameter("user_email");
		boolean showAll = (userEmail.equals(SUPER_USER_EMAIL));
		List<MonitoringJsonObject> monitors = AddsMonitorController.getInstance().getCurrentMonitorsFromFile();
		if (!showAll)
			monitors = filterMonitors(monitors, userEmail);
		Collections.sort(monitors);
		String json = new Gson().toJson(monitors);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	/**
	 * Handles Add, Stop, Start, Stop All, Start All and returns all monitors.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userEmail = request.getParameter("user_email");
		boolean showAll = (userEmail.equals(SUPER_USER_EMAIL));
		AddsMonitorController controller = AddsMonitorController.getInstance();
		String value = request.getParameter("value");
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		String email = request.getParameter("email");
		String frequency = request.getParameter("frequency");
		if (value.equals("stop")) {
			controller.stopMonitor(name, url, email, frequency);
		} else if (value.equals("start")) {
			controller.startMonitor(name, url, email, frequency);
		} else if (value.equals("remove")) {
			controller.removeMonitor(name, url, email);
		}
		List<MonitoringJsonObject> monitors = controller.getCurrentMonitorsFromFile();
		if (!showAll)
			monitors = filterMonitors(monitors, userEmail);
		Collections.sort(monitors);
		String json = new Gson().toJson(monitors);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	List<MonitoringJsonObject> filterMonitors(List<MonitoringJsonObject> monitors, String userEmail) {
		for (Iterator<MonitoringJsonObject> iterator = monitors.iterator(); iterator.hasNext();) {
			MonitoringJsonObject monitoringJsonObject = (MonitoringJsonObject) iterator.next();
			if (!monitoringJsonObject.getEmail().equals(userEmail))
				iterator.remove();
		}
		return monitors;
	}

}
