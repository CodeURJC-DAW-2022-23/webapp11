import { Component, OnInit } from '@angular/core';
import { AuthService } from "../services/auth.service";
import { Router } from "@angular/router";
import { ProductService } from "../services/product.service";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  items: any[] = [];
  total: number = 0;
  page: number = 0;
  size: number = 10;
  hasItems: boolean = false;
  hasMore: boolean = false;
  totalPrice: number = 0;
  loadingMore: boolean = false;
  loading: boolean = true;
  deletingItemId: string = '';

  constructor(private authService: AuthService, private router: Router, private productService: ProductService) { }

  ngOnInit() {
    this.page = 0;
    this.total = 0;
    this.authService.authdetails().pipe(
      catchError((error) => {
        this.router.navigate(['/forbidden']);
        return throwError(error);
      })
    ).subscribe((response: any) => {
      // If it is an admin, he should not be here
      if (response.roles.includes('ADMIN')) {
        this.router.navigate(['/forbidden']);
      }
      this.getCart();
    });
  }

  private getCart() {
    this.productService.getCart(this.page, this.size).subscribe((response: any) => {
      this.items = response.content;
      this.total = response.totalElements;
      this.hasMore = response.last === false;  // If it's not the last page, there are more items
      this.hasItems = this.total > 0;
      // Calculate the total price
      for (let item of this.items) {
        this.totalPrice += item.productPrice;
      }
      parseFloat(this.totalPrice.toFixed(2));
      this.loading = false;
    });
  }

  loadMore() {
    this.loadingMore = true;
    this.page++;
    this.productService.getCart(this.page, this.size).subscribe((response: any) => {
      this.items = this.items.concat(response.content);
      this.hasMore = response.last === false;
      this.loadingMore = false;
    });
  }

  removeFromCart(productId: string) {
    this.deletingItemId = productId;
    this.productService.removeFromCart(productId).subscribe((response: any) => {
      this.getCart();
    });

  }
}
