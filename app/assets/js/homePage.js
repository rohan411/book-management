function renderBooks(url) {
   $.ajax({
     url: url
   })
     .done(function( data ) {
       if ( console && console.log ) {
         console.log( "Sample of data:", data );
       }
     });
}