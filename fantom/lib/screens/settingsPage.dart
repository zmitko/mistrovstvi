import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class SettingsPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return SettingsPageState();
  }
}

class SettingsPageState extends State<SettingsPage> {
  bool _transactionsEnabled = true;
  bool _loaded = false;

  initState() {
    super.initState();

    getTransactionSettings();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
        child: Scaffold(
            appBar: AppBar(
              title: Text('Nastavení'),
            ),
            body: this._loaded == false
                ? Container()
                : SingleChildScrollView(
                    child: Column(
                      children: <Widget>[
                        //Transactions
                        Container(
                          margin: EdgeInsets.only(left: 10.0, top: 10.0),
                          child: Row(
                            children: <Widget>[
                              Text(
                                'Převody',
                                style: TextStyle(
                                  fontSize: 20,
                                ),
                              ),
                              Switch(
                                  value: this._transactionsEnabled,
                                  onChanged: saveChanges)
                            ],
                          ),
                        ),
                        Container(
                            child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: <Widget>[
                            Row(
                              children: <Widget>[
                                Container(
                                  margin: EdgeInsets.only(left: 10.0),
                                  child: Icon(Icons.info),
                                ),
                                Expanded(
                                    child: Container(
                                  margin: EdgeInsets.only(left: 4.0),
                                  child: Text(
                                    'Automaticky převádět žetony',
                                    style: TextStyle(fontSize: 20.0),
                                  ),
                                ))
                              ],
                            ),
                            Container(
                              margin: EdgeInsets.only(left: 54.0, top: 12.0),
                              child: Row(
                                children: <Widget>[
                                  Text('Detektiv -1',
                                      style: TextStyle(fontSize: 18.0)),
                                  Icon(Icons.arrow_forward),
                                  Text('Fantom +1',
                                      style: TextStyle(fontSize: 18.0))
                                ],
                              ),
                            ),
                            Container(
                              margin: EdgeInsets.only(left: 54.0, top: 12.0),
                              child: Row(
                                children: <Widget>[
                                  Text('Detektiv +1',
                                      style: TextStyle(fontSize: 18.0)),
                                  Icon(Icons.arrow_forward),
                                  Text('Fantom -1',
                                      style: TextStyle(fontSize: 18.0))
                                ],
                              ),
                            )
                          ],
                        )),
                      ],
                    ),
                  )),
        onWillPop: onWillPop);
  }

  Future<bool> onWillPop() async {
    Navigator.pop(context, this._transactionsEnabled);
  }

  getTransactionSettings() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    bool value = (prefs.getBool('transactions') ?? true);
    this._transactionsEnabled = value;
    this._loaded = true;
    setState(() {});
  }

  saveChanges(bool newValue) async {
    this._transactionsEnabled = newValue;
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setBool('transactions', this._transactionsEnabled);
    setState(() {});
  }
}
