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
    return this.http.get(url, { responseType: 'text' });
  }

  removeReview(reviewId: string) {
    const url = `${this.baseUrl}/delete/${reviewId}`;
    return this.http.delete(url);
  }

  getPfpId(reviewId: string) {
    const url = `${this.baseUrl}/${reviewId}/user/pfp`;
    return this.http.get(url, { responseType: 'text' });
  }

  addReview(rating: number, reviewText: string, images: File[] | undefined, reviewTitle: string, productId: string){
    const url = `${this.baseUrl}/create/${productId}`;
    const formData = new FormData();
    formData.append('rating', String(rating));
    formData.append('reviewText', reviewText);
    formData.append('reviewTitle', reviewTitle);
    if (images !== undefined) {
      for (const image of images) {
        formData.append('images', image);
      }
    }
    return this.http.post(url, formData);
  }
}
