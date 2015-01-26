package com.example.drawonphoto;

public class ColorStringPair {
	public int Color;
	public String Name;
	public ColorStringPair(int Color, String Name)
	{
		this.Color = Color;
		this.Name = Name;
	}
	
	@Override
	public String toString() {
		return Name;
	}
}
