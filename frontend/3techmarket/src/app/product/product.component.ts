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

  constructor(private productService: ProductService, private router: Router, private activatedRoute:ActivatedRoute, private reviewsService: ReviewsService) { }

  ngOnInit(){
    this.productService.getProduct(this.id).subscribe((response: any) => {
      this.data = response;
      this.images = response.images;
      this.reviews = response.reviews;
      this.pfps = this.getProfilePictures(this.reviews);
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

  protected readonly environment = environment;

  private getProfilePictures(reviews: any[]) {
    let pfps: any[] = [];
    for (let review of reviews) {
      this.reviewsService.getEmailbyReviewId(review.reviewId)
        .subscribe((response: any) => {
          this.emails.push(response);
        });
      this.reviewsService.getPfpId(review.reviewId)
        .subscribe((response: any) => {
          this.pfps.push(response);

        });
    }
    return pfps;
  }
}
