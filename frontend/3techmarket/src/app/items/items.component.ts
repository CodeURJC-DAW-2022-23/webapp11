import {Component, OnInit} from '@angular/core';
import {ProductService} from "../services/product.service";


@Component({
  selector: 'items-root',
  templateUrl: 'items.component.html',
  providers:[ProductService]

})



export class ItemsComponent implements OnInit{
name = 'Santiago';
products = [];
  constructor(private productService: ProductService) { }
  ngOnInit() {
    let products : any = []
     this.productService.getProducts().subscribe(
       response => {
         let data : any = response;
         for(let i = 0; i < data.content.length; i++){
           products.push(data.content[i]);
         }

       }
     );
    console.log(products);


  };





}
