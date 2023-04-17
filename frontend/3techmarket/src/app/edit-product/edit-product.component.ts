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
  produtPrice: string = '';
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
      this.produtPrice=response.productPrice;
      this.productStock=response.productStock;
      this.tags=response.tags;


    })

  }

}
