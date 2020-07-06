package domain;

public class Item {
	//클라이언트용 프로그램이면 속성 앞의 접근 지정자를 public 으로 하고
	//생성자만 만들고 접근자 메소드는 만들지 않아도 됩니다.
	private int code;
	private String title;
	private String category;
	private String description;
	
	public Item() {
		super();
	}
	public Item(int code, String title, String category, String description) {
		super();
		this.code = code;
		this.title = title;
		this.category = category;
		this.description = description;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Item [code=" + code + ", title=" + title + ", category=" + category + ", description=" + description
				+ "]";
	}
}
