package zju.apd.yjn.appointmeet.service.ex;

public class UserEmailDuplicatedException extends ServiceException{
    public UserEmailDuplicatedException() {
        super();
    }

    public UserEmailDuplicatedException(String message) {
        super(message);
    }

    public UserEmailDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserEmailDuplicatedException(Throwable cause) {
        super(cause);
    }

    protected UserEmailDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
