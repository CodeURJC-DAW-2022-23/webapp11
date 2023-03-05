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
                purchases.forEach(function(purchase) {
                    html += '<div class="row purchasedetails">';
                    html += '<div class="col-md-4">';
                    html += '<img src="/product/' + purchase.product.productId + '/image" alt="thumbnail" width="200" height="200">';
                    html += '</div>';
                    html += '<div class="col-md-2">';
                    html += '<h3>' + purchase.product.productName + '</h3>';
                    html += '<p class="details">Date: <span class="badge text-bg-info">' + purchase.timestamp + '</span></p>';
                    html += '<p class="details">' + purchase.product.productPrice + '€</p>';
                    html += '</div>';
                    html += '<div class="col-md-4">';
                    html += '<h3>Shipping address and payment method</h3>';
                    html += '<p class="details">Address: <b>' + purchase.address + '</b></p>';
                    html += '<p class="details">Payment method: <b>' + purchase.paymentMethod + '</b></p>';
                    html += '<p class="details"><a href="/invoice/' + purchase.purchaseId + '">';
                    html += '<button type="button" class="btn btn-info">Download your invoice (PDF)</button>';
                    html += '</a></p>';
                    html += '</div>';
                    html += '<div class="col-md-2">';
                    if (purchase.isCancelled) {
                        html += '<button type="button" class="btn btn-outline-secondary btn-lg mt-2" disabled>This order has been cancelled</button>'
                    } else {
                        html += '<button type="button" onclick="window.location.href=\'/product/' + purchase.product.productId + '/review\'" class="btn btn-outline-success btn-lg mt-1">Leave a review</button>'
                        html += '<br>';
                        html += '<button type="button" onclick="window.location.href=\'/product/' + purchase.product.productId + '/report\'" class="btn btn-outline-primary btn-lg mt-2">Contact support</button>'
                        html += '<br>';
                        html += '<button type="button" onclick="window.location.href=\'/return/' + purchase.purchaseId + '\'" class="btn btn-outline-secondary btn-lg mt-2">Return product</button>'
                        }
                    html += '</div>';
                    html += '</div>';
                    html += '<hr>';
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