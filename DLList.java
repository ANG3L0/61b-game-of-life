public class DLList{
    
    public DLNode head;
    public DLNode tail;
    private int listSize = 0;
    
    //constructor with no arguments => DLList
    //with a head
    public DLList(){
        this.listSize = 0;
        this.head = null;
        this.tail = null;
    }
    
    /* -type is either SHARK, EMPTY, or FISH.
       -size is the length of the encoding that the particular
      node represents
    */
    
    public void delete(DLNode node){
		if (node.prev==null){
			this.head = node.next;
		} else {
			node.prev.next = node.next;
		}
		if (node.next == null){
			this.tail = node.prev;
		} else {
			node.next.prev = node.prev;
		}
		this.listSize--;
	}
    
    public void insertAfter(DLNode node, DLNode newNode){
        newNode.prev = node;
        newNode.next = node.next;
        if (node.next == null){
            this.tail = newNode;
        } else {
            node.next.prev = newNode; //if ordered A,B,C originally, makes C's prev = B.
        }
        node.next = newNode;
        this.listSize++;
    }
    
    public void insertBefore(DLNode node, DLNode newNode){
        newNode.next = node;
        newNode.prev = node.prev;
        if (node.prev == null){
            this.head = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
        
        
        this.listSize++;
    }
    
    public void pushBeginning(DLNode newNode){
        //empty list.
        if (head==null){
            head = newNode;
            tail = newNode;
            newNode.prev = null;
            newNode.next = null;
            listSize++;
        } else {
            insertBefore(this.head, newNode);
        }
        
    }
    
    public void pushEnd(DLNode newNode){
        //empty list.
        if (tail==null){
            pushBeginning(newNode);
        } else {
            insertAfter(this.tail, newNode);
        }
        
    }
    
    public int getLength(){
        return this.listSize;
    }
    
}
