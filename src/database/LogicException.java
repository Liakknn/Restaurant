package database;

public class LogicException extends Exception {

    public LogicException(String message) {
        super(message);
    }

    public LogicException(String format, Object... args) {
        super(String.format(format, args));
    }

}
