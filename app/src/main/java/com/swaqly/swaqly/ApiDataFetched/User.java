package com.swaqly.swaqly.ApiDataFetched;

public class User {
    private static boolean status;
    private static String massege;
    private static String accessToken;
    private static int id;
    private static String name;
    private static String address;
    private static String email;

    public static boolean isStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        User.status = status;
    }

    public static String getMassege() {
        return massege;
    }

    public static void setMassege(String massege) {
        User.massege = massege;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        User.accessToken = accessToken;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        User.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        User.address = address;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public static void setEmailVerifiedAt(String emailVerifiedAt) {
        User.emailVerifiedAt = emailVerifiedAt;
    }

    private static String emailVerifiedAt;

}
