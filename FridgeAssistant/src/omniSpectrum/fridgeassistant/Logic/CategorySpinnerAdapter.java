package omniSpectrum.fridgeassistant.Logic;

import omniSpectrum.fridgeassistant.R;
import omniSpectrum.fridgeassistant.Models.Category;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {
	
	private final Context context;
	private final Category[] categoriesArray;

	public CategorySpinnerAdapter(Context context, Category[] array) {
		super(context, R.layout.categories_spinner_row, array);
		this.context = context;
	    this.categoriesArray = array;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

		//inflater - is a magic that creates new instance of given view
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    
	    // Creating Row
		View mySpinnerRow = 
				inflater.inflate(R.layout.categories_spinner_row, parent, false);
		
		// Fetching Views
		TextView myText = (TextView) mySpinnerRow.findViewById(R.id.categoryRowText);
		ImageView icon = (ImageView) mySpinnerRow.findViewById(R.id.categoryRowIcon);
		
		//Assign Values	
		myText.setText(categoriesArray[position].getName());
		int imageRid = context.getResources()
				.getIdentifier(categoriesArray[position].getImgId().getPath(), 
				"drawable", context.getPackageName());
		icon.setImageResource(imageRid);
		
		return mySpinnerRow;
	}
}
