import java.awt.*;
import java.util.*;

public class Critters{

    public final static int EMPTY = 0;
    public final static int SHARK = 1;
    public final static int FISH = 2;
    protected int iama;
    
    public Critters(){
        iama = EMPTY;
    }
    
    public int getCritterType(){
        return iama;
    }
    
    
    
    
    
}