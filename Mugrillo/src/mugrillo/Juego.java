/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mugrillo;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Elliot
 */
public class Juego extends JPanel implements ActionListener{
    
    Clip gameMusic;
    private boolean windowsOpen,recordBeanWindowOpen;
    private RecordsBean guardar;
    private int ingame;
    private int puntaje=0;
    private Mugrillo mugrillo;
    private ArrayList<Bloque> bloque;
    private final int MUGRILLO_X = 40;
    private final int MUGRILLO_Y = 610;
    private final int PANEL_WIDTH = 1360;
    private final int PANEL_HEIGHT = 700;
    private final int DELAY = 10;
    private Timer timer = new Timer(DELAY,this);
    private final String[] imgBloques = {
      "cucaracha.png","led_amarillo.png","led_rojo.png"
      ,"llave.png","mouse.png","serrucho.png",
      "vernier.png","araña.png","cucaracha_grande.png"
    };

    public boolean isWindowsOpen() {
        return windowsOpen;
    }

    public void setWindowsOpen(boolean windowsOpen) {
        this.windowsOpen = windowsOpen;
    }

    public boolean isRecordBeanWindowOpen() {
        return recordBeanWindowOpen;
    }

    public void setRecordBeanWindowOpen(boolean recordBeanWindowOpen) {
        this.recordBeanWindowOpen = recordBeanWindowOpen;
    }

    public int getPuntaje() {
        return puntaje;
    }
    
    private final int[] posXBloques = {
        700, 600, 200,
        1200, 1000, 400,
        100, 300, 500,
        800, 900, 1100
    };
    private final int[] posYBloques = {
        -200,-50,-60,
        -90,-80,-40,
        -90,-70,-50,
        -40,-60,-70
    };

    public int getIngame() {
        return ingame;
    }

    public void setIngame(int ingame) {
        this.ingame = ingame;
    }

    public Timer getTimer() {
        return timer;
    }
            
    public Juego(){
    }

