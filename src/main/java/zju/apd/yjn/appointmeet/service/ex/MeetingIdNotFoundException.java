package zju.apd.yjn.appointmeet.service.ex;

public class MeetingIdNotFoundException extends ServiceException{
    public MeetingIdNotFoundException() {
        super();
    }

    public MeetingIdNotFoundException(String message) {
        super(message);
    }

    public MeetingIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MeetingIdNotFoundException(Throwable cause) {
        super(cause);
    }

    protected MeetingIdNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
