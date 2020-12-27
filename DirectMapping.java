/*16 bit memory system => 2^16 (total size of memory space)
 256 Bytes = 2^8 bytes(size of cache)
 Block Size = 1 Bytes( 2^0 bytes)
  total block in cache = 2^8 / 2^0 = 2^8 (256 blocks)
 So we use 16 bit addressing system where 16 bits tag */
import java.util.*;
public class DirectMapping {
   static Node[] cache=new Node[(int)Math.pow(2,8)];
    static class Node{
        String[] tagArr=new String[1];
        char[] dataArr=new char[1];
        public  Node(String val,char val1){
            tagArr[0]=val;
            dataArr[0]=val1;
        }
    }
    public static void write(String address,char data){
        int addres=HexaToDeci(address);
        int cacheline=addres%cache.length;
        replacementChecking(cacheline);
        Node put= new Node(address, data);
        cache[cacheline]=put;
    }
    public static int HexaToDeci(String address){
        int number=0;
        for (int i = address.length()-1; i>=0; i--) {
            char c=address.charAt(i);
            int place=0;
            if(c-'a'>=0) {
                place = (c - 'a') + 10;
            }
            else{
                place=Integer.parseInt(Character.toString(c));
            }
            number=number+place*(int)Math.pow(16,address.length()-1-i);
        }
        return number;
    }
    public static void read(String address,Scanner sc){
        int put= HexaToDeci(address)%cache.length;
        if(cache[put] !=null && cache[put].tagArr[0].equals(address)){
            System.out.println(cache[put].dataArr[0]);
        }
        else {
            System.out.println("Cache Miss");
            System.out.println("----------");
            System.out.println("Do you want to write data to this address ");
            String s=sc.nextLine();
            if(s.equals("yes")){
                System.out.println("Please enter data that you want to write ");
                write(address,sc.nextLine().charAt(0));
                System.out.println("------------------");
            }
        }
    }
    public static void replacementChecking(int address){
        if(cache[address]!= null) {
            System.out.println("given address has replaced the address => " + cache[address].tagArr[0]);
        }
    }
    public static void writeMethodCalling(String address,char data){
        write(address,data);
    }
    public static void readMethodCalling(String address,Scanner sc){
        read(address,sc);
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        /* please give 16 bit address in hexadecimal format using small alphabets */
        int n=Integer.parseInt(sc.nextLine());
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
