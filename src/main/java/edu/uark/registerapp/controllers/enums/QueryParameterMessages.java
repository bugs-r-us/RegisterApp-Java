package edu.uark.registerapp.controllers.enums;

import java.util.HashMap;
import java.util.Map;

public enum QueryParameterMessages {
	NOT_DEFINED(-1, ""),
	SESSION_NOT_ACTIVE(1001, "The current user's session is no longer active."),
	NO_PERMISSIONS_TO_VIEW(1101, "You do not have permission to view this resource."),
	NO_PERMISSIONS_FOR_ACTION(1102, "You do not have permission to perform this action."),
	SIGN_IN_ERROR(1103, "There was an error siging in... your ID or Password is wrong."),
	NO_TRANSACTION_ERROR(1104, "There is no linked transation, please start a new transaction");
	
	public int getKey() {
		return this.key;
	}
	public String getKeyAsString() {
		return Integer.toString(this.key);
	}
	public String getMessage() {
		return this.message;
	}

	public static String mapMessage(final int key) {
		if (valueMap == null) {
			valueMap = new HashMap<Integer, String>();

			for (final QueryParameterMessages status : QueryParameterMessages.values()) {
				valueMap.put(status.getKey(), status.getMessage());
			}
		}

		return (valueMap.containsKey(key)
			? valueMap.get(key)
			: QueryParameterMessages.NOT_DEFINED.getMessage());
	}

	private int key;
	private String message;

	private static Map<Integer, String> valueMap = null;

	private QueryParameterMessages(final int key, final String message) {
		this.key = key;
		this.message = message;
	}
}
