import { Component, OnInit } from '@angular/core';
import {environment} from "../../environments/environment";
import { AuthService } from "../services/auth.service";
import { ProductService } from "../services/product.service";
import { ReviewsService } from "../services/reviews.service";
import { Router, ActivatedRoute } from "@angular/router";
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-addreview',
  templateUrl: './addreview.component.html',
  styleUrls: ['./addreview.component.css']
})
export class AddreviewComponent implements OnInit{
  data: any;
  id: string = this.activatedRoute.snapshot.paramMap.get('id') || '';
  rating: number = 0;
  reviewText: string = '';
  reviewTitle: string = '';
  images: File[] = [];

  constructor(private authService:AuthService, private reviewService: ReviewsService,private router: Router,
              private productService:ProductService,  private activatedRoute: ActivatedRoute) {
  }

  protected readonly environment = environment;

  ngOnInit(): void {
    this.authService.authdetails().pipe(
      catchError((error) => {
        this.router.navigate(['/forbidden']);
        return throwError(error);
      })
    ).subscribe((data) => {
      this.getProduct();
    });
  }

  private getProduct() {
    this.productService.getProduct(this.id).subscribe((response:any) =>{
      this.data = response
    });
  }

  addreview(rating: number, reviewText: string, images: File[], reviewTitle: string) {
    this.reviewService.addReview(rating,reviewText,images,reviewTitle,this.id).subscribe((response:any)=>{
      this.router.navigate(["/"]);
    });
  }
}
