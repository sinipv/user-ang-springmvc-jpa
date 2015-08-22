package org.home.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;
	private String name;
	private String description;
	private String category;
	
	public User(){
		
	}
	
	public User(String name, String description, String category, int id) {
		super();
		this.name = name;
		this.description = description;
		this.category = category;
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

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
    public boolean equals(Object object) {
        if (object instanceof User){
            User user = (User) object;
            return user.id == id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
