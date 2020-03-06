package paging;

public interface Pageable {
    int getPage();
    int getSize();
    int getOffset();

}
