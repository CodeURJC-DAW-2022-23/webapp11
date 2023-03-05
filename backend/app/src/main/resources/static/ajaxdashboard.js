$(document).ready(function() {
    const total = $("#total").val();
    let start = 10;
    $("#loadMore").click(function() {
        $.ajax({
            url: "/dashboard/loadmore",
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
                    html += '<div class="col-md-2">';
                    html += '<a href="/product/' + product.productId + '">';
                    html += '<img src="/product/' + product.productId + '/image" alt="resultimage" width="120" height="120">';
                    html += '<p class="name">' + product.productName + '</p>';
                    html += '<p>' + product.productPrice + ' € <span class="badge text-bg-success">Stock: ' + product.productStock + '</span></p>';
                    html += '<p><a href="/product/' + product.productId + '/editproduct" class="btn btn-primary">Edit</a>&nbsp;';
                    html += '<a href="/product/' + product.productId + '/removeFromStock" class="btn btn-danger">Delete</a>&nbsp;';
                    html += '<a href="/reviewhistory/' + product.productId + '" class="btn btn-dark">Review history</a>&nbsp;';
                    html += '<a href="/pricehistory/' + product.productId + '" class="btn btn-dark">Price history</a>';
                    html += '</p>';
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