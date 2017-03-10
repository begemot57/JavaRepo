package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.Logger;
import beans.AdsMonitor;
import beans.MonitoringJsonObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Front end page:
 * 
 * TextFields (non editable) Buttons Name, URL, email, frequency, status | Stop,
 * Start, Remove
 * 
 * Buttons: Check Status, Add New
 * 
 * @author Leo
 * 
 */
public class AdsMonitorController {

	private static AdsMonitorController controller = new AdsMonitorController();
	private List<Thread> monitors;
	private Logger logger = Logger.getInstance();
	private File tasks_file;
	private String STATUS_RUNNING = "running";
	private String STATUS_STOPPED = "stopped";
	private String STATUS_ERROR = "error";

	// private constructor. This class cannot be instantiated from outside and
	// prevents sub classing.
	private AdsMonitorController() {
		tasks_file = new File("monitoring_tasks_users.txt");
		if (monitors == null)
			monitors = new ArrayList<Thread>();
		// check existing tasks and run if needed
		resumeExistingTasks();
	}

	public static AdsMonitorController getInstance() {
		return controller;
	}

	// we can't add monitors without starting
	public void startMonitor(String name, String URL, String email,
			String frequency) {
		try {
			String monitor_name = name + "-" + URL + "-" + email;
			// check if the name is already there - if so don't do anything
			if (alreadyRunning(monitor_name)) {
				logger.log("This monitoring task is already running");
				return;
			}

			// add an entry to the monitor_tasks.txt file
			List<MonitoringJsonObject> curr_monitors = getCurrentMonitorsFromFile();
			MonitoringJsonObject this_monitor = new MonitoringJsonObject(name,
					URL, email);
			for (Iterator<MonitoringJsonObject> iterator = curr_monitors
					.iterator(); iterator.hasNext();) {
				MonitoringJsonObject carMonitoringJsonObject = (MonitoringJsonObject) iterator
						.next();
				if (carMonitoringJsonObject.equals(this_monitor)) {
					iterator.remove();
					break;
				}
			}
			curr_monitors.add(new MonitoringJsonObject(name, URL, email,
					frequency, STATUS_RUNNING));
			writeMonitorsIntoFile(curr_monitors);

			AdsMonitor monitor = new AdsMonitor(name, URL, email, frequency);
			Thread moinitor_thread = new Thread(monitor);
			moinitor_thread.setName(monitor_name);
			moinitor_thread.start();
			monitors.add(moinitor_thread);

			// wait for monitor to fail if something is wrong so we can update
			// the status
			Thread.sleep(2000);

			// if new guy failed this will reset the status and remove monitor
			// from monitors
			getCurrentMonitorsFromFile();
		} catch (Exception e) {
			logger.close();
		}
	}

	public void stopMonitor(String name, String URL, String email,
			String frequency) {
		String task_name = name + "-" + URL + "-" + email;
		// update file
		List<MonitoringJsonObject> curr_monitors = getCurrentMonitorsFromFile();
		MonitoringJsonObject this_monitor = new MonitoringJsonObject(name, URL,
				email);
		for (Iterator<MonitoringJsonObject> iterator = curr_monitors.iterator(); iterator
				.hasNext();) {
			MonitoringJsonObject carMonitoringJsonObject = (MonitoringJsonObject) iterator
					.next();
			if (carMonitoringJsonObject.equals(this_monitor)) {
				if (carMonitoringJsonObject.getStatus().equals(STATUS_STOPPED)) {
					logger.log("Monitor already stopped: " + task_name);
					return;
				} else {
					carMonitoringJsonObject.setStatus(STATUS_STOPPED);
					break;
				}

			}
		}
		writeMonitorsIntoFile(curr_monitors);

		// kill thread
		try {
			for (Iterator<Thread> iterator = monitors.iterator(); iterator
					.hasNext();) {
				Thread moinitor_thread = (Thread) iterator.next();
				if (moinitor_thread.getName().equals(task_name)) {
					// there could only be one with the same name
					logger.log("Stopping: " + moinitor_thread.getName());
					moinitor_thread.interrupt();
					iterator.remove();
					break;
				}
			}
		} catch (Exception e) {
			logger.close();
		}
	}

