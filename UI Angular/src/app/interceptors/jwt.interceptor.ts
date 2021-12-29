import { environment } from './../../environments/environment';
import { TokenService } from 'src/app/services/token.service';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private tokenService : TokenService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if(request.url === `${environment.apiUrl}`+"/USER-SERVICE/login" || request.url === `${environment.apiUrl}`+"/USER-SERVICE/refreshToken"){
      return next.handle(request);
    }
    else{
        request = request.clone({
          setHeaders: {
            "Authorization" : `Bearer ${this.tokenService.getAccesToken()}`
          }
        })
        return next.handle(request);
      }
  }
}
