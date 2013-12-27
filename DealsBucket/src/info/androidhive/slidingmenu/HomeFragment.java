package info.androidhive.slidingmenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HomeFragment extends ListFragment {
	String[] itemsList; // itemsList store the list of items to boe displayed
	JSONObject jsonList; // the json object declaration
	String apiUrl; // the string that stored the url

	public HomeFragment() {
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(getActivity().getApplicationContext(),
				DealsInDetailActivity.class);
		try {
			Log.d("the extra string is", jsonList.getJSONArray("deals")
					.getJSONObject(position).get("title").toString());
			intent.putExtra("thetext", jsonList.getJSONArray("deals")
					.getJSONObject(position).get("title").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startActivity(intent);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onActivityCreated(savedInstanceState);

		// ListView lvDeals = (ListView) findViewById(android.R.id.list);
		apiUrl = "https://api.groupon.com/v2/deals.json?division_id=san-francisco&client_id=e7e8598aacb082585913a164ea4c7eb5060b9f60";
		Log.d("we are", "here");
		try {
			jsonList = readJsonFromUrl(apiUrl);
			itemsList = new String[jsonList.getJSONArray("deals").length()];
			for (int i = 0; i < jsonList.getJSONArray("deals").length(); i++) {
				itemsList[i] = jsonList.getJSONArray("deals").getJSONObject(i)
						.get("title").toString();
			}

		} catch (Exception e) {
			// TODO: handle exception
			Log.d("the unknown exception", "");
		}

		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(getActivity(),
		// android.R.layout.simple_list_item_1, itemsList);
		// setListAdapter(adapter);

		setListAdapter(new MyAdapter(getActivity().getApplicationContext(),
				android.R.layout.simple_list_item_1, R.id.textView1, itemsList));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rootView = inflater.inflate(R.layout.dealsbucket_main, container,
				false);

		return rootView;
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	private class MyAdapter extends ArrayAdapter<String> {

		public MyAdapter(Context context, int resource, int textViewResourceId,
				String[] strings) {
			super(context, resource, textViewResourceId, strings);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflator = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflator.inflate(R.layout.itemlist_view, parent, false);
			String[] items = itemsList;

			ImageView iv = (ImageView) row.findViewById(R.id.dealImage);
			TextView tv = (TextView) row.findViewById(R.id.textView1);
			TextView tv1 = (TextView) row.findViewById(R.id.textView2);

			tv.setText(items[position]);

			try {
				for (int j = 0; j < jsonList.getJSONArray("deals").length(); j++) {
					if (items[position] == jsonList.getJSONArray("deals")
							.getJSONObject(j).get("title").toString()) {
						String url = jsonList.getJSONArray("deals")
								.getJSONObject(j).get("grid4ImageUrl")
								.toString();
						int loader = R.drawable.loading_icon;
						ImageLoad downloader = new ImageLoad(iv);
						downloader.execute(url);
						//ImageLoader imgLoader = new ImageLoader(this.getContext().getApplicationContext());
						//Log.d("in image loader", "");
						//imgLoader.DisplayImage(url, loader, iv);
						tv1.setText("status : "
								+ jsonList.getJSONArray("deals")
										.getJSONObject(j).get("status")
										.toString());
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return row;
		}

	}
}
