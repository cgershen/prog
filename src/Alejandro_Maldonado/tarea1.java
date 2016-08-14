/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Ideone
{
    public static void main (String[] args)
    {
        Client c = new Client();
        c.setFirst_name("Alejandro");
        c.setLast_name("Maldonado");
        c.setEmail("amaldonado480@gmail.com");
        c.setPersonListener(new OnGeneratePersonListener() {
            @Override
            public void onCreatePerson(GenericPerson person) {
                System.out.println("Persona "+person.getFirst_name()+" creada");
            }
        });
        c.createPerson();
        
        User u = new User();
        u.setFirst_name("Juan");
        u.setLast_name("Maldonado");
        u.setEmail("juan@gmail.com");
        u.setUser_name("JuanXd");
        u.setPersonListener(new OnGeneratePersonListener() {
            @Override
            public void onCreatePerson(GenericPerson person) {
                System.out.println("Usuario "+person.getFirst_name()+" creado");
            }
        });
        u.createPerson();
    }
}

class Client extends GenericPerson{
    private String rfc;
    private String address;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
class User extends GenericPerson{
    private String user_name;
    private String password;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class GenericPerson {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private  OnGeneratePersonListener personListener;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPersonListener(OnGeneratePersonListener personListener) {
        this.personListener = personListener;
    }
    public void createPerson(){
        personListener.onCreatePerson(this);
    }
}
interface OnGeneratePersonListener{
    public void onCreatePerson(GenericPerson person);
}