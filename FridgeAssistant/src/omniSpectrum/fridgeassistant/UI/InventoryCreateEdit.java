package omniSpectrum.fridgeassistant.UI;

import java.util.ArrayList;
import java.util.List;

import omniSpectrum.fridgeassistant.R;
import omniSpectrum.fridgeassistant.R.id;
import omniSpectrum.fridgeassistant.R.layout;
import omniSpectrum.fridgeassistant.R.menu;
import omniSpectrum.fridgeassistant.Logic.DatabaseHelper;
import omniSpectrum.fridgeassistant.Models.Category;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class InventoryCreateEdit extends ActionBarActivity {
	
	DatabaseHelper db;
	Spinner spinner;
	Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_create_edit);
		
		//Calling database
		db = new DatabaseHelper(this);
		
		//Finding elements in the fragment
		spinner = (Spinner)findViewById(R.id.spCategory);
		
		//Populate spinner with data from DB
		loadspinner();
		
	
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inventory_create_edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//Method to populate Spinner
	private void loadspinner() {
		List<Category> categories = db.getAllCategories();
		
		ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(this, 
				android.R.layout.simple_spinner_item, categories);
		
		spinner.setAdapter(dataAdapter);
		
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast.makeText(InventoryCreateEdit.this, "Hello", Toast.LENGTH_SHORT).show();
		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_inventory_create_edit, container, false);
			return rootView;
		}
	}

}
