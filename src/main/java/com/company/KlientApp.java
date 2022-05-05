package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class KlientApp extends JFrame {
    public static String[] naglowki = null, producenci = null, proporcje = null;
    public static int wiersze = 0, kolumny = 15;
    public static Object[][] dane;
    public static KlientApp klientFrame = new KlientApp();
    public static DefaultTableModel tableModel;
    public static String textProporcje = "liczba laptopów z matrycami\no określonych proporcjach";
    public static URL url = null;
    public static QName qName;
    public static Service service;
    public static LaptopsInterface laptopsInterface;

    public KlientApp() {
        setSize(1500, 700);
        setTitle("Integracja systemów Lab5 - Aplikacja Klienta - Piotr Błażewicz");
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void connectSoap() throws MalformedURLException {
        url = new URL("http://localhost:8888/laptopy?wsdl");
        qName = new QName("http://company.com/", "LaptopsBeanService");
        service = Service.create(url, qName);
        laptopsInterface = service.getPort(LaptopsInterface.class);
    }

    public static void main(String[] args) throws MalformedURLException {
        connectSoap();

        naglowki = new String[]{"Producent",
                "Przekątna",
                "Rozdzielczość",
                "Rodzaj powierzchni ekranu",
                "Ekran dotykowy",
                "Procesor",
                "Liczba rdzeni",
                "Prędkość taktowania MHz",
                "RAM",
                "pojemność dysku",
                "rodzaj dysku",
                "Układ graficzny",
                "Pamięć GPU",
                "System operacyjny",
                "Napęd fizyczny"};

        dane = new Object[wiersze][kolumny];

        producenci = new String[]{"Samsung",
                "Acer",
                "Asus",
                "Fujitsu",
                "Huawei",
                "MSI",
                "Dell",
                "Sony"};

        proporcje = new String[]{"5:4",
                "4:3",
                "3:2",
                "16:10",
                "16:9",
                "5:3",
                "2:1",
                "21:9"};

        JComboBox<String> comboBox_Producent = new JComboBox<String>(producenci);

        JButton button_Producent = new JButton("Liczba laptopów producenta");

        JTextArea liczbaProducentowTA = new JTextArea();
        liczbaProducentowTA.setText("Liczba laptopow wybranego producenta: ");
        liczbaProducentowTA.setFocusable(false);

        JComboBox<String> comboBox_Matryca = new JComboBox<String>();
        comboBox_Matryca.addItem("matowa");
        comboBox_Matryca.addItem("blyszczaca");

        JButton button_Matryca = new JButton("Laptopy z określoną matrycą");

        JComboBox<String> comboBox_Proporcje = new JComboBox<String>(proporcje);

        JButton button_Proporcje = new JButton("<html>" + textProporcje.replaceAll("\\n", "<br>") + "</html>");

        JTextArea liczbaProporcjiTA = new JTextArea();
        liczbaProporcjiTA.setText("Liczba laptopow z matrycami o określonych proporcjach: ");
        liczbaProporcjiTA.setFocusable(false);

        tableModel = new DefaultTableModel(0, 0);
        tableModel.setColumnIdentifiers(naglowki);

        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(1480, 600));

        klientFrame.add(comboBox_Producent);
        klientFrame.add(button_Producent);
        klientFrame.add(liczbaProducentowTA);

        klientFrame.add(comboBox_Matryca);
        klientFrame.add(button_Matryca);

        klientFrame.add(comboBox_Proporcje);
        klientFrame.add(button_Proporcje);
        klientFrame.add(liczbaProporcjiTA);

        klientFrame.add(scrollPane);
        klientFrame.setVisible(true);

        button_Producent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                liczbaProducentowTA.setText("Liczba laptopow wybranego producenta: " + laptopsInterface.laptopsByProducent(Objects.requireNonNull(comboBox_Producent.getSelectedItem()).toString()));
            }
        });

        button_Matryca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = dane.length - 1; i >= 0; i--) {
                    tableModel.removeRow(i);
                }

                dane = laptopsInterface.laptopsByMatryca(Objects.requireNonNull(comboBox_Matryca.getSelectedItem()).toString());

                for (Object[] objects : dane) {
                    tableModel.addRow(objects);
                }
            }
        });

        button_Proporcje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                liczbaProporcjiTA.setText("Liczba laptopow z matrycami o określonych proporcjach: " + laptopsInterface.laptopsByProporcje(Objects.requireNonNull(comboBox_Proporcje.getSelectedItem()).toString()));
            }
        });
    }
}
