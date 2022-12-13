package zju.apd.yjn.appointmeet.service.ex;

public class UpdateLoginDateException extends ServiceException{
    public UpdateLoginDateException() {
        super();
    }

    public UpdateLoginDateException(String message) {
        super(message);
    }

    public UpdateLoginDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateLoginDateException(Throwable cause) {
        super(cause);
    }

    protected UpdateLoginDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
