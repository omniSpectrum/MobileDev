package omniSpectrum.fridgeassistant.Models;

//TODO change field path from string to byte[]
public class Image {
	int id;
	String path;
	
	public Image(){}

	public Image(String path) {
		this.path = path;
	}

	public Image(int id, String path) {
		this.id = id;
		this.path = path;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (id != image.id) return false;
        if (!path.equals(image.path)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + path.hashCode();
        return result;
    }
}
