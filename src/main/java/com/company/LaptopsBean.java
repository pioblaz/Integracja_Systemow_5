package com.company;

import javax.jws.WebService;

@WebService(endpointInterface = "com.company.LaptopsInterface")
public class LaptopsBean implements LaptopsInterface {

    Object[][] laptopsData;

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
    public String[] producents() {
        String[] producenci = null;
        String producenciTMP = "";
        int indeks = 0;

        for (int i = 0; i < laptopsData.length; i++) {
            producenci[i] = laptopsData[i][0].toString();

            /*if (i == 0) {
                producenci[indeks] = producenciTMP;
                indeks++;
            }

            for (int j = 0; j < producenci.length; j++) {
                if (!producenciTMP.equals(producenci[j])) {
                    producenci[indeks] = producenciTMP;
                    indeks++;
                }
            }*/
        }

        return producenci;
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
    public int countLaptopsByMatryca(String proporcje) {
        int suma = 0;

        for (int i = 0; i < laptopsData.length; i++) {
            if (laptopsData[i][0].equals(proporcje)) {
                suma++;
            }
        }

        return suma;
    }
}
