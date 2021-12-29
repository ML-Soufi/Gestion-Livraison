import { User } from './../../Beans/User';
import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { Chain } from '@angular/compiler';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  formGroup !: FormGroup; 
  users : User[] = [];
  user !: User;
  pageNumbers : number = 0;
  currentPage : number = 1;
  isUpdateModal : boolean = false;
  isPasswordMatched : boolean = false;

  constructor(private userService : UserService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.initForm();
    this.getUsers(this.currentPage);
  }

  onSearchChange(event : any){
    if(event.target.value == ""){
      this.pageNumbers = 0;
      this.currentPage = 1;
      this.getUsers(this.currentPage);
    }else{
      this.userService.searchUsersByUserLastName(event.target.value).subscribe(
          res => {
            this.users = res.dtos;
            this.pageNumbers = res.pageSizes;
          }
      );
    }
  }

  initForm(){
    this.formGroup = new FormGroup({
      userFirstName : new FormControl('', [Validators.required, Validators.maxLength(10),]),
      userLastName : new FormControl('', [Validators.required, Validators.maxLength(10),]),
      userPassword : new FormControl('', [Validators.required, Validators.maxLength(8), Validators.maxLength(12),]),
      userRole : new FormControl('', [Validators.required,])
    })
  }

  myFunction(){
    this.isUpdateModal = false;
    this.clearForm();
  }

  clearForm(){
    this.formGroup.get('userFirstName')?.reset();
    this.formGroup.get('userLastName')?.reset();
    this.formGroup.get('userPassword')?.reset();
    this.formGroup.get('userRole')?.reset();
  }

  updateUser(data : any){
    this.isUpdateModal = true;
    this.users.filter((p : User)=> {
      if(p.userId == data)
        this.user = p;
    });
    this.formGroup.get('userFirstName')?.setValue(this.user.userFirstName);
    this.formGroup.get('userLastName')?.setValue(this.user.userLastName);
    this.formGroup.get('userRole')?.setValue(this.user.userRole);
    document.getElementById('btnAddModal')?.click();
  }

  deleteUser(data : any){
    Swal.fire({
      icon: 'question',
      text: 'Vous voulez vraiment supprimer cet utilisateur ?',
      showDenyButton: true,
      confirmButtonText: 'Oui',
      denyButtonText: `Non`,
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.deleteUser(data).subscribe();
        this.users = this.users.filter((p : User) => p.userId != data);        
        this.initForm();
        Swal.fire('', 'La suppreseion est faite.','success')
      } else if (result.isDenied) {
        Swal.fire('', 'La suppreseion est anulée.', 'info')
      }
    })
  }

  activatUser(data : any){
    this.userService.activetUser(data).subscribe();
    this.users.find(user => {
      if(user.userId == data)
        user.userState = !user.userState;
    }
    );
  }

  addUser(){
    this.userService.addUser(this.formGroup.value).subscribe(
      res=>{
        document.getElementById('closebutton')?.click();
        this.toastr.success("Un utilisateur est ajouté.", "", {timeOut: 3500})
        this.users.push(res);
        this.clearForm();
      }
    )
  }


  getUsers(data : number) :any{
    this.userService.getAllUsers(data).subscribe(
      res=> {
        this.users = res.dtos;
        this.pageNumbers=res.pageSizes;
      }
    );
  }

  precedent(){
    if(this.currentPage - 1 == 0)
      this.toastr.warning("Attention ! C'est la premier page.", "", {timeOut: 3500})
    else{
      this.currentPage = this.currentPage - 1;
      this.getUsers(this.currentPage);
    }  
  }

  suivant(){
    if(this.currentPage+1 == this.pageNumbers+1)
      this.toastr.warning("Attention ! C'est la dernier page.", "", {timeOut: 3500})
    else{
      this.currentPage = this.currentPage + 1;
      this.getUsers(this.currentPage);
    }
  }

  myfunction(value : any, event : any){
    this.currentPage = value;
    this.getUsers(this.currentPage);
  }
  
  paginationPages(i : number) {
    return  new Array(i);
  }


}
