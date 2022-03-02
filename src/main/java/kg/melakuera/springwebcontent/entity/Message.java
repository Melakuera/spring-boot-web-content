package kg.melakuera.springwebcontent.entity;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="text")
	private String text;

	@Column(name="tag")
	private String tag;
	
	public Message(String text, String tag) {
		this.text = text;
		this.tag = tag;
	}
	
	public Message() {}
	
	@Override
	public String toString() {
		return "Message [id=" + id + ", text=" + text + ", tag=" + tag + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	
}
