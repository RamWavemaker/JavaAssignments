package repository;

import models.Login;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class LoginRepositoryImpl {
    ArrayList<Login> list = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public void Loginuser(int ID,String Email,String Password) { //O(1)
        Login login = new Login(ID,Email,Password);
        list.add(login);
    }


    public ArrayList<Login> getLoginusers() { //O(n)
        return new ArrayList<>(list);
    }

    public void deleteuser(int ID) { //O(n)
        Iterator<Login> iterator = list.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Login login = iterator.next();
            if (login.getID() == ID) {
                iterator.remove();
                found = true;
                System.out.println("User Deleted");
                break;
            }
        }
        if (!found) {
            System.out.println("User with ID " + ID + " not found.");
        }
    }
}
