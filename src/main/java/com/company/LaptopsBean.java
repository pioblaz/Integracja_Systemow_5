package com.company;

import javax.jws.WebService;

@WebService(endpointInterface = "com.company.LaptopsInterface")
public class LaptopsBean implements LaptopsInterface {

    Object[][] laptopsData = {};

    @Override
    public int laptopsByProducent(String producent) {
        int suma = 0;

        for (int i = 0; i < laptopsData.length; i++) {
            if (laptopsData[i][0].equals(producent)) {
                suma++;
            }
        }

        return suma;
    }

    @Override
    public Object[][] laptopsByMatryca(String matryca) {
        int indeks = 0, length = 0;

        for (int i = 0; i < laptopsData.length; i++) {
            if (laptopsData[i][3].equals(matryca)) {
                length++;
            }
        }

        Object[][] laptopsDataMatryca = new Object[length][15];

        for (int i = 0; i < laptopsData.length; i++) {
            if (String.valueOf(laptopsData[i][3]).equals(matryca)) {
                laptopsDataMatryca[indeks] = laptopsData[i];
                indeks++;
            }
        }

        return laptopsDataMatryca;
    }

    @Override
    public int laptopsByProporcje(String proporcje) {
        int suma = 0;
        double a, b, c, d, x1, x2;
        String[] liczby1 = proporcje.split(":");
        String[] liczby2;

        a = Double.parseDouble(liczby1[0]);
        b = Double.parseDouble(liczby1[1]);
        x1 = a / b;
        x1 = Math.round(x1 * 100.0) / 100.0;

        for (int i = 0; i < laptopsData.length; i++) {
            if (!(laptopsData[i][2].toString().equals("Brak informacji"))) {
                liczby2 = laptopsData[i][2].toString().split("x");
                c = Double.parseDouble(liczby2[0]);
                d = Double.parseDouble(liczby2[1]);
                x2 = c / d;
                x2 = Math.round(x2 * 100.0) / 100.0;

                if (x1 == x2) {
                    suma++;
                }
            }
        }

        return suma;
    }
}
