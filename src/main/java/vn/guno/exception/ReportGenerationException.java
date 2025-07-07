package vn.guno.exception;

/**
 * @author vu
 * @created 07/06/2024
 */
public class ReportGenerationException extends Exception {
    private int code;

    public ReportGenerationException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}