package zju.apd.yjn.appointmeet.service.ex;

public class DateTimeIllegalException extends ServiceException{
    public DateTimeIllegalException() {
        super();
    }

    public DateTimeIllegalException(String message) {
        super(message);
    }

    public DateTimeIllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateTimeIllegalException(Throwable cause) {
        super(cause);
    }

    protected DateTimeIllegalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
