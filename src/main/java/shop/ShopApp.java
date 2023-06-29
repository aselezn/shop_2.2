package shop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ShopApp {

    public static final String databaseUrl = "jdbc:mysql://localhost:3306/shop_db";
    public static final String databaseUser = "root";
    public static final String databasePassword = "root";

    private JTextField databaseUrlField;
    private JTextField tableNameField;
    private JButton loadButton;
    private JTable dataTable;

    public void shopApp() {

        JFrame frame = new JFrame("Shop Data Base");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.add(createMainPanel());
        frame.setVisible(true);
    }

    public JPanel createMainPanel() {

        JPanel panel = new JPanel(new BorderLayout());

        //создаем текстовые поле для db_url
        databaseUrlField = new JTextField();
        databaseUrlField.setPreferredSize(new Dimension(200, 15));
        databaseUrlField.addKeyListener(createKeyListener());

        //создаем текстовые поле для db_table_name
        tableNameField = new JTextField();
        tableNameField.setPreferredSize(new Dimension(200, 15));
        tableNameField.addKeyListener(createKeyListener());

        //создаем кнопку load_data
        loadButton = new JButton("Load Data");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataFromTable();
            }
        });

        // добавление компонентов на панель
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Database URL:"));
        inputPanel.add(databaseUrlField);
        inputPanel.add(new JLabel("Table Name:"));
        inputPanel.add(tableNameField);
        inputPanel.add(loadButton);

        // добавление табличного вида данных на панель
        dataTable = new JTable();
        dataTable.setGridColor(Color.BLACK);
        dataTable.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(dataTable);

        // Добавление панелей на главную панель приложения
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private KeyListener createKeyListener(){
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
        };
    }

    private void loadDataFromTable() {

        String tableName = tableNameField.getText();

        try (Connection connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery("SELECT * FROM " + tableName)) {

            // создание модели таблицы и заполнение данными
            DefaultTableModel tableModel = new DefaultTableModel();
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(resultSet.getMetaData().getColumnName(i));
            }
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(row);
            }

            // Установка модели таблицы
            dataTable.setModel(tableModel);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка при загрузке данных: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }

    }


}

