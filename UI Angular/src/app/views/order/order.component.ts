import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  constructor(private toastr : ToastrService) { }

  pageNumbers : number = 0;
  currentPage : number = 1;
  isUpdateModal : boolean = false;
  isPasswordMatched : boolean = false;

  ngOnInit(): void {
  }

    // this methode reset the reactive form
    clearForm(){
    }
  

  // this methode is called after closing Modal
  onModalClose(){
    this.clearForm();
  }

  // this Methode to add New Product
  addProduct(){
  }

  // this methode to get list of Products
  getProducts(data : any){
  }

  
  // this mothode to get product image
  getProductImage(data : any){
  }
  precedent(){
    if(this.currentPage - 1 == 0)
      this.toastr.warning("Attention ! C'est la premier page.", "", {timeOut: 3500})
    else{
      this.currentPage = this.currentPage - 1;
      this.getProducts(this.currentPage);
    }  
  }

  suivant(){
    if(this.currentPage+1 == this.pageNumbers+1)
      this.toastr.warning("Attention ! C'est la dernier page.", "", {timeOut: 3500})
    else{
      this.currentPage = this.currentPage + 1;
      this.getProducts(this.currentPage);
    }
  }

  goToSpesificPage(value : any, event : any){
    this.currentPage = value;
    this.getProducts(this.currentPage);
  }
  
  paginationPages(i : number) {
    return  new Array(i);
  }

}
