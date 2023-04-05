import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/productsearch.service';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.css']
})
export class ProductSearchComponent implements OnInit {
  results: any[] = [];
  product: string = '';
  page: number = 0;
  size: number = 10;
  total: number = 0;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
  }

  searchProducts() {
    this.page = 0;
    this.productService.searchProducts(this.product, this.page, this.size)
      .subscribe((response: any) => {  // Use subscribe() to get the response because searchProducts() returns an Observable
        this.results = response.results;
        this.total = response.total;
      });
  }

  loadMore() {
    this.page++;
    this.productService.searchProducts(this.product, this.page, this.size)
      .subscribe((response: any) => {
        this.results = this.results.concat(response.results);
        this.total = response.total;
      });
  }
}

export class ProductsearchComponent {
}
