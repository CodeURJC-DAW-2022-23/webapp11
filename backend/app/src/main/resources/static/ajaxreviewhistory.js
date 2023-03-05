$(document).ready(function() {
    const total = $("#total").val();
    const id = $("#id").val();
    let start = 10;
    $("#loadMore").click(function() {
        $.ajax({
            url: "/reviewhistory/" + id + "/loadmore",
            type: "GET",
            data: {
                start: start
            },
            // Add a bootstrap spinner while loading
            beforeSend: function() {
                $("#loadMore").html('<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Loading...');
            },
            success: function(data) {
                const reviews = JSON.parse(data).data;  // we need to add the .data to get the array of products, otherwise we get the whole response
                let html = '';
                reviews.forEach(function(review) {
                    html += '<div class="row item">';
                    html += '<div class="col-md-4">';
                    html += '<img src="/' + review.user.id + '/userpfp" alt="User profile picture" width="200" height="200">';
                    html += '</div>';
                    html += '<div class="col-md-6">';
                    html += '<h4>' + review.user.email + '</h4>';
                    html += '<br>'
                    html += '<h5>' + review.reviewTitle + '</h5>';
                    html += '<p class="description">Stars: ' + review.rating + '</p>';
                    html += '<p class="description">' + review.reviewText + '</p>';
                    html += '</div>';
                    html += '<div class="col-md-2">';
                    html += '<button class="btn btn-outline-danger" onclick="window.location.href=\'/removereview/' + review.id + '\'">Remove</button>';
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