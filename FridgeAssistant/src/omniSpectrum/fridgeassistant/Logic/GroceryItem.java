package omniSpectrum.fridgeassistant.Logic;

public class GroceryItem {
	
	int id;
	ItemDefinition myItem;
	String name;
	
	public GroceryItem(){
		
	}

	public GroceryItem(int id, ItemDefinition myItem, String name) {
		this.id = id;
		this.myItem = myItem;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemDefinition getMyItem() {
		return myItem;
	}

	public void setMyItem(ItemDefinition myItem) {
		this.myItem = myItem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
