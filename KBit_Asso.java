import java.util.*;
public class KBit_Asso {
    static int size=256;
    static int numberSet=8;
    static Node[] cache=new Node[size];
    static class Node{
        String[] tagArr=new String[1];
        char[] dataArr=new char[1];
        public  Node(String val,char val1){
            tagArr[0]=val;
            dataArr[0]=val1;
        }
    }
    public static void write(String address,char data){
        String hexa_bin_address=HexaToBinary(address);
        String setNumber=hexa_bin_address.substring(13,16);
        int setNum=Integer.parseInt(setNumber,2);
        int setposition=(size/numberSet)*setNum;
        Node n=new Node(address,data);
        placeinSet(n,setposition,setposition+(size/numberSet)-1);
    }
    public static void placeinSet(Node n,int start,int end){
        for (int i = start; i <=end; i++) {
            if(cache[i]==null){
                cache[i]=n;
                return;
            }
            if(cache[i].tagArr[0].equals(n.tagArr[0])){
                System.out.println("value of address "+n.tagArr[0]+" has been updated to "+n.dataArr[0]+" from "+ cache[i].dataArr[0]);
                System.out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
                replace(i,n,end);
                return;
            }
        }
        System.out.println("value of address "+cache[start].tagArr[0] +" has been replaced by "+n.tagArr[0]);
        System.out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
        replace(start,n,end);
    }
    public static void replace(int position,Node n,int end){
        for (int i =position; i <end; i++) {
            if(cache[i+1]!=null){
                cache[i]=cache[i+1];
            }
            if(cache[i+1]==null || i+1==end){
                cache[i+1]=n;
                return;
            }
        }

    }
    public static void read(String address, Scanner sc){
        String hexa_bin_address=HexaToBinary(address);
        String setNumber=hexa_bin_address.substring(13,16);
        int setNum=Integer.parseInt(setNumber,2);
        int setposition=(size/numberSet)*setNum;
        for (int i = setposition; i <setposition+(size/numberSet); i++) {
            if(cache[i].tagArr[0].equals(address)){
                System.out.println(cache[i].dataArr[0]);
                replace(setposition,new Node(cache[i].tagArr[0],cache[i].dataArr[0]),setposition+(size/numberSet)-1);
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
            System.out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
        }
    }
    static HashMap<Character,String> hexaToBinary;
    public static String HexaToBinary(String address){
        String number="";
        put();
        for (int i =0; i<address.length(); i++) {
            char c=address.charAt(i);
            hexaToBinary.get(c);
            number=number+ hexaToBinary.get(c);
        }
        return number;
    }
    public static void writeMethodCalling(String address, char data) {
        write(address, data);
    }

    public static void readMethodCalling(String address, Scanner sc) {
        read(address, sc);
    }
    public static void put(){
        hexaToBinary=new HashMap<>();
        hexaToBinary.put('0',"0000");
        hexaToBinary.put('1',"0001");
        hexaToBinary.put('2',"0010");
        hexaToBinary.put('3',"0011");
        hexaToBinary.put('4',"0100");
        hexaToBinary.put('5',"0101");
        hexaToBinary.put('6',"0110");
        hexaToBinary.put('7',"0111");
        hexaToBinary.put('8',"1000");
        hexaToBinary.put('9',"1001");
        hexaToBinary.put('a',"1010");
        hexaToBinary.put('b',"1011");
        hexaToBinary.put('c',"1100");
        hexaToBinary.put('d',"1101");
        hexaToBinary.put('e',"1110");
        hexaToBinary.put('f',"1111");
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
