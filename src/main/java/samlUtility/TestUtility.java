package samlUtility;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.PropertyConfigurator;

public class TestUtility
{

	//Set Date For Log4J.
		public static void setDateForLog4j()
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
			System.setProperty("current_date", dateFormat.format(new Date()));
			PropertyConfigurator.configure("./src/main/resources/log4j.properties");
		}

}
