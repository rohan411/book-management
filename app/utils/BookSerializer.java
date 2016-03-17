package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Book;
import models.Constant;

import java.util.List;

import static utils.PaginationHandler.pageCount;

/**
 * Created by rohan on 17/03/16.
 */
public class BookSerializer {
    private static ObjectNode generatePayload(List<models.Book> books, ObjectNode filters) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode bookNode = mapper.valueToTree(books);
        ObjectNode response = play.libs.Json.newObject();
        response.putArray("books").addAll(bookNode);
        response.put("filters", filters);
        return response;
    }

    public static ObjectNode generateResponse(ObjectNode payload, ObjectNode meta) {
        ObjectNode responseBody = play.libs.Json.newObject();
        responseBody.put("payload", payload);
        responseBody.put("meta", meta);
        return responseBody;
    }

    private static ObjectNode generateFilter(String author, String isbn) {
        ObjectNode response = play.libs.Json.newObject();
        response.put("isbn", isbn);
        response.put("author", author);
        return response;
    }

    public static ObjectNode generateResponse(String author, String isbn, int pageId, List<models.Book> books, int totalCount) {

        ObjectNode response = play.libs.Json.newObject();
        ObjectNode responseBody = play.libs.Json.newObject();
        responseBody.put("payload", generatePayload(books, generateFilter(author, isbn)));

        response.put("pageCount", pageCount(totalCount, Constant.PAGESIZE));
        response.put("pageId", pageId + 1);
        responseBody.put("meta", response);
        return responseBody;
    }
}
