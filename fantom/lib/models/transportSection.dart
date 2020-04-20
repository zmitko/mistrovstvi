import 'package:flutter/material.dart';

class TransportSection extends StatefulWidget {
  var figuresList = new List(6);
  final List<Icon> iconsList = [Icon(Icons.tram), Icon(Icons.local_taxi), Icon(IconData(0xe900, fontFamily: 'horse')), Icon(Icons.directions_boat), Icon(Icons.looks_two)];
  final int playerIndex;
  final int typeIndex;
  final GestureTapCallback onPressedAdd;
  final GestureTapCallback onPressedRemove;

  TransportSection(
      {@required this.figuresList,
      @required this.playerIndex,
      @required this.typeIndex,
      @required this.onPressedAdd,
      @required this.onPressedRemove});

  @override
  State<StatefulWidget> createState() {
    return TransportSectionState();
  }

  getTypeIndex() {
    return this.typeIndex;
  }
}

class TransportSectionState extends State<TransportSection> {
  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Container(
          padding: EdgeInsets.only(top: 10.0),
          child: widget.iconsList[widget.typeIndex],
        ),
        RawMaterialButton(
          constraints: BoxConstraints.tight(Size(40, 40)),
          shape: CircleBorder(),
          child: Icon(
            Icons.add,
            color: Colors.green,
            size: 30.0,
          ),
          onPressed: widget.onPressedAdd,
        ),
        Text(
          widget.figuresList[widget.playerIndex]
              .getItem(widget.typeIndex)
              .toString(),
          style: TextStyle(fontSize: 30.0),
        ),
        RawMaterialButton(
          constraints: BoxConstraints.tight(Size(40, 40)),
          shape: CircleBorder(),
          child: Icon(
            Icons.remove,
            color: Colors.red,
            size: 30.0,
          ),
          onPressed: widget.onPressedRemove,
        ),
      ],
    );
  }
}
