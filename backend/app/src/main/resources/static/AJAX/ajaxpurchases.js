$(document).ready(function() {
    const total = $("#total").val();
    let start = 10;
    $("#loadMore").click(function() {
        $.ajax({
            url: "/purchases/loadmore",
            type: "GET",
            data: {
                start: start
            },
            // Add a bootstrap spinner while loading
            beforeSend: function() {
                $("#loadMore").html('<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Loading...');
            },
            success: function(data) {
                const purchases = JSON.parse(data).data;  // we need to add the .data to get the array of products, otherwise we get the whole response
                let html = '';
                products.forEach(function(product) {
                    //<div className="row purchasedetails">
                    html += '<div class="row purchasedetails">';
                    html += '<div class="col-md-2">';
                    html += '<img src="/product/' + purchases.product.productId + '/image" alt="thumbnail" width="150" height="150">';
                    html += '</div>';
                    html += '<div class="col-md-4">';
                    html += '<h3>' + purchases.product.productName + '</h3>';
                    html += '<p class="details">Date: <span class="badge text-bg-info">' + purchases.timestamp + '</span></p>';
                    html += '<p class="details">' + purchases.product.price + '€</p>';
                    html += '</div>';
                    html += '<div class="col-md-4">';
                    html += '<h3>Shipping address and payment method</h3>';
                    html += '<p class="details">Address: <b>' + purchases.address + '</b></p>';
                    html += '<p class="details">Payment method: <b>' + purchases.paymentMethod + '</b></p>';
                    html += '<p class="details"><a href="/invoice/' + purchases.purchaseId + '">';
                    html += '<button type="button" class="btn btn-info">Download your invoice (PDF)</button>';
                    html += '</a></p>';
                    html += '</div>';
                    html += '<div class="col-md-2">';
                    html += '<button type="button" onclick="window.location.href=\'/product/' + purchases.product.id + '/review\'" class="btn btn-outline-success btn-lg mt-1">Leave a review</button>';
                    html += '<br>';
                    html += '<button type="button" onclick="window.location.href=\'/messages\'" class="btn btn-outline-primary btn-lg mt-2">Contact support</button>';
                    html += '<br>';
                    html += '<button type="button" onclick="window.location.href=\'/return/' + purchases.purchaseId + '\'" class="btn btn-outline-secondary btn-lg mt-2">Return product</button>';
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