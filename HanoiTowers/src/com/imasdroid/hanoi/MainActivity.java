package com.imasdroid.hanoi;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemSelectedListener {
	private static final String[] items = { "3", "4", "5", "6" };
	private String value;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button b = (Button) findViewById(R.id.button1);
		final Spinner spinner = (Spinner) findViewById(R.id.level_spinner);
		
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter
				.createFromResource(this, R.array.level_array,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String text = String.valueOf(spinner.getSelectedItem());
				boolean check = isInteger(text);
				if (check == true && Integer.parseInt(text) < 7) {

					Intent intent = new Intent(MainActivity.this,
							HanoiGameActivity.class);
					intent.putExtra("thetext", value);
					startActivity(intent);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setMessage("enter numerical value less than 7");
					builder.setCancelable(false);
					builder.setPositiveButton("Exit",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									MainActivity.this.finish();
								}
							});
					builder.setNegativeButton("back",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
					AlertDialog alert = builder.create();
					alert.show();

				}

			}
		});

	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		
		// An item was selected. You can retrieve the selected item using
       value = parent.getItemAtPosition(pos).toString();
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hanoi_game, menu);
		return true;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

}