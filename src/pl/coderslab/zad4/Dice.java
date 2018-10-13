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

        System.out.println("Rzut D6 : "+dice("D6"));
        System.out.println("Rzut 2D3+10 : "+dice("2D3+10"));
        System.out.println("Rzut 2D3 : "+dice("2D3"));
        System.out.println("Rzut D100-1 : "+dice("D100-1"));
        System.out.println("Rzut D12-15 : "+dice("D12-15"));// wartośc ujemna zwraca 0;
        System.out.println("Rzut 0D12 : "+dice("0D12"));   // zero kostek traktuje jak 1 kostka

    }

    //tu jest ta metoda.
    private static int dice(String code) {

        code = code.trim(); // przypadkowe spacje.

        String numTries = "";       // kawałek kodu oznaczający liczbę rzutów
        String numFaces = "";       // kawałek kodu oznaczający liczbę ścianek
        String plusNumber = "";     // kawałek kodu oznaczający liczbę modyfikująca wynik

        boolean gotD = false;       // czy odczytał "must have" literę D ?
        boolean contPlus = false;   // czy kod zawiera modyfikator wyniku
        boolean dodawanie = true;   // czy modyfikuje wynik na + czy na -

        int numT = 0;       // liczba rzutów;
        int numF = 0;       // liczba ścianek kostki
        int plusN = 0;      // liczba +/- extra)
        int result = 0;      // wynik

        // Po kolei obrabiamy każdy znak z kodu.
        for (int i = 0; i < code.length(); i++) {
            char option = code.charAt(i);

            // Poniższy warunek - jeśli jeszcze nie odczytał D to niech odczyta D lub cyfry przed D
            if ((!gotD) && (Character.toString(option).equals("D"))) {
                if (numTries.equals("")) numTries = "1";        // tu wchodzi gdy ma "D". Wartość domyślna liczby rzutów to 1
                gotD = true;                                    // gotD true - już tu nie wejdzie
                i++;                                            // przeskok do następnego znaku po "D"
                option = code.charAt(i);                        // i ten znak do option
            } else if (!gotD) {                                 // tu wchodzi jeżeli nie ma D i ma cyfrę.
                if (Character.isDigit(option)) {                // dodatkowe sprawdzenie czy to cyfra
                    numTries += Character.toString(option);     // Konkatenujemy string z liczbą rzutów.
                } else {
                    // jeśli nie i nie trafił na D to musi być błąd; // tu jeżeli nie było ani "D" ani cyfry"
                    System.out.println(("Błędny kod rzutu"));
                    return -1; // błędny kod - kończymy pętlę
                }
            }

            // Poniższy warunek - jeśli odczytał D i nie doszedł do koduplus to musi być liczba ścianek do końca stringu
            // Odczyt cyfr po "D" i ewentualnie +/-
            if ((gotD) && (!contPlus)) {                        // już odczytał D ale jeszcze nie modyfikator rzutu, czyli teraz ścianki
                if (Character.isDigit(option)) {
                    numFaces = numFaces + Character.toString(option);   // Jeżeli to cyfra, to konkatenujemy string z liczbą ścianek.

                } else if (Character.toString(option).equals("+")) {    // Jeżeli to nie cyfra to pewnie "+".
                    contPlus = true;                                    // jest modyfikator wyniku
                    dodawanie = true;                                   // i jest to zwiększenie
                    i++;                                                // przeskok do nast znaku
                    option = code.charAt(i);

                } else if (Character.toString(option).equals("-")) {    // Jeżeli to nie cyfra ani "+" to pewnie "-"
                    contPlus = true;                                    // jest modyfikator wyniku
                    dodawanie = false;                                  // i jest to zmniejszenie.
                    i++;                                                // przeskok do nast znaku
                    option = code.charAt(i);
                } else {                                                // ani + ani - ani cyfra - czyli błędny kod po lit. "D"
                    // jeśli nie i nie trafił na D to musi być błąd;
                    System.out.println(("Błędny kod rzutu"));
                    return -1; // błędny kod - kończymy pętlę
                }
            }

            // Poniższy warunek - jeśli istnieje +/- do rzutu to tu tworzymy string z liczbami modyfikatora rzutu.
            if (contPlus) {
                if (Character.isDigit(option)) {
                    plusNumber = plusNumber + Character.toString(option); // Tworzymy string z liczbą extra modyf wynik

                } else {
                    // jeśli nie i nie trafił na D to musi być błąd;
                    System.out.println(("Błędny kod rzutu"));
                    return -1; // błędny kod - kończymy pętlę
                }

            }
        }

        try { // ewentualne błędy przy zamianie Strin na liczbę Int. Raczej nie powinno ich być.
            if (!numTries.equals("")) numT = Integer.parseInt(numTries);    // zamiana liczby rzutów na liczbę int;
            else numT = 1;                                                 // wartość domyślna - brak przd "D" liczby rzutów.

            if (!numFaces.equals("")) numF = Integer.parseInt(numFaces); // wartość domyślna - liczba ścianek minimum 1. Program nie powinien tu dojść
            else numF = 1;                                               // zbędna linia - dla ostrożności i przejrzystości.

            if (!plusNumber.equals("")) plusN = Integer.parseInt(plusNumber);
            else plusN = 0;                                             // wartość domyślna 0 brak modyfikatora wyniku, gdy kod kończy się bez +/-
        } catch (NumberFormatException e) {
            System.out.println("Problem z zamianą na liczbę");          // tu nie dojdzie, ale proszę testować, może się uda :)
        }


        if (numT == 0) numT=1; // nie może być zero kostek, minimalna liczba to 1;
        if (numT > 0) {         // też nie ma szans żeby teraz tu nie wszedł, mogłoby nie być tego if chyba, że coś zmieni się w kodzie.
            for (int i = 0; i < numT; i++) {
                result += drawNumber(numF);
            }
            if (contPlus) {
                if (dodawanie)
                    result += plusN;
                else {
                    if (plusN < result) result -= plusN;
                    else result = 0;                        // w razie gdyby result był mniejszy od korekty to przyjmij 0;
                }

            }
        } else result = -1;      // czyli jeżeli liczba kostek poniżej 0 to wynik -1

        // Sprawdzić można co wszło.
        // System.out.println("ile kostek : " + numT);
        //System.out.println("ile ścianek : " + numF);
        //System.out.println("korekta : " + plusNumber);

        // nasz klien nasz pan. Jak chce te kostki tylko to dostanie to co chce.
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