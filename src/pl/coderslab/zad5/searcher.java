package pl.coderslab.zad5;

//
///Wyszukiwarka najpopularniejszych słów
//Wywołaj pobieranie dla wybranych serwisów internetowych.
//Pomiń wszystkie elementy krótsze niż 3-znakowe.
//Utwórz tablicę elementów wykluczonych np. oraz, ponieważ
//Wczytaj utworzony plik popular_words.txt i na jego podstawie utwórz plik filtered_popular_words.txt, który zawierać będzie wszystkie znalezione słowa, pomijając słowa wykluczone.
//Wyszukiwarka najpopularniejszych słów Zaimportuj do projektu bibliotekę jsoup, możesz ją pobrać z adresu:
//https://jsoup.org/download Wyszukaj w popularnych serwisach internetowych nagłówków artykułów, a następnie zapisz pojedyncze słowa w nich występujące do pliku o nazwie popular_words.txt. Przykład pobrania tytułów z tagu html span z atrybutem class o wartości title: Connection connect = Jsoup.connect("http://www.onet.pl/"); try { Document document = connect.get(); Elements links = document.select("span.title"); for (Element elem : links) { System.out.println(elem.text()); } } catch (IOException e) { e.printStackTrace(); }

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Scanner;

//"http://www.onet.pl/","http://www.interia.pl/",
public class searcher {

    // Działa, zapisuje, ale przy przepisywaniu filterd, tnie się na onet/interia.
    // Zamiast kontynuować interia, zaczyna od nowa onet i dopiero po drugim "onet" jest interia prawidłowo.


    public static void main(String[] args) {
        String[] adresses = new String[]{"http://www.onet.pl/", "http://www.interia.pl/"};
        String fileName = "popular_words.txt";
        String updtFilename = "filtered_popular_words.txt";

        for (String i : adresses) {
            readAdr(i, fileName);
            chkWrds(fileName, updtFilename);
        }
    }

    private static void chkWrds (String fileName, String updtFilename) {

        String textLine;

        try {
            File readfile = new File(fileName);
            FileWriter out = new FileWriter(updtFilename, true);

            Scanner scan = new Scanner(readfile);
            while (scan.hasNextLine()) {
                textLine = scan.nextLine();   // założenie, każde słowo w oddzielnej linii.
                if (!forbiddenWord(textLine)) {
                    out.append(textLine+"\n");
                }
            }
            out.close();
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku");
        } catch (IOException e) {
            System.out.println("Błąd zapisu/odczytu");
        }
    }


    private static  void readAdr (String adress, String fileName) {

        Connection connect = Jsoup.connect(adress);

        try {
            Document document = connect.get();
            Elements links = document.select("span.title");
            for (Element elem : links) {
                String oneLine =elem.text();
                String oneLineNoComas = "";
                char temp;
                StringBuilder sb = new StringBuilder(oneLineNoComas);
                for (int i=0; i < oneLine.length(); i++) {
                    temp = oneLine.charAt(i);
                    if (checkLetter(temp)) {                          // check letter eliminates "',:;?!<>()
                        sb.append(Character.toUpperCase(temp));
                    }
                }
                oneLineNoComas = sb.toString();
                String[] wordsOneLine = oneLineNoComas.split(" ");
                oneLineNoComas="";
                sb = new StringBuilder(oneLineNoComas);
                for (String druk: wordsOneLine) {
                    if (druk.length()>=3) {
                        sb.append(druk+"\n");
                    }
                }
                oneLineNoComas = sb.toString();
                try {
                    FileWriter out = new FileWriter(fileName,true);
                    out.append(oneLineNoComas);
                    out.close ();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // if leeter is a special sign to cutoff
    private static boolean checkLetter (char letter) {
        char[] forbidLetters = {'.',',',';',':','!','?','(','(','"','-'};
        for (int i: forbidLetters) {
            if(i==letter) {
                return false;
            }
        }
        return true;
    }
    // check if the word is on the forbidden words list
    private static boolean forbiddenWord (String theWord) {
       String[] notUsefullWords ={"ORAZ","PONIEWAŻ","CZY","JEST","DLA","MAJĄ","MASZ","DLA","SIĘ","BYŁ","NIE",
               "LUB","POD","MAM","JAK"};
       for (String word: notUsefullWords) {
           if (word.equals(theWord)) return true;
       }
       return false;
    }
}
