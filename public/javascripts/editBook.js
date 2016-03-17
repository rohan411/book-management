function renderEditBookForm(url) {
      $.ajax({
         url: url
       })
     .done(function( data ) {
        var source = $("#edit-book-form-template").html();
        var template = Handlebars.compile(source);
        $("#hd").html(template({book: data.payload.book, cover: ["Hardcover", "Softcover"]}));
        registerEditBook();
     });
}

function registerEditBook() {
    $( "#update-book" ).click(function(e) {
        e.preventDefault();
        var id = $(this).data("id");
        var book = {
            isbn: $("#isbn").val(),
            title:$("#title").val(),
            author:$("#author").val(),
            cover: $("#cover").val()
        }
        $.ajax({
            url: '/books/' + id + '/update',
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
               renderBooks("/books");
            }
        });
    });

    $( "#delete-book" ).click(function(e) {
            e.preventDefault();
            var id = $(this).data("id");
            $.ajax({
                url: '/books/' + id + '/delete',
                type: 'post',
                success: function (response) {
                    $('.notifications').html("<div class='alert alert-success'> <strong>Success! </strong>" + response.meta.message + "</div>");
                    renderBooks("/books");
                },
                 error: function(err) {
                   $('.notifications').html("<div class='alert alert-danger'> <strong>Failure! </strong>" + err.responseJSON.meta.message + "</div>");
                   renderBooks("/books");
                }
            });
    });
}