	// removes monitor from monitoring_tasks.txt
	// kills monitoring thread
	public void removeMonitor(String name, String URL, String email) {
		String task_name = name + "-" + URL + "-" + email;
		boolean found = false;
		// delete entry from file (no matter running or not)
		List<MonitoringJsonObject> curr_monitors = getCurrentMonitorsFromFile();
		MonitoringJsonObject this_monitor = new MonitoringJsonObject(name, URL,
				email);
		for (Iterator<MonitoringJsonObject> iterator = curr_monitors.iterator(); iterator
				.hasNext();) {
			MonitoringJsonObject carMonitoringJsonObject = (MonitoringJsonObject) iterator
					.next();
			if (carMonitoringJsonObject.equals(this_monitor)) {
				iterator.remove();
				found = true;
				break;
			}
		}
		if (found)
			writeMonitorsIntoFile(curr_monitors);
		else
			return;
		// kill thread
		try {
			for (Iterator<Thread> iterator = monitors.iterator(); iterator
					.hasNext();) {
				Thread moinitor_thread = (Thread) iterator.next();
				if (moinitor_thread.getName().equals(task_name)) {
					logger.log("Stopping: " + moinitor_thread.getName());
					moinitor_thread.interrupt();
					iterator.remove();
					// there could only be one with the same name
					break;
				}
			}
		} catch (Exception e) {
			logger.close();
		}
	}

