function renderEditBookForm(url) {
      $.ajax({
         url: url
       })
     .done(function( book ) {
        var source = $("#edit-book-form-template").html();
        var template = Handlebars.compile(source);
        $("#hd").html(template({book: book, cover: ["Hardcover", "Softcover"]}));
        registerEditBook();
     });
}

function registerEditBook() {
    $( "#update-book" ).click(function(e) {
        e.preventDefault();
        debugger
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
                $('.notifications').html("<div class='alert alert-success'> <strong>Success! </strong>" + response.message + "</div>");
                renderBooks("/books");
            },
             error: function(response) {
             debugger
               alert("Status: " + response.responseJSON.message);
               $('.notifications').html("<div class='alert alert-success'> <strong>Failure! </strong>" + response.responseJSON.message + "</div>");
               renderBooks("/books");
            }
        });
    });

    $( "#delete-book" ).click(function(e) {
            e.preventDefault();
            $.ajax({
                url: '/books/' + id + 'delete',
                type: 'post',
                success: function (response) {
                    $('.notifications').html("<div class='alert alert-success'> <strong>Success! </strong>" + response.message + "</div>");
                    renderBooks("/books");
                },
                 error: function(response) {
                   alert("Status: " + response.responseJSON.message);
                   $('.notifications').html("<div class='alert alert-success'> <strong>Failure! </strong>" + response.responseJSON.message + "</div>");
                   renderBooks("/books");
                }
            });
    });
}
