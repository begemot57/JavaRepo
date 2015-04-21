package test.misc;

public class StringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringTest test = new StringTest();
//		String str = "cmd : /opt/IBM/JazzSM/profile//bin/wsadmin.sh -lang jython -user smadmin -password smadmin -f /tmp//libertyinstall_root/export_key.jy /tmp//libertyinstall_root/pi_dash_ltpa.keys passw0rd";
//		System.out.println(test.replaceTest(str));
//		str = "root@nc9042036111.tivlab.raleigh.ibm.com  /opt/IBM/JazzSM/profile//bin/wsadmin.sh -user smadmin -password smadmin -lang jython -f /tmp//libertyinstall_root/export_keys.jy \"passw0rd\"";
//		System.out.println(test.replaceTest(str));
		
		test.printString(null);
	}
	
	String replaceTest(String str){
		str = str.replaceAll("pass[^\\s]+[\\s]+[^\\s]+", "password XXXXXX");
		
		str = str.replaceAll("export_keys.jy[\\s]+[^\\s]+", "export_keys.jy XXXXXX");
		if(str.contains("jython"))
			str = str.replaceAll("pi_dash_ltpa.keys[\\s]+[^\\s]+", "pi_dash_ltpa.keys XXXXXX");
		return str;
	}
	
	void printString(String str){
		System.out.println("str: "+str);
	}

}
