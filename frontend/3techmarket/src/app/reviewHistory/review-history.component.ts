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

  ngOnInit(): void {
    let id = this.activatedRoute.snapshot.paramMap.get('id') || ''
    this.getProductReviews(id);


  }

  getProductReviews(productId: string) {
    this.reviewsService.getProductReviews(productId)
      .subscribe((response: any) => {
        for(let i=0;i<response.length;i++){
          this.total += 1;
          this.reviews.push(response[i]);
        }
        console.log(this.reviews)
      });
  }

  getEmail(reviewId:string) {
    this.reviewsService.getEmailbyReviewId(reviewId)
      .subscribe((response: any) => {
        console.log(response.content)
      });



  }

}
