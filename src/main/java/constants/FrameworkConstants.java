package constants;

public class FrameworkConstants {

	private static final String PROJECT_PATH = System.getProperty("user.dir");

	private static final String SAML_FILE_PATH = PROJECT_PATH + "\\src\\test\\java\\sMAL_File\\FILI_SAML_AmishaSR.xml";




	public static String getProjectPath() {
		return PROJECT_PATH;
	}


	public static String getSAMLFilePath()
	{
		return SAML_FILE_PATH;
	}

}
