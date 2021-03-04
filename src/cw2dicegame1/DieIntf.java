/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw2dicegame1;

//packages
import javax.swing.ImageIcon;

/**
 *
 * @author E SECURED
 */
public interface DieIntf {
  
    	ImageIcon getDieImage();
	void setImage(ImageIcon image);
	void setValue(int v);
	int getValue();	
}