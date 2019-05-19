package mugrillo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class RecordsBean extends JFrame{
    
    private boolean windowOpen;
    private JLabel l1;
    private JButton b1;
    private JTextField t1;
    private int puntaje;
    private Jugadores jugador;
    private AccesoArchivos guardar;
            
    public RecordsBean(int puntaje){
       setVisible(true);
       setBounds(200,200,300,200);
       setTitle("Guardar Puntaje");
       setLayout(null);
       setResizable(false);
       setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
       this.puntaje = puntaje;
       windowOpen = true;
       guardar = new AccesoArchivos();
       inicializar();
//       Cerrar();
    }
    
    public void finalizar(){
        this.setVisible(false);
    }
    
    public void inicializar(){
       l1 = new JLabel("Jugador :");
       b1 = new JButton("Aceptar");
       t1 = new JTextField("");
       this.add(l1);
       this.add(b1);
       this.add(t1);
       l1.setBounds(20,50,80,30);
       b1.setBounds(105,100,80,30);
       t1.setBounds(80,50,175,30);    
    }
    
    public boolean Cerrar(){
       windowOpen = true;
       b1.addActionListener(new ActionListener() {
          @Override
           public void actionPerformed(ActionEvent e) {
               if(t1.getText().toString().length()!= 0 && t1.getText().toString().length()<25){
                   jugador = new Jugadores(t1.getText(),puntaje);
                   guardar.registrarJugador(jugador);
                   windowOpen = false;
                   finalizar();
               }else{
                    JOptionPane.showMessageDialog(null,"No ingreso nombre o sobrepaso los 25 caracteres","WARNING",JOptionPane.WARNING_MESSAGE);           
                    windowOpen = true;
               }
           }
       });
       return windowOpen;
    }

    public boolean isWindowClosed() {
        return windowOpen;
    }

    public void setWindowClosed(boolean windowClosed) {
        this.windowOpen = windowClosed;
    }
    
    
    public JLabel getL1() {
        return l1;
    }

    public void setL1(JLabel l1) {
        this.l1 = l1;
    }

    public JButton getB1() {
        return b1;
    }

    public void setB1(JButton b1) {
        this.b1 = b1;
    }

    public JTextField getT1() {
        return t1;
    }

    public void setT1(JTextField t1) {
        this.t1 = t1;
    }
 
}
