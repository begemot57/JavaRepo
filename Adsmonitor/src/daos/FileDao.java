package daos;

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

import beans.MonitoringJsonObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class FileDao implements MonitorDao{

	private Logger logger = Logger.getInstance();
	private File tasks_file;
	private String STATUS_RUNNING = "running";
	private String STATUS_STOPPED = "stopped";
	private String STATUS_ERROR = "error";
	private List<Thread> monitors;
	
	public FileDao(){
		super();
		tasks_file = new File("monitoring_tasks_users.txt");
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
}
