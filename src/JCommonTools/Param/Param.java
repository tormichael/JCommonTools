package JCommonTools.Param;

public class Param 
{
	private String _prefsPath;
	private String _title;
	private String _desc;
	private String _type;
	
	private String _swCtrlName;
	private String _swCtrlOpt;
	private String _swCtrlRestriction;
	
	public String getPrefsPath() {
		return _prefsPath;
	}
	public void setPrefsPath(String _prefsPath) {
		this._prefsPath = _prefsPath;
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
		_prefsPath = null;
		_title = null;
		_desc = null;
		_type = null;
		
		_swCtrlName = null;
		_swCtrlOpt = null;
		_swCtrlRestriction = null;
	}
}
