import { TokenService } from 'src/app/services/token.service';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor( private tokenService:TokenService) { }

  private loggedIn = new BehaviorSubject<Boolean>(this.tokenService.isValid());

  authStatus = this.loggedIn.asObservable();

  changeAuthStatus(value : Boolean){
    this.loggedIn.next(value);
  }
}
