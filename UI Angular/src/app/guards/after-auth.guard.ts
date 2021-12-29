import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenService } from '../services/token.service';

@Injectable({
  providedIn: 'root'
})
export class AfterAuthGuard implements CanActivate {
  constructor(private tokenService : TokenService, private router : Router){
  }
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
      if(this.tokenService.isLogIn()){
        this.router.navigateByUrl("/Dashboard");
        return false;
      }
      
      return true;
  }
  
}
