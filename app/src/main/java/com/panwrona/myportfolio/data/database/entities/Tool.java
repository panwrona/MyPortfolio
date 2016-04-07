package com.panwrona.myportfolio.data.database.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import java.util.List;

@Table(name = "tools")
public class Tool extends Model {

	@Column(name = "name")
	public String name;

	@Column(name = "description")
	public String description;

	@Column(name = "level")
	public int skillLevel;

	public Tool() {
		super();
	}

	public Tool(String name, String description, int skillLevel) {
		this.name = name;
		this.description = description;
		this.skillLevel = skillLevel;
	}

	public static List<Tool> getAll() {
		return new Select()
			.all()
			.from(Tool.class)
			.execute();
	}
}
