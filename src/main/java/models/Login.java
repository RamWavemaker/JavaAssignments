package models;

public class Login {
    public int ID;
    public String Email;
    public String Password;

    public Login(int ID, String Email, String Password){
        this.ID = ID;
        this.Email = Email;
        this.Password = Password;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
