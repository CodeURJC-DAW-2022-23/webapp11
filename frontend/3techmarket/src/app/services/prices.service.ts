import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PricesService {
  private baseUrl = '/api/products/price-history';
  private baseUrl2 = '/api/products/product-name';

  constructor(private http: HttpClient) { }

  getPrices(productId: string) : Observable<any> {
    const url = `${this.baseUrl}/${productId}`;
    return this.http.get(url);
  }

}
