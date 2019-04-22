package group.j.android.markdownald.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Stores data for one note.
 */
public class Note implements MultiItemEntity {
    private String title;
    private String content;

    public Note(String title) {
        this.title = title;
        this.content = "";
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return 1;
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
