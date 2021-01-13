package stuff;

import org.json.simple.JSONObject;

public class ajaxResult {
	public ajaxResult() {
		jsonObject = new JSONObject();
	}
	private JSONObject jsonObject;
	
	public void insert(boolean success, Object pair, Object object) {
		jsonObject.put("success", success);
		jsonObject.put("pair", pair);
		jsonObject.put("object", object);
	}
	
	public void success() {
		jsonObject.put("success", true);
		jsonObject.put("pair", "");
		jsonObject.put("object", "");
	}
	
	public void exception() {
		jsonObject.put("success", false);
		jsonObject.put("pair", "");
		jsonObject.put("object", "exception");
	}
	
	public JSONObject retrieve() {
		return jsonObject;
	}
}
