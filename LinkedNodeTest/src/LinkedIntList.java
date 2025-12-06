public class LinkedIntList {
    private ListNode front;
    public static void main(String[] args) {
        ListNode p = new ListNode(1, new ListNode(2));
        ListNode q = new ListNode(4, new ListNode(3));
        printList(p);
        printList(q);
        p.next.next = q;
        q = p.next;
        p.next = p.next.next.next;
        q.next.next = null;
        printList(p);
        printList(q);
    }

    public ListNode expand(ListNode head) {
        if (head != null) {
            head = expand(head.next, head.data);
        }
        return head;
    }

    private ListNode expand(ListNode cur, int num) {
        if (cur == null) return null;
        if (num == 0) {
            return expand(cur.next, cur.data);
        } else if (num == 1) {
            cur.next = expand(cur.next, 0);
        } else {
            cur.next = expand(new ListNode(cur.data, cur.next), num-1);
        }
        return cur;
    }

    public ListNode expandIterative() {
        if (front == null) return front;
        int count = front.data;
        front = front.next;
        ListNode cur = front;

        while (cur.next != null) {
            while (count > 1) {
                cur.next = new ListNode(cur.data, cur.next);
                cur = cur.next;
                count--;
            }
            count = cur.next.data;
            cur.next = cur.next.next;
            cur = cur.next;
        }
        if (cur != null) {
            while (count > 1) {
                cur.next = new ListNode(cur.data, cur.next);
                count--;
            }
        }
        return cur;
    }

    public LinkedIntList removeAlternating() {
        LinkedIntList list2 = new LinkedIntList();
        if (front != null && front.next != null) {
            list2.front = front;
            front = front.next;

            ListNode cur = front;
            ListNode cur2 = list2.front;
            cur2.next = null;
            int count = 0;
            while (cur != null && cur.next != null && cur.next.next != null) {
                if (count % 2 == 1) {
                    cur2.next = cur.next;
                    cur.next = cur.next.next;
                } else {
                    cur2.next = cur.next.next;
                    cur.next.next = cur.next.next.next;
                }
                cur = cur.next;
                cur2 = cur2.next;
                cur2.next = null;
                count++;
            }
        }
        return list2;
    }

    public static void printList(ListNode front) {
        ListNode cur = front;
        while (cur != null) {
            System.out.print(cur.data + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public int getLength() {
        return getLength(front);
    }
    
    private int getLength(ListNode cur) {
        if (cur == null) return 0;
        return getLength(cur.next) + 1;
    }

    public static class ListNode {
        public int data;
        public ListNode next;

        public ListNode() {
            this(0, null);
        }

        public ListNode(int data) {
            this(data, null);
        }

        public ListNode(int data, ListNode next) {
            this.data = data;
            this.next = next;
        }
    }
}