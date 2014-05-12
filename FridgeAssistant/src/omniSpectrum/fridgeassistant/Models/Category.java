/**
 * Category Model
 */
package omniSpectrum.fridgeassistant.Models;

/**
 * @author danfreitas
 *
 */
public class Category {
	int id;
	String name;
	Image imgId;
	
	public Category(){}
	
	public Category(String name)
	{
		this.name = name;
	}
	
	public Category(String name, Image imgId){
		this.name = name;
		this.imgId = imgId;
	}
	public Category(int id, String name, Image img){
		this.id = id;
		this.name = name;
		this.imgId = img;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getImgId() {
		return imgId;
	}

	public void setImgId(Image imgId) {
		this.imgId = imgId;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        if (!imgId.equals(category.imgId)) return false;
        if (!name.equals(category.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + imgId.hashCode();
        return result;
    }
}
