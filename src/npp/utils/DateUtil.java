package npp.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import npp.dto.DatePair;


public class DateUtil {
	private static SimpleDateFormat dfwt = null;
	private static SimpleDateFormat df = null;

	private DateUtil() {
	}

	public static SimpleDateFormat getDateWithTimeFormatInstance() {
		if (dfwt == null)
			dfwt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dfwt;
	}

	public static SimpleDateFormat getDateFormatInstance() {
		if (df == null)
			df = new SimpleDateFormat("yyyy-MM-dd");
		return df;
	}



	public static int getMonthsBetweenDates(Date dateFrom, Date dateTo) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(dateFrom);
		int year1 = c.get(Calendar.YEAR);
		int month1 = c.get(Calendar.MONTH);

		c.setTime(dateTo);
		int year2 = c.get(Calendar.YEAR);
		int month2 = c.get(Calendar.MONTH);

		int result = 0;
		if (year1 == year2) {
			result = month2 - month1;
		} else {
			result = 12 * (year2 - year1) + month2 - month1;
		}
		return result;
	}

	public static int getWeeksBetweenDates(Date d1, Date d2) {
		Calendar dateFrom = Calendar.getInstance();
		dateFrom.setTime(d1);

		Calendar dateTo = Calendar.getInstance();
		dateTo.setTime(d2);

		dateFrom.setFirstDayOfWeek(Calendar.MONDAY);
		dateTo.setFirstDayOfWeek(Calendar.MONDAY);

		int yearFrom = dateFrom.get(Calendar.YEAR);
		int yearTo = dateTo.get(Calendar.YEAR);

		int weeksBetween = 0;
		// the year of dateFrom
		int weekFrom = dateFrom.get(Calendar.WEEK_OF_YEAR);
		if (weekFrom == 1 && dateFrom.get(Calendar.MONTH) == Calendar.DECEMBER)// 腊月
		{
			dateFrom.set(yearFrom, Calendar.DECEMBER, 25, 23, 59, 59);
			weeksBetween -= dateFrom.get(Calendar.WEEK_OF_YEAR);
		}
		weeksBetween -= weekFrom;
		// years between dates
		for (int i = 1; i <= yearTo - yearFrom; i++) {
			Calendar _lastDay = Calendar.getInstance();
			_lastDay.setFirstDayOfWeek(Calendar.MONDAY);
			_lastDay.set(yearFrom + i, 11, 25, 23, 59, 59);
			weeksBetween += _lastDay.get(Calendar.WEEK_OF_YEAR);
		}
		// the year of dateTo
		int weekTo = dateTo.get(Calendar.WEEK_OF_YEAR);
		if (weekTo == 1 && dateTo.get(Calendar.MONTH) == Calendar.DECEMBER)// 腊月
		{
			dateTo.set(yearTo, Calendar.DECEMBER, 25, 23, 59, 59);
			weeksBetween += dateTo.get(Calendar.WEEK_OF_YEAR);
		}
		weeksBetween += weekTo;

		return weeksBetween;
	}

	public static Date getDateAfterMonths(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, num);
		return cal.getTime();
	}

	public static int getDaysBetweenDates(Date dateFrom, Date dateTo) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(dateFrom);

		Calendar d2 = Calendar.getInstance();
		d2.setTime(dateTo);
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	public static Date getDateAfterWeeks(Date dateFrom, int count) {
		return getDateAfterDays(dateFrom, count * 7);
	}

	public static Date getDateAfterDays(Date dateFrom, int count) {
		Calendar now = Calendar.getInstance();
		now.setTime(dateFrom);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + count);
		return now.getTime();

	}

	public static ArrayList<DatePair> makeDatePairsByMonth(Date from, Date to) {
		ArrayList<DatePair> list = new ArrayList<DatePair>();
		if(from == null || to == null)
			return list;
		Calendar d1 = Calendar.getInstance();
		d1.setTime(from);
		d1.setTime(getFirstDateOfMonth(d1));
		Calendar d2 = Calendar.getInstance();
		d2.setTime(to);
		d2.setTime(getLastDateOfMonth(d2));
		while(d1.compareTo(d2) <= 0)
		{
			DatePair dp = new DatePair();
			dp.setFrom(getFirstDateOfMonth(d1));
			dp.setTo(getLastDateOfMonth(d1));
			list.add(dp);
			d1.add(Calendar.MONTH, 1);
		}
		return list;
	}

	private static Date getFirstDateOfMonth(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());
        c.set(Calendar.DAY_OF_MONTH, c
                .getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
	}

	private static Date getLastDateOfMonth(Calendar cal) {
		Calendar c = Calendar.getInstance();
		c.setTime(cal.getTime());
        c.set(Calendar.DAY_OF_MONTH, c
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
	}

	public static Date getFirstDayOfWeek(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//设置周一
        return c.getTime();
	}
	public static Date getLastDayOfWeek(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//设置周一
        c.add(Calendar.WEEK_OF_YEAR, 1);
        return c.getTime();
    }
	public static void main(String arg[]) {
		Calendar from = Calendar.getInstance();
		from.set(2010, 11, 1);
		Calendar to = Calendar.getInstance();
		to.set(2010, 8, 13);
		System.out.println(getDateWithTimeFormatInstance().format(getFirstDayOfWeek(from.getTime())));
		System.out.println(getDateWithTimeFormatInstance().format(getLastDayOfWeek(from.getTime())));

	}
}