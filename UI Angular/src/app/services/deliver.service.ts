import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DeliverService {

  constructor(private http : HttpClient) { }

  getDelivers(data : any): Observable<any>{
    return this.http.get(`${environment.apiUrl}`+"/DELIVER-SERVICE/page/"+data)
  }

  getDeliverByFirstName(data : any): Observable<any>{
    return this.http.get(`${environment.apiUrl}`+"/DELIVER-SERVICE/contains/"+data)
  }

  addDeliver(data : any): Observable<any>{
    return this.http.post(`${environment.apiUrl}`+"/DELIVER-SERVICE/", data);
  }

  updateDeliver(data1 : any, data2 : any): Observable<any>{
    return this.http.put(`${environment.apiUrl}`+"/DELIVER-SERVICE/"+data1, data2);
  }

  deleteDeliver(data :any): Observable<any>{
    return this.http.delete(`${environment.apiUrl}`+"/DELIVER-SERVICE/"+data)
  }

}
