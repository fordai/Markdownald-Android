package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


import group.j.android.markdownald.R;
import group.j.android.markdownald.base.BaseActivity;
import group.j.android.markdownald.db.DatabaseHelper;
import group.j.android.markdownald.model.Note;
import group.j.android.markdownald.util.scan.decode.DecodeThread;

public class ResultActivity extends BaseActivity {

	private DatabaseHelper mDatabase;
	private TextView mResultText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		mDatabase = getDatabase();
		Bundle extras = getIntent().getExtras();
		mResultText = (TextView) findViewById(R.id.result_text);

		if (null != extras) {
			int width = extras.getInt("width");
			int height = extras.getInt("height");

			LayoutParams lps = new LayoutParams(width, height);
			lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
			lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
			lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());

			String result = extras.getString("result");
			mResultText.setText(result);

			Bitmap barcode = null;
			byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
			if (compressedBitmap != null) {
				barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
				// Mutable copy:
				barcode = barcode.copy(Bitmap.Config.RGB_565, true);
			}
	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_scan, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_show:
				String content = mResultText.getText().toString();
				Intent intent = new Intent(this, NotePreviewActivity.class);
				intent.putExtra("note_name", "myscanNote");
				intent.putExtra("note_content", content);
				startActivity(intent);
				break;
			case R.id.menu_expot_note:
				String content2 = mResultText.getText().toString();
				if (!mDatabase.isNotebook("Default")) {
					mDatabase.createNotebook("Default");
				}
				long id = mDatabase.createNote(new Note("newScan", content2));
				mDatabase.createNoteToNotebook(id, mDatabase.getNotebookByName("Default").getId());
				Intent noteIntent = new Intent(ResultActivity.this, MainActivity.class);
				startActivity(noteIntent);
				break;
			case android.R.id.home:
				finish();
				break;
			default:
				break;
		}

		return true;
	}

}
