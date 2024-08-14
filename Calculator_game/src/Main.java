import java.math.BigInteger;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.print("Enter the value1:(if you want to exit enter exit) : ");
            String valuea = sc.next();
            if(valuea.equals("exit")){
                break;
            }
            BigInteger a = new BigInteger(valuea);
            System.out.print("Enter the value2: ");
            BigInteger b = new BigInteger(sc.next());
            System.out.print("Operation Wanted(add,sub,div,mul): ");
            String operation = sc.next().toLowerCase();
            if(operation.equals("add")){
                System.out.println(a.add(b));
            }else if(operation.equals("sub")){
                System.out.println(a.subtract(b));
            }else if(operation.equals("div")){
                System.out.println(a.divide(b));
            }else if(operation.equals("mul")){
                System.out.println(a.multiply(b));
            }
        }
    }
}