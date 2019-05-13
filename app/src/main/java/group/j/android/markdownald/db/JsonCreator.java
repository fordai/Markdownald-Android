package group.j.android.markdownald.db;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonCreator {

    public JsonObject registerJson(String pid, String name, String password) {
        JsonObject json = new JsonObject();
        json.addProperty("operate","register");
        json.addProperty("pid",pid);
        json.addProperty("name",name);
        json.addProperty("password",password);
        JsonArray array = new JsonArray();
        return json;
    }

    public JsonObject loginJson(String pid, String password){
        JsonObject json = new JsonObject();
        json.addProperty("operate","login");
        json.addProperty("pid",pid);
        json.addProperty("password",password);
        return json;
    }

    public JsonObject addNote(int nid, String title, String clas, String data, String pid){
        JsonObject json = new JsonObject();
        json.addProperty("operate","addNote");
        json.addProperty("nid",nid);
        json.addProperty("title",title);
        json.addProperty("class",clas);
        json.addProperty("data",data);
        json.addProperty("pid",pid);
        return json;
    }

    public JsonObject updateNote(int nid, String title, String clas, String data, String date, String pid){
        JsonObject json = new JsonObject();
        json.addProperty("operate","updateNote");
        json.addProperty("nid",nid);
        json.addProperty("title",title);
        json.addProperty("class",clas);
        json.addProperty("data",data);
        json.addProperty("date",date);
        json.addProperty("pid",pid);
        return json;
    }

}