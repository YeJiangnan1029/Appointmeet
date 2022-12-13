package zju.apd.yjn.appointmeet.service.ex;

public class ParticipationDuplicatedException extends ServiceException{
    public ParticipationDuplicatedException() {
        super();
    }

    public ParticipationDuplicatedException(String message) {
        super(message);
    }

    public ParticipationDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParticipationDuplicatedException(Throwable cause) {
        super(cause);
    }

    protected ParticipationDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
