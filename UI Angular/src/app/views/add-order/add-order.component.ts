import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-order',
  templateUrl: './add-order.component.html',
  styleUrls: ['./add-order.component.css']
})
export class AddOrderComponent implements OnInit {

  constructor() { }

  pill1 : boolean = true;
  pill2 : boolean = false;
  pill3 : boolean = false;

  ngOnInit(): void {
  }
}
