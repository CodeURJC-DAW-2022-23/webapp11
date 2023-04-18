import { Component, OnInit } from '@angular/core';
import { AuthService} from "../services/auth.service";
import { Router } from "@angular/router";
import { ProductService} from "../services/product.service";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent {
  loading: boolean = true;
  items: any[] = [];
  page: number = 0;
  size: number = 50;  // Big number to get all items
  totalPrice: number = 0;
  loadingbtn: boolean = false;
  address: string = '';
  constructor(private authService: AuthService, private router: Router, private productService: ProductService) { }

  ngOnInit() {
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
      if (this.items.length > 0) {
        for (let item of this.items) {
          this.totalPrice += item.productPrice;
        }
      }
      this.loading = false;
    });
  }

  checkout(address: string) {
    this.loadingbtn = true;
    this.productService.checkout(address).subscribe((response: any) => {
      this.router.navigate(['/purchases']);
    });
  }

  protected readonly environment = environment;
}
