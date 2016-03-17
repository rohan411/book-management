package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Constant;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.BookSerializer;
import views.html.homepage;

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
        ObjectNode response = BookSerializer.generateResponse(author, isbn, pageId, books, totalCount);
        return ok(response);
    }

    public  Result create() {
        ObjectNode response = play.libs.Json.newObject();
        ObjectNode metaRespone = play.libs.Json.newObject();
        try {
            JsonNode json = request().body().asJson();
            models.Book book = play.libs.Json.fromJson(json, models.Book.class);
            book.save();
            metaRespone.put("message", "Book created");
            return ok(BookSerializer.generateResponse(response, metaRespone));
        }
        catch (Exception ex) {
            metaRespone.put("message", "Cannot create book : " + ex.getMessage());
            return badRequest(BookSerializer.generateResponse(response, metaRespone));
        }
    }

    public  Result edit(String id) {
        ObjectNode response = play.libs.Json.newObject();
        ObjectNode metaRespone = play.libs.Json.newObject();
        models.Book book = models.Book.find.byId(id);
        if (book != null) {
            response.put("book", Json.toJson(book));
            return ok(BookSerializer.generateResponse(response, metaRespone));
        } else {
            metaRespone.put("message", "Book with id : " + id + " is not in the database");
            return notFound(BookSerializer.generateResponse(response, metaRespone));
        }
    }

    public  Result update(String id) {
        ObjectNode response = play.libs.Json.newObject();
        ObjectNode metaRespone = play.libs.Json.newObject();
        JsonNode json = request().body().asJson();
        models.Book updatedBook = play.libs.Json.fromJson(json, models.Book.class);
        models.Book bookDb = models.Book.find.byId(id);
        if(bookDb != null) {
            try {
                updatedBook.setId(id);
                updatedBook.update();
                metaRespone.put("message", "Book : " + updatedBook.title + " has been updated");
                return ok(BookSerializer.generateResponse(response, metaRespone));
            }
            catch (Exception ex) {
                metaRespone.put("message", "Cannot create book : " + ex.getMessage());
                return badRequest(BookSerializer.generateResponse(response, metaRespone));
            }
        } else {
            metaRespone.put("message", "Incorrect Id");
            return notFound(BookSerializer.generateResponse(response, metaRespone));
        }
    }

    public  Result delete(String id) {
        ObjectNode response = play.libs.Json.newObject();
        ObjectNode metaRespone = play.libs.Json.newObject();
        models.Book book = models.Book.find.byId(id);
        if (book != null) {
            book.delete();
            metaRespone.put("message", "Book deleted");
            return ok(BookSerializer.generateResponse(response, metaRespone));
        } else {
            metaRespone.put("message", "Book with id : " + id + " is not in the database");
            return notFound(BookSerializer.generateResponse(response, metaRespone));
        }
    }
}
