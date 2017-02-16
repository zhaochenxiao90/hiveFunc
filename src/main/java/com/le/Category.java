package com.le;

import java.util.ArrayList;
import java.util.List;

public class Category {
     public String name=null;
     public String aliase=null;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAliase() {
		return aliase;
	}
	public void setAliase(String aliase) {
		this.aliase = aliase;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aliase == null) ? 0 : aliase.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (aliase == null) {
			if (other.aliase != null)
				return false;
		} else if (!aliase.equals(other.aliase))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Category [name=" + name + ", aliase=" + aliase + "]";
	}
	public Category(String name, String aliase) {
		super();
		this.name = name;
		this.aliase = aliase;
	}
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
     
	 
	
}
