package omniSpectrum.fridgeassistant.UI;

import java.util.ArrayList;
import java.util.Random;

import omniSpectrum.fridgeassistant.Logic.Category;
import omniSpectrum.fridgeassistant.Logic.DatabaseHelper;
import omniSpectrum.fridgeassistant.Logic.Image;
import omniSpectrum.fridgeassistant.Logic.ItemDefinition;

import com.example.fridgeassistant.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	
	DatabaseHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		playWithDataBase();
		
		populateInventoryListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	/**
	 * Function ply with data base
	 */
	private void playWithDataBase(){
		
		db = new DatabaseHelper(getApplicationContext());
		
		//Creating images
		Image image1 = new Image("Test");
		Image image2 = new Image("Test2");
		Image image3 = new Image("Test3");
		
		//Inserting images into db
		long image1_id = db.createImage(image1);
		long image2_id = db.createImage(image2);
		long image3_id = db.createImage(image3);
		
		Log.d("Tag count", "Image Count: " + db.getAllImages());
		//Closing db
		db.closeDb();
		
		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}
	
	/**
	 * Function populates Inventory List view
	 */
	private void populateInventoryListView(){
		
		// TODO GET ALL LIST FROM DB Here
		
		// Dummy data TODO delete dummy data later
		ArrayList<ItemDefinition> myDummyList = new ArrayList<ItemDefinition>();
		myDummyList.add(
				new ItemDefinition(
						new Category("Drinks", 
								new Image("herePathToImg")), "Milk"));
		myDummyList.add(
				new ItemDefinition(
						new Category("Drinks", 
								new Image("herePathToImg")), "Cola"));
		myDummyList.add(
				new ItemDefinition(
						new Category("Drinks", 
								new Image("herePathToImg")), "Beer"));
		
		// Find list view layout
		ListView inventoryListView = 
				(ListView) findViewById(R.id.inventoryListView);
		
		// Create Adapter
		// TODO Custom adapter
		ArrayAdapter<ItemDefinition> adapter = 
				new ArrayAdapter<ItemDefinition>(this,
		        android.R.layout.simple_expandable_list_item_1, 
		        myDummyList);
		
		
		// Assign Adapter to listView
		inventoryListView.setAdapter(adapter);
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}
