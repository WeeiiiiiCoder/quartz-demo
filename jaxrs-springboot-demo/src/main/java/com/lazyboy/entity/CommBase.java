package com.lazyboy.entity;

/**
 * 通讯报文基础类
 * 
 * @author hx
 * 
 */
public class CommBase {

	public static int RESULT_SUCCESS = 0;
	public static int RESULT_FAIL = 1;
	public static int RESULT_NOLOGIN = -1;
	public static int RESULT_NOREGISTER = -2;
	public static int RESULT_ACCESS_DENIED = -3;

	/**
	 * 0：成功 1：失败
	 * 
	 */

	private int _result;
	private String _desc;
	private String _sid;
	private boolean _login;
	private String _taskName;
	private int _taskId;
	private int _taskType;
	private int _userTaskState;
	private int _taskPoint;
   
	public int get_taskPoint() {
		return _taskPoint;
	}

	public void set_taskPoint(int _taskPoint) {
		this._taskPoint = _taskPoint;
	}

	public String get_taskName() {
		return _taskName;
	}

	public void set_taskName(String _taskName) {
		this._taskName = _taskName;
	}

	public int get_taskId() {
		return _taskId;
	}

	public void set_taskId(int _taskId) {
		this._taskId = _taskId;
	}

	public int get_taskType() {
		return _taskType;
	}

	public void set_taskType(int _taskType) {
		this._taskType = _taskType;
	}

	public int get_userTaskState() {
		return _userTaskState;
	}

	public void set_userTaskState(int _userTaskState) {
		this._userTaskState = _userTaskState;
	}

	public String get_sid() {
		return _sid;
	}

	public void set_sid(String _sid) {
		this._sid = _sid;
	}

	public boolean is_login() {
		return _login;
	}

	public void set_login(boolean _login) {
		this._login = _login;
	}

	public int get_result() {
		return _result;
	}

	public void set_result(int _result) {
		this._result = _result;
	}

	public String get_desc() {
		return _desc;
	}

	public void set_desc(String _desc) {
		this._desc = _desc;
	}

	public void resultWithDesc(int result, String desc) {
		this._desc = desc;
		this._result = result;
	}

	public static CommBase success(String msg) {
		CommBase res = new CommBase();
		res.resultWithDesc(CommBase.RESULT_SUCCESS, msg);
		return res;
	}

	public static CommBase fail(int code, String msg) {
		CommBase res = new CommBase();
		res.resultWithDesc(code, msg);
		return res;
	}
}