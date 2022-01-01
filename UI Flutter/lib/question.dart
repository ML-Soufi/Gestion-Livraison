import 'package:flutter/material.dart';

class QuestionText extends StatelessWidget{
  String questionText;

  QuestionText(this.questionText);

  @override
  Widget build(BuildContext context) {
    return Text(questionText);
  }
}