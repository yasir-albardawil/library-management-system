package net.buammar.model;

import com.google.gson.Gson;
import net.buammar.alert.AlertMaker;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;

public class Preferences {

    public static final String CONFIG_FILE = "config.txt";
    int noDaysWithoutFine;
    float finePerDay;
    String username;
    String password;

    public Preferences() {
        noDaysWithoutFine = 14;
        finePerDay = 2;
        username = "admin";
        setPassword("admin");
    }

    public int getNoDaysWithoutFine() {
        return noDaysWithoutFine;
    }

    public Preferences setNoDaysWithoutFine(int noDaysWithoutFine) {
        this.noDaysWithoutFine = noDaysWithoutFine;
        return this;
    }

    public float getFinePerDay() {
        return finePerDay;
    }

    public Preferences setFinePerDay(float finePerDay) {
        this.finePerDay = finePerDay;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Preferences setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 16) {
            this.password = DigestUtils.shaHex(password);
        } else {
            this.password = password;
        }

    }

    public static void initConfig() {
        Preferences perseverance= new Preferences();
        Gson gson = new Gson();
        Writer writer = null;
        try {
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(perseverance, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setPerseverance(Preferences perseverance) {
        Gson gson = new Gson();
        Writer writer = null;
        try {
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(perseverance, writer);

            AlertMaker.showSimpleAlert("Success", "Settings updated");
        } catch (IOException e) {
            AlertMaker.showErrorMessage(e, "Failed", "Can't save configuration file");
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Preferences getPreference() {
        Gson gson = new Gson();
        Preferences perseverance = new Preferences();
        try {
            perseverance = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);

        } catch (FileNotFoundException e) {
            initConfig();
            e.printStackTrace();
        }

        return perseverance;
    }
}
