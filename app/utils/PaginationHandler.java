package utils;

/**
 * Created by rohan on 15/03/16.
 */
public class PaginationHandler {
    public static int pageCount(int totalCount, int pageSize) {
        if((totalCount % pageSize) == 0)
            return totalCount / pageSize;
        else
            return (totalCount / pageSize) + 1;
    }

    public static String extractString(String obj) {
        return obj != null ? obj : "";
    }

    public static Integer stringToPageId(String obj) {
        return obj != null ? (Integer.parseInt(obj) - 1 >= 0 ? Integer.parseInt(obj) -1 : 0 )  : 0 ;
    }
}
