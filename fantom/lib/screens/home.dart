import 'package:flutter/material.dart';
import 'package:fantom_app/models/figure.dart';
import 'package:fantom_app/models/transportSection.dart';
import 'dart:io';
import 'package:path_provider/path_provider.dart';
import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:fantom_app/screens/settingsPage.dart';

class Home extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _HomeState();
  }
}

class _HomeState extends State<Home> {
  var _figuresList = new List<Figure>(6);
  var _colorsList = new List<String>(5);
  Directory directory;
  File file;
  bool _transactionsEnabled = false;

  final double _blurRadius = 5.0;
  final double _cardRadius = 20.0;
  final double _colorStripeHeight = 30.0;
  final double _cardHeight = 200.0;

  final GlobalKey<ScaffoldState> _scaffoldState =
      new GlobalKey<ScaffoldState>();

  @override
  void initState() {
    super.initState();

    _colorsList[0] = 'white';
    _colorsList[1] = 'yellow';
    _colorsList[2] = 'red';
    _colorsList[3] = 'green';
    _colorsList[4] = 'blue';

    loadData();
    getSharedPrefsSettings();
  }

  getSharedPrefsSettings() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    _transactionsEnabled = (prefs.getBool('transactions') ?? true);
    setState(() {});
  }

  openSettingsForResult() async {
    final result = await Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => SettingsPage()),
    );
    _transactionsEnabled = result;
  }

  loadData() async {
    directory = await getApplicationDocumentsDirectory();
    file = new File(directory.path + '/players.txt');
    if (await file.exists()) {
      String contents = await file.readAsString();
      final jsonResponse = json.decode(contents);
      for (int i = 0; i < _figuresList.length; i++) {
        _figuresList[i] = new Figure.fromJson(jsonResponse[i]);
      }
      setState(() {});
    } else {
      _figuresList[0] = new Figure('black', 3, 3, 4, 2, 2);
      for (int i = 0; i < _colorsList.length; i++) {
        _figuresList[i + 1] = new Figure(_colorsList[i], 5, 9, 10, 0, 0);
      }
      await saveFiguresData();
      setState(() {});
    }
  }

  saveFiguresData() async {
    String mJson = "[";
    for (Figure p in _figuresList) {
      mJson += json.encode(p);
      mJson += ",";
    }
    mJson = mJson.substring(0, mJson.length - 1);
    mJson += "]";
    await file.writeAsString(mJson);
    setState(() {});
  }

  transactionCheckAdd(int gainingFigure, int itemIndex) {
    _figuresList[gainingFigure].addToItem(itemIndex);
    if (_transactionsEnabled) {
      _figuresList[0].removeFromItem(itemIndex);
    }
    setState(() {});
  }

  transactionCheckRemove(int losingFigure, int itemIndex) {
    if (_figuresList[losingFigure].getItem(itemIndex) != 0) {
      _figuresList[losingFigure].removeFromItem(itemIndex);
      if (_transactionsEnabled) {
        if (losingFigure != 0) {
          _figuresList[0].addToItem(itemIndex);
        }
      }
    }
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        key: _scaffoldState,
        appBar: AppBar(
          title: Text('FantomApp'),
          actions: <Widget>[
            IconButton(
              icon: Icon(
                Icons.replay,
                color: Colors.white,
              ),
              tooltip: 'Nová hra',
              onPressed: () {
                showDialog(
                    context: context,
                    barrierDismissible: false,
                    builder: (BuildContext context) {
                      return AlertDialog(
                        title: Text('Nová hra'),
                        content: Text('Přejete si začít novou hru?'),
                        actions: <Widget>[
                          FlatButton(
                            child: Text('Ano'),
                            onPressed: () {
                              _figuresList[0] =
                                  new Figure('black', 3, 3, 4, 2, 2);
                              for (int i = 0; i < _colorsList.length; i++) {
                                _figuresList[i + 1] =
                                    new Figure(_colorsList[i], 5, 9, 10, 0, 0);
                              }
                              saveFiguresData();
                              _scaffoldState.currentState.showSnackBar(SnackBar(
                                content: Text('Nová hra byla spuštěna.'),
                                duration: Duration(seconds: 2),
                              ));
                              Navigator.pop(context);
                            },
                          ),
                          FlatButton(
                            child: Text('Ne'),
                            onPressed: () {
                              Navigator.pop(context);
                            },
                          )
                        ],
                      );
                    });
              },
            ),
            IconButton(
                icon: Icon(
                  Icons.settings,
                  color: Colors.white,
                ),
                tooltip: 'Nastavení',
                onPressed: openSettingsForResult)
          ],
        ),
        body: SingleChildScrollView(
          child: _figuresList[0] == null
              ? Center(child: Text('Načítání...'))
              : Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: <Widget>[
                    //Black
                    Container(
                      margin: EdgeInsets.fromLTRB(10, 10, 10, 5),
                      height: _cardHeight,
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius:
                              BorderRadius.all(Radius.circular(_cardRadius)),
                          boxShadow: [
                            BoxShadow(color: Colors.grey, blurRadius: 10.0)
                          ]),
                      child: Column(
                        children: <Widget>[
                          Container(
                            height: _colorStripeHeight,
                            decoration: BoxDecoration(
                                color: Colors.black,
                                borderRadius: BorderRadius.only(
                                    topLeft: Radius.circular(_cardRadius),
                                    topRight: Radius.circular(_cardRadius))),
                          ),
                          Container(
                            height: _cardHeight - _colorStripeHeight,
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: <Widget>[
                                //Trams
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 0,
                                    typeIndex: 0,
                                    onPressedAdd: () {
                                      setState(() {
                                        _figuresList[0].addTram();
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(0, 0);
                                        saveFiguresData();
                                      });
                                    }),
                                //Taxis
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 0,
                                    typeIndex: 1,
                                    onPressedAdd: () {
                                      setState(() {
                                        _figuresList[0].addTaxi();
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(0, 1);
                                        saveFiguresData();
                                      });
                                    }),
                                //Horses
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 0,
                                    typeIndex: 2,
                                    onPressedAdd: () {
                                      setState(() {
                                        _figuresList[0].addHorse();
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(0, 2);
                                        saveFiguresData();
                                      });
                                    }),
                                //Boats
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 0,
                                    typeIndex: 3,
                                    onPressedAdd: () {
                                      setState(() {
                                        _figuresList[0].addBoat();
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(0, 3);
                                        saveFiguresData();
                                      });
                                    }),
                                //Doubles
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 0,
                                    typeIndex: 4,
                                    onPressedAdd: () {
                                      setState(() {
                                        _figuresList[0].addDouble();
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(0, 4);
                                        saveFiguresData();
                                      });
                                    }),
                              ],
                            ),
                          )
                        ],
                      ),
                    ),
                    //White
                    Container(
                      margin: EdgeInsets.fromLTRB(10, 5, 10, 5),
                      height: _cardHeight,
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius:
                              BorderRadius.all(Radius.circular(_cardRadius)),
                          boxShadow: [
                            BoxShadow(
                                color: Colors.grey, blurRadius: _blurRadius)
                          ]),
                      child: Column(
                        children: <Widget>[
                          Container(
                              height: _colorStripeHeight,
                              decoration: BoxDecoration(
                                color: Colors.white,
                                borderRadius: BorderRadius.only(
                                    topLeft: Radius.circular(_cardRadius),
                                    topRight: Radius.circular(_cardRadius)),
                              )),
                          Container(
                            height: 0.5,
                            decoration: BoxDecoration(color: Colors.black),
                          ),
                          Container(
                            height: _cardHeight - _colorStripeHeight - 0.5,
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.only(
                                  bottomLeft: Radius.circular(_cardRadius),
                                  bottomRight: Radius.circular(_cardRadius),
                                  topLeft: Radius.circular(0),
                                  topRight: Radius.circular(0)),
                            ),
                            child: Row(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceEvenly,
                                crossAxisAlignment: CrossAxisAlignment.center,
                                children: <Widget>[
                                  //Trams
                                  TransportSection(
                                      figuresList: this._figuresList,
                                      playerIndex: 1,
                                      typeIndex: 0,
                                      onPressedAdd: () {
                                        setState(() {
                                          transactionCheckAdd(1, 0);
                                          saveFiguresData();
                                        });
                                      },
                                      onPressedRemove: () {
                                        setState(() {
                                          transactionCheckRemove(1, 0);
                                          saveFiguresData();
                                        });
                                      }),
                                  //Taxis
                                  TransportSection(
                                      figuresList: this._figuresList,
                                      playerIndex: 1,
                                      typeIndex: 1,
                                      onPressedAdd: () {
                                        setState(() {
                                          transactionCheckAdd(1, 1);
                                          saveFiguresData();
                                        });
                                      },
                                      onPressedRemove: () {
                                        setState(() {
                                          transactionCheckRemove(1, 1);
                                          saveFiguresData();
                                        });
                                      }),
                                  //Horses
                                  TransportSection(
                                      figuresList: this._figuresList,
                                      playerIndex: 1,
                                      typeIndex: 2,
                                      onPressedAdd: () {
                                        setState(() {
                                          transactionCheckAdd(1, 2);
                                          saveFiguresData();
                                        });
                                      },
                                      onPressedRemove: () {
                                        setState(() {
                                          transactionCheckRemove(1, 2);
                                          saveFiguresData();
                                        });
                                      }),
                                ]),
                          )
                        ],
                      ),
                    ),
                    //Yellow
                    Container(
                      margin: EdgeInsets.fromLTRB(10, 5, 10, 5),
                      height: _cardHeight,
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius:
                              BorderRadius.all(Radius.circular(_cardRadius)),
                          boxShadow: [
                            BoxShadow(
                                color: Colors.grey, blurRadius: _blurRadius)
                          ]),
                      child: Column(
                        children: <Widget>[
                          Container(
                            height: _colorStripeHeight,
                            decoration: BoxDecoration(
                                color: Colors.yellow,
                                borderRadius: BorderRadius.only(
                                    topLeft: Radius.circular(_cardRadius),
                                    topRight: Radius.circular(_cardRadius))),
                          ),
                          Container(
                            height: _cardHeight - _colorStripeHeight,
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: <Widget>[
                                //Trams
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 2,
                                    typeIndex: 0,
                                    onPressedAdd: () {
                                      setState(() {
                                        transactionCheckAdd(2, 0);
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(2, 0);
                                        saveFiguresData();
                                      });
                                    }),
                                //Taxis
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 2,
                                    typeIndex: 1,
                                    onPressedAdd: () {
                                      setState(() {
                                        transactionCheckAdd(2, 1);
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(2, 1);
                                        saveFiguresData();
                                      });
                                    }),
                                //Horses
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 2,
                                    typeIndex: 2,
                                    onPressedAdd: () {
                                      setState(() {
                                        transactionCheckAdd(2, 2);
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(2, 2);
                                        saveFiguresData();
                                      });
                                    }),
                              ],
                            ),
                          )
                        ],
                      ),
                    ),
                    //Red
                    Container(
                      margin: EdgeInsets.fromLTRB(10, 5, 10, 5),
                      height: _cardHeight,
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius:
                              BorderRadius.all(Radius.circular(_cardRadius)),
                          boxShadow: [
                            BoxShadow(
                                color: Colors.grey, blurRadius: _blurRadius)
                          ]),
                      child: Column(
                        children: <Widget>[
                          Container(
                            height: _colorStripeHeight,
                            decoration: BoxDecoration(
                                color: Colors.red,
                                borderRadius: BorderRadius.only(
                                    topLeft: Radius.circular(_cardRadius),
                                    topRight: Radius.circular(_cardRadius))),
                          ),
                          Container(
                            height: _cardHeight - _colorStripeHeight,
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: <Widget>[
                                //Trams
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 3,
                                    typeIndex: 0,
                                    onPressedAdd: () {
                                      setState(() {
                                        transactionCheckAdd(3, 0);
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(3, 0);
                                        saveFiguresData();
                                      });
                                    }),
                                //Taxis
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 3,
                                    typeIndex: 1,
                                    onPressedAdd: () {
                                      setState(() {
                                        transactionCheckAdd(3, 1);
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(3, 1);
                                        saveFiguresData();
                                      });
                                    }),
                                //Horses
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 3,
                                    typeIndex: 2,
                                    onPressedAdd: () {
                                      setState(() {
                                        transactionCheckAdd(3, 2);
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(3, 2);
                                        saveFiguresData();
                                      });
                                    }),
                              ],
                            ),
                          )
                        ],
                      ),
                    ),
                    //Green
                    Container(
                      margin: EdgeInsets.fromLTRB(10, 5, 10, 5),
                      height: _cardHeight,
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius:
                              BorderRadius.all(Radius.circular(_cardRadius)),
                          boxShadow: [
                            BoxShadow(
                                color: Colors.grey, blurRadius: _blurRadius)
                          ]),
                      child: Column(
                        children: <Widget>[
                          Container(
                            height: _colorStripeHeight,
                            decoration: BoxDecoration(
                                color: Colors.green,
                                borderRadius: BorderRadius.only(
                                    topLeft: Radius.circular(_cardRadius),
                                    topRight: Radius.circular(_cardRadius))),
                          ),
                          Container(
                            height: _cardHeight - _colorStripeHeight,
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: <Widget>[
                                //Trams
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 4,
                                    typeIndex: 0,
                                    onPressedAdd: () {
                                      setState(() {
                                        transactionCheckAdd(4, 0);
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(4, 0);
                                        saveFiguresData();
                                      });
                                    }),
                                //Taxis
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 4,
                                    typeIndex: 1,
                                    onPressedAdd: () {
                                      setState(() {
                                        transactionCheckAdd(4, 1);
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(4, 1);
                                        saveFiguresData();
                                      });
                                    }),
                                //Horses
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 4,
                                    typeIndex: 2,
                                    onPressedAdd: () {
                                      setState(() {
                                        transactionCheckAdd(4, 2);
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(4, 2);
                                        saveFiguresData();
                                      });
                                    }),
                              ],
                            ),
                          )
                        ],
                      ),
                    ),
                    //Blue
                    Container(
                      margin: EdgeInsets.fromLTRB(10, 5, 10, 10),
                      height: _cardHeight,
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius:
                              BorderRadius.all(Radius.circular(_cardRadius)),
                          boxShadow: [
                            BoxShadow(
                                color: Colors.grey, blurRadius: _blurRadius)
                          ]),
                      child: Column(
                        children: <Widget>[
                          Container(
                            height: _colorStripeHeight,
                            decoration: BoxDecoration(
                                color: Colors.blue,
                                borderRadius: BorderRadius.only(
                                    topLeft: Radius.circular(_cardRadius),
                                    topRight: Radius.circular(_cardRadius))),
                          ),
                          Container(
                            height: _cardHeight - _colorStripeHeight,
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: <Widget>[
                                //Trams
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 5,
                                    typeIndex: 0,
                                    onPressedAdd: () {
                                      setState(() {
                                        transactionCheckAdd(5, 0);
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(5, 0);
                                        saveFiguresData();
                                      });
                                    }),
                                //Taxis
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 5,
                                    typeIndex: 1,
                                    onPressedAdd: () {
                                      setState(() {
                                        transactionCheckAdd(5, 1);
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(5, 1);
                                        saveFiguresData();
                                      });
                                    }),
                                //Horses
                                TransportSection(
                                    figuresList: this._figuresList,
                                    playerIndex: 5,
                                    typeIndex: 2,
                                    onPressedAdd: () {
                                      setState(() {
                                        transactionCheckAdd(5, 2);
                                        saveFiguresData();
                                      });
                                    },
                                    onPressedRemove: () {
                                      setState(() {
                                        transactionCheckRemove(5, 2);
                                        saveFiguresData();
                                      });
                                    }),
                              ],
                            ),
                          )
                        ],
                      ),
                    ),
                  ],
                ),
        ));
  }
}
