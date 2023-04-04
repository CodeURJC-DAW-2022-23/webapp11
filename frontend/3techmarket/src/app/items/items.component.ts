import {Component, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'items-root',
  templateUrl: './items.component.html',

})



export class ItemsComponent implements OnInit{
name = 'Santiago';
  constructor(private httpClient: HttpClient) { }
  ngOnInit() {
    this.httpClient.get('https://localhost:8443/api/products?page=1&size=4').subscribe(response => {
      console.log(response);
      
    });
  }


}
