package rph.mikazuki.microservices.account.model;

public class Account {
    private String id;
    private String token;

    public Account(String id) {
        this.id = id;
        this.token = generateToken(id);
    }

    public String getId(){
        return id;
    }

    public String getToken(){
        return token;
    }

    private String generateToken(String id) {
        return id;
    }

}
