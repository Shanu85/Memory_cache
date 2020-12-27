import java.util.LinkedList;
import java.util.*;

/* 16 bit memory system => 2^16 (total size of memory space)
 256 Bytes = 2^8 bytes(size of cache)
 Block Size = 1 Bytes( 2^0 bytes)
  total block in cache = 2^8 / 2^0 = 2^8 (256 blocks)
 So we use 16 bit addressing system where 16 bits tag*/
public class Associative_Cache {
    static Node[] cache = new Node[(int) Math.pow(2,8)];
    static LinkedList<Node> LRUqueue = new LinkedList<>();
    static LRUQueue head;
    static LRUQueue tail;

    static class Node {
        String[] tagArr = new String[1];
        char[] dataArr = new char[1];

        public Node(String val, char val1) {
            tagArr[0] = val;
            dataArr[0] = val1;
        }
    }

    public static void write(String address, char data) {
        Node temp = new Node(address, data);
        int i = 0;
        while (i < cache.length) {
            if (cache[i] == null) {
                cache[i] = temp;
                break;
            }
            if (cache[i].tagArr[0].equals(address)) {
                cache[i] = temp;
                break;
            }
            i++;
        }
        if (i == cache.length) {
            writeWithRepalcement(address, data);
        }
        Queue.insert(temp.tagArr[0]);
    }

    public static void writeWithRepalcement(String address, char data) {
        LRUQueue node = tail;
        for (int i = 0; i < cache.length; i++) {
            if (cache[i].tagArr[0].equals(node.addresskey)) {
                cache[i] = new Node(address, data);
                System.out.println("Address => " + address + " has replaced the address => " + tail.addresskey + " due to unavailability of space ");
                System.out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
                Queue.tailupdate();
                return;
            }
        }
    }

    public static void read(String address, Scanner sc) {
        for (int i = 0; i < cache.length; i++) {
            if (cache[i].tagArr[0].equals(address)) {
                System.out.println(cache[i].dataArr[0]);
                Queue.insert(address);
                return;
            }
        }
        System.out.println("Cache Miss");
        System.out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
        System.out.println("Do you want to write data to this address ");
        String s = sc.nextLine();
        if (s.equals("yes")) {
            System.out.println("Please enter data that you want to write ");
            char data = sc.nextLine().charAt(0);
            write(address, data);
            Queue.insert(address);
            System.out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
        }
    }

    static class LRUQueue {
        String addresskey;
        LRUQueue next;

        public LRUQueue(String k) {
            this.addresskey = k;
            this.next = null;
        }
    }

    static class Queue {
        public static void insert(String n) {
            LRUQueue lruNode = new LRUQueue(n);
            if (head == null && tail == null) {
                head = tail = lruNode;
                return;
            }
            LRUQueue temp = head;
            while (temp.next != null) {
                if (temp.next.addresskey.equals(n)) {
                    if (temp.next == tail) {
                        LRUQueue temp1 = temp.next;
                        temp.next = null;
                        tail = temp;
                        temp1.next = head;
                        head = temp1;
                        return;
                    } else {
                        LRUQueue temp1 = temp.next;
                        temp.next = temp1.next;
                        temp1.next = null;
                        temp1.next = head;
                        head = temp1;
                        return;
                    }
                }
                temp = temp.next;
            }
            lruNode.next = head;
            head = lruNode;
        }
        public static void tailupdate(){
            LRUQueue temp=head;
            while(temp.next!=tail){
                temp=temp.next;
            }
            temp.next=null;
            tail=temp;
        }
        public static void writeMethodCalling(String address, char data) {
            write(address, data);
        }

        public static void readMethodCalling(String address, Scanner sc) {
            read(address, sc);
        }

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int n = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < n; i++) {
                String input = sc.nextLine();
                String[] arr = input.split(" ");
                if (arr[0].equals("write")) {
                    writeMethodCalling(arr[1], arr[2].charAt(0));
                }
                if (arr[0].equals("read")) {
                    readMethodCalling(arr[1], sc);
                }
            }
        }
    }
}
