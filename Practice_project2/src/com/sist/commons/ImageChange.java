package com.sist.commons;

import java.awt.Image;

import javax.swing.ImageIcon;
// 이미지 축소 / 확대 
public class ImageChange {
   public static Image getImage(ImageIcon icon,
		   int width,int height)
   {
	   return icon.getImage().
			   getScaledInstance(width, height, 
					       Image.SCALE_SMOOTH);
   }
}