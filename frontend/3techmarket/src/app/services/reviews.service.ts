import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReviewsService {

  private baseUrl = '/api/reviews';

  constructor(private http: HttpClient) { }

  getProductReviews(productId: string) {
    const url = `${this.baseUrl}/get/${productId}`;
    return this.http.get(url);
  }

  getEmailbyReviewId(reviewId: string) {
    const url = `${this.baseUrl}/${reviewId}/user-email`;
    return this.http.get(url);
  }
}
