import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class RecommendationService {

  constructor(private http:HttpClient) { }

  getRecommendedProducts(){
    const url = '/api/recommendations'
    return this.http.get(url)

  }
}
