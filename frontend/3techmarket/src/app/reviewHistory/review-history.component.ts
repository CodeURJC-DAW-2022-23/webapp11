import { Component,OnInit } from '@angular/core';
import { ReviewsService } from '../services/reviews.service';
import { ActivatedRoute,Router } from '@angular/router';
import {AuthService} from "../services/auth.service";
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import {environment} from "../../environments/environment";


@Component({
  selector: 'app-review-history',
  templateUrl: './review-history.component.html',
  styleUrls: ['./review-history.component.css']
})
export class ReviewHistoryComponent implements OnInit {
 total: number = 0;
  constructor(private reviewsService: ReviewsService,private activatedRoute: ActivatedRoute,
              private authService: AuthService,private router: Router) {
  }
  reviews: any[] = [];
  loading: boolean = true;
  emails: string[] = [];
  removingReviewId: string = '';
  pfps : any[] = [];


  ngOnInit(): void {
    this.authService.authdetails().pipe(
        catchError((error) => {
            this.router.navigate(['/forbidden']);
            return throwError(error);
        })
    ).subscribe((response) => {
      if(response.roles.includes('ADMIN')){
        let id = this.activatedRoute.snapshot.paramMap.get('id') || ''
        this.getProductReviews(id);
        this.loading = false;

      }
    });



  }

  getProductReviews(productId: string) {
    this.reviewsService.getProductReviews(productId)
      .subscribe((response: any) => {
        this.reviews = response;
        this.total = response.length;
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

  removeReview(reviewId: string) {
    this.removingReviewId = reviewId;
    this.reviewsService.removeReview(reviewId)
    .subscribe((response: any) => {
      this.removingReviewId=''
      this.ngOnInit();

    });
  }


  protected readonly environment = environment;
}
