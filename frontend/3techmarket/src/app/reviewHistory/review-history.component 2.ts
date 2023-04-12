import { Component,OnInit } from '@angular/core';
import { ReviewsService } from '../services/reviews.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-review-history',
  templateUrl: './review-history.component.html',
  styleUrls: ['./review-history.component.css']
})
export class ReviewHistoryComponent implements OnInit {
 total: number = 0;
  constructor(private reviewsService: ReviewsService,private activatedRoute: ActivatedRoute) {
  }
  reviews: any[] = [];
  loading: boolean = true;
  emails: string[] = [];

  ngOnInit(): void {
    let id = this.activatedRoute.snapshot.paramMap.get('id') || ''
    this.getProductReviews(id);
    this.loading = false;
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
        });
      });
  }

}
