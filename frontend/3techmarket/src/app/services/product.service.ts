import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = '/api/products';

  constructor(private http: HttpClient) {
  }

  getProduct(id: string) {
    const url = `${this.baseUrl}/info/${id}`;
    return this.http.get(url);
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

  addNewProduct(productName:string,description:string,price:string,amount:string,tags:string,image:File | undefined,images:File[] | undefined) {
    const url = `/api/products/add-product`;
    const formData = new FormData();
    formData.append('productName', productName);
    formData.append('description', description);
    formData.append('price', price);
    formData.append('amount', amount);
    formData.append('tags', tags);
    if (image !== undefined){
      formData.append('image', image);
    }
    if (images !== undefined){
    for (let i = 0; i < images.length; i++) {
      formData.append('images', images[i]);
      }
    }

    return this.http.post(url, formData);


  }

  editProductService(productName:string,description:string,productPrice:string,productStock:string,tags:string,image:File | undefined,images:File[] | undefined) {
    const url = `${this.baseUrl}`;
    const formData = new FormData();
    formData.append('productName', productName);
    formData.append('description', description);
    formData.append('productPrice', productPrice);
    formData.append('productStock', productStock);
    formData.append('tags', tags);
    if (image !== undefined){
      formData.append('image', image);

    }
    if (images !== undefined){
    for (let i = 0; i < images.length; i++) {
      formData.append('images', images[i]);
      }
    }

    return this.http.put(url, formData);
  }



}
