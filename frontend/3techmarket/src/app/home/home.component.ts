import { Component, OnInit } from '@angular/core';
import { RecommendationService } from '../services/recommendations.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  recommendedProducts: any[] = [];
  anonymous: boolean = false;
  constructor(private recommendations: RecommendationService) {
  }
  ngOnInit():void{
    this.isLoggedIn()
    if (this.anonymous == false) {
      this.getRecommendedProducts()
    }
    else {

    }
  }
  getRecommendedProducts(){
    this.recommendations.getRecommendedProducts()
      .subscribe((response: any ) => {
        this.recommendedProducts = response;
      })
  }
  isLoggedIn(){

  }
}

