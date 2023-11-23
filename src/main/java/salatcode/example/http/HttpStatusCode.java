package salatcode.example.http;

public enum HttpStatusCode {
    //client ERR
    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad request"),
    CLIENT_ERROR_401_METHOD_NOT_ALLOWED(401, "Method not allowed"),
    CLIENT_ERROR_414_URI_TO_LONG(414, "URI_TO_LONG"),
    //server ERR
    SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    SERVER_ERROR_501_NOT_IMPLEMENTED(501, "INTERNAL_SERVER_ERROR"),
    ;

    public final int STATUS_CODE;
    public final String MESSAGE;
    HttpStatusCode(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }
}
