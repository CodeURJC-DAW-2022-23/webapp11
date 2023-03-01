function rate(){
    let img =document.getElementById("starRating");
    let imgProduct = document.getElementById(productRating);
    let int =document.getElementById("rate").textContent;
    let int2 =document.getElementById("productRate").textContent;

    img.src="../assets/images/"+ int + ".png"
    imgProduct.src="../assets/images/"+ int2 + ".png"
}

rate();