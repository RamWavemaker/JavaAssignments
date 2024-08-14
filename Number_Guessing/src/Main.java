import java.util.*;
public class Main {
    public static  void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        int n = random.nextInt(1000);
        while(true){
            System.out.print("Guess The Number : ");
            int a = sc.nextInt();
            if(a==n){
                System.out.println("you have guessed right! hurry");
                break;
            }else if(a<n){
                System.out.println("That is a Bigger Number than you guessed");
            }else{
                System.out.println("That is a Smaller Number than you guessed");
            }
        }
    }
}