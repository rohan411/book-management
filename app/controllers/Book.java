package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Constant;
import play.api.libs.json.Json;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import utils.BookSerializer;
import views.html.homepage;
import views.html.newBook;
import views.html.edit;
import java.util.List;
import java.util.Map;


import static utils.PaginationHandler.*;

/**
 * Created by rohan on 14/03/16.
 */
public class Book extends Controller {

    public  Result homePageRedirect() {
        return redirect(routes.Book.home());
    }

//    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public Result home() {
        return ok(homepage.render());
    }

    public Result index() {
        String author = extractString(request().getQueryString("author"));
        String isbn = extractString(request().getQueryString("isbn"));
        int pageId = stringToPageId(request().getQueryString("pageId"));
        Map result = models.Book.page(isbn, author, pageId, Constant.PAGESIZE);
        List<models.Book> books = (List<models.Book>) result.get("books");
        int totalCount = (int) result.get("totalCount");
        ObjectNode response = BookSerializer.getJsonNodes(author, isbn, pageId, books, totalCount);
        return ok(response);
    }

    public  Result create() {
        ObjectNode response = play.libs.Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            models.Book book = play.libs.Json.fromJson(json, models.Book.class);
            book.save();
            response.put("message", "Book created");
            return ok(response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            response.put("message", "Cannot create book : " + ex.getMessage());
            return badRequest(response);
        }
    }

    public  Result edit(String id) {
        models.Book book = models.Book.find.byId(id);
        return ok(play.libs.Json.toJson(book));
    }

    public  Result update(String id) {
        ObjectNode response = play.libs.Json.newObject();
        JsonNode json = request().body().asJson();
        models.Book updatedBook = play.libs.Json.fromJson(json, models.Book.class);
        models.Book bookDb = models.Book.find.byId(id);
        if(bookDb != null) {
            updatedBook.setId(id);
            updatedBook.update();
            response.put("message", "Book " + updatedBook.title + " has been updated");
            return ok(response);
        } else {
            response.put("message", "Incorrect Id");
            return badRequest(response);
        }
    }

    public  Result delete(String id) {
        ObjectNode response = play.libs.Json.newObject();
        models.Book.find.ref(id).delete();
        response.put("message", "Book deleted");
        return ok(response);
    }
}
