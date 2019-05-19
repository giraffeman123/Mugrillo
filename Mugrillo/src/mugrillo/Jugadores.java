package mugrillo;


public class Jugadores{
  private String nombre;
  private int record;

  public Jugadores(String nombre, int record){
    this.nombre = nombre;
    this.record = record;
  }
  public Jugadores(){
    this.nombre = "";
    this.record = 0;
  }

  public String getNombre(){
    return nombre;
  }

  public void setNombre(String nombre){
    this.nombre = nombre;
  }

  public int getRecord(){
    return record;
  }

  public void setRecord(int record){
    this.record = record;
  }
}
