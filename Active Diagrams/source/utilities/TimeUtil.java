/**
 * 
 */
package utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Rejeesh G.
 *
 * Handles independent time management functions.
 * Most of these functions doesn't need object creation.
 */
public class TimeUtil {

	/**
	 * User for getting current date in text format.
	 * @return Text formatted current date.
	 */
	public static String now() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S X");
		String tCurrentDate = df.format(Calendar.getInstance().getTime());
		return tCurrentDate;
	}
}
