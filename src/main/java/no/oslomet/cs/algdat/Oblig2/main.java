package no.oslomet.cs.algdat.Oblig2;

public class main {
    public static void main(String[] args) {
        DobbeltLenketListe liste = new DobbeltLenketListe<>();

        liste.leggInn(0, 4);  // ny verdi i tom liste
        liste.leggInn(1, 2);  // ny verdi legges forrest
        liste.leggInn(1, 6);  // ny verdi legges bakerst
        liste.leggInn(1, 3);  // ny verdi nest forrest
        liste.leggInn(1, 5);  // ny verdi nest bakerst
        liste.leggInn(1, 1);  // ny verdi forrest
        liste.leggInn(1, 7);  // ny verdi legges bakerst

        System.out.println(liste);
        }
    }
