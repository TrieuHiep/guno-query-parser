package vn.guno.reporting.core;


public class Pagination {
    private int offset;
    private int limit;

    public Pagination() {
    }

    public Pagination(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() { return offset; }
    public int getLimit() { return limit; }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
