import { Component, OnInit } from '@angular/core';

import { ProductService } from '../services/productsearch.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  results: any[] = [];
  page: number = 0;
  size: number = 10;
  total: number = 0;
  loadingMore : boolean = false;

  removingProductId: string = '';
  constructor(private productService: ProductService) {
  }

  ngOnInit() {
    this.getProducts()
  }

  getProducts() {

    this.productService.getProducts(this.page, this.size)
      .subscribe((response: any) => {
        this.total = response.totalElements;
        this.results = response.content;
      });


  }
  loadMore() {
    this.page++;
    this.loadingMore = true;
    this.productService.getProducts(this.page, this.size)
      .subscribe((response: any) => {
        this.results = this.results.concat(response.content);
        this.loadingMore = false;
      });
  }

  removeFromStock(productId: string) {
    this.removingProductId = productId;
    this.productService.removeFromStock(productId)
      .subscribe((response: any) => {
        this.getProducts();
        this.removingProductId = '';

      });

  }

}
