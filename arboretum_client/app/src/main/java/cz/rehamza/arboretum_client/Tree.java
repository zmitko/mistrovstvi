package cz.rehamza.arboretum_client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Tree implements Serializable, Comparable<Tree> {

    private int uid;
    private String name;
    private String planted;
    private String height;
    private String lat;
    private String lon;
    private String imageCode;
    private String description;
    private String latin;
    private String origin;
    private boolean visited;

    public Tree(){

    }
    public Tree(String name, String planted, String height, String lat, String lon, String imageCode, String description, String latin, String origin, boolean visited) {
        this.name = name;
        this.planted = planted;
        this.height = height;
        this.lat = lat;
        this.lon = lon;
        this.imageCode = imageCode;
        this.description = description;
        this.latin = latin;
        this.origin = origin;
        this.visited = visited;
    }
    public Tree(JSONObject tree) {
        try {
            uid = tree.getInt("ID");
            name = tree.getString("Name");
            planted = tree.getString("Planted");
            height = tree.getString("Height");
            lat = tree.getString("Lat");
            lon = tree.getString("Lon");
            imageCode = tree.getString("ImageCode");
            description = tree.getString("Description");
            latin = tree.getString("Latin");
            origin = tree.getString("Origin");
            if(tree.has("Visited"))
                visited = tree.getBoolean("Visited");
            else
                visited = false;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean hasBeenVisited(){
        return visited;
    }

    public void toggleVisited(){
        visited = !visited;
    }

    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public String getHeight() {
        return height;
    }

    public Tree setHeight(String height) {
        this.height = height;
        return this;
    }

    public String getLat() {
        return lat;
    }

    public Tree setLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getLon() {
        return lon;
    }

    public Tree setLon(String lon) {
        this.lon = lon;
        return this;
    }

    public String getImageCode() {
        return imageCode;
    }

    public Tree setImageCode(String imageCode) {
        this.imageCode = imageCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Tree setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getUid() {
        return uid;
    }

    public Tree setUid(int uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Tree setName(String name) {
        this.name = name;
        return this;
    }

    public String getLatin(){
        return latin;
    }

    public String getOrigin(){
        return origin;
    }

    public String getPlanted() {
        return planted;
    }

    public Tree setPlanted(String planted) {
        this.planted = planted;
        return this;
    }

    public JSONObject getJson() throws JSONException {
        return new JSONObject()
                .put("uid", uid)
                .put("name", name)
                .put("planted", planted)
                .put("height", height)
                .put("lat", lat)
                .put("lon", lon)
                .put("imageCode", imageCode)
                .put("description", description)
                .put("latin", latin)
                .put("origin", origin)
                .put("visited", visited);
    }

    @Override
    public int compareTo(Tree t) {
        return this.getName().compareTo(t.getName());
    }

    @Override
    public String toString() {
        return "{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", planted='" + planted + '\'' +
                ", height='" + height + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", imageCode='" + imageCode + '\'' +
                ", description='" + description + '\'' +
                ", latin='" + latin + '\'' +
                ", origin='" + origin + '\'' +
                ", visited'" + visited + '\'' +
                '}';
    }
}
