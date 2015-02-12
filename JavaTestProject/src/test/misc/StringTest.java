package test.misc;

public class StringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringTest test = new StringTest();
		test.replaceTest();
	}
	
	void replaceTest(){
		String str = "/opt/IBM/JazzSM/profile//bin/wsadmin.sh -lang jython -user smadmin -password smadmin -f /tmp//libertyinstall_root/delete_signer_cert.jy";
		System.out.println(str.replaceAll("pass[^\\s]+[\\s]+[^\\s]+", "password XXXXXX"));
	}

}
