import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../services/product.service';

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

  constructor(private route: ActivatedRoute, private productService: ProductService) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.product = params.get('product') || '';
      this.searchProducts();
    });
  }

  searchProducts() {
    this.page = 0;
    this.total = 0;
    this.productService.searchProducts(this.product, this.page, this.size)
      .subscribe((response: any) => {
        this.total = response.totalElements;
        this.results = response.content;
        console.log(this.total)

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
}
