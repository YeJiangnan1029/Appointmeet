package zju.apd.yjn.appointmeet.service.ex;

public class UserIdNotFoundException extends ServiceException{
    public UserIdNotFoundException() {
        super();
    }

    public UserIdNotFoundException(String message) {
        super(message);
    }

    public UserIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIdNotFoundException(Throwable cause) {
        super(cause);
    }

    protected UserIdNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
