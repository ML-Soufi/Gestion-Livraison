import 'package:flutter/material.dart';

class Home extends StatelessWidget{

  Widget listItem(String message){
    return Card(
      child: ListTile(title: Text(message)),
      elevation: 8,
      shadowColor: Colors.deepOrange,

    );
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.lightBlue,
      body: Center(
        child: ListWheelScrollView(
          itemExtent: 250,
          physics: FixedExtentScrollPhysics(),
          children: [
            listItem("First Card"),
            listItem("Second Card"),
            listItem("Third Card"),
            listItem("Fourth Card"),
            listItem("Random Card"),
            listItem("Random Card"),
            listItem("Random Card"),
            listItem("Random Card"),
            listItem("Random Card"),
          ],
        ),
      ),
    );   
  }

}

class OrdersItem{
  String title;
  String message;

  OrdersItem(this.title, this.message);
}