import { Token } from './../Beans/Token';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  storeTokens(data : Token){
    localStorage.setItem("access-token", data['access-token']);
    localStorage.setItem("refresh-token", data['refresh-token']);
  }

  getAccesToken(){
    return localStorage.getItem("access-token");
  }

  getRefreshToken(){
    return localStorage.getItem("refresh-token");
  }

  removeTokens(){
    localStorage.removeItem("access-token");
    localStorage.removeItem("refresh-token");
  }

  decodePayloadToken(payload:any){
    return JSON.parse(atob(payload));
  }

  getPayload(token : string){
    const payload = token.split(".")[1];
    return this.decodePayloadToken(payload);
  }

  tokenExpired(){
    const token = this.getAccesToken();
    if(token){
      const expiry = this.getPayload(token).exp;
      return (Math.floor((new Date).getTime() / 1000)) >= expiry;
    }
    return false;
  }


  tokenRole(){
    const token = this.getAccesToken();
    if(token){
      const role : string = this.getPayload(token).roles[0];
      return role;
    }
    return false;
  }

  tokenFullName(){
    const token = this.getAccesToken();
    if(token){
      const fullName : string = this.getPayload(token).fullName;
      return fullName;
    }
    return false;
  }


  tokenSubject(){
    const token = this.getAccesToken();
    if(token){
      const subject : string = this.getPayload(token).sub;
      return (subject.length !== 0);
    }
    return false;
  }

  isValid(){
    const token = this.getAccesToken();
    if(token)
      return (this.tokenSubject() && !this.tokenExpired());
    else
      return false;
  }

  isLogIn(){
    return this.isValid();
  }

  refreshTokens(tokens : Token){
    this.storeTokens(tokens);
  }
}
