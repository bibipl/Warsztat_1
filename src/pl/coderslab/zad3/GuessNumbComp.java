package pl.coderslab.zad3;

/*
Odwróćmy teraz sytuację z warsztatu "Gra w zgadywanie liczb": to użytkownik pomyśli sobie liczbę z zakresu 1-1000, a komputer będzie zgadywał i zrobi to maksymalnie w 10 ruchach (pod warunkiem, że gracz nie będzie oszukiwał).

        Zadaniem gracza będzie udzielanie odpowiedzi "więcej", "mniej", "trafiłeś".

        Na następnym slajdzie znajduje się schemat blokowy algorytmu.

        Dostępny jest także pod adresem:

        https://gist.github.com/arek-jozwiak-coderslab/4783d45e75a71793a123673cc0998ae3

        Zaimplementuj go w Javie.
*/

import java.util.Scanner;

public class GuessNumbComp {
    public static void main(String[] args) {

        int range = 1000; // zakres liczb do podania/zgadywania
        int numberToGuess = takeNumber(range);
        int numTries = CompGuessTheNumber(range);
        if (numTries == 1)
            System.out.println("Wygrałem!!! Zgadłem liczbę w "+numTries+" próbie.");
        else
            System.out.println("Wygrałem!!! Zgadłem liczbę w "+numTries+" próbach.");
    }

    // Zwraca liczbę prób w których zgadł liczbę
    private static int CompGuessTheNumber(int range) {
        // Zgadnij liczbę
        Scanner scan = new Scanner(System.in);


        int numTries = 0;  // ile potrzebował komputer prób do zgadnięcia
        int theNumber;
        int min = 0;
        int max = range+1;
        boolean iHaveGotTheNumber = false;  // czy już mam prawidłowy numer ?

        while (!iHaveGotTheNumber) {
            numTries ++;
            theNumber = (max-min) / 2 + min;
            System.out.println("Zgaduję ["+numTries+"] raz. To liczba : "+theNumber);
            while (true) {       // czy wprowadzono dozwolony znak
                System.out.println("Czy twoja liczba to : "+theNumber+" ?");
                System.out.println("Jeśli tak wciśnij \"Enter\", jeśli twoja liczb jest większa wpisz \"+ Enter\" a jeśli mniejsza wpisz \"- Enter\"");
                String read = scan.nextLine();
                if (read.equals("+")) {
                    System.out.println("Twoja liczba jest większa. Dobrze...");
                    min=theNumber;
                    break;
                }
                else if (read.equals("-")) {
                    System.out.println("Twoja liczba jest mniejsza. Dobrze...");
                    max=theNumber;
                    break;
                }

                else if (read.equals("")) {
                    iHaveGotTheNumber = true;
                    break;
                }
                else { // odpowiada : "Nie oszukuj
                    System.out.println("Nie rozumiem tego co do mnie piszesz. Spróbuj jeszcze raz");
                }
            }
        }
        scan.close();
        return numTries;
    }


    // Metoda pobiera liczbę z zakresu 0-range klawiatury wymaga "java.util.scanner"
    private static int takeNumber (int range) {

        Scanner scan = new Scanner(System.in);

        System.out.print ("Pomyśl liczbę z zakresu 0-"+range+" a ją zgadnę : ");
        int pickedNumber;                       // poza zakresem bo jest od 1-range
        while (true) {       // int zakres 1-range
            while (!scan.hasNextInt()) {
                scan.nextLine();
                System.out.println("To nie jest liczba całkowita!");    // nit int
                System.out.print ("Podaj jeszcze raz liczbę z zakresu 1-"+range+" : ");
            }
            pickedNumber = scan.nextInt();
            if ((pickedNumber < 0) || (pickedNumber > (range))) {
                System.out.println("To nie jest liczba z zakresu 0-"+range+" !");
                System.out.print ("Podaj jeszcze raz liczbę z zakresu 0-"+range+" : ");
            }
            else {
                break;  // jeżeli liczba jest int i z zakresu ok to kończy pętlę.
            }
        }
        return pickedNumber;
    }
}
