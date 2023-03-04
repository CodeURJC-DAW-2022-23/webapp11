function rate(){

    let img =document.getElementById("starRating");
    let int =document.getElementById("rate");

    if (int!=null){
        img.src="/"+ int.textContent + ".png"
    }

}

rate();