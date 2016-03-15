package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.homepage;
import views.html.newBook;
import views.html.edit;
import java.util.List;
import static utils.PaginationHandler.*;

/**
 * Created by rohan on 14/03/16.
 */
public class Book extends Controller {

    public  Result homePageRedirect() {
        return redirect(routes.Book.index());
    }

    public  Result index() {
        String author = extractString(request().getQueryString("author"));
        String isbn = extractString(request().getQueryString("isbn"));
        String page = request().getQueryString("pageID");
        int pageId = stringToPageId(request().getQueryString("pageId"));
        int pageSize = 2;
        List result =  models.Book.page(isbn, author, pageId, pageSize);
        List<models.Book> books = (List<models.Book>) result.get(0);
        int totalCount = (int) result.get(1);
        return ok(homepage.render(books, isbn, author, pageCount(totalCount, pageSize), pageId+1));
    }

    public  Result newBook() {
        Form<models.Book> bookForm = Form.form(models.Book.class);
        return ok(
                newBook.render(bookForm)
        );
    }

    public  Result create() {
        Form<models.Book> bookForm = Form.form(models.Book.class);
        try {
            models.Book book = bookForm.bindFromRequest().get();
            book.save();
            flash("success", "Book created");
            return redirect(routes.Book.index());
        }
        catch (Exception ex) {
            flash("failure", "Cannot create book : " + ex.getMessage());
            return badRequest(newBook.render(bookForm));
        }
    }

    public  Result edit(String id) {
        models.Book book = models.Book.find.byId(id);
        return ok(edit.render(id, book));
    }

    public  Result update(String id) {
        Form<models.Book> bookForm = Form.form(models.Book.class).bindFromRequest();
        if (bookForm.hasErrors()) {
            return badRequest(edit.render(id, bookForm.get()));
        }
        models.Book bookDb = models.Book.find.byId(id);
        if(bookDb != null) {
            models.Book updatedBook = bookForm.get();
            updatedBook.setId(id);
            updatedBook.update();
            flash("success", "Book " + bookForm.get().title + " has been updated");
            return redirect(routes.Book.index());
        } else {
            flash("failure", "Incorrect Id");
            return redirect(routes.Book.index());
        }
    }

    public  Result delete(String id) {
        models.Book.find.ref(id).delete();
        flash("success", "Book deleted");
        return redirect(routes.Book.index());
    }
}
