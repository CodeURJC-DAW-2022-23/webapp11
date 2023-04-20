import { Component, OnInit } from '@angular/core';
import { PurchaseService } from '../services/purchase.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-recentorders',
  templateUrl: './recentorders.component.html',
  styleUrls: ['./recentorders.component.css']
})
export class RecentordersComponent implements OnInit {
  purchases: any;
  loading: boolean = true;
  deletingPurchaseId: string = '';

  constructor(private purchaseService: PurchaseService, private authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.authService.authdetails().pipe(
      catchError((error) => {
        this.router.navigate(['/forbidden']);
        return throwError(error);
      })
    ).subscribe((data) => {
      if (data.roles.includes('ADMIN')) {
        this.router.navigate(['/forbidden']);
      }
      this.purchaseService.getRecentOrders().subscribe((data: any) => {
        this.purchases = data.content;
        this.loading = false;
      });
    });
  }

  returnProduct(id: string) {
    this.deletingPurchaseId = id;
    this.purchaseService.returnProduct(id).subscribe((data) => {
      this.purchaseService.getRecentOrders().subscribe((data: any) => {
        this.purchases = data.content;
      });
    });
  }

  getPDF(id: string) {
    this.purchaseService.getPDF(id).subscribe((data) => {
      const blob = new Blob([data], {type: 'application/pdf'});
      const url = window.URL.createObjectURL(blob);
      window.open(url);
    });
  }

    protected readonly environment = environment;
}
