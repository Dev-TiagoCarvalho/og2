package dev.online.monitor.method.model;

public class WritableException {

    private final String type;
    private final String occurrence;
    private final String message;
    private final WritableException cause;

    public WritableException(Throwable e) {
        this.type = e.getClass().getTypeName();
        this.occurrence = e.getStackTrace()[0].toString();
        this.message = e.getMessage();
        this.cause = e.getCause() != null ? new WritableException(e.getCause()) : null;
    }

    public String getType() { return this.type; }
    public String getOccurrence() { return this.occurrence; }
    public String getMessage() { return this.message; }
    public WritableException getCause() { return this.cause; }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Exception[");
        builder.append("type=").append(this.type).append(',');
        builder.append("message=").append(this.message).append(',');
        builder.append("occurrence=").append(this.occurrence).append(',');
        builder.append("cause=").append(this.cause == null ? "null" : this.cause.toString()).append(']');
        return builder.toString();
    }
}
