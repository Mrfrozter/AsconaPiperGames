package hill.ascona.asconapipergames.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private int id;

    @Column(name = "p_name", length =25, nullable = false)
    private String name;

    @Column(name = "p_lastname", length =25, nullable = false)
    private String lastname;

    @Column(name = "p_nickname", length =25, nullable = false)
    private String nickname;

    @Column(name = "p_address", length =30)
    private String address;

    @Column(name = "p_postNumber", length = 10)
    private String postNumber;

    @Column(name = "p_city", length =15, nullable = false)
    private String city;

    @Column(name = "p_country", length =15, nullable = false)
    private String country;

    @Column(name = "p_email", length =50, nullable = false)
    private String email;

    @Column(name = "p_role", length =15, nullable = false)
    private String role;

    @Column(name = "p_teamID",length = 3, nullable = false)
    private String teamID;

    public Person() {
    }

    public Person(String name, String lastname, String nickname, String address, String postNumber, String city, String country, String email, String role, String teamID) {
        this.name = name;
        this.lastname = lastname;
        this.nickname = nickname;
        this.address = address;
        this.postNumber = postNumber;
        this.city = city;
        this.country = country;
        this.email = email;
        this.role = role;
        this.teamID = teamID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    @Override
    public String toString() {
        return
                "id: " + id +
                " " + name +
                " " + lastname +
                ", nickname: " + nickname +
                ", address: " + address +
                ", postNumber: " + postNumber +
                ", city: " + city +
                ", country: " + country +
                ", email: " + email +
                ", role: " + role +
                ", teamID: " + teamID;
    }
}
