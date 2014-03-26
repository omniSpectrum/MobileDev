package omniSpectrum.fridgeassistant.Logic;

public class ItemDefinition {
	
	int id;
	Category itemCategory;
	String name;
	
	public ItemDefinition(){}
	
	

	public ItemDefinition(Category itemCategory, String name) {
		this.itemCategory = itemCategory;
		this.name = name;
	}
	
	public ItemDefinition(int itemId, Category itemCategory, String name) {
		super();
		this.id = itemId;
		this.itemCategory = itemCategory;
		this.name = name;
	}

	public int getItemId() {
		return id;
	}


	public void setItemId(int itemId) {
		this.id = itemId;
	}

	public Category getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(Category itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
