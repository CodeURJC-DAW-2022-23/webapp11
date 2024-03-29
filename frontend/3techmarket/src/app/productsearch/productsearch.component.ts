import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../services/product.service';
import {environment} from "../../environments/environment";

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
  loadingMore: boolean = false;
  hasMore: boolean = false;
  loading: boolean = true;

  constructor(private route: ActivatedRoute, private productService: ProductService) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.product = params.get('product') || '';
      this.searchProducts();
    });
  }

  searchProducts() {
    this.loading = true;
    this.page = 0;
    this.total = 0;
    this.productService.searchProducts(this.product, this.page, this.size)
      .subscribe((response: any) => {
        this.total = response.totalElements;
        this.results = response.content;
        this.hasMore = response.last === false;  // If it is not the last page, there are more results
        this.loading = false;
      });
  }

  loadMore() {
    this.loadingMore = true;
    this.page++;
    this.productService.searchProducts(this.product, this.page, this.size)
      .subscribe((response: any) => {
        this.results = this.results.concat(response.content);
        this.hasMore = response.last === false;
        this.loadingMore = false;
      });
  }

  protected readonly environment = environment;
}
