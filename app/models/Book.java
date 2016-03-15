package models;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohan on 13/03/16.
 */

@Entity
public class Book extends Model {

    @Id
    public String id;

    @Column(unique = true)
    @Constraints.Required
    public String isbn;

    @Constraints.Required
    public String title;

    @Constraints.Required
    public String author;

    @Constraints.Required
    public Cover cover;

    public static Finder<String, Book> find = new Model.Finder<String, Book>(Book.class);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static ExpressionList<Book> authorFilter(ExpressionList<Book> expressionList, String author) {
        return expressionList.like("author", "%" + author + "%");
    }

    public static ExpressionList<Book> isbnFilter(ExpressionList<Book> expressionList, String isbn) {
        return expressionList.ilike("isbn", "%" + isbn + "%");
    }

    private static ExpressionList<Book> applyFilters(ExpressionList<Book> expressionList, String isbn, String author) {
        if(!isbn.isEmpty()){
            expressionList = isbnFilter(expressionList, isbn);
        }
        if(!author.isEmpty()){
            expressionList = authorFilter(expressionList, author);
        }
        return expressionList;
    }

    public static List page(String isbn, String author, int page, int pageSize) {
        ExpressionList<Book> expressionList = Book.find.where();
        expressionList = applyFilters(expressionList, isbn, author);
        int count  = expressionList.findRowCount();
        List<Book> list = expressionList
                .findPagedList(page, pageSize)
                .getList();
        List resultList = new ArrayList<>();
        resultList.add(0, list);
        resultList.add(1, count);
        return resultList;
    }


}
