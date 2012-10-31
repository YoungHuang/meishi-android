package meishi.network;

public class ResponseException extends Exception {
	private static final long serialVersionUID = 1L;

	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}
}
