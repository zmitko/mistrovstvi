class Figure {
  //0 Tram; 1 Taxi; 2 Horse; 3 Boat; 4 Double
  var _inventory = new List(5);
  String _color;

  Figure(
      String color, int trams, int taxis, int horses, int boats, int doubles) {
    this._color = color;
    _inventory[0] = trams;
    _inventory[1] = taxis;
    _inventory[2] = horses;
    _inventory[3] = boats;
    _inventory[4] = doubles;
  }

  Figure.fromJson(Map<String, dynamic> json) {
    _color = json['color'];
    _inventory[0] = json['trams'];
    _inventory[1] = json['taxis'];
    _inventory[2] = json['horses'];
    _inventory[3] = json['boats'];
    _inventory[4] = json['doubles'];
  }

  Map<String, dynamic> toJson() => {
        'trams': _inventory[0],
        'taxis': _inventory[1],
        'horses': _inventory[2],
        'boats': _inventory[3],
        'doubles': _inventory[4]
      };

  getItem(int index) {
    return this._inventory[index];
  }

  addToItem(int index) {
    this._inventory[index]++;
  }

  removeFromItem(int index) {
    if(this._inventory[index] > 0) {
      this._inventory[index]--;
    }
  }

  getColor() {
    return this._color;
  }

  addTram() {
    this._inventory[0]++;
  }

  addTaxi() {
    this._inventory[1]++;
  }

  addHorse() {
    this._inventory[2]++;
  }

  addBoat() {
    this._inventory[3]++;
  }

  addDouble() {
    this._inventory[4]++;
  }

  removeTram() {
    if(this._inventory[0] > 0)
      this._inventory[0]--;
  }

  removeTaxi() {
    if(this._inventory[1] > 0)
      this._inventory[1]--;
  }

  removeHorse() {
    if(this._inventory[2] > 0)
      this._inventory[2]--;
  }

  removeBoat() {
    if(this._inventory[3] > 0)
      this._inventory[3]--;
  }

  removeDouble() {
    if(this._inventory[4] > 0)
      this._inventory[4]--;
  }

  setTrams(int newValue) {
    this._inventory[0] = newValue;
  }

  setTaxis(int newValue) {
    this._inventory[1] = newValue;
  }

  setHorses(int newValue) {
    this._inventory[2] = newValue;
  }

  setBoats(int newValue) {
    this._inventory[3] = newValue;
  }

  setDoubles(int newValue) {
    this._inventory[4] = newValue;
  }

  getTrams() {
    return _inventory[0];
  }

  getTaxis() {
    return _inventory[1];
  }

  getHorses() {
    return _inventory[2];
  }

  getBoats() {
    return _inventory[3];
  }

  getDoubles() {
    return _inventory[4];
  }
}
