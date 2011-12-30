public class Shark extends Critters{

    private int ST;
    
    public Shark(int starveTime){
        this.ST = starveTime;
        iama = SHARK;
    }
    public Shark(){
        iama = SHARK;
    }
    
    public int starveSomeMoar(){
        this.ST--;
        return this.ST;
    }
    
    public void setStarveTime(int x){
        this.ST = x;
    }
    
    public int getStarveTime(){
        return this.ST;
    }
}