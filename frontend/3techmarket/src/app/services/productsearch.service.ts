import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = 'https://localhost:8443/api/products';

  constructor(private http: HttpClient) { }

  searchProducts(product: string, page: number, size: number) {
    const url = `${this.baseUrl}/search/?product=${product}&page=${page}&size=${size}`;
    return this.http.get(url);
  }
}

export class ProductsearchService {
}