package omniSpectrum.fridgeassistant.UI;

import java.util.ArrayList;
import java.util.Random;

import omniSpectrum.fridgeassistant.Logic.DatabaseHelper;
import omniSpectrum.fridgeassistant.Logic.InventoryArrayAdapter;
import omniSpectrum.fridgeassistant.Models.Category;
import omniSpectrum.fridgeassistant.Models.Image;
import omniSpectrum.fridgeassistant.Models.ItemDefinition;

import omniSpectrum.fridgeassistant.R;

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
		
		//playWithDataBase();
		
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
		Random myDummyAmount = new Random();
		ItemDefinition[] myDummyList = {
				new ItemDefinition(1,
						new Category("Drinks", 
								new Image("herePathToImg")), 
									"Milk", myDummyAmount.nextInt(20)),
				new ItemDefinition(2,
						new Category("Drinks", 
								new Image("herePathToImg")), "" +
									"Cola", myDummyAmount.nextInt(20)),
				new ItemDefinition(3,
						new Category("Drinks", 
								new Image("herePathToImg")), 
									"Beer", myDummyAmount.nextInt(20))
		};
		
		// Find list view layout
		ListView inventoryListView = 
				(ListView) findViewById(R.id.inventoryListView);
		
		// Create Adapter
		// TODO Custom adapter
		InventoryArrayAdapter adapter = 
				new InventoryArrayAdapter(this, myDummyList);
		
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
