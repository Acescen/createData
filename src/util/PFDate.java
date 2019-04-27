package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PFDate {
	private TimeZone zone = null;
	

	
	/**
	 * 
	 * @param TimeZone  时区 GMT+8:00
	 */
	public PFDate(TimeZone z){
		zone = z;
		TimeZone.setDefault(zone);
	}


	
	/**
	    * 获取两个日期相差的月数
	    * @param d2  较大的日期
	    * @param d1  较小的日期
	    * @return 如果d1>d2返回 月数差 否则返回0
	    */
	public static int getMonthDiff(String d1, String d2)throws ParseException {
		//
		//Calendar 提供了一个类方法 getInstance，以获得此类型的一个通用的对象
		//获取一个Canlender 对象
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		//将String日期转换成date
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date1=sdf.parse(d1); 
		java.util.Date date2=sdf.parse(d2);

		//void setTime(Date date)  使用给定的 Date设置此日历的时间。
		c1.setTime(date1);
		c2.setTime(date2);

		//判断两个日期的大小

				if(c2.getTimeInMillis() < c1.getTimeInMillis()) {
					return 0;
				}
			  int year1 = c1.get(Calendar.YEAR);
			  int year2 = c2.get(Calendar.YEAR);
			  int month1 = c1.get(Calendar.MONTH);
			  int month2 = c2.get(Calendar.MONTH);
			  int day1 = c1.get(Calendar.DAY_OF_MONTH);
			  int day2 = c2.get(Calendar.DAY_OF_MONTH);
			  // 获取年的差值 假设 d1 = 2015-9-30   d2 = 2015-12-16
			  int yearInterval = year2 - year1;
			  // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
			  if(month2 < month1 || month1 == month2 && day2 < day1) {
			  	yearInterval --;
			  }
				// 获取月数差值
			int monthInterval = (month2 + 12) - month1 ;
			if(day2 > day1) {
				monthInterval ++;
			}
			monthInterval %= 12;
			return yearInterval * 12 + monthInterval;
	}

}
