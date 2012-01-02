public class DLListTestDrive{
	
	public static void main(String args[]){
		DLNode initNode = new DLNode(Ocean.SHARK, 2);
		DLNode initNode3 = new DLNode(Ocean.EMPTY, 4);
		DLNode initNode2 = new DLNode(Ocean.FISH, 15);
		
		DLList list = new DLList();

		list.pushEnd(initNode);
		list.pushEnd(initNode3);
		list.insertBefore(list.tail, initNode2);
		System.out.println(list.head.data.type + " " + list.head.data.size);
		System.out.println(list.tail.data.type + " " + list.tail.data.size);
		System.out.println(list.head.next.data.type + " " + list.head.next.data.size);
		System.out.println(list.getLength());
	}
	
}
