import { Component, OnInit } from '@angular/core';
import { RecommendationService } from '../services/recommendations.service';
import { AuthService } from '../services/auth.service';
import {ProductService} from "../services/product.service";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  recommendedProducts: any[] = [];
  anonymous: boolean = false;

  constructor(private recommendations: RecommendationService, private auth: AuthService,
              private productService:ProductService) {
  }

  ngOnInit(): void {
    this.isLoggedIn()
    if (this.anonymous === false) {
      this.getRecommendedProducts()
    } else {
      this.getProducts()

    }
  }

  getRecommendedProducts() {
    this.recommendations.getRecommendedProducts().pipe(
      catchError((error) =>{
        this.getProducts();
        return throwError(error);
      })
    )
      .subscribe((response: any) => {
        this.recommendedProducts = response;
      })
  }

  isLoggedIn() {
    this.auth.authdetails().pipe(
      catchError((error)=> {
        this.anonymous = true
        return throwError(error);
      })
    ).subscribe((response: any) => {
      this.anonymous = false

    });
    console.log(this.anonymous)
  }
  getProducts(){
    this.productService.getProducts(0,30).subscribe(
      (response:any) => {

        for (let i=0;i<4;i++){
          let num = Math.floor(Math.random()*30)

          this.recommendedProducts.push(response.content[num])



        }


      }
    )

  }
}

