import { TokenService } from 'src/app/services/token.service';
import { environment } from './../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Credentails } from '../Beans/Credentials';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient, private tokenService : TokenService) { }
  

  login(data: Credentails):Observable<any>{
    return this.http.post(`${environment.apiUrl}`+"/USER-SERVICE/login", data, {
      'headers':new HttpHeaders()
      .set('content-type', 'multipart/form-data')
    });
  }

  refreshToken():Observable<any>{
    return this.http.get(`${environment.apiUrl}`+"/USER-SERVICE/refreshToken", {
      'headers':new HttpHeaders().
      set('Authorization', `Bearer ${this.tokenService.getRefreshToken()}`)
    });
  }
}



