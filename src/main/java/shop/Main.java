package shop;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        SwingUtilities.invokeLater(() -> new ShopApp().shopApp());

    }
}