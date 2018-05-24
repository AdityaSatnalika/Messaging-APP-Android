package sendmessage.aditya.com.message;

public class User {

    public String name;
    public String email;
    public String number;
    public String password;

    public User()
    {
    }

    public User(String name, String email,String password,String number ) {
        this.name = name;
        this.email=email;
        this.password= password;
        this.number=number;
    }
}