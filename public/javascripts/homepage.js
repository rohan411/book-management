function renderBooks(url) {
   $.ajax({
     url: url
   })
     .done(function( data ) {
        if ( console && console.log ) {
            var source = $("#home-page-template").html();
            var template = Handlebars.compile(source);
            $("#hd").html(template(data));
            init(data.meta.pageId)
        }
     });
}

function init(pageId){
    registerWithClearBook();
    registerWithNewBook();
    registerWithEditBook();
    registerWithApplyFilters(pageId);
    $(".pagination > #" + pageId).addClass("active")
    $('a').click(function(e) {
        e.preventDefault();
        var clickedPage = $(this).parent().attr('id');
        var url = '';
        if($('#isbn').val())
            url += "isbn=" + $('#isbn').val() + "&";
        if($('#author').val())
            url += "author=" + $('#author').val() + "&";
        url += "pageId=" + clickedPage;
        renderBooks("/books?" + url);
    });
}

function registerWithEditBook() {
    $( ".edit" ).click(function() {
      var id = $(this).data("id")
      var url = "/books/" + id + "/edit";
      renderEditBookForm(url);
    });
}
function registerWithNewBook() {
     $( "#newBook" ).click(function() {
        renderCreateBookForm()
     });
}

function registerWithClearBook() {
    $( "#clear" ).click(function(e) {
        e.preventDefault();
        renderBooks("/books");;
    });
}

function registerWithApplyFilters(pageId) {
    $( "#apply-filter" ).click(function(e) {
        e.preventDefault();
        var url = '';
        if($('#isbn').val())
            url += "isbn=" + $('#isbn').val() + "&";
        if($('#author').val())
            url += "author=" + $('#author').val() + "&";
        if (pageId)
            url += "pageId=" + pageId;
        renderBooks("/books?" + url);
    });
}