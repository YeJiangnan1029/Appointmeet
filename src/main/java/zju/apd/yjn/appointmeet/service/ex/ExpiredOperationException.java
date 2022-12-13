package zju.apd.yjn.appointmeet.service.ex;

public class ExpiredOperationException extends ServiceException{
    public ExpiredOperationException() {
        super();
    }

    public ExpiredOperationException(String message) {
        super(message);
    }

    public ExpiredOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredOperationException(Throwable cause) {
        super(cause);
    }

    protected ExpiredOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
