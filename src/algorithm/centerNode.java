package algorithm;

import java.util.LinkedList;

public class centerNode {
	centerNode next;

	public static void main(String[] args) {
		LinkedList list = new LinkedList<>();
		

	}

	
	public centerNode middleNode(centerNode head) {
        if(head == null){
            return null;
        }
        
        // // 1.Violent :  O(N - length), O(100)
        // ListNode[] nodeArray = new ListNode[100];
        // int i = 0;
        // while(head.next != null){
        //     nodeArray[i++] = head;
        //     head = head.next;
        // }
        // return nodeArray[i/2];
        
//         // 2. Two point :   O(n - 2*length), O(1)
//         ListNode n1 = head, n2 = head;
//         int i = 1;
//         while(n1.next != null){
//             n1 = n1.next;
//             i++;
//         }
        
//         int middle = i/2;
//         while(middle-- > 0){
//             n2 = n2.next;
//         }
//         return n2;
        
        // 3. fast and slow point
        centerNode slow = head, fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
	
	private void testMiddleNode() {
		
	}
}
