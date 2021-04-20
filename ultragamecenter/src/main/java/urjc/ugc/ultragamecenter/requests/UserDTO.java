package urjc.ugc.ultragamecenter.requests;

public class UserDTO {
    private String name;

    private String passwordUnhashed;

    private String lastName;

    private String email;


    public UserDTO(String name, String passwordUnhashed, String lastName, String email) {
        this.name = name;
        this.passwordUnhashed = passwordUnhashed;
        this.lastName = lastName;
        this.email = email;
    }


    public UserDTO() {
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPasswordHash() {
        return passwordUnhashed;
    }


    public void setPasswordHash(String passwordHash) {
        this.passwordUnhashed = passwordHash;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return String.format("[ email = %s, lastname = %s, name = %s, password = %s]", email,this.lastName,name,this.passwordUnhashed);
    }
}

    


