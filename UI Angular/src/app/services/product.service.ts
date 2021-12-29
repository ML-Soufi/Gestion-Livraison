import { environment } from './../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http : HttpClient) { }

  getProduct(data:any): Observable<any>{
    return this.http.get(`${environment.apiUrl}`+"/PRODUCT-SERVICE/page/"+data)
  }

  getProductImage(data : any) : Observable<any>{
    return this.http.get(`${environment.apiUrl}`+"/PRODUCT-SERVICE/image/"+data);
  }
  addProduct(data:any): Observable<any>{
    return this.http.post(`${environment.apiUrl}`+"/PRODUCT-SERVICE/", data)
  }
}
