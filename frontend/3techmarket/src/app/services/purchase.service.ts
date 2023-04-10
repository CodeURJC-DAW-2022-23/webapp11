import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

  constructor(private http: HttpClient) { }

  getRecentOrders() {
    const url = '/api/purchase-history?size';
    return this.http.get(url);
  }

  returnProduct(id: string) {
    const url = '/api/user/return-purchase/' + id;
    return this.http.post(url, {});
  }

  getPDF(id: string) {
    const url = '/api/user/purchase/generate-pdf/' + id;
    return this.http.get(url, {responseType: 'blob'});
  }
}
