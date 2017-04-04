package daos;

import java.util.List;

import beans.MonitoringJsonObject;


public interface MonitorDao {
	public List<MonitoringJsonObject> getCurrentMonitorsFromFile();
}
