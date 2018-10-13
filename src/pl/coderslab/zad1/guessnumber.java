package pl.coderslab.zad1;

// Program losuje liczbę z zakresu 1- range (ustaw w "int range");
// Następnie wczytuje liczby z tego zakresu z klawiatury
// informuje czy wczytana liczba jest > czy <
// powtarza tak długo aż zgadniesz liczbę.

import java.util.Random;
import java.util.Scanner;

public class guessnumber {
    public static void main(String[] args) {

        int range = 100; //od 1 do 100

        int number = drawNumber (range); // number losuje komputer
        int counter = 0;                            // liczymy ile razy zgadujesz zanim się uda.
        while (true) {                              // pętla powtarza się aż zgadniesz.
            int inputNumber = takeNumber(range);    // wczytujemy liczbę z klawiatury
            counter++;
            if (inputNumber == number) break;         // zgadłeś !!! kończy pętlę.
            else if (inputNumber > number)
                System.out.println(inputNumber+" to za dużo! Spróbuj z mniejszą liczbą.");
            else
                System.out.println(inputNumber+" to za mało! Spróbuj z większą liczbą.");
        }
        System.out.println("Zgadłeś, to była liczba "+number+". Potrzebowałeś na to "+counter+ " prób. Dziękuję za wspólną zabawę.");
    }

    // Metoda losuje liczbę z zakresu 1 - range. Wymaga java.util.Random
    private static int drawNumber(int range) {

        Random random = new Random();
        return random.nextInt(range) + 1;

    }

    // Metoda pobiera liczbę z zakresu 1-range klawiatury wymaga "java.util.scanner"
    private static int takeNumber (int range) {

        Scanner scan = new Scanner(System.in);

        System.out.print ("Zgadnij liczbę z zakresu 1-"+range+" :");
        int pickedNumber;                       // poza zakresem bo jest od 1-range
        while (true) {       // int zakres 1-range
            while (!scan.hasNextInt()) {
                scan.nextLine();
                System.out.println("To nie jest liczba całkowita!");    // nit int
                System.out.print ("Zgadnij jeszcze raz liczbę z zakresu 1-"+range+" : ");
            }
            pickedNumber = scan.nextInt();
            if ((pickedNumber < 1) || (pickedNumber > (range))) {
                System.out.println("To nie jest liczba z zakresu 1-"+range+" !");
                System.out.print ("Zgadnij jeszcze raz liczbę z zakresu 1-"+range+" : ");
            }
            else {
                break;  // jeżeli liczba jest int i z zakresu ok to kończy pętlę.
            }
        }
        return pickedNumber;
    }

}
