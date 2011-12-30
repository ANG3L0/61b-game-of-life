import java.awt.*;
import java.util.*;

public class testDrive{
    
    public static void main(String[] args){
        Critters[][] x = new Critters[5][5];
        for (int i = 0; i<5; i++){
            for (int j=0; j<5; j++){
                x[i][j]=new Critters();
            }
        }
        int whatIsIt = 44;
        whatIsIt = x[2][3].getCritterType();
        System.out.println(whatIsIt);
        x[2][3] = new Fish();
        whatIsIt = x[2][3].getCritterType();
        System.out.println(whatIsIt +" this is fish?");
        x[2][3] = new Shark(16);
        whatIsIt = x[2][3].getCritterType();
        System.out.println(whatIsIt +" this is shark?");
    }
    
    
}