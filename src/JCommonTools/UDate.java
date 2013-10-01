package JCommonTools;

/*
 * Class definition date with undefined part. 
 * May be defined only year or only day and month.
 * 
 * Create 16.08.2013
 * Author: M.Tor
 */
public class UDate {

	private int _year;
	private int _month;
	private int _day;
	
	/*
	 * error code; 
	 * 0 - all right
	 * 101 - year out correct range
	 * 201 - incorrect month value
	 * 301 - incorrect day value
	 */
	private int _err;

	
	public int get_year() {
		return _year;
	}

	public void set_year(int aYear) {
		if (aYear > 0 && aYear < 9999)
			this._year = aYear;
		else
			_err = 101;
	}

	public int get_month() {
		return _month;
	}

	public void set_month(int aMonth) {
		if (aMonth > 0 && aMonth < 13)
			this._month = aMonth;
		else
			_err = 201;
	}

	public int get_day() {
		return _day;
	}

	public void set_day(int aDay) {
		if (aDay > 0 && aDay < 32)
			this._day = aDay;
		else
			_err = 301;
	}
	
	public int get_error(){
		return _err;
	}
	
	public UDate (int aYear, int aMonth, int aDay) {
		_err = 0;
		_year = aYear;
		_month = aMonth;
		_day = aDay;
	}
	
}