	public void stopAllMonitors() {
		logger.log("Stopping all monitors");
		for (Iterator<Thread> iterator = monitors.iterator(); iterator
				.hasNext();) {
			Thread moinitor_thread = (Thread) iterator.next();
			moinitor_thread.interrupt();
			iterator.remove();
		}
		try {
			List<MonitoringJsonObject> list = getCurrentMonitorsFromFile();
			for (MonitoringJsonObject carMonitoringJsonObject : list) {
				carMonitoringJsonObject.setStatus(STATUS_STOPPED);
			}
			// write list into file
			PrintWriter printWriter = new PrintWriter(tasks_file);
			BufferedWriter bw = new BufferedWriter(printWriter);
			bw.write(new Gson().toJson(list));
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startAllMonitors() {
		logger.log("Starting all monitors");
		try {
			List<MonitoringJsonObject> list = getCurrentMonitorsFromFile();
			for (MonitoringJsonObject carMonitoringJsonObject : list) {
				String name = carMonitoringJsonObject.getName();
				String URL = carMonitoringJsonObject.getUrl();
				String email = carMonitoringJsonObject.getEmail();
				String frequency = carMonitoringJsonObject.getFrequency();
				String monitor_name = name + "-" + URL + "-" + email;
				if (alreadyRunning(monitor_name)) {
					carMonitoringJsonObject.setStatus(STATUS_RUNNING);
					continue;
				}
				AdsMonitor monitor = new AdsMonitor(name, URL, email,
						frequency);
				Thread moinitor_thread = new Thread(monitor);
				moinitor_thread.setName(monitor_name);
				moinitor_thread.start();
				monitors.add(moinitor_thread);
				// update carMonitoringJsonObject's status
				carMonitoringJsonObject.setStatus(STATUS_RUNNING);
			}

			// give some time for monitors to fail
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.log(e.getMessage());
			}

			// write list into file
			PrintWriter printWriter = new PrintWriter(tasks_file);
			BufferedWriter bw = new BufferedWriter(printWriter);
			bw.write(new Gson().toJson(list));
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeAllMonitors() {
		logger.log("Removing all monitors");
		for (Iterator<Thread> iterator = monitors.iterator(); iterator
				.hasNext();) {
			Thread moinitor_thread = (Thread) iterator.next();
			moinitor_thread.interrupt();
			iterator.remove();
		}
		tasks_file.delete();
	}

	/**
	 * Structure of monitoring_tasks.txt task_name, url, email, frequency,
	 * status running/stopped
	 */
	void resumeExistingTasks() {
		if (!tasks_file.exists())
			return;
		List<MonitoringJsonObject> list = getCurrentMonitorsFromFile();
		for (Iterator<MonitoringJsonObject> iterator = list.iterator(); iterator
				.hasNext();) {
			MonitoringJsonObject carMonitoringJsonObject = (MonitoringJsonObject) iterator
					.next();
			String name = carMonitoringJsonObject.getName();
			String url = carMonitoringJsonObject.getUrl();
			String email = carMonitoringJsonObject.getEmail();
			String frequency = carMonitoringJsonObject.getFrequency();
			boolean isStopped = carMonitoringJsonObject.getStatus().equals(
					STATUS_STOPPED) ? true : false;
			if (!isStopped) {
				logger.log("Resuming task: " + name);
				startMonitor(name, url, email, frequency);
			}
		}
	}

	// writes given list of monitor objects into file
	public void writeMonitorsIntoFile(List<MonitoringJsonObject> list) {
		try {
			// write list into file
			PrintWriter printWriter = new PrintWriter(tasks_file);
			BufferedWriter bw = new BufferedWriter(printWriter);
			bw.write(new Gson().toJson(list));
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<MonitoringJsonObject> getCurrentMonitorsFromFile() {
		logger.log("reading current monitors from file");
		try {
			List<MonitoringJsonObject> list = new ArrayList<MonitoringJsonObject>();
			if (tasks_file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(
						tasks_file));
				StringBuffer content = new StringBuffer();
				String sCurrentLine;
				while ((sCurrentLine = br.readLine()) != null) {
					content.append(sCurrentLine);
				}
				br.close();

				if (!content.toString().isEmpty()) {
					Type listType = new TypeToken<ArrayList<MonitoringJsonObject>>() {
					}.getType();
					list = new Gson().fromJson(content.toString(), listType);
				}

				// check future tasks for exception
				// if any exceptions found set status to "error"
				boolean statusUpdated = false;
				for (Iterator<Thread> iterator = monitors.iterator(); iterator
						.hasNext();) {
					Thread moinitor_thread = iterator.next();
					if (moinitor_thread.getUncaughtExceptionHandler() == null) {
						logger.log("found interrupted thread -> updating status for "
								+ moinitor_thread.getName());
						// update status and remove moinitor_thread from the
						// list
						updateStatusToError(list, moinitor_thread);
						iterator.remove();
						statusUpdated = true;
					}
				}
				logger.log("currently running threads: " + monitors.size());
				if (statusUpdated)
					writeMonitorsIntoFile(list);
			}

			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void updateStatusToError(List<MonitoringJsonObject> list,
			Thread moinitor_thread) {
		for (Iterator<MonitoringJsonObject> iterator = list.iterator(); iterator
				.hasNext();) {
			MonitoringJsonObject carMonitoringJsonObject = (MonitoringJsonObject) iterator
					.next();
			String name = carMonitoringJsonObject.getName() + "-"
					+ carMonitoringJsonObject.getUrl() + "-"
					+ carMonitoringJsonObject.getEmail();
			if (name.equals(moinitor_thread.getName())) {
				carMonitoringJsonObject.setStatus(STATUS_ERROR);
			}
		}
	}

	private boolean alreadyRunning(String name) {
		for (Thread monitor : monitors) {
			if (monitor.getName().equals(name))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		AdsMonitorController controller = AdsMonitorController.getInstance();
		String URL1 = "https://www.donedeal.ie/cars/Mercedes-Benz/E-Class?area=Munster&sort=publishDate%20desc&price_to=3000&year_from=2002&year_to=2005&price_from=1000&transmission=Automatic";
		String frequency = "30";
		String email = "ioffe.leo@gmail.com";
		controller.startMonitor("monitor1", URL1, email, frequency);
		String URL2 = "https://www.donedeal.ie/cars/Mercedes-Benz/E-Class?area=Munster&sort=publishDate%20desc&price_to=4000&year_from=2002&year_to=2005&price_from=1000&transmission=Automatic";
		controller.startMonitor("monitor2", URL2, email, frequency);
		String URL3 = "https://www.donedeal.ie/cars/Mercedes-Benz/E-Class?area=Munster&sort=publishDate%20desc&price_to=5000&year_from=2002&year_to=2005&price_from=1000&transmission=Automatic";
		controller.startMonitor("monitor3", URL3, email, frequency);

		// try {
		// Thread.sleep(5000);
		// log.write("1 go to sleep for 5000 mills \n");
		// log.flush();
		// controller.removeMonitor("monitor1", URL1, email);
		// Thread.sleep(5000);
		// log.write("2 go to sleep for 5000 mills \n");
		// log.flush();
		// controller.removeMonitor("monitor2", URL2, email);
		// Thread.sleep(5000);
		// log.write("3 go to sleep for 5000 mills \n");
		// log.flush();
		// controller.removeMonitor("monitor3", URL3, email);
		// System.out.println("bla \n");
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } finally {
		// log.write("Close file\n");
		// log.flush();
		// log.close();
		// }
	}

}
