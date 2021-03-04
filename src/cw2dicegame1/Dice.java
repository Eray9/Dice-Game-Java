/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw2dicegame1;

//packages
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author E SECURED
 */
public class Dice {
    
   Die[] dies = new Die[6]; //die arrays
	Random rnd = new Random();
	
	public Dice()//Sets the image of the dice in relation outcome
	{
		dies[0] = new Die();
		dies[0].setValue(1);
		dies[0].setImage(new ImageIcon("die_face_1.png"));
		dies[1] = new Die();
		dies[1].setValue(2);
		dies[1].setImage(new ImageIcon("die_face_2.png"));
		dies[2] = new Die();
		dies[2].setValue(3);
		dies[2].setImage(new ImageIcon("die_face_3.png"));
		dies[3] = new Die();
		dies[3].setValue(4);
		dies[3].setImage(new ImageIcon("die_face_4.png"));
		dies[4] = new Die();
		dies[4].setValue(5);
		dies[4].setImage(new ImageIcon("die_face_5.png"));
		dies[5] = new Die();
		dies[5].setValue(6);
		dies[5].setImage(new ImageIcon("die_face_6.png"));
	}

	public Die[] roll()//rol method to get 5 random figures 
	{
		Die[] d = new Die[5];

		for(int i=0; i<5; i++)
		{
			d[i] = dies[rnd.nextInt(dies.length)];
		}

		return d;		
	}
}
