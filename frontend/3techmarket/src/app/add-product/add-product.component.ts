import { Component,OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import{Router} from "@angular/router";
import {AuthService} from "../services/auth.service";
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import {ProductService} from "../services/product.service";


@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit{

  productName:string ='';
  description:string ='';
  price:string = "";
  amount:string = "";
  tags:string = "";
  loading:boolean = true;
  image: File | undefined;
  images: File[] | undefined= [];

  constructor(private http:HttpClient, private router:Router,private authService:AuthService,
              private productService: ProductService) { }

  ngOnInit() {
    this.authService.authdetails().pipe(
      catchError((error) => {
        this.router.navigate(['/forbidden']);
        return throwError(error);
      })
    ).subscribe((response: any) => {
      this.loading=false;

    });
  }
  addProduct(productName:string,description:string,price:string,amount:string,tags:string,image:File | undefined,images:File[] | undefined){
      this.productService.addNewProduct(productName,description,price,amount,tags,image,images).subscribe((response:any)=>{
        this.router.navigate(['/dashboard']);
      });

  }

  onImageUpload(event: any) {
    this.image= event.target.files[0];

  }

  onImagesUpload(event: any) {
    this.images= event.target.files;

  }


}



