package pl.coderslab.zad2;

/*
program, który:

        zapyta o typowane liczby, przy okazji sprawdzi następujące warunki:
        czy wprowadzony ciąg znaków jest poprawną liczbą,
        czy użytkownik nie wpisał tej liczby już poprzednio,
        czy liczba należy do zakresu 1-49,
        po wprowadzeniu 6 liczb, posortuje je rosnąco i wyświetli na ekranie,
        wylosuje 6 liczb z zakresu i wyświetli je na ekranie,
        poinformuje gracza, czy trafił przynajmniej "trójkę".
*/


import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class lotto {
    public static void main(String[] args) {
        int range = 49;          // zakres liczb 1-49
        int manyNumbers = 6;     // ile liczb losujemy

        int[] coupon =new int[manyNumbers]; // tyle liczb poda klient
        int[] losy =new int[manyNumbers];   // tyle liczb wylosuje komputer


        // Podaj liczby
        coupon=takeX(manyNumbers,range);// losuje "manyNumbers" liczb z zakresu nange bez powtórzeń.
        Arrays.sort(coupon);
        System.out.print("\nWybrałeś liczby : ");
        for(int i:coupon) System.out.print (i+" ");
        System.out.println("");

        // Losuję liczby
        losy = drawX (manyNumbers, range); // losuje "manyNumbers" liczb z zakresu nange bez powtórzeń.
        Arrays.sort(losy);
        System.out.print("\nWylosowane liczby : ");
        for(int i:losy) System.out.print (i+" ");
        System.out.println("");


        // Porównuje czy trafiono 3,4,5,6 czy nie.
        int numbOfWinns = compare2intArr (coupon, losy); // numbOfWins zawiela liczbę trafionych
        System.out.print ("\nTwoja liczba trafień to : "+numbOfWinns+". "); // kominukat końcowy o liczbie trafień.
        if (numbOfWinns>2)System.out.println("Wygrałeś nagrodę!");
        else System.out.println("Niestety nic nie wygrałeś.");
    }
// ### Koniec main ###

    // Porównuje 2 tablice jednowymoarowe int. Jeżeli size <>  -> -1;
    // Jeżeli size ==; zwraca liczbę takich samych liczb
    static int compare2intArr (int[] coupon, int[] losy){

        if (coupon.length != losy.length) return -1;
        else {
            int counter =0;
            for (int cou : coupon)
                for (int los : losy) {
                    if (cou== los) {
                        counter++;
                        break;
                    }
                }
            return counter;
        }

    } //### koniec compare

    // Pobiera z klawiatury "range" liczb z zakresu od 1 do "range"
    static int[] takeX (int manyNumbers, int range){

        System.out.println("Podaj "+manyNumbers+" liczb z zakresu 1-"+range+" Liczby nie mogą się powtarzać.");
        int[] coupon = new int[manyNumbers];
        int counter = 0;
        while (counter<manyNumbers) {                      // powtarzamy pętlę aż uzyskamy 6 poprawnych liczb.
            coupon[counter] = takeNumber(range);    //
            boolean repeats = false;
            for (int i = 0; i < counter; i++) {
                if (coupon[counter] == coupon[i]) {
                    repeats = true;
                    System.out.println("Ta liczba już była podaj inną.");
                }
            }
            if (repeats == false) counter++;

        }
        return coupon;
    }//### koniec takeX

    static int takeNumber (int range) {

        Scanner scan = new Scanner(System.in);

        System.out.print ("Podaj liczbę z zakresu 1-49 :");
        int pickedNumber = 0;                       // poza zakresem bo jest od 1-range
        while (true) {       // int zakres 1-range
            while (!scan.hasNextInt()) {
                scan.nextLine();
                System.out.println("To nie jest liczba całkowita!");    // nit int
                System.out.print ("Podaj jeszcze raz liczbę z zakresu 1-49 :");
            }
            pickedNumber = scan.nextInt();
            if ((pickedNumber < 1) || (pickedNumber > (range))) {
                System.out.println("To nie jest liczba z zakresu 1-49!");
                System.out.print ("Podaj jeszcze raz liczbę z zakresu 1-49 :");
            }
            else {
                break;  // jeżeli liczba jest int i z zakresu ok to kończy pętlę.
            }
        }
        return pickedNumber;
    }//### koniec takeNumber

    static int[] drawX (int manyNumbers, int range){

        System.out.println("\nLosuję...");
        int[] coupon = new int[manyNumbers];
        int counter = 0;
        while (counter<manyNumbers) {                      // powtarzamy pętlę aż uzyskamy 6 poprawnych liczb.
            coupon[counter] = drawNumber(manyNumbers,range);            // losuje z zakresu 1-range
            boolean repeats = false;
            for (int i = 0; i < counter; i++) {
                if (coupon[counter] == coupon[i])
                    repeats = true;
            }
            if (repeats == false) counter++;

        }
        return coupon;
    }//### koniec drawX

    static int drawNumber(int manyNumbers, int range) {

        Random random = new Random();
        return random.nextInt(range) + 1;
    }//### koniec drawNumber
}
