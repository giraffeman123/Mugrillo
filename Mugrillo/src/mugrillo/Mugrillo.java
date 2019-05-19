/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mugrillo;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author Elliot
 */
public class Mugrillo extends Sprite{

    private int dx;
    private int dy;
    private int life;
    private final Set<Integer> pressed = new HashSet<Integer>();
    
    public Mugrillo(int x, int y) {
        super(x, y);
        initMurillo();
    }
    
    private void initMurillo(){
        life = 3;
        dx = 0;
        dy = 0;
        setImage("mugrillo_derecha.png");
        getImageDimension();
    }

    public void move(){
        
        x += dx;
        y += dy;
        
        if(x < 1 ){
            x = 1;
        }
        if(x > 1300){
            x = 1300;
        }
    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        boolean a=true;

        pressed.add(key);
        if (pressed.size() > 1) {
            dx = 0;
        }else{
        
            if(key == KeyEvent.VK_LEFT || pressed.contains(KeyEvent.VK_LEFT)){         
                dx = -4;
                if(a){
                    setImage("mugrillo_corriendoIzquierda1.png");    
                    a = false;
                }
                else{
                    setImage("mugrillo_corriendoIzquierda2.png");
                    a = true;
                }
            }
            if(key == KeyEvent.VK_RIGHT || pressed.contains(KeyEvent.VK_RIGHT)){
                dx = 4;
                if(a){
                    setImage("mugrillo_corriendoDerecha1.png");
                    a = false;
                }
                else{
                    setImage("mugrillo_corriendoDerecha2.png"); 
                    a = true;
                }
            }
        
        }
        System.out.println("dx: "+dx);
    }
    
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        pressed.remove(key);        
        
        if(key == KeyEvent.VK_LEFT){
            dx = 0;
            setImage("mugrillo_izquierda.png");
        }
        if(key == KeyEvent.VK_RIGHT){
            dx = 0;
            setImage("mugrillo_derecha.png");
        }
        System.out.println("dx: "+dx);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
    
    
}
