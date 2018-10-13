package pl.coderslab.zad4;

/*

Kostka do gry

        Kod takiej kostki wygląda następująco:
        xDy+z

        gdzie:

        y – rodzaj kostek, których należy użyć (np. D6, D10),
        x – liczba rzutów kośćmi (jeśli rzucamy raz, ten parametr jest pomijalny),
        z – (opcjonalnie) liczba, którą należy dodać (lub odjąć) do wyniku rzutów.

        Przykłady:

        2D10+10 – 2 rzuty D10, do wyniku dodaj 10,
        D6 – zwykły rzut kostką sześcienną,
        2D3 – rzut dwiema kostkami trójściennymi,
        D12-1 – rzut kością D12, od wyniku odejmij 1.


Napisz funkcję, która:

    przyjmie w parametrze taki kod w postaci String,
    rozpozna wszystkie dane wejściowe:
        rodzaj kostki,
        liczbę rzutów,
        modyfikator,
    wykona symulację rzutów i zwróci wynik.

Typy kostek występujące w grach:

D3, D4, D6, D8, D10, D12, D20, D100.



*/

import java.util.Random;

public class Dice {
    public static void main(String[] args) {

        System.out.println(dice("D6"));
        System.out.println(dice("2D3+10"));
        System.out.println(dice("2D3"));
        System.out.println(dice("D100-1"));
        System.out.println(dice("D12-15"));// wartośc ujemna zwraca 0;
        System.out.println(dice("0D12"));   // zero kostek traktuje jak 1 kostka

    }

    private static int dice(String code) {

        code = code.trim(); // przypadkowe spacje.

        boolean failure = false; // czy da się odczytać kod?
        String numTries = "";
        String numFaces = "";
        String plusNumber = "";

        boolean gotD = false;   // czy odczytał "must have" literę D ?
        boolean gotS = false;   // czy odczytał :must have liczbę ścianek ?
        boolean contTries = true;
        boolean contPlus = false;
        boolean dodawanie = true;

        int numT = 0;       // liczba rzutów;
        int numF = 0;       // liczba ścianek kostki
        int plusN = 0;      // liczba +/- extra)
        int result = 0;      // wynik

        // wczytujemy D lub same liczby - ilość rzutów;
        for (int i = 0; i < code.length(); i++) {
            char option = code.charAt(i);

            // Poniższy warunek - jeśli nie odczytał D to niech odczyta D lub liczbę.
            if ((!gotD) && (Character.toString(option).equals("D"))) {
                if (numTries.equals("")) numTries = "1";
                gotD = true;
                i++;
                option = code.charAt(i);
            } else if (!gotD) {
                if (Character.isDigit(option)) {
                    numTries += Character.toString(option); // Tworzymy string z liczbą rzutów.
                } else {
                    failure = true;// jeśli nie i nie trafił na D to musi być błąd;
                    System.out.println(("Błędny kod rzutu"));
                    return -1; // błędny kod - kończymy pętlę
                }
            }

            // Poniższy warunek - jeśli odczytał D i nie doszedł do koduplus to musi być liczba ścianek do końca stringu
            if ((gotD) && (!contPlus)) {
                if (Character.isDigit(option)) {
                    numFaces = numFaces + Character.toString(option); // Tworzymy string z liczbą ścianek.

                } else if (Character.toString(option).equals("+")) {
                    contPlus = true;
                    dodawanie = true;
                    i++;
                    option = code.charAt(i);

                } else if (Character.toString(option).equals("-")) {
                    contPlus = true;
                    dodawanie = false;
                    i++;
                    option = code.charAt(i);
                } else {
                    failure = true;// jeśli nie i nie trafił na D to musi być błąd;
                    System.out.println(("Błędny kod rzutu"));
                    return -1; // błędny kod - kończymy pętlę
                }
            }

            // Poniższy warunek - jeśli istnieje +/- do rzutu to tu torzymy string z liczbami
            if (contPlus) {
                if (Character.isDigit(option)) {
                    plusNumber = plusNumber + Character.toString(option); // Tworzymy string z liczbą extra modyf wynik

                } else {
                    failure = true;// jeśli nie i nie trafił na D to musi być błąd;
                    System.out.println(("Błędny kod rzutu"));
                    return -1; // błędny kod - kończymy pętlę
                }

            }
        }

        try {
            if (!numTries.equals("")) numT = Integer.parseInt(numTries);
            else numT = 1;

            if (!numFaces.equals("")) numF = Integer.parseInt(numFaces);
            else numF = 1;

            if (!plusNumber.equals("")) plusN = Integer.parseInt(plusNumber);
            else plusN = 0;
        } catch (NumberFormatException e) {
            System.out.println("Problem z zamianą na liczbę");
        }


        if (numT == 0) numT=1; // nie może być zero kostek
        if (numT > 0) {
            for (int i = 0; i < numT; i++) {
                result += drawNumber(numF);
            }
            if (contPlus) {
                if (dodawanie)
                    result += plusN;
                else {
                    if (plusN < result) result -= plusN;  // w razie gdyby result był mniejszy od korekty to przyjmij 0;
                    else result = 0;
                }

            }
        } else result = 0;

        System.out.println("ile kostek : " + numT);
        System.out.println("ile ścianek : " + numF);
        System.out.println("korekta : " + plusNumber);

        if (numT != 3 && numF != 4 && numF !=6 && numF !=8 && numF != 10 && numF != 12 && numF!=20 && numF !=100) {
            System.out.println("Tu nie używamy kostek D"+numT);
            System.out.println("Dozwolone kostki to D2, D4, D6, D8, D10, D12, D20 oraz D100");
            result = -1;
        }


        return result;
    }

    // Metoda losuje liczbę z zakresu 1 - range. Wymaga java.util.Random
    private static int drawNumber(int range) {

        Random random = new Random();
        return random.nextInt(range) + 1;

    }
}