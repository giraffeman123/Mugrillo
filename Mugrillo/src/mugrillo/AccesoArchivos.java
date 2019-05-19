
package mugrillo;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccesoArchivos {
  File arch1;
  FileInputStream flujoLectura;
  FileOutputStream flujoEscritura;
  DataInputStream leer;
  DataOutputStream escribir;
  String texto = "";
  String archivo = "puntajes.txt";

  public AccesoArchivos(){
    arch1 = new File(this.archivo);
  }

  public void registrarJugador(Jugadores a){
      try {
          flujoEscritura = new FileOutputStream(arch1,true);
          escribir = new DataOutputStream(flujoEscritura);
          escribir.writeUTF(a.getNombre());
          escribir.writeInt(a.getRecord());
          escribir.close();
      }catch(FileNotFoundException e){
      }catch(IOException e){
      }
  }

  public String leerRecords(){
      String linea;
      String todo="";
      this.texto = "";
      try {
          flujoLectura = new FileInputStream(arch1);
          leer = new DataInputStream(flujoLectura);
          while (true) {
              todo = todo + leer.readUTF() + "," + leer.readInt() +"\n";
          }
         
      }catch(FileNotFoundException e){
      }catch(IOException e){
      }
      try {
          leer.close();
      } catch (IOException ex) {
          Logger.getLogger(AccesoArchivos.class.getName()).log(Level.SEVERE, null, ex);
      }
      return todo;
  }

}
