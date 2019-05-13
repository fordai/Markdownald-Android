package group.j.android.markdownald.db;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

import group.j.android.markdownald.base.BaseActivity;
import group.j.android.markdownald.model.Note;



public class DownLoadServer extends BaseActivity {

    private DatabaseHelper mDatabase;

    public void downloadData(String result){
        mDatabase = getDatabase();
        JsonObject js = new JsonParser().parse(result).getAsJsonObject();
        int f =0;
        for(Map.Entry<String, JsonElement> entry : js.entrySet()){
            if(f>=4){
                JsonObject notejs = new JsonParser().parse(entry.getValue().getAsString()).getAsJsonObject();
                int nid = Integer.parseInt(entry.getKey());
                String title = notejs.get("title").getAsString();
                if(!mDatabase.isNote(title)) {
                    String cla = notejs.get("class").getAsString();
                    String data = notejs.get("data").getAsString();
                    Note note = new Note(title, data);
                    mDatabase.createNote(note);
                    if(mDatabase.isNotebook(cla))
                        mDatabase.createNotebook(cla);
                    mDatabase.createNoteToNotebook(note.getId(), mDatabase.getNotebookByName(cla).getId());
                }
            }
            f = f+1;
        }
    }
}
