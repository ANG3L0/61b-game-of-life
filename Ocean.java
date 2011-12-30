/* Ocean.java */

/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

  /**
   *  Do not rename these constants.  WARNING:  if you change the numbers, you
   *  will need to recompile Test4.java.  Failure to do so will give you a very
   *  hard-to-find bug.
   */

  public final static int EMPTY = 0;
  public final static int SHARK = 1;
  public final static int FISH = 2;

  /**
   *  Define any variables associated with an Ocean object here.  These
   *  variables MUST be private.
   */
  private int starveTime;
  private int width;
  private int height;
  
  /* MY VARIABLES */
  private Critters[][] grid; //grid represents ocean state.
  


  /**
   *  The following methods are required for Part I.
   */

  /**
   *  Ocean() is a constructor that creates an empty ocean having width i and
   *  height j, in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public Ocean(int i, int j, int starveTime) {
    // Your solution here.
    this.width = i;
    this.height = j;
    this.starveTime = starveTime;
    grid = new Critters[i][j];
    for (int x=0; x<i; x++){
        for(int y=0; y<j; y++){
            grid[x][y]=new Critters();
        }
    }
  }

  /**
   *  width() returns the width of an Ocean object.
   *  @return the width of the ocean.
   */

  public int width() {
    // Replace the following line with your solution.
    return this.width;
  }

  /**
   *  height() returns the height of an Ocean object.
   *  @return the height of the ocean.
   */

  public int height() {
    // Replace the following line with your solution.
    return this.height;
  }

  /**
   *  starveTime() returns the number of timesteps sharks survive without food.
   *  @return the number of timesteps sharks survive without food.
   */

  public int starveTime() {
    // Replace the following line with your solution.
    return this.starveTime;
  }

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    // Your solution here.
    //If the cell isn't empty, then put a new fish in there.
    int[] gridCoord = this.modReturn(x,y);
    if (grid[gridCoord[0]][gridCoord[1]].getCritterType() == EMPTY){
      grid[gridCoord[0]][gridCoord[1]] = new Fish();
    }
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    // Your solution here.
    //if it isn't empty, put a shark in there.
    int[] gridCoord = this.modReturn(x,y);
    if (grid[gridCoord[0]][gridCoord[1]].getCritterType() == EMPTY){
      grid[gridCoord[0]][gridCoord[1]] = new Shark(this.starveTime);
    }
  }

  /**
   *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
   *  a fish, and SHARK if it contains a shark.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int cellContents(int x, int y) {
    // Replace the following line with your solution.
    int[] gridCoord = this.modReturn(x,y);
    return grid[gridCoord[0]][gridCoord[1]].getCritterType();
  }

  /**
   *  timeStep() performs a simulation timestep as described in README.
   *  @return an ocean representing the elapse of one timestep.
   */

  public Ocean timeStep() {
    // Replace the following line with your solution.
    Ocean nextOcean = new Ocean(this.width,this.height,this.starveTime);
    int content = 0;
        for (int i = 0; i<this.width; i++){
            for (int j = 0; j<this.height; j++){
                content = cellContents(i,j);
                switch (content){
                case EMPTY:
                    updateEmptyCell(i,j,nextOcean);
                    break;
                case FISH:
                    updateFishCell(i,j,nextOcean);
                    break;
                
                case SHARK:
                    updateSharkCell(i,j,nextOcean);
                    break;
                }
            }
        }
    return nextOcean;
  }
  
  /* MY METHODS */
  /*
   input queries x,y
   output (x mod width, y mod height)
  */
  private int[] modReturn(int x, int y){
    //x--;
    //y--;
    int newX;
    int newY;
    int xmod = x%this.width;
    int ymod = y%this.height;
    //if stuff makes it more robust against negative solutions
      if (xmod < 0) newX = xmod + this.width;
      else newX = xmod;
      if (ymod < 0) newY = ymod + this.height;
      else newY = ymod;
    int[] gridConv = new int[] {newX,newY};
    return gridConv;
  }
  private int[] checkNeighbors(int x, int y){
        int numFish=0;
        int numShark=0;
        int numEmpty=0;
        int typ;
        //store neighbor coordinates.
        int[][] neighbor = new int[][] {modReturn(x+1,y), modReturn(x-1,y),
        modReturn(x,y+1), modReturn(x,y-1), modReturn(x+1,y+1),
        modReturn(x+1,y-1),modReturn(x-1,y+1),modReturn(x-1,y-1)};
        
        //run through neighbors to see what type they are.
        for (int i = 0; i<8; i++){
            typ = this.grid[neighbor[i][0]][neighbor[i][1]].getCritterType();
            switch (typ){
                case EMPTY:
                    numEmpty++;
                    break;
                case FISH:
                    numFish++;
                    break;
                case SHARK:
                    numShark++;
                    break;
            }
        }
        
        int[] emptySharkFish = new int[] {numEmpty, numShark, numFish};
        return emptySharkFish;
  }
  
  private void updateSharkCell(int x, int y, Ocean nextOcean){
    //mod x y
    int[] z = modReturn(x,y);
    x=z[0];
    y=z[1];
    //Initially, we have an empty ocean, so we add the appropriate
    //critter onto the next ocean which starts out blank.
    Shark nextShark = new Shark();
    nextShark = ((Shark) grid[x][y]);
    //check neighbors on current ocean
    int[] esf = this.checkNeighbors(x,y);
    //do stuff to next ocean's shark cell.
    if (esf[2]==0){
        if (nextShark.starveSomeMoar() >= 0){
            nextOcean.addShark(x,y);
            ((Shark) nextOcean.grid[x][y]).setStarveTime(nextShark.getStarveTime());
            //set next ocean's new shark to the current ocean's starve time
        } //still not hungry enough like me to die.
    }
    else{
        nextOcean.addShark(x,y);
        nextShark.setStarveTime(this.starveTime); //reset hunger counter.
    }
  }
  private void updateFishCell(int x, int y, Ocean nextOcean){
    //mod x y
    int[] z = modReturn(x,y);
    x=z[0];
    y=z[1];
    //check neighbors
    int[] esf = this.checkNeighbors(x,y);
    //do stuff to next ocean's fish cell
    if (esf[1]>1) nextOcean.addShark(x,y); //add a shark in current cell instead.
    if (esf[1]==0) nextOcean.addFish(x,y); //coast is clear, add a fish.
  }
  
  private void updateEmptyCell(int x, int y, Ocean nextOcean){
    //mod x y
    int[] z = modReturn(x,y);
    x=z[0];
    y=z[1];
    //check neighbors
    int[] esf = this.checkNeighbors(x,y);
    //do stuff to the next ocean's empty cell
    if (esf[2] >= 2 && esf[1] < 2) nextOcean.addFish(x,y);
    else if (esf[2] >=2 && esf[1] >=2) nextOcean.addShark(x,y);
  }
  
  
  /**
   *  The following method is required for Part II.
   */

  /**
   *  addShark() (with three parameters) places a shark in cell (x, y) if the
   *  cell is empty.  The shark's hunger is represented by the third parameter.
   *  If the cell is already occupied, leave the cell as it is.  You will need
   *  this method to help convert run-length encodings to Oceans.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   *  @param feeding is an integer that indicates the shark's hunger.  You may
   *         encode it any way you want; for instance, "feeding" may be the
   *         last timestep the shark was fed, or the amount of time that has
   *         passed since the shark was last fed, or the amount of time left
   *         before the shark will starve.  It's up to you, but be consistent.
   */

  public void addShark(int x, int y, int feeding) {
    // Your solution here.
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  sharkFeeding() returns an integer that indicates the hunger of the shark
   *  in cell (x, y), using the same "feeding" representation as the parameter
   *  to addShark() described above.  If cell (x, y) does not contain a shark,
   *  then its return value is undefined--that is, anything you want.
   *  Normally, this method should not be called if cell (x, y) does not
   *  contain a shark.  You will need this method to help convert Oceans to
   *  run-length encodings.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int sharkFeeding(int x, int y) {
    // Replace the following line with your solution.
    return 0;
  }

}
