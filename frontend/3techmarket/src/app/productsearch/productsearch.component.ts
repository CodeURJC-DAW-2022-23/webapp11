import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../services/productsearch.service';

@Component({
  selector: 'app-product-search',
  templateUrl: './productsearch.component.html',
  styleUrls: ['./productsearch.component.css']
})
export class ProductSearchComponent implements OnInit {
  product: string = '';
  results: any[] = [];
  page: number = 0;
  size: number = 10;
  total: number = 0;

  constructor(private route: ActivatedRoute, private productService: ProductService) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.product = params.get('product') || '';
      this.searchProducts();
    });
  }

  searchProducts() {
    this.page = 0;
    this.productService.searchProducts(this.product, this.page, this.size)
      .subscribe((response: any) => {
        this.total = response.totalElements;
        this.results = response.content;
      });
  }

  loadMore() {
    this.page++;
    this.productService.searchProducts(this.product, this.page, this.size)
      .subscribe((response: any) => {
        this.results = this.results.concat(response.content);
        this.total = response.totalElements;
      });
  }
}
