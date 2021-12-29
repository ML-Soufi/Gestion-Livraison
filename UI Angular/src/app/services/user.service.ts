import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUsers(data : number): Observable<any>{
    return this.http.get(`${environment.apiUrl}`+"/USER-SERVICE/page/"+data);
  }

  searchUsersByUserLastName(data : any): Observable<any>{
    return this.http.get(`${environment.apiUrl}`+"/USER-SERVICE/contains/"+data);
  }

  addUser(data : any): Observable<any>{
    return this.http.post(`${environment.apiUrl}`+"/USER-SERVICE/signup",data);
  }

  deleteUser(data : any) : Observable<any>{
    return this.http.delete(`${environment.apiUrl}`+"/USER-SERVICE/"+data);
  }

  activetUser(data : any): Observable<any>{
    return this.http.get(`${environment.apiUrl}`+"/USER-SERVICE/active/"+data);
  }
}
