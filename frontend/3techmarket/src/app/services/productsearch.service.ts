import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = '/api/products';

  constructor(private http: HttpClient) { }

  searchProducts(product: string, page: number, size: number) {  // We don't need to specify the Observable type because it's inferred
    const url = `${this.baseUrl}/search/?product=${product}&page=${page}&size=${size}`;
    return this.http.get(url);
  }
}

export class ProductsearchService {
}
