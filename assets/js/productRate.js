function rate(){
    let img =document.getElementById("starRating");
    let int =document.getElementById("rate").textContent;

    img.src="../assets/images/"+ int + ".png"
}

rate();