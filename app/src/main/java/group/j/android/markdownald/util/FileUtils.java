package group.j.android.markdownald.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import group.j.android.markdownald.model.Note;
import group.j.android.markdownald.model.Notebook;

/**
 * Implements file operations.
 */
public class FileUtils {
    public static boolean exists(Context context, String name) {
        File file = context.getFileStreamPath(name);
        return file.exists();
    }

    public static void save(Context context, String title) {
        save(context, title, "");
    }

    public static void save(Context context, String title, String content) {
        FileOutputStream out = null;
        BufferedWriter writer = null;

        try {
            out = context.openFileOutput(title, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save(final Context context, final String title, final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            Handler handler = new Handler(Looper.myLooper());
            Runnable runnable;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacks(runnable);
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        FileUtils.save(context, title, editText.getText().toString());
                    }
                };
                handler.postDelayed(runnable, 500);
            }
        });
    }

    public static ArrayList<MultiItemEntity> load(Context context) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        String noteTitle;
        Notebook notebook = new Notebook("Default");
        File directory = context.getFilesDir();
        File[] files = directory.listFiles();

        for (File file : files) {
            if (!file.isDirectory()) {
                noteTitle = file.getName();
                Note note = new Note(noteTitle, load(context, noteTitle));
                notebook.addSubItem(note);
            }
        }
        res.add(notebook);

        return res;
    }

    public static String load(Context context, String fileName) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            in = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content.toString();
    }

    public static void delete(Context context, String title) {
        context.deleteFile(title);
    }

}
