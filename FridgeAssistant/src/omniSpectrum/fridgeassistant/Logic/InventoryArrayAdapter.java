package omniSpectrum.fridgeassistant.Logic;

import omniSpectrum.fridgeassistant.R;
import omniSpectrum.fridgeassistant.Models.ItemDefinition;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InventoryArrayAdapter extends ArrayAdapter<ItemDefinition>{

	private final Context context;
	private final ItemDefinition[] itemsArray;
	
	public InventoryArrayAdapter(Context context, ItemDefinition[] array) {
	    super(context, R.layout.inventory_listview_single_item, array);
	    this.context = context;
	    this.itemsArray = array;
	}
	
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		
		//inflater - is a magic that creates new instance of given view
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    
	    //Create single row view
	    View rowView = inflater.inflate(R.layout.inventory_listview_single_item, 
	    		parent, false);
	    
	    //Fetch lines and image holder
	    TextView firstLineView = 
	    		(TextView) rowView.findViewById(R.id.listViewSingleItemFistLine);
	    TextView secondLineView = 
	    		(TextView) rowView.findViewById(R.id.listViewSingleItemSecondLine);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.listViewSingleItemIcon);
	    
	    //Assign values
	    firstLineView.setText(itemsArray[position].getName());
	    Integer itemBalance = itemsArray[position].getBalance();
	    secondLineView.setText(itemBalance.toString());
	    imageView.setImageResource(R.drawable.ic_launcher);

	    return rowView;
	  }	
	
}
