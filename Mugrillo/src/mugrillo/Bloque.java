package mugrillo;

public class Bloque extends Sprite{
    
    private int dx;
    private int dy;
    private String nombreImagen;

    public Bloque(int x, int y,String nombreImagen) {
        super(x, y);
        this.nombreImagen = nombreImagen;
        initBloque();
    }
    
    public void initBloque(){
        dx = 0;
        dy = 2;
        setImage(nombreImagen);
        getImageDimension();
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

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }    
    
    public void move(){
        
        if(y<635)
            y += dy;        
    }
}
