import { Component, OnInit } from '@angular/core';
import { AuthService } from "../services/auth.service";
import { Router } from "@angular/router";
import { ProductService } from "../services/productsearch.service";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent implements OnInit {

  items: any[] = [];
  total: number = 0;
  page: number = 0;
  size: number = 10;
  hasItems: boolean = false;
  hasMore: boolean = false;
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
      this.getWishlist();
    });
  }

  getWishlist() {
    this.productService.getWishlist(this.page, this.size).subscribe((response: any) => {
      this.items = response.content;
      this.total = response.totalElements;
      this.hasMore = response.last === false;  // If it's not the last page, there are more items
      this.hasItems = this.total > 0; // If there are items, show them


    });
  }


  loadMore() {
    this.loadingMore = true;
    this.page++;
    this.productService.getWishlist(this.page, this.size).subscribe((response: any) => {
      this.items = this.items.concat(response.content);
      this.hasMore = response.last === false;
      this.loadingMore = false;
    });
  }

  removeFromWishlist(id: string) {
    this.deletingItemId = id;
    this.productService.removeFromWishlist(id).subscribe((response: any) => {
      this.getWishlist();
    });

  }
}
