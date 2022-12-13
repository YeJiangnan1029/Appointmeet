package zju.apd.yjn.appointmeet.service.ex;

public class MeetingPasswordNotMatchException extends ServiceException{
    public MeetingPasswordNotMatchException() {
        super();
    }

    public MeetingPasswordNotMatchException(String message) {
        super(message);
    }

    public MeetingPasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public MeetingPasswordNotMatchException(Throwable cause) {
        super(cause);
    }

    protected MeetingPasswordNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
