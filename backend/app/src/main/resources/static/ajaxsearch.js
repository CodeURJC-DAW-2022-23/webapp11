$(document).ready(function() {
    const total = $("#total").val();
    let start = 10;
    $("#loadMore").click(function() {
        const product = $("#product").val();
        $.ajax({
            url: "/search/loadmore",
            type: "GET",
            data: {
                product: product,
                start: start
            },
            // Add a spinner while loading
            beforeSend: function() {
                $("#loadMore").html('<i class="fa fa-spinner fa-spin"></i> Loading');
            },
            success: function(data) {
                $("#results").append(data);
                start += 10;
                if (start >= total) {
                    $("#loadMore").hide();
                }
            },
            complete: function() {
                $("#loadMore").html("Load more");
            }
        });
    });
});