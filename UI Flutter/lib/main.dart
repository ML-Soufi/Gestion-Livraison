import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:flutter/material.dart';
import './question.dart';
import './home.dart';
import './history.dart';
import './add.dart';
import './profile.dart';





void main() => runApp(MyApp());

class MyApp extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return MyAppState();
  }
}

class MyAppState extends State<MyApp>{
  int selectedItem = 0;
  int questionIndex = 0;
 
  List<Widget> widgets = <Widget> [
    Home(),
    History(),
    Add(),
    Profile(),
  ];
  void changeItemIndex(int value){
    setState(() {
      selectedItem=value;
    });
  }
  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: Scaffold(
        backgroundColor: Colors.lightBlue,
        appBar: AppBar(
          title: Text('Deliver App', textAlign: TextAlign.center,),
          backgroundColor: Colors.black,
          actions: [
            IconButton(onPressed: () => {}, icon: Icon(Icons.qr_code_scanner_sharp)),
            IconButton(onPressed: () => {}, icon: Icon(Icons.logout)),
          ],
        ),
        body: widgets.elementAt(selectedItem),
        bottomNavigationBar: CurvedNavigationBar(
          items: [
            Icon(Icons.home, size: 30,),
            Icon(Icons.format_align_justify_outlined, size: 30,),
            Icon(Icons.add, size: 30,),         
            Icon(Icons.person, size: 30,),
          ],
          backgroundColor: Colors.lightBlue,
          index: selectedItem,
          onTap: changeItemIndex,
        ),
        ),
        debugShowCheckedModeBanner: false,
    );    
  }
}

