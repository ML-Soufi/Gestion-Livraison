import { ErrorTemplate } from 'src/app/Beans/ErrorTemplate';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Token } from 'src/app/Beans/Token';
import { AuthService } from 'src/app/services/auth.service';
import { TokenService } from 'src/app/services/token.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  formGroup !: FormGroup; 
  constructor(private authService : AuthService, private tokenService : TokenService, private router: Router, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.initForm();
  }
  
  initForm(){
    this.formGroup = new FormGroup({
      userId : new FormControl('', [Validators.required, Validators.maxLength(12), Validators.minLength(4)]),
      password : new FormControl('', [Validators.required, Validators.maxLength(12), Validators.minLength(4)])
    })
  }


  loginMethod():any{
      this.authService.login(this.formGroup.value).subscribe(
        result => {
          this.handleLogin(result);
        },
        err => {
          const errorTemplate = err.error;
          this.toastr.error(errorTemplate.errorMessage, "", {timeOut: 3000})
        }
      ); 
  }
   
  handleLogin(result:Token): any{
    this.tokenService.storeTokens(result);
    this.router.navigateByUrl("/Dashboard");
  }

}
