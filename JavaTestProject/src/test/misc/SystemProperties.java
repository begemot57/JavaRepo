package test.misc;

public class SystemProperties {

	public static void main(String[] args) {
		//java.lang.System.setProperty("enableCBCProtection", "false");
		System.getProperties().list(System.out);
	}

}
