import {Component, OnInit } from '@angular/core';
import { ProductService} from "../services/product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";
import {AuthService} from "../services/auth.service";
import {environment} from "../../environments/environment"
import {ReviewsService} from "../services/reviews.service";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit{
  data: any;
  id:string = this.activatedRoute.snapshot.paramMap.get('id') || '';
  images: any[] = [];
  reviews:any[] = [];
  addingToCartItemId: string = '';
  addingToWishlistItemId: string = '';
  pfps: any [] = [];
  emails: string [] = [];
  reviewImages: any[] = [];

  constructor(private productService: ProductService, private router: Router, private activatedRoute:ActivatedRoute, private reviewsService: ReviewsService) { }

  ngOnInit(){
    this.productService.getProduct(this.id).subscribe((response: any) => {
      this.data = response;
      this.images = response.images;
      this.reviews = response.reviews;
      this.reviewImages = response.reviews.images;
      this.getProductReviews(this.id);
    });
  }

  addToCart(id: string){
    this.addingToCartItemId = id;
    this.productService.addToCart(id).subscribe((response: any) => {
      this.addingToCartItemId = '';
    });
  }

  addToWishlist(id: string){
    this.addingToWishlistItemId = id;
    this.productService.addToWishlist(id).subscribe((response: any) =>{
      this.addingToWishlistItemId = '';
    });
  }

  getProductReviews(productId: string) {
    this.reviewsService.getProductReviews(productId)
      .subscribe((response: any) => {
        this.reviews = response;
        this.reviews.forEach((review: any) => {
          this.reviewsService.getEmailbyReviewId(review.reviewId)
            .subscribe((response: any) => {
              this.emails.push(response);
            });
          this.reviewsService.getPfpId(review.reviewId)
            .subscribe((response: any) => {
              this.pfps.push(response);


            });
        });

      });
  }


  protected readonly environment = environment;
}
