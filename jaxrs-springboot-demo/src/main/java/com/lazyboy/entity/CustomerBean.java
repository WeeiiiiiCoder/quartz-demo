package com.lazyboy.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @auther: zhouwei
 * @date: 2021/1/14 12:08
 */
public class CustomerBean {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private Boolean married;
    private Integer numberOfKids;
    private String favouriteQuote;
    private String email;
    private String customerNo;
    private long loyaltyPoints;
    private String mailingAddress;
    private BigDecimal salary;
    private Boolean vip;
    private Integer age;
    private Long times;
    private Double rate;
    private Character character;
    private String items;
    private String convertNullTo;
    private String emaildd;
    private String token;
    private String trime;

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getConvertNullTo() {
        return convertNullTo;
    }

    public void setConvertNullTo(String convertNullTo) {
        this.convertNullTo = convertNullTo;
    }

    public String getEmaildd() {
        return emaildd;
    }

    public void setEmaildd(String emaildd) {
        this.emaildd = emaildd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTrime() {
        return trime;
    }

    public void setTrime(String trime) {
        this.trime = trime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getMarried() {
        return married;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }

    public Integer getNumberOfKids() {
        return numberOfKids;
    }

    public void setNumberOfKids(Integer numberOfKids) {
        this.numberOfKids = numberOfKids;
    }

    public String getFavouriteQuote() {
        return favouriteQuote;
    }

    public void setFavouriteQuote(String favouriteQuote) {
        this.favouriteQuote = favouriteQuote;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public long getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(long loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }


}