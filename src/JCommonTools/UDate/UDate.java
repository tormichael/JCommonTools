package JCommonTools.UDate;

import java.util.Calendar;
import java.util.Date;

import JCommonTools.Convert;

/*
 * Class definition date with undefined part. 
 * May be defined only year or only day and month.
 * 
 * Create 16.08.2013
 * Author: M.Tor
 */
public class UDate 
{
	public final static String DSF_YYYY = "YYYY";
	public final static String DSF_MM = "MM";
	public final static String DSF_DD = "DD";
	
	public final static int STATE_OK = 0x0;
	public final static int STATE_UNDEFINED_YEAR = 0x0100;
	public final static int STATE_UNDEFINED_MONTH = 0x0010;
	public final static int STATE_UNDEFINED_DAY = 0x0001;
	
	private int _year;
	private int _month;
	private int _day;
	
	/*
	 * error code; 
	 * 0 - all right
	 */
	private int _state;

//	public boolean IsCorrectYear(int aYear)
//	{
//		return (aYear > 0 && aYear < 9999) ;
//	}

	public int getYear() 
	{
		return _year;
	}

	public void setYear(int aYear) 
	{
		if (aYear > 0 && aYear < 9999)
		{
			this._year = aYear;
		}
		else
		{
			this._year = 0;
			_state |= STATE_UNDEFINED_YEAR;
		}
	}

	public int getMonth() 
	{
		return _month;
	}

	public void setMonth(int aMonth) 
	{
		if (aMonth > 0 && aMonth < 13)
		{
			this._month = aMonth;
		}
		else
		{
			this._month = 0;
			_state |= STATE_UNDEFINED_MONTH;
		}
	}

	public int getDay() 
	{
		return _day;
	}

	public void setDay(int aDay) 
	{
		if (aDay > 0 && aDay < 32)
		{
			this._day = aDay;
		}
		else
		{
			this._day = 0;
			_state |= STATE_UNDEFINED_DAY;
		}
	}
	
	public int getState()
	{
		return _state;
	}
	
	public Date getDate()
	{
		Date ret = null;
		if (_day*_month*_year > 0)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, _year);
			cal.set(Calendar.MONTH, _month-1);
			cal.set(Calendar.DAY_OF_MONTH, _day);
			ret = cal.getTime();
		}
		return ret;
	}
	
	public void setDate(java.util.Date aDT)
	{
		if (aDT != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(aDT);
			setCalendar(cal);
		}
		else
		{
			setCalendar(null);
		}
	}
	public void setCalendar(Calendar aCal)
	{
		if (aCal != null)
		{
			_year = aCal.get(Calendar.YEAR);
			_month = aCal.get(Calendar.MONTH);
			_day = aCal.get(Calendar.DAY_OF_MONTH);
			_state = STATE_OK;
		}
		else
		{
			Initialize();
		}
	}
	public void setDateFromStringFormat(String aYYYYMMDD)
	{
		if (aYYYYMMDD != null && aYYYYMMDD.length() ==8)
		{
			_year = Convert.ToIntegerOrZero(aYYYYMMDD.substring(0, 4));
			_month = Convert.ToIntegerOrZero(aYYYYMMDD.substring(4, 6)); 
			_day = Convert.ToIntegerOrZero(aYYYYMMDD.substring(6, 8));
			_state = STATE_OK;
		}
		else
		{
			Initialize();
		}
		
	}
	
	public String getDateFormatString()
	{
		return "yyyyMMdd";
	}
	
	public UDate () 
	{
		this(0,0,0);
	}
	public UDate (int aYear, int aMonth, int aDay) 
	{
		_state = STATE_OK;
		setYear(aYear);
		setMonth(aMonth);
		setDay(aDay);
	}
	public UDate (java.util.Date aDT)
	{
		setDate(aDT);
	}
	public UDate (Calendar aCal)
	{
		setCalendar(aCal);
	}
	public UDate (String aYYYYMMDD)
	{
		setDateFromStringFormat(aYYYYMMDD);
	}
	
	public void Initialize()
	{
		_year = 0;
		_month = 0;
		_day = 0;
		_state = STATE_UNDEFINED_YEAR|STATE_UNDEFINED_MONTH|STATE_UNDEFINED_DAY;
	}
	
	/**
	 * default string format yyyymmdd
	 */
	@Override
	public String toString() 
	{
		return getYearAsYYYY(DSF_YYYY)+getMonthAsMM(DSF_MM)+getDayAsDD(DSF_DD);
	}

	public String getYearAsYYYY(String aIfUndefined)
	{
		String yyyy = "000"+_year;
		if (_year == 0)
			yyyy = aIfUndefined;
		else
			yyyy = yyyy.substring(yyyy.length() - 4);
		
		return yyyy;
	}
	
	public String getMonthAsMM(String aIfUndefined)
	{
		String mm = "0"+_month;
		if (_month == 0)
			mm = aIfUndefined;
		else
			mm = mm.substring(mm.length()-2);

		return mm;
	}
	
	public String getDayAsDD(String aIfUndefined)
	{
		String dd = "0"+_day;
		if (_day == 0)
			dd = aIfUndefined;
		else
			dd = dd.substring(dd.length()-2);

		return dd;
	}
}
