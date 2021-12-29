import { AuthService } from 'src/app/services/auth.service';
import { TokenService } from 'src/app/services/token.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private tokenService : TokenService, private authService : AuthService, private router : Router){
  }
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
      if(!this.tokenService.isLogIn()){
        if(this.tokenService.tokenExpired()){
          console.log("token is expired.");
          this.authService.refreshToken().subscribe(
            res => {
              console.log(res);
              this.tokenService.refreshTokens(res)
              return true;
            },
            err =>{
              this.tokenService.removeTokens();
              this.router.navigateByUrl("/");
              return false;
            }
          );
        }
        this.tokenService.removeTokens();
        this.router.navigateByUrl("/");
        return false;
      }
      
      return true;
  }
  
}
