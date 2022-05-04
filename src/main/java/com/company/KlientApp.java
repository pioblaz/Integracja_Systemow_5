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
    public static String[] naglowki = null, producenci = null;
    public static int wiersze = 0, kolumny = 15;
    public static Object[][] dane;
    public static KlientApp klientFrame = new KlientApp();
    public static DefaultTableModel tableModel;

    public KlientApp() {
        setSize(1500, 700);
        setTitle("Integracja systemów Lab5 Klient - Piotr Błażewicz");
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws MalformedURLException {
        URL url = null;

        url = new URL("http://localhost:8888/laptopy?wsdl");
        QName qName = new QName("http://company.com/", "LaptopsBeanService");

        Service service = Service.create(url, qName);
        LaptopsInterface laptopsInterface = service.getPort(LaptopsInterface.class);

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

        //producenci = laptopsInterface.producents();

        JComboBox<String> comboBox_Producent = new JComboBox<String>();
        comboBox_Producent.addItem("Dell");
        comboBox_Producent.addItem("Samsung");

        JButton button_Producent = new JButton("Liczba laptopów producenta");

        JTextArea liczbaProducentowTA = new JTextArea();     //text area do wyswietlania info
        liczbaProducentowTA.setText("Liczba laptopow wybranego producenta: ");
        liczbaProducentowTA.setFocusable(false);

        JComboBox<String> comboBox_Matryca = new JComboBox<String>();
        comboBox_Matryca.addItem("matowa");
        comboBox_Matryca.addItem("blyszczaca");

        JButton button_Matryca = new JButton("Laptopy z określoną matrycą");

        JComboBox<String> comboBox_Proporcje = new JComboBox<String>();
        comboBox_Proporcje.addItem("16x9");
        comboBox_Proporcje.addItem("16x10");

        JButton button_Proporcje = new JButton("Liczba laptopów z matrycami o określonych proporcjach");

        JTextArea liczbaProporcjiTA = new JTextArea();     //text area do wyswietlania info
        liczbaProporcjiTA.setText("Liczba laptopow z matrycami o określonych proporcjach: ");
        liczbaProporcjiTA.setFocusable(false);

        tableModel = new DefaultTableModel(0, 0);     //TableModel
        tableModel.setColumnIdentifiers(naglowki);

        JTable table = new JTable(tableModel) {
            @Override
            public void setValueAt(Object aValue, int row, int column) {    //edycja danych
                if (aValue.toString().trim().isEmpty()) {   //trim usuniecie bialych znakow - zeby spacje uznawalo jako puste
                    JOptionPane.showMessageDialog(klientFrame, "Pole nie może być puste!");
                } else if ((column == 4 || column == 10) && aValue.toString().trim().length() != 3) {
                    JOptionPane.showMessageDialog(klientFrame, "Tekst musi miec 3 znaki!");
                } else if (column == 1 && !aValue.toString().endsWith("\"")) {
                    JOptionPane.showMessageDialog(klientFrame, "Pole musi się kończyć na \"");
                } else if (column == 2 & !aValue.toString().matches("[0-9]+x[0-9]+")) {
                    JOptionPane.showMessageDialog(klientFrame, "Wprowadź wartość według wzoru, np. 1920x1080");
                } else {
                    super.setValueAt(aValue, row, column);
                }
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

                for (int i = 0; i < dane.length; i++) {
                    tableModel.addRow(dane[i]);
                }
            }
        });

        button_Proporcje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
