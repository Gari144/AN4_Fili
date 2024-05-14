package samlUtility;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.openqa.selenium.By;

import testBase.TestBase;

public class Base64Encode extends TestBase
{

	public static String readFileAsString(String fileName)throws Exception
	  {
	    String data = "";
	    data = new String(Files.readAllBytes(Paths.get(fileName)));
	    return data;
	  }

	public static String SAML_base64Encode()
	{
		try {
			String data = readFileAsString(System.getProperty("user.dir")+"\\src\\test\\java\\sMAL_File\\POS_Protectioniul.xml");
			String BasicBase64format = Base64.getEncoder().encodeToString(data.getBytes());
			 try (PrintStream out = new PrintStream(new FileOutputStream(System.getProperty("user.dir")+"\\src\\test\\java\\sMAL_File\\Encoded_Data.txt")))
		        {
		            out.print(BasicBase64format);
		        }
			 driver.findElement(By.id("SAMLResponse")).sendKeys(BasicBase64format);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Pass";
	}



}
