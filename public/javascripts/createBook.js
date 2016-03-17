function renderCreateBookForm() {
    var source = $("#book-form-template").html();
    var template = Handlebars.compile(source);
    $("#hd").html(template({cover: ["hardcover", "softcover"]}));
    registerWithCreateBook();
}

function registerWithCreateBook() {
    $( ".create-book" ).click(function(e) {
        e.preventDefault();
        var book = {
            isbn: $("#isbn").val(),
            title:$("#title").val(),
            author:$("#author").val(),
            cover: $("#cover").val()
        }
        $.ajax({
            url: '/books/create',
            type: 'post',
            data: JSON.stringify(book),
            dataType: 'json',
            contentType: "application/json",
            success: function (response) {
                $('.notifications').html("<div class='alert alert-success'> <strong>Success! </strong>" + response.meta.message + "</div>");
                renderBooks("/books");
            },
             error: function(err) {
               $('.notifications').html("<div class='alert alert-danger'> <strong>Failure! </strong>" + err.responseJSON.meta.message + "</div>");
               renderCreateBookForm();
            }
        });
    });
}
