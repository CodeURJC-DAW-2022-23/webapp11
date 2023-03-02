$(document).ready(function() {
    const total = $("#total").val();
    let start = 10;
    $("#loadMore").click(function() {
        $.ajax({
            url: "/wishlist/loadmore",
            type: "GET",
            data: {
                start: start
            },
            // Add a bootstrap spinner while loading
            beforeSend: function() {
                $("#loadMore").html('<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Loading...');
            },
            success: function(data) {
                const products = JSON.parse(data).data;  // we need to add the .data to get the array of products, otherwise we get the whole response
                let html = '';
                products.forEach(function(product) {
                    html += '<div class="row item">';
                    //<div className="col-md-4">
                    html += '<div class="col-md-4">';
                    html += '<img src="/product/' + product.id + '/image" alt="item" width="200" height="200">';
                    html += '</div>';
                    html += '<div class="col-md-4">';
                    html += '<h3>' + product.name + '</h3>';
                    html += '<p>' + product.description + '</p>';
                    html += '</div>';
                    html += '<div class="col-md-4">';
                    html += '<h3>Price</h3>';
                    html += '<p>€' + product.price + '</p>';
                    html += '</div>';
                    html += '</div>';
                });
                $("#items").append(html);
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