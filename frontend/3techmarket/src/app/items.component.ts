import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'items-root',
  templateUrl: './items.component.html',

})



export class ItemsComponent {


  constructor(private httpClient: HttpClient){}


  getRecommendedproducts(){
    let url = 'https://localhost:8443/api/recommendations'
    this.httpClient.get(url).subscribe(
      response => {
          let data : any = response;
          console.log(data)


      })
  }
  


}
