/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw2dicegame1;

//Packages
import javax.swing.ImageIcon;

/**
 *
 * @author E SECURED
 */
public class Die implements DieIntf, Comparable<Die>{
   
      	private ImageIcon dieImage;
	private int value;

	public ImageIcon getDieImage() 
	{
		return dieImage;
	}

	public void setImage(ImageIcon image)
	{
		dieImage = image;
	}

	public void setValue(int v)
	{
		value = v;
	}

	public int getValue()
	{
		return value;
	}

	public int compareTo(Die die)
	{
		if (value>die.value)
			return 1;
		if (value<die.value)
			return -1;
		return 0;
	}  
}
