$(document).ready(function() {
    const total = $("#total").val();
    let start = 10;
    $("#loadMore").click(function() {
        $.ajax({
            url: "/chats/loadmore",
            type: "GET",
            data: {
                start: start
            },
            // Add a bootstrap spinner while loading
            beforeSend: function() {
                $("#loadMore").html('<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Loading...');
            },
            success: function(data) {
                const users = JSON.parse(data).data;  // we need to add the .data to get the array of products, otherwise we get the whole response
                let html = '';
                users.forEach(function(user) {
                    html += '<div class="row item">';
                    html += '<div class="col-md-4">';
                    html += '<img src="/' + user.id + '/userpfp" alt="User profile image" class="img-fluid" width="150" height="150">';
                    html += '</div>';
                    html += '<div class="col-md-4">';
                    html += '<h3>' + user.firstName + '</h3>';
                    html += '<h4>' + user.email + '</h4>';
                    html += '</div>';
                    html += '<div class="col-md-4">';
                    html += '<button class="btn btn-outline-primary" onclick="window.location.href=\'/messages/' + user.id + '\'">Access chat</button>';
                    html += '<br><br>';
                    html += '</div>';
                    html += '</div>';
                    html += '<hr>';
                });
                $("#chats").append(html);
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