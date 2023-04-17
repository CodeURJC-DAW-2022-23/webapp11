import {Component, OnInit } from '@angular/core';
import { ProductService} from "../services/product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit{
  data: any;
  id:string = this.activatedRoute.snapshot.paramMap.get('id') || '';
  images: any[] = [];

  constructor(private productService: ProductService, private router: Router, private activatedRoute:ActivatedRoute) { }

  ngOnInit(){
    this.productService.getProduct(this.id).subscribe((response: any) => {
      this.data = response;
      this.images = response.images;
    });
  }





}
