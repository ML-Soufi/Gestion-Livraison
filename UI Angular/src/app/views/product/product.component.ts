import { Product } from './../../Beans/Product';
import { ProductService } from './../../services/product.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  constructor(private toastr: ToastrService, public productService : ProductService) { }

  formGroup !: FormGroup;
  numberRate : number = 4.5;
  file !: any;
  formData : FormData = new FormData();
  products : Product[] = []; 
  pageNumbers : number = 0;
  currentPage : number = 1;
  isUpdateModal : boolean = false;
  imageWith : number = 50;
  imageHeight : number = 1;
  imageToShow: any;

  ngOnInit(): void {
    this.initForm();
    this.getProducts(this.currentPage);
  }

  // this methode initialize the reactive form
  initForm(){
    this.formGroup = new FormGroup({
      productName : new FormControl('', [Validators.required, Validators.maxLength(20),]),
      productPrice : new FormControl('', [Validators.required,]),
      productQuantity : new FormControl('', [Validators.required,])
    })
  }

  //

  // this methode reset the reactive form
  clearForm(){
    this.formGroup.reset();
    this.formData.delete('product');
    this.formData.delete('file');
  }

  // this methode is called after closing Modal
  onModalClose(){
    this.clearForm();
  }

  // this Methode to add New Product
  addProduct(){
    this.productService.addProduct(this.formData).subscribe(
      res=> {
        document.getElementById('closeButton')?.click();
        this.clearForm();
        this.products.push(res);
      }
    );
  }

  // this methode to get list of Products
  getProducts(data : any){
    this.productService.getProduct(data).subscribe(
      res => {
        this.products = res.dtos;
        this.pageNumbers = res.pageSizes;
      }
    )
  }

  // this methode to upload Product image
  uploadImage(event : any){
    this.file = event.target.files[0];
  }
  
  // this mothode to get product image
  getProductImage(data : any){
    this.productService.getProductImage(data).subscribe(
      res=> {return res;}
    )
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
