package meishi.network;

public class ResponseMessage {
	public final static int FAILED = 1000;
	public final static int NETWORK_ERROR = 1001;
	public final static int NOT_LOGIN_ERROR = 1002;
	public final static int DB_ERROR = 1003;
	
	private int errorCode;
	private String errorMessage;

	public ResponseMessage() {
		
	}
	
	public ResponseMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
