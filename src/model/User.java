/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Client
 */
public class User {
    static int id_user=0;
    static String nama_user="", role ="";
    static String password;

    public User() {
    }

    public static int getId_user() {
        return id_user;
    }

    public static void setId_user(int id_user) {
        User.id_user = id_user;
    }

    public static String getNama_user() {
        return nama_user;
    }

    public static void setNama_user(String nama_user) {
        User.nama_user = nama_user;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        User.role = role;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }
    
}

