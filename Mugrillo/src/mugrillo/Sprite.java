/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mugrillo;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Elliot
 */
public class Sprite {
   protected int x;
   protected int y;
   protected int width;
   protected int height;
   protected boolean visible;
   protected Image image;
   
   public Sprite(int x,int y){
       this.x = x;
       this.y = y;
       visible = true;
   }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(String imageName) {
        ImageIcon aux = new ImageIcon(imageName);
        image = aux.getImage();
    }
   
   public void getImageDimension(){
       width = image.getWidth(null);
       height = image.getHeight(null);
   }
   
   public Rectangle getBounds(){
       return new Rectangle(x,y,width,height);
   }
}
