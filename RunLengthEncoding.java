/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
  private int width; //width of ocean
  private int height; //height of ocean
  private int st; //starve time
  private DLList dllist; //doubly-linked list.
  private DLNode iterator; //doubly-linked node.
  private DLNode preit = new DLNode(); //the -1th node in the list for nextRun();


  /**
   *  The following methods are required for Part II.
   */

	/**
   *  RunLengthEncoding() (with three parameters) is a constructor that creates
   *  a run-length encoding of an empty ocean having width i and height j,
   *  in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public RunLengthEncoding(int i, int j, int starveTime) {
    // Your solution here.
    this.width = i;
    this.height = j;
    this.st = starveTime;
    this.dllist = new DLList();
    
    DLNode initNode = new DLNode(Ocean.EMPTY, i*j, st);
    this.dllist.pushEnd(initNode);
	
	preit.next = this.dllist.head;
	this.iterator = preit; //write the iterator to point to
	//the head of the list.
  }

  /**
   *  RunLengthEncoding() (with five parameters) is a constructor that creates
   *  a run-length encoding of an ocean having width i and height j, in which
   *  sharks starve after starveTime timesteps.  The runs of the run-length
   *  encoding are taken from two input arrays.  Run i has length runLengths[i]
   *  and species runTypes[i].
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   *  @param runTypes is an array that represents the species represented by
   *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
   *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
   *         sharks (which are equivalent to sharks that have just eaten).
   *  @param runLengths is an array that represents the length of each run.
   *         The sum of all elements of the runLengths array should be i * j.
   */

  public RunLengthEncoding(int i, int j, int starveTime,
                           int[] runTypes, int[] runLengths) {
    // Your solution here.
    this.width = i;
    this.height = j;
    this.st = starveTime;
    this.dllist = new DLList();
    int numRuns = runTypes.length;
    
    //keep pushing nodes with kth runTypes/runLengths
    //iterations = # of total runs.
    for (int k = 0; k < numRuns; k++){
		dllist.pushEnd(new DLNode(runTypes[k], runLengths[k], this.st));
	}
	preit.next = this.dllist.head;
	this.iterator = preit; //write the iterator to point to
	//the head of the list.
  }

  /**
   *  restartRuns() and nextRun() are two methods that work together to return
   *  all the runs in the run-length encoding, one by one.  Each time
   *  nextRun() is invoked, it returns a different run (represented as a
   *  TypeAndSize object), until every run has been returned.  The first time
   *  nextRun() is invoked, it returns the first run in the encoding, which
   *  contains cell (0, 0).  After every run has been returned, nextRun()
   *  returns null, which lets the calling program know that there are no more
   *  runs in the encoding.
   *
   *  The restartRuns() method resets the enumeration, so that nextRun() will
   *  once again enumerate all the runs as if nextRun() were being invoked for
   *  the first time.
   *
   *  (Note:  Don't worry about what might happen if nextRun() is interleaved
   *  with addFish() or addShark(); it won't happen.)
   */

  /**
   *  restartRuns() resets the enumeration as described above, so that
   *  nextRun() will enumerate all the runs from the beginning.
   */

  public void restartRuns() {
    // Your solution here.
    preit.next = this.dllist.head;
    this.iterator = preit;
  }

  /**
   *  nextRun() returns the next run in the enumeration, as described above.
   *  If the runs have been exhausted, it returns null.  The return value is
   *  a TypeAndSize object, which is nothing more than a way to return two
   *  integers at once.
   *  @return the next run in the enumeration, represented by a TypeAndSize
   *          object.
   */

  public TypeAndSize nextRun() {
    // Replace the following line with your solution.
    this.iterator = this.iterator.next;
    if (this.iterator != null){
		return this.iterator.data;
	} else {
		return null;
	}
  }

  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

  public Ocean toOcean() {
    // Replace the following line with your solution.
    DLNode iter = this.dllist.head;
    Ocean ocean = new Ocean(this.width, this.height, this.st);
    int runT;
    int runL;
    int x = 0;
    int y = 0; //start out at 0,0.
    while(iter != null){
		runT = iter.data.type; 
		runL = iter.data.size;
		//System.out.println(runT + " " + runL);
		
		switch (runT){
			case Ocean.EMPTY:
			for (int i = 0; i < runL; i++){
				if (x == this.width){
					x = 0;
					y++;
					x++;
				} else {
					x++;
				}
			}
			break;
			case Ocean.FISH:
			for (int i = 0; i < runL; i++){
				if (x == this.width){
					x = 0;
					y++;
					ocean.addFish(x,y);
					x++;
				} else {
					ocean.addFish(x,y);
					x++;
				}
			}
			break;
			
			case Ocean.SHARK:
			for (int i = 0; i < runL; i++){
				if (x == this.width){
					x = 0;
					y++;
					ocean.addShark(x,y,iter.starveTime);
					x++;
				} else {
					ocean.addShark(x,y,iter.starveTime);
					x++;
				}
			}
			break;
		}
		
		iter = iter.next;
		//System.out.println(x + " " + y);
	}
	
    return ocean;
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of an input Ocean.  You will need to implement
   *  the sharkFeeding method in the Ocean class for this constructor's use.
   *  @param sea is the ocean to encode.
   */

  public RunLengthEncoding(Ocean sea) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    //variables for iteration
    this.width = sea.width();
    this.height = sea.height();
    this.st = sea.starveTime();
    
    //variables for linked list
    this.dllist = new DLList();
    int type = sea.cellContents(0,0);
    int typeCopy = type;
    int length = 0;
    int hunger;
    if (type == Ocean.SHARK) hunger = sea.sharkFeeding(0,0);
    else hunger = -1;
    int hungerCopy = hunger;
    
    for (int y = 0; y<this.height; y++){
		for (int x = 0; x<this.width; x++){
			type = sea.cellContents(x,y);
			if (type == Ocean.SHARK) hunger = sea.sharkFeeding(x,y);
			
			//check if types are different
			if (typeCopy != type){
				if (typeCopy == Ocean.SHARK){
					this.dllist.pushEnd(new DLNode(typeCopy, length, hunger));
					//sharks need a hunger input, while empty/fish do not.
				} else {
					this.dllist.pushEnd(new DLNode(typeCopy, length));
				}
				typeCopy = type;
				hungerCopy = hunger;
				length = 0;
			} //if types are same make sure that hungers haven't changed.
			else if (hungerCopy != hunger && type==Ocean.SHARK){
				this.dllist.pushEnd(new DLNode(typeCopy, length, hungerCopy));
				hungerCopy = hunger;
				length = 0;
			}
			length++;	
		}
	}
	//the last segment hasn't been added, so we add it now:
	if (type == Ocean.SHARK) this.dllist.pushEnd(new DLNode(type, length, hunger));
	else this.dllist.pushEnd(new DLNode(type, length));
    
    preit.next = this.dllist.head;
    this.iterator = preit;
    
    check();
  }

  /**
   *  The following methods are required for Part IV.
   */

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.  The final run-length
   *  encoding should be compressed as much as possible; there should not be
   *  two consecutive runs of sharks with the same degree of hunger.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    int width = this.width;
    int height = this.height;
    int EMPTY = Ocean.EMPTY;
    
	Ocean ocean = new Ocean(width,height,1); //Just a helper object
	DLNode node = new DLNode(Ocean.FISH,1);
	DLNode iter = new DLNode(); //iterator
	int[] z = ocean.modReturn(x,y);
	x = z[0]; y = z[1];
	
	//set start/end indices of the fish node.
	node.setStart(x,y,width);
	node.setEnd(x,y,width);
	if (this.dllist.head == null){
		/*	empty case: nothing to search for, and thus we will have to
			add EMPTY, FISH, EMPTY.
		  * Since addFish adds one fish at a time, we can always just push
		  * a fish of length 1 for the EMPTY, FISH, EMPTY case.
		  */	
		if (x!=0 || y!=0){
			//if not addFish(0,0)
			DLNode emptyPrior = new DLNode(EMPTY,node.start);
			emptyPrior.start = 0;
			emptyPrior.end = node.start - 1;
			this.dllist.pushEnd(emptyPrior);
		}
		//otherwise just add the fish, and then add an empty node at
		//the end.
		this.dllist.pushEnd(node);
		//Do settings for the empty node after the fish.
		DLNode emptyAfter = new DLNode(EMPTY,width*height-node.end-1);
		emptyAfter.start = node.end + 1;
		emptyAfter.end = width*height - 1;
		//push it.
		this.dllist.pushEnd(emptyAfter);
	} else {
		//nonempty case:
		//find proper place to put the node and insert it, if it needs
		//to be inserted:
		iter = this.dllist.head;
		iter.start=0; iter.end=iter.data.size+iter.start-1; //set iterator start/end initially.
		DLNode otherLeg = new DLNode(EMPTY,1);
		
		while(iter != null){
			//Assume node we add is in the middle of a long EMPTY segment. ie
			/*
			 * XXXXX---O------XXX
			*/
			if (node.start > iter.start && node.end < iter.end && iter.data.type==EMPTY){
				//otherLeg here is a node representing a leg of empty space AFTER our fish node.
				//update end/start.
				otherLeg.end = iter.end;
				otherLeg.start = node.end+1;
				//update length
				otherLeg.data.size = otherLeg.end-otherLeg.start+1;
				//no need to update type (still empty).
				
				//alter first leg properties:
				iter.end = node.start-1; //update "end" position (no need to update "start").
				iter.data.size = iter.end-iter.start+1; //update length
				//still empty, no need to update type.
	
				//add in our Fish
				this.dllist.insertAfter(iter, node);
				//add in latter leg.			
				this.dllist.insertAfter(node,otherLeg);
				break;
			} else if (node.start==iter.start && node.end < iter.end && iter.data.type==EMPTY){
				//XXXXO------XXXX
				//insert before iter (empty leg)
				this.dllist.insertBefore(iter, node);
				//alter iter properties
				iter.data.size--; //shrinks by 1.
				iter.start++; //start position increases by 1.
				//absorb prior X's if necessary.
				if (node.prev!=null && (node.prev.data.type==node.data.type)){
					node.prev.end++; 
					node.prev.data.size++;
					this.dllist.delete(node);
				}
				break;
			} else if (node.start > iter.start && node.end == iter.end && iter.data.type==EMPTY){
				//XXXX-------OXXXX
				//insert after iter (empty leg)
				this.dllist.insertAfter(iter, node);
				//alter iter properties
				iter.data.size--;
				iter.end--;
				//absorb later X's if necessary.
				if (node.next!=null && (node.next.data.type == node.data.type)){
					node.next.start--;
					node.next.data.size++;
					this.dllist.delete(node);
				}
				break;
			} else if (node.start == iter.start && node.end == iter.end && iter.data.type==EMPTY){
				System.out.println("debug this");
				//XXXXOXXXX
				//replace empty leg's .type with fish.
				iter.data.type=Ocean.FISH;
				node = iter;
				//absorb prior and/or later X's if necessary.
				if (node.prev!=null && (node.prev.data.type==node.data.type)){
					node.prev.end++;
					node.prev.data.size++;
					node = node.prev;//node is now combined fish node, node.next
					//is now an extraneous 1-length fish node.
					this.dllist.delete(node.next);
				}
				if (node.next!=null && (node.next.data.type==node.data.type)){
					node.next.start=node.start;
					node.next.data.size+=node.data.size;
					this.dllist.delete(node);
				}
				break;
			}
		iter = iter.next;
		iter.start = iter.prev.end+1;
		iter.end = iter.start + iter.data.size - 1;
		}
	}
	
      
    check();
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  The final run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs of sharks with the same degree
   *  of hunger.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
	int width = this.width;
    int height = this.height;
    int starveTime = this.st;
    int EMPTY = Ocean.EMPTY;
    
	Ocean ocean = new Ocean(width,height,1); //Just a helper object
	DLNode node = new DLNode(Ocean.SHARK,1,starveTime);
	DLNode iter = new DLNode(); //iterator
	int[] z = ocean.modReturn(x,y);
	x = z[0]; y = z[1];
	
	//set start/end indices of the shark node.
	node.setStart(x,y,width);
	node.setEnd(x,y,width);
	if (this.dllist.head == null){
		/*	empty case: nothing to search for, and thus we will have to
			add EMPTY, SHARK, EMPTY.
		  * Since addFish adds one fish at a time, we can always just push
		  * a fish of length 1 for the EMPTY, SHARK, EMPTY case.
		  */	
		if (x!=0 || y!=0){
			//if not addShark(0,0)
			DLNode emptyPrior = new DLNode(EMPTY,node.start);
			emptyPrior.start = 0;
			emptyPrior.end = node.start - 1;
			this.dllist.pushEnd(emptyPrior);
		}
		//otherwise just add the fish, and then add an empty node at
		//the end.
		this.dllist.pushEnd(node);
		//Do settings for the empty node after the fish.
		DLNode emptyAfter = new DLNode(EMPTY,width*height-node.end-1);
		emptyAfter.start = node.end + 1;
		emptyAfter.end = width*height - 1;
		//push it.
		this.dllist.pushEnd(emptyAfter);
	} else {
		//nonempty case:
		//find proper place to put the node and insert it, if it needs
		//to be inserted:
		iter = this.dllist.head;
		iter.start=0; iter.end=iter.start+iter.data.size-1;
		DLNode otherLeg = new DLNode(EMPTY,1);
		while(iter != null){
			//Assume node we add is in the middle of a long EMPTY segment. ie
			/*
			 * XXXXX---O------XXX
			*/
			if (node.start > iter.start && node.end < iter.end && iter.data.type==EMPTY){
				otherLeg.end = iter.end;
				otherLeg.start = node.end+1;
				//update length
				otherLeg.data.size = otherLeg.end-otherLeg.start+1;
				//no need to update type (still empty).
				
				//alter first leg properties:
				iter.end = node.start-1; //update "end" position (no need to update "start").
				iter.data.size = iter.end-iter.start+1; //update length
				//still empty, no need to update type.
	
				//add in our Shark
				this.dllist.insertAfter(iter, node);
				//add in latter leg.			
				this.dllist.insertAfter(node,otherLeg);
				break;
			} else if (node.start==iter.start && node.end < iter.end && iter.data.type==EMPTY){
				//XXXXO------XXXX
				//insert before iter (empty leg)
				this.dllist.insertBefore(iter, node);
				//alter iter properties
				iter.data.size--; //shrinks by 1.
				iter.start++; //start position increases by 1.
				//absorb prior X's if necessary.
				if (node.prev!=null && (node.prev.data.type==node.data.type) &&
						(node.prev.starveTime==node.starveTime)){
					node.prev.end++;
					node.prev.data.size++;
					this.dllist.delete(node);
				}
				break;
			} else if (node.start > iter.start && node.end == iter.end && iter.data.type==EMPTY){
				//XXXX-------OXXXX
				//insert after iter (empty leg)
				this.dllist.insertAfter(iter, node);
				//alter iter properties
				iter.data.size--;
				iter.end--;
				//absorb later X's if necessary.
				if (node.next!=null && (node.next.data.type == node.data.type) &&
						(node.next.starveTime==node.starveTime)){
					node.next.start--;
					node.next.data.size++;
					this.dllist.delete(node);
				}
				break;
			} else if (node.start == iter.start && node.end == iter.end && iter.data.type==EMPTY){
				//XXXXOXXXX
				//replace empty leg's .type with fish.
				iter.data.type=Ocean.SHARK;
				node = iter;
				//absorb prior and/or later X's if necessary.
				if (node.prev!=null && (node.prev.data.type==node.data.type) &&
						(node.prev.starveTime==node.starveTime)){
					node.prev.end++;
					node.prev.data.size++;
					node = node.prev;//node is now combined fish node, node.next
					//is now an extraneous 1-length fish node.
					this.dllist.delete(node.next);
				}
				if (node.next!=null && (node.next.data.type==node.data.type) &&
						(node.next.starveTime == node.starveTime)){
					node.next.start=node.start;
					node.next.data.size+=node.data.size;
					this.dllist.delete(node);
				}
				break;
			}
		iter = iter.next;
		iter.start = iter.prev.end+1;
		iter.end = iter.data.size + iter.start-1;
		}
	}
    check();
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */

  public void check() {
	  int size = this.width*this.height;
	  int sizeCounter = 0;
	  DLNode iter = new DLNode();
	  iter = this.dllist.head;
	  while (iter!=null){
		  sizeCounter+=iter.data.size;
		  iter = iter.next;
	  }

	  if (sizeCounter!=size) System.out.println("Sum of lengths != total length");
	  iter = this.dllist.head;
	  while (iter!=null && iter.next!=null){
		  if (iter.data.type==iter.next.data.type){
			  if (iter.data.type!=Ocean.SHARK || (iter.data.type==Ocean.SHARK &&
				iter.starveTime==iter.next.starveTime))
			  System.out.println("Compactness is violated");
			  break;
		  }
		  iter=iter.next;
	  }
  }
	public static void paint(Ocean sea) {
		  if (sea != null) {
			  int width = sea.width();
			  int height = sea.height();

			  /* Draw the ocean. */
			  for (int x = 0; x < width + 2; x++) {
				  System.out.print("-");
			  }
			  System.out.println();
			  for (int y = 0; y < height; y++) {
				  System.out.print("|");
				  for (int x = 0; x < width; x++) {
					  int contents = sea.cellContents(x, y);
					  if (contents == Ocean.SHARK) {
						  System.out.print('S');
					  } else if (contents == Ocean.FISH) {
						  System.out.print('~');
					  } else {
						  System.out.print(' ');
					  }
				  }
				  System.out.println("|");
			  }
			  for (int x = 0; x < width + 2; x++) {
				  System.out.print("-");
			  }
			  System.out.println();
		  }
	  }	
	  
	  public static void main(String args[]){
		  
	  }
}
