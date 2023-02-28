function rate(){
    let img =document.getElementById("starRating");
    let imgProduct = document.getElementById("productRating");
    let int =document.getElementById("rate").textContent;
    let int2 =document.getElementById("productRate").textContent;

    img.src="/"+ int + ".png"
    imgProduct.src="/"+ int2 + ".png"
}

rate();