    public void initJugar() {
        recordBeanWindowOpen = false;
        windowsOpen = true;
        addKeyListener(new AdapterMugrillo());
        setFocusable(true);
        setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        setBackground(Color.BLACK);
        
        mugrillo = new Mugrillo(MUGRILLO_X,MUGRILLO_Y); /*Posicion Inicial de Mugrillo*/
        initBloques();
        
        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("Jugando.wav").getAbsoluteFile());
            gameMusic = AudioSystem.getClip();
            gameMusic.open(audioInputStream);
            gameMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }          
        
        puntaje = 0;
        ingame = 1;
        timer.start(); /*Inicia el actionlistener del timer */
    }
    
    public void initBloques(){
        bloque = new ArrayList<>();
        int numImg,posX,posY; 
     
        for(String i : imgBloques){
            numImg = (int) (Math.random()*9);
            posY = (int) (Math.random()*12);
            posX = (int) (Math.random()*12);
            bloque.add(new Bloque(posXBloques[posX],posYBloques[posY],imgBloques[numImg]));
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        if(ingame == 1){ /*Si esta jugando se dibujan los objetos*/
            drawObjects(g);
        }
        if(ingame == -1){ /*Si esta en pausa se dibujan los objetos y el gamePause*/
            drawObjects(g);
            gamePause(g);
        }
        if(ingame == 0){ /* Si perdio el jugador se dibuja el panel de Game Over*/
            gameOver(g);
        }
    }
    
    private void gameOver(Graphics g){
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (PANEL_WIDTH - fm.stringWidth(msg)) / 2,
                PANEL_HEIGHT / 2);
        ImageIcon img = new ImageIcon("mugrillo_muerto.png");
        Image mMuerto = img.getImage();
        g.drawImage(mMuerto,580,450,200,80,this);
      
    }
    
    private void gamePause(Graphics g){
        Font small = new Font("Helvetica", Font.BOLD, 24);       
        FontMetrics fm = getFontMetrics(small);        
        g.setColor(Color.green);
        g.setFont(small);
        g.drawString("Pause", (PANEL_WIDTH - fm.stringWidth("Pause")) / 2,
                PANEL_HEIGHT / 2);        
    }
    
    private void drawObjects(Graphics g){

        ImageIcon img = new ImageIcon("fondo.png");
        Image fondo = img.getImage();
        g.drawImage(fondo,0,0,PANEL_WIDTH,PANEL_HEIGHT-30,this);        
        
        if(mugrillo.isVisible())
            g.drawImage(mugrillo.getImage(), mugrillo.getX(),mugrillo.getY(),this);
        for(Bloque i : bloque)
            if(i.isVisible())
                g.drawImage(i.getImage(),i.getX(),i.getY(),this);
        
        g.setColor(Color.red);
        g.drawString("Lifes left: "+mugrillo.getLife(),5,15);
        g.drawString("Points: "+puntaje,80,15);
    }
    
    private void inGame(){
        
        /* Dificultad */
        if(puntaje > 1000)
            for(Bloque i : bloque){
                i.setDy(5);
            }
        if(puntaje > 3000)
            for(Bloque i : bloque){
                i.setDy(10);
            }
        if(puntaje > 10000)
            for(Bloque i : bloque){
                i.setDy(15);
            }        
        /////////////////////
        
        if(ingame == -1){ /* Pausa */
            timer.stop();
            gameMusic.stop();
        }
        if(ingame == 1){ /* Jugando */
            timer.start();
            gameMusic.start();
        }
        if(ingame == 0){ /* Game Over */
            
            // Aqui agregaremos el textfield para guardar nombre de jugador y puntaje
            timer.stop();
            gameMusic.stop();
            if(windowsOpen){
                guardar = new RecordsBean(puntaje);
                recordBeanWindowOpen = guardar.Cerrar();
//                if(!recordBeanWindowOpen)
//                    gameOverMusic.stop();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        inGame();
        updateMugrillo();
        updateBloque();
        checkCollision();
        this.repaint();
//        System.out.println(ingame);
    }
    
    private void updateMugrillo(){
        if(mugrillo.isVisible())
            mugrillo.move();
        if(mugrillo.getLife() == 0){
            ingame = 0;        
        }
    }
    
    private void updateBloque(){
        for(Bloque i : bloque)
            if(i.isVisible())
                i.move();
    }
    
    public void checkCollision(){
        int numImg,posX,posY;
        Rectangle recMugrillo = new Rectangle(mugrillo.getBounds());
        
        for(int x=0;x<bloque.size();x++){
            Rectangle recBloque = new Rectangle(bloque.get(x).getBounds());
            if(recMugrillo.intersects(recBloque) && 
                    (bloque.get(x).getNombreImagen().equalsIgnoreCase("cucaracha.png") || 
                     bloque.get(x).getNombreImagen().equalsIgnoreCase("cucaracha_grande.png") ||
                     bloque.get(x).getNombreImagen().equalsIgnoreCase("araña.png") ) ){
                mugrillo.setImage("mugrillo_golpeado.png");
                mugrillo.setLife(mugrillo.getLife()-1);
                bloque.get(x).setVisible(false);
                bloque.get(x).setHeight(0);
                bloque.remove(x);
                numImg = (int) (Math.random()*9);
                posY = (int) (Math.random()*12);
                posX = (int) (Math.random()*12);   
                bloque.add(new Bloque(posXBloques[posX],posYBloques[posY],imgBloques[numImg]));
            }else if( recMugrillo.intersects(recBloque) ){
                puntaje += 140;
                bloque.get(x).setVisible(false);
                bloque.remove(x);
                numImg = (int) (Math.random()*9);
                posY = (int) (Math.random()*12);
                posX = (int) (Math.random()*12);   
                bloque.add(new Bloque(posXBloques[posX],posYBloques[posY],imgBloques[numImg]));                
            }
            if(recBloque.getY() >= 635){ /*635 es el piso,donde se encuentra murillo*/
                bloque.get(x).setVisible(false);
                bloque.remove(x);
                numImg = (int) (Math.random()*9);
                posY = (int) (Math.random()*12);
                posX = (int) (Math.random()*12);   
                bloque.add(new Bloque(posXBloques[posX],posYBloques[posY],imgBloques[numImg]));                
            }
        }
        
    }
    
    
    
    private class AdapterMugrillo extends KeyAdapter{
        
        @Override
        public void keyReleased(KeyEvent e){
            mugrillo.keyReleased(e);           
        }
        @Override
        public void keyPressed(KeyEvent e){
            mugrillo.keyPressed(e);
            int key = e.getKeyCode();
                if(key == KeyEvent.VK_ENTER){
                    ingame = -1;
                }
                if(key == KeyEvent.VK_BACK_SPACE){
                    ingame = 1;
                    inGame();
                }
        }
        

    }
}
