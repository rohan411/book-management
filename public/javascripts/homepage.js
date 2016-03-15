function init(pageId){
    registerWithClearBook();
    registerWithNewBook();
    registerWithEditBook();
    $(".pagination > #" + pageId).addClass("active")
    $('a').click(function(e) {
        console.log("11")
        e.preventDefault();
        var clickedPage = $(this).parent().attr('id');
        var url = '';
        if($('#isbn').val())
            url += "isbn=" + $('#isbn').val() + "&";
        if($('#author').val())
            url += "author" + $('#author').val() + "$";
        url += "pageId=" + clickedPage;
        window.location.href = "/books?" + url;
    });
}

function registerWithEditBook() {
    $( "#edit" ).click(function() {
      var id = $(this).data("id")
      location.href = "/books/" + id + "/edit";
    });
}
function registerWithNewBook() {
     $( "#newBook" ).click(function() {
        location.href = "/books/new";
     });
}
function registerWithClearBook() {
    $( "#clear" ).click(function() {
      location.href = "/books";
    });
}