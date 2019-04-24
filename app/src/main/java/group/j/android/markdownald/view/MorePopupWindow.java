package group.j.android.markdownald.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import group.j.android.markdownald.R;

/**
 * Implements <code>PopupWindow</code> for more operations, such as renaming and move.
 */
public class MorePopupWindow extends PopupWindow {
    private Context context;
    private View view;
    private Button btn_move;
    private Button btn_rename;

    public MorePopupWindow(Context context) {
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.activity_main_window, null);
        this.setContentView(this.view);
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.layout_window).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        btn_move = view.findViewById(R.id.btn_move);
        btn_rename = view.findViewById(R.id.btn_rename);
        btn_move.setOnClickListener(onClickListener);
        btn_rename.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_move:
                    break;
                case R.id.btn_rename:
                    break;
            }
        }
    };

}
