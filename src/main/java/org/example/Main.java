package org.example;
import com.ImportCSV;
import java.util.Scanner;
import com.Menu;
import comBase.ConnectionBase;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Menu.menuApp();
        ConnectionBase.connect();
    }
}