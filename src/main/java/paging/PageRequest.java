package paging;

public class PageRequest implements Pageable {
    private int pageSize;
    private int pageIndex;

    public PageRequest(int pageSize, int pageeIndex) {
        this.pageSize = pageSize;
        this.pageIndex = pageeIndex;
    }

    public static PageRequest of(int pageSize, int pageeIndex ){
        return new PageRequest(pageSize, pageeIndex);
    }
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageeIndex) {
        this.pageIndex = pageeIndex;
    }

    @Override
    public int getPage() {
        return pageIndex;
    }

    @Override
    public int getSize() {
        return pageSize;
    }

    @Override
    public int getOffset() {
        return (pageIndex-1)*pageSize +1 ;
    }
}
