package mugrillo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JScrollPane;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Elliot
 */
public class MenuPrincipal extends JFrame{
    
    JFrame frameOfGame;
    Juego jugar;
    JPanel principal,mostrarRecords,mostrarCreditos;
    JButton juego,records,creditos,regresar,RegCreditos,mugrillo_baile;
    ImageIcon butJugar,butRecord,butCreditos,butRegresar,butRegCreditos;
    JTextArea puntajes;
    AccesoArchivos aA;
    Clip menuMusic;
    private final int PANEL_WIDTH = 1360;
    private final int PANEL_HEIGHT = 700;
    
    public MenuPrincipal(){
        initGUI();
    }
    
    private void initGUI(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(PANEL_WIDTH,PANEL_HEIGHT);
        setLayout(new BorderLayout(1,0));
        setResizable(false);

        /*GIF mugrillo bailando */
        mugrillo_baile = new JButton();  
        mugrillo_baile.setVisible(false);
        RegCreditos = new JButton();        
        RegCreditos.setVisible(false);  
        regresar = new JButton();
        regresar.setVisible(false);   
        
        /************* Audio  *********************/
        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("Menu.wav").getAbsoluteFile());
            menuMusic = AudioSystem.getClip();
            menuMusic.open(audioInputStream);
            menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        //*** Ventana del juego ****//
        frameOfGame = new JFrame("Mugrillo");
        frameOfGame.setBounds(0,0,PANEL_WIDTH,PANEL_HEIGHT);
        frameOfGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameOfGame.setLayout(null);
        frameOfGame.setResizable(false);
        frameOfGame.setVisible(false);
        frameOfGame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosed(WindowEvent e) {
                    menuMusic.start();
                    System.out.println("cerro");
                    jugar.setWindowsOpen(false);
                    jugar.setVisible(true);
                    jugar.setIngame(0);
            }
        });
        // Instanciacion de panel de juego
        jugar = new Juego();
        jugar.setBounds(1,2,PANEL_WIDTH,PANEL_HEIGHT);
        frameOfGame.add(jugar);  
        ////////////////////////////////////////////

     
        add(menu());
        add(mostrarRecords());
        add(mostrarCreditos());
    }
    
    public JPanel menu(){
        //Instanciacion de panel principal(menu)
        principal = new JPanel();
        principal.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        principal.setLayout(null);
        JLabel fondo = new JLabel(new ImageIcon("fondo_menu.png"));
        fondo.setBounds(-10,-30,PANEL_WIDTH+15,PANEL_HEIGHT);
        
        juego = new JButton("Jugar");
        juego.setBounds(530,250,290,50);
        butJugar = new ImageIcon("jugar.png");
        juego.setIcon(butJugar);
        records = new JButton("Records");
        records.setBounds(530,320,290,50);  
        butRecord = new ImageIcon("records.png");
        records.setIcon(butRecord);
        
        creditos = new JButton("Creditos");
        creditos.setBounds(530,390,290,50);
        butCreditos = new ImageIcon("creditos.png");
        creditos.setIcon(butCreditos);
        
        juego.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                  menuMusic.stop();
                  jugar.initJugar();
                  frameOfGame.setVisible(true);
//                setVisible(false);
            }  
        });
        
        records.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                regresar.setVisible(true);
                RegCreditos.setVisible(false);                  
                mugrillo_baile.setVisible(false); 
                principal.setVisible(false);
                mostrarCreditos.setVisible(false);                
                mostrarRecords.setVisible(true);
                String[] todo,nombres,stringRecord;
                int[] record = new int[100];
                int aux1;
                String auxRecord="",auxNombres="",aux,aux2 = "";
                aA = new AccesoArchivos();                
                aux = aA.leerRecords();                
                todo = aux.split("\n");
                int ban = 0;
                for(int x=0;x<todo.length;x++){
                   String[] datos = todo[x].split(",");
                   auxNombres += datos[0] +"\n";
                   auxRecord += datos[1] +"\n";
                }
                
                nombres = auxNombres.split("\n");
                stringRecord = auxRecord.split("\n");
                
                for(int x=0;x<stringRecord.length;x++){
                    record[x] = Integer.parseInt(stringRecord[x]);
                }
                
                for(int x=1;x<nombres.length;x++){
                    for(int y=0;y<nombres.length-x;y++){
                        if(record[y]<record[y+1]){
                           aux1 = record[y];
                           record[y] = record[y+1];
                           record[y+1] = aux1;
                           aux2 = nombres[y];
                           nombres[y] = nombres[y+1];
                           nombres[y+1] = aux2;
                        }
                    }
                }
                String records = "";
                for(int x=0;x<todo.length;x++){
                    records += nombres[x]+"----"+record[x]+"\n";
                }
                
                puntajes.setText(null);
                puntajes.append(records);
            }  
        });
        
        
        creditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               regresar.setVisible(false);
               RegCreditos.setVisible(true);                  
               mugrillo_baile.setVisible(true);
               principal.setVisible(false);
               mostrarRecords.setVisible(false);
               mostrarCreditos.setVisible(true);
            }
        });
               
        principal.add(juego);
        principal.add(creditos);
        principal.add(records);
        principal.add(fondo);        
        principal.setVisible(true);        
        return principal;
    }
    
    public JPanel mostrarCreditos(){
        mostrarCreditos = new JPanel();
        mostrarCreditos.setLayout(null);
        mostrarCreditos.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        mostrarCreditos.setLocation(0,0);
        
        JLabel fondo = new JLabel(new ImageIcon("fondo_menu.png"));
        fondo.setBounds(-10,-30,PANEL_WIDTH+15,PANEL_HEIGHT);
    
//        RegCreditos = new JButton();        
//        RegCreditos.setVisible(false);
        butRegCreditos = new ImageIcon("regresar1.png");
        RegCreditos.setIcon(butRegCreditos);
        RegCreditos.setBounds(800,600,274,45);
        RegCreditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               mugrillo_baile.setVisible(false);
               principal.setVisible(true);
               mostrarRecords.setVisible(false);
               mostrarCreditos.setVisible(false);
            }
        });        
              
        ImageIcon m_baile = new ImageIcon("mugrillo_bailando.gif");
        mugrillo_baile.setIcon(m_baile);
        mugrillo_baile.setBounds(400,400,48,44);
        
        JTextArea creditos = new JTextArea();
        creditos.setFont(new Font("Castellar",Font.BOLD,15));
        creditos.setEditable(false);
        
        Border marco = BorderFactory.createLineBorder(Color.BLACK);
        creditos.setBackground(Color.LIGHT_GRAY);
        creditos.setBorder(marco);
        creditos.append("Programadores:\n" +
            "Gonzalez Cruz Alan Fernando\n" +
            "Martinez Palomino Eduardo Elliot\n" +
            "Diseño:\n" +
            "Yañez Nevarez Luis Armando\n" +
            "Musica:\n" +
            "Martinez Palomino Eduardo Elliot");
        creditos.setBounds(500,300,400,150);
        
        mostrarCreditos.add(creditos);
        mostrarCreditos.add(mugrillo_baile);
        mostrarCreditos.add(RegCreditos);
        mostrarCreditos.add(fondo);
