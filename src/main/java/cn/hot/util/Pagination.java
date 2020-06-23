package cn.hot.util;

public class Pagination {

    private int page = 1;
    private int limit = 50; //它的作用和pageSize是完全相同的，只是limit是layui的默认名字
    private int pageSize = 50;
    private long total = 1;
    private long pageTotal = 1;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
        setPageTotal((total+pageSize-1)/pageSize);
    }

    public long getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(long pageTotal) {
        this.pageTotal = pageTotal;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
