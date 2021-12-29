import { TokenService } from 'src/app/services/token.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private tokenService : TokenService, private router : Router) { }

  fullName !: any;
  role !: any;

  ngOnInit(): void {
    this.fullName = this.tokenService.tokenFullName();
    this.role = this.tokenService.tokenRole();
  }

  toggleSideBar(){
    document.querySelector(".sidebar")?.classList.toggle("close");
  }

  showSubMenu(Event : any){
    Event.target.parentElement.parentElement.classList.toggle("showMenu");
  }

  logOut(){
    this.tokenService.removeTokens();
    this.router.navigateByUrl("/");
  }
}
