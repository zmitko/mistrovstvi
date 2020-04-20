import 'package:flutter/material.dart';
import 'screens/home.dart';

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        primaryColor: Color.fromARGB(255, 180, 5, 31),
        accentColor: Colors.redAccent
      ),
      home: Home(),
    );
  }
}
