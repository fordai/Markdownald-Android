package group.j.android.markdownald.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


import group.j.android.markdownald.R;
import group.j.android.markdownald.scan.decode.DecodeThread;

public class ResultActivity extends Activity {

//	private ImageView mResultImage;
	private TextView mResultText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		Bundle extras = getIntent().getExtras();
//		mResultImage = (ImageView) findViewById(R.id.result_image);
		mResultText = (TextView) findViewById(R.id.result_text);

		if (null != extras) {
			int width = extras.getInt("width");
			int height = extras.getInt("height");

			LayoutParams lps = new LayoutParams(width, height);
			lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
			lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
			lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
			
//			mResultImage.setLayoutParams(lps);

			String result = extras.getString("result");
			mResultText.setText(result);

			Bitmap barcode = null;
			byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
			if (compressedBitmap != null) {
				barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
				// Mutable copy:
				barcode = barcode.copy(Bitmap.Config.RGB_565, true);
			}
//			mResultImage.setImageBitmap(barcode);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_note_edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_preview:
				String content = mResultText.getText().toString();
				Intent intent = new Intent(this, NotePreviewActivity.class);
				intent.putExtra("note_name", "myscanNote");
				intent.putExtra("note_content", content);
				startActivity(intent);
				break;
			case R.id.menu_share:
				String contentQR = mResultText.getText().toString();
				Intent intentQR = new Intent(this, NoteQRActivity.class);
				intentQR.putExtra("note_QR", contentQR);
				startActivity(intentQR);
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
