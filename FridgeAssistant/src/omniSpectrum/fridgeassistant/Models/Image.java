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
}
