import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = '/api/products';

  constructor(private http: HttpClient) {
  }

  searchProducts(product: string, page: number, size: number) {  // We don't need to specify the Observable type because it's inferred
    const url = `${this.baseUrl}/search/?product=${product}&page=${page}&size=${size}`;
    return this.http.get(url);
  }

  getProducts(page: number, size: number) {
    const url = `${this.baseUrl}?page=${page}&size=${size}`;
    return this.http.get(url);
  }

  getCart(page: number, size: number) {
    const url = `/api/cart?page=${page}&size=${size}`;
    return this.http.get(url);
  }

  getWishlist(page: number, size: number) {
    const url = `/api/wishlist?page=${page}&size=${size}`;
    return this.http.get(url);
  }

  removeFromCart(productId: string) {
    const url = `/api/cart/removeProduct/${productId}`;
    return this.http.delete(url);
  }

  removeFromWishlist(productId: string) {
    const url = `/api/wishlist/removeProduct/${productId}`;
    return this.http.delete(url);
  }


  removeFromStock(productId: string) {
    const url = `/api/products/remove-from-stock/${productId}`;
    return this.http.get(url);
  }

  checkout(address: string) {
    const url = `/api/checkout`;
    return this.http.post(url, address);
  }

  addToCart(productId: string) {
    const url = `/api/cart/addProduct/${productId}`;
    return this.http.post(url, null);
  }

  addProduct(productName:string,description:string,price:number,amount:number,tags:string[],image:File | undefined,images:FileList | undefined){
    const url = `/api/products/add-product`;
    const formData = new FormData();
    formData.append('productName', productName);
    formData.append('description', description);
    formData.append('price', price.toString());
    formData.append('amount', amount.toString());
    formData.append('tags', tags.toString());
    if (image) {
      formData.append('image', image);
    }
    if (images) {
      for (let i = 0; i < images.length; i++) {
        formData.append('images', images[i]);
      }

    }
    return this.http.post(url, formData);
  }
}
