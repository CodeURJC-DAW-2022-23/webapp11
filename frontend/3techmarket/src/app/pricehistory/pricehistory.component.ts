import {Component, OnInit} from '@angular/core';
import * as Highcharts from 'highcharts';
import {ReviewsService} from "../services/reviews.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";
import {PricesService} from "../services/prices.service";

@Component({
  selector: 'app-pricehistory',
  template: `
    <highcharts-chart
      [Highcharts]="Highcharts"
      [options]="chartOptions"
      style="width: 100%; height: 400px;"
    ></highcharts-chart>
  `,
  styleUrls: ['./pricehistory.component.css']
})
export class PricehistoryComponent implements OnInit {
  total: number = 0;

  constructor(private pricesService: PricesService, private activatedRoute: ActivatedRoute,
              private authService: AuthService, private router: Router) {
  }

  Highcharts: typeof Highcharts = Highcharts;

  chartOptions: Highcharts.Options = {
    chart: {
      renderTo: 'container',
      type: 'bar',
      inverted: true
    },
    title: {
      text: 'Loading...'
    },
    xAxis: {
      title: {
        text: 'Changes'
      }
    },
    yAxis: {
      title: {
        text: 'Prices'
      }
    },
    series: [{
      name: 'Price',
      type: 'line',
      data: [0, 0, 0]
    }]
  };

  prices: any[] = [];
  priceHistory: number[] = [];
  changes: number[] = [];
  name: string = "";
  loading: boolean = true;

  ngOnInit(): void {
    this.authService.authdetails().pipe(
      catchError((error) => {
        this.router.navigate(['/forbidden']);
        return throwError(error);
      })
    ).subscribe((response) => {
      if (response.roles.includes('ADMIN')) {
        let id = this.activatedRoute.snapshot.paramMap.get('id') || ''
        this.getProductPrices(id);
        const message = `Price history for id: ${id}`;
        this.loading = false;
        setTimeout(() => {
          this.chartOptions = {
            title: {
              text: message
            },
            series: [{
              name: 'Price',
              type: 'line',
              data: this.priceHistory
            }]
          };
          Highcharts.chart('app-line-chart', this.chartOptions);

        }, 1000)
      }
    });
  }

  getProductPrices(productId: string) {
    this.pricesService.getPrices(productId)
      .subscribe((response: any) => {
        this.prices = response
        this.prices.forEach((price: any) => {
          this.priceHistory.push(parseInt(price.toString()));
        });
      })
  }
}
