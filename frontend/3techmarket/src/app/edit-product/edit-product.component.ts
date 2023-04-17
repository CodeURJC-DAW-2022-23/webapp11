import { Component, OnInit } from '@angular/core';
import { ActivatedRoute,Router} from '@angular/router';
import {AuthService} from "../services/auth.service";
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import {ProductService} from "../services/product.service";


@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit{
  constructor(private router: ActivatedRoute, private route: Router, private auth:AuthService,
              private productService:ProductService) {
  }

  productId: string = '';
  description: string = '';
  mainImage: File| undefined;
  images: File[] | undefined;
  productName: string = '';
  productPrice: string = '';
  productStock: string = '';
  tags: string = '';


  ngOnInit() {
    this.auth.authdetails().pipe(
      catchError((error) => {
        this.route.navigate(['/forbidden']);
        return throwError(error);
      })
    ).subscribe((response: any) => {
      if(response.roles.includes('ADMIN')){
        let id = this.router.snapshot.paramMap.get('id') || '';
        this.getproduct(id);


      }
    })
  }

  getproduct(id:string){
    this.productService.getProduct(id).subscribe((response:any)=>{
      this.productId=response.productId;
      this.description=response.description;
      this.productName=response.productName;
      this.productPrice=response.productPrice;
      this.productStock=response.productStock;
      this.tags=response.tags;


    })

  }

  editProduct(productName:string,description:string,productPrice:string,productStock:string,tags:string,mainImage:File | undefined,images:File[] | undefined){
    this.productService.editProductService(productName,description,productPrice,productStock,tags,mainImage,images).subscribe((response:any)=>{
      this.ngOnInit();
    })

  }

  onImageChange(event: any) {
    this.mainImage = event.target.files[0];
  }
  onImagesChange(event: any) {
    this.images = event.target.files;
  }

}
