import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {getXHRResponse} from "rxjs/internal/ajax/getXHRResponse";

@Injectable()
export class ProductService{

  constructor(private httpClient: HttpClient) { }
  getProducts() {


    return this.httpClient.get('https://localhost:8443/api/products?page=1&size=10');


  }
}
