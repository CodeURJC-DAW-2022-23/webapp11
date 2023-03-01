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
                const products = JSON.parse(data).data;
                let html = '';
                products.forEach(function(product) {
                    html += '<div class="col-md-2">';
                    html += '<a href="/product/' + product.productId + '">';
                    html += '<img src="/product/' + product.productId + '/image" alt="resultimage" width="120" height="120">';
                    html += '<p class="name">' + product.productName + '</p>';
                    html += '<p>' + product.productPrice + ' € <span class="badge text-bg-success">' + product.productStock + '</span></p>';
                    html += '</a>';
                    html += '</div>';
                });
                $("#results").append(html);
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