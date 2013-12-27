package info.androidhive.slidingmenu;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DealsInDetailActivity extends Activity {
	JSONObject json;
	String prevData;
	String OutputData = "exception";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dealsin_detail);

		TextView tv = (TextView) findViewById(R.id.textView4);
		ImageView iv = (ImageView) findViewById(R.id.imageView4);
		prevData = getIntent().getExtras().getString("thetext");
		String url = "https://api.groupon.com/v2/deals.json?division_id=san-francisco&client_id=e7e8598aacb082585913a164ea4c7eb5060b9f60";
		String imageUrl;
		try {
			new HomeFragment();
			json = HomeFragment.readJsonFromUrl(url);
			Log.d("the length", json.getJSONArray("deals").length() + "");
			for (int i = 0; i < json.getJSONArray("deals").length(); i++) {
				Log.d("in for loop", "hurray");
				Log.d(prevData, json.getJSONArray("deals").getJSONObject(i)
						.get("title").toString());
				if (prevData.equals(json.getJSONArray("deals").getJSONObject(i)
						.get("title").toString())) {
					Log.d("in if", "loop");
					OutputData = json.getJSONArray("deals").getJSONObject(i)
							.get("title").toString()
							+ " \n "
							+ "start at:  "
							+ json.getJSONArray("deals").getJSONObject(i)
									.get("startAt").toString()
							+ " \n "
							+ "end at : "
							+ json.getJSONArray("deals").getJSONObject(i)
									.get("endAt").toString();
					imageUrl = json.getJSONArray("deals").getJSONObject(i)
							.get("largeImageUrl").toString();
					tv.setText(OutputData);
					int loader = R.drawable.ic_launcher;
					ImageLoader imgLoader = new ImageLoader(
							getApplicationContext());
					Log.d("in image loader", "");
					imgLoader.DisplayImage(imageUrl, loader, iv);

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tv.setText(OutputData + "1");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tv.setText(OutputData + "2");

		}

		tv.setText(OutputData);

	}

}
