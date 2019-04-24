package group.j.android.markdownald.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Stores a list of <code>Note</code>.
 */
public class Notebook extends AbstractExpandableItem<Note> implements MultiItemEntity {
    private String title;

    public Notebook(String title) {
        this.title = title;
    }

    public Notebook(String title, List<Note> notes) {
        this.title = title;
        this.setSubItems(notes);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
