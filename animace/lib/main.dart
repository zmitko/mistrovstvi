import 'package:flutter/material.dart';

void main() {
  runApp(new MaterialApp(
    home: Scaffold(
      body: new BarLoadingScreen(),
    ),
  ));
}

class BarLoadingScreen extends StatefulWidget {
  @override
  _BarLoadingScreenState createState() => new _BarLoadingScreenState();
}

// You have to add this class mixin in order for flutter to know to treat it as
// an animation containing widget
class _BarLoadingScreenState extends State<BarLoadingScreen>
    with SingleTickerProviderStateMixin {
  AnimationController _controller; // new
  Animation<Color> animation; // new

  @override
  initState() {
    super.initState();
    // Because this class has now mixed in a TickerProvider
    // It can be its own vsync. This is what you need almost always
    _controller = new AnimationController(
      duration: const Duration(milliseconds: 3000),
      vsync: this,
    );
    // A tween that begins at grey and ends at a green
    // The chained 'animate' function is required
    animation = new ColorTween(
      begin: const Color.fromRGBO(10, 10, 10, 0.5),
      end: const Color.fromRGBO(0, 200, 100, 0.5),
    ).animate(_controller)
      // This is a another chained method for Animations.
      // It will call the callback passed to it everytime the
      // value of the tween changes. Call setState within it
      // to repaint the widget with the new value
      ..addListener(() {
        setState(() {});
      });
    // Tell the animation to start
    _controller.forward();
  }

  // This is important for perf. When the widget is gone, remove the controller.
  @override
  dispose() {
    _controller?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new Container(
      // This is where you pass the animation value
      // Each time set state gets called,
      // this widget gets rebuilt,
      // and the value of the animation is something inbetween
      // the starting grey and the ending green
      // thanks to our ColorTween
      decoration: new BoxDecoration(color: animation.value),
      child: new Center(
        child: new Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            new Bar(),
            new Bar(),
            new Bar(),
            new Bar(),
          ],
        ),
      ),
    );
  }
}

class Bar extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Container(
      width: 35.0,
      height: 15.0,
      decoration: new BoxDecoration(
          color: const Color.fromRGBO(0, 0, 255, 1.0),
          borderRadius: new BorderRadius.circular(10.0),
          boxShadow: [
            new BoxShadow(
              color: Colors.black12,
              blurRadius: 8.0,
              spreadRadius: 1.0,
              offset: new Offset(1.0, 0.0),
            ),
            new BoxShadow(
              color: Colors.black26,
              blurRadius: 6.0,
              spreadRadius: 1.5,
              offset: new Offset(1.0, 0.0),
            ),
          ]),
    );
  }
}
