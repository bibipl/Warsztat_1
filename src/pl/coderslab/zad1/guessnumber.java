package pl.coderslab.zad1;

import java.util.Random;
import java.util.Scanner;

public class guessnumber {
    public static void main(String[] args) {

        int range = 100; //od 1 do 100

        int number = drawNumber (range); // number losuje komputer

        while (true) {                              // pętla powtarza się aż zgadniesz.
            int inputNumber = takeNumber(range);    // wczytujemy liczbę z klawiatury
            if (inputNumber == number) break;         // zgadłeś !!! kończy pętlę.
            else if (inputNumber > number)
                System.out.println(inputNumber+" to za dużo! Spróbuj z mniejszą liczbą.");
            else
                System.out.println(inputNumber+" to za mało! Spróbuj z większą liczbą.");
        }
        System.out.println("Zgadłeś, to była liczba "+number+". Dziękuję za wspólną zabawę.");
    }

    static int drawNumber(int range) {

        Random random = new Random();
        return random.nextInt(range) + 1;

    }

    static int takeNumber (int range) {

        Scanner scan = new Scanner(System.in);

        System.out.print ("Zgadnij liczbę z zakresu 1-100 :");
        int pickedNumber = 0;                       // poza zakresem bo jest od 1-range
        while (true) {       // int zakres 1-range
            while (!scan.hasNextInt()) {
                scan.nextLine();
                System.out.println("To nie jest liczba całkowita!");    // nit int
                System.out.print ("Zgadnij jeszcze raz liczbę z zakresu 1-100 :");
            }
            pickedNumber = scan.nextInt();
            if ((pickedNumber < 1) || (pickedNumber > (range))) {
                System.out.println("To nie jest liczba z zakresu 1-100!");
                System.out.print ("Zgadnij jeszcze raz liczbę z zakresu 1-100 :");
            }
            else {
                break;  // jeżeli liczba jest int i z zakresu ok to kończy pętlę.
            }
        }
        return pickedNumber;
    }

}
