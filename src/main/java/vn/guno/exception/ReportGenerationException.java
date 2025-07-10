package vn.guno.exception;

/**
 * @author vu
 * @created 07/06/2024
 */
public class ReportGenerationException extends Exception {
    private int code;

    private String rootCause;

    public ReportGenerationException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ReportGenerationException(String message, int code, String rootCause) {
        super(message);
        this.code = code;
        this.rootCause = rootCause;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }
}