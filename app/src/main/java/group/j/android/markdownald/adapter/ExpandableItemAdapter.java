package group.j.android.markdownald.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import group.j.android.markdownald.R;
import group.j.android.markdownald.model.Note;
import group.j.android.markdownald.model.Notebook;
import group.j.android.markdownald.ui.activity.NoteEditActivity;
import group.j.android.markdownald.util.FileUtils;

/**
 * Implements <code>Adapter</code> with the expandable item.
 * Swiping to delete is also offered.
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final int TYPE_LEVEL_ZERO = 0;
    private static final int TYPE_LEVEL_ONE = 1;

    private Context context;

    public ExpandableItemAdapter(List<MultiItemEntity> data, Context context, int layoutResId) {
        super(data);
        this.context = context;
        this.setDefaultViewTypeLayout(layoutResId);
        addItemType(TYPE_LEVEL_ZERO, R.layout.activity_main_recycler);
        addItemType(TYPE_LEVEL_ONE, R.layout.activity_main_recycler);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_ZERO:
                holder.setText(R.id.text_title, ((Notebook) item).getTitle());

                holder.getView(R.id.view_content).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Notebook notebook = (Notebook) getData().get(pos);
                        if (notebook.isExpanded()) {
                            collapse(pos, true);
                        } else {
                            expand(pos, true);
                        }
                    }
                });

                break;
            case TYPE_LEVEL_ONE:
                holder.setText(R.id.text_title, ((Note) item).getTitle());

                holder.getView(R.id.view_content).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Note note = (Note) getData().get(pos);
                        Intent intent = new Intent(context, NoteEditActivity.class);
                        intent.putExtra("note_title", note.getTitle());
                        intent.putExtra("note_content", note.getContent());
                        context.startActivity(intent);
                    }
                });

                break;
        }

        holder.getView(R.id.text_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (getData().get(pos) instanceof Note) {
                    FileUtils.delete(context, ((Note) getData().get(pos)).getTitle());
                }
                remove(pos);

                notifyItemRemoved(pos);
            }
        });
    }
}
