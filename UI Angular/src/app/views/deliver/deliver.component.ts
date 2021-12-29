import { DeliverService } from './../../services/deliver.service';
import { Deliver } from './../../Beans/Deliver';
import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-deliver',
  templateUrl: './deliver.component.html',
  styleUrls: ['./deliver.component.css']
})
export class DeliverComponent implements OnInit {

  constructor(private toastr: ToastrService, private deliverService: DeliverService) { }

  
  formGroup !: FormGroup;
  delivers : Deliver[] = [];
  deliver !: Deliver;
  pageNumbers : number = 0;
  currentPage : number = 1;
  isUpdateModal : boolean = false;
  imageWith : number = 50;

  ngOnInit(): void {
    this.initForm();
    this.getDelivers(this.currentPage);
  }

  // this methode initialize the reactive form
  initForm(){
    this.formGroup = new FormGroup({
      deliverCni : new FormControl('', [Validators.required, Validators.maxLength(10), Validators.pattern("^[a-z|A-Z]{2}[0-9]+$")]),
      firstName : new FormControl('', [Validators.required, Validators.maxLength(50),]),
      lastName : new FormControl('', [Validators.required, Validators.maxLength(50),]),
      deliverSex : new FormControl('', [Validators.required, Validators.pattern("^[fm]$")]),
      deliverPhone : new FormControl('', [Validators.required, Validators.maxLength(13), Validators.pattern("^(\\+212|0)([567])[0-9]{8}$")]),
      deliverEmail : new FormControl('', [Validators.required, Validators.maxLength(50), Validators.email])
    })
  }

  // this methode reset the reactive form
  clearForm(){
    this.formGroup.reset();    
  }

  // this methode is called after closing Modal
  onModalClose(){
    this.isUpdateModal = false;
    this.clearForm();
  }

  
  // this methode to get list of Products
  getDelivers(data : any){
    this.deliverService.getDelivers(data).subscribe(
      res => {
        this.delivers = res.dtos;
        this.pageNumbers = res.pageSizes;
      }
    )
  }

  // this Methode to add New Deliver
  addDeliver(){
    this.deliverService.addDeliver(this.formGroup.value).subscribe(
      res =>{
        document.getElementById('closeButton')?.click();
        this.clearForm();
        this.delivers.push(res)
      }
    );
  }

  // this methode to search Deliver
  onSearchChange(event : any){
    if(event.target.value == ""){
      this.pageNumbers = 0;
      this.currentPage = 1;
      this.getDelivers(this.currentPage);
    }else{
      this.deliverService.getDeliverByFirstName(event.target.value).subscribe(
        res => {
          this.delivers = res.dtos;
          this.pageNumbers = res.pageSizes;
        }
      );
    }
  }

  // this methode to update a Deliver
  updateDeliver(data : any){
    this.isUpdateModal = !this.isUpdateModal;
    document.getElementById('btnAddModal')?.click();
  }

  // thise methode to delete Deliver
  deleteDeliver(data : any){
    Swal.fire({
      icon: 'question',
      text: 'Vous voulez vraiment supprimer cet livreur ?',
      showDenyButton: true,
      confirmButtonText: 'Oui',
      denyButtonText: `Non`,
    }).then((result) => {
      if (result.isConfirmed) {
        this.deliverService.deleteDeliver(data).subscribe();
        this.delivers = this.delivers.filter((d : Deliver) => d.deliverId != data);        
        this.initForm();
        Swal.fire('', 'La suppreseion est faite.','success')
      } else if (result.isDenied) {
        Swal.fire('', 'La suppreseion est anulée.', 'info')
      }
    })
  }
  
  precedent(){
    if(this.currentPage - 1 == 0)
      this.toastr.warning("Attention ! C'est la premier page.", "", {timeOut: 3500})
    else{
      this.currentPage = this.currentPage - 1;
      this.getDelivers(this.currentPage);
    }  
  }

  suivant(){
    if(this.currentPage+1 == this.pageNumbers+1)
      this.toastr.warning("Attention ! C'est la dernier page.", "", {timeOut: 3500})
    else{
      this.currentPage = this.currentPage + 1;
      this.getDelivers(this.currentPage);
    }
  }

  goToSpesificPage(value : any, event : any){
    this.currentPage = value;
    this.getDelivers(this.currentPage);
  }
  
  paginationPages(i : number) {
    return  new Array(i);
  }

}