//        mostrarCreditos.setVisible(true);
        return mostrarCreditos;
    }
    
    public JPanel mostrarRecords(){
        mostrarRecords = new JPanel();
        mostrarRecords.setBackground(new Color(80,150,100));
        mostrarRecords.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        mostrarRecords.setLocation(0,0);
        mostrarRecords.setLayout(null);        

        JLabel etiqueta = new JLabel("Records");
        Border marco = BorderFactory.createLineBorder(Color.BLACK);
        etiqueta.setFont(new Font("Castellar",Font.ITALIC,60));
        
        butRegresar = new ImageIcon("regresar1.png");
        regresar.setIcon(butRegresar);
        regresar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                mugrillo_baile.setVisible(false);                
                principal.setVisible(true);
                mostrarCreditos.setVisible(false);
                mostrarRecords.setVisible(false);
            }  
        });        
                
        puntajes = new JTextArea(); 
        puntajes.setFont(new Font("Castellar",Font.BOLD,15));
        puntajes.setEditable(false);
        puntajes.setBorder(marco);
        puntajes.setBackground(Color.LIGHT_GRAY);
        
//        mostrarRecords.setVisible(false);
        
        etiqueta.setBounds(475,50,480,100);
        puntajes.setBounds(450,200,400,300);
        regresar.setBounds(800,600,274,45);
        
        JScrollPane scroll = new JScrollPane(puntajes);
        scroll.setBounds(450,200,400,300);      
        
        mostrarRecords.add(scroll);
        mostrarRecords.add(etiqueta);
        mostrarRecords.add(regresar);
        return mostrarRecords;
    }
    
    public static void main(String[] args){
        MenuPrincipal menu = new MenuPrincipal();
        menu.setVisible(true);
    }
    
}
