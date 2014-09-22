package JCommonTools.Param;

import java.util.prefs.Preferences;

import javax.xml.bind.annotation.XmlTransient;

public class Param 
{
	private int _num;
	private String _prefsKey;
	private String _title;
	private String _desc;
	private String _type;
	
	private String _swCtrlName;
	private String _swCtrlOpt;
	private String _swCtrlRestriction;
	
	public int getNumber() {
		return _num;
	}
	public void setNumber(int _num) {
		this._num = _num;
	}
	
	public String getPrefsKey() {
		return _prefsKey;
	}
	public void setPrefsKey(String _prefsKey) {
		this._prefsKey = _prefsKey;
	}

	public String getTitle() {
		return _title;
	}
	public void setTitle(String _title) {
		this._title = _title;
	}

	public String getDescription() {
		return _desc;
	}
	public void setDescription(String _desc) {
		this._desc = _desc;
	}

	public String getType() {
		return _type;
	}
	public void setType(String _type) {
		this._type = _type;
	}

	public String getSwingCtrlName() {
		return _swCtrlName;
	}
	public void setSwingCtrlName(String _swCtrlName) {
		this._swCtrlName = _swCtrlName;
	}

	public String getSwingCtrlOptional() {
		return _swCtrlOpt;
	}
	public void setSwingCtrlOptional(String _swCtrlOpt) {
		this._swCtrlOpt = _swCtrlOpt;
	}
	
	public String getSwingCtrlRestriction() {
		return _swCtrlRestriction;
	}
	public void setSwingCtrlRestriction(String _swCtrlRestriction) {
		this._swCtrlRestriction = _swCtrlRestriction;
	}

	public Param ()
	{
		_num=0;
		_prefsKey = null;
		_title = null;
		_desc = null;
		_type = null;
		
		_swCtrlName = null;
		_swCtrlOpt = null;
		_swCtrlRestriction = null;
	}
	
}
