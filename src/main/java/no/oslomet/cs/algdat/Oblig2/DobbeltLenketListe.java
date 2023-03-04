package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
    }

    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a, "Tabellen a er null");
            endringer = 0;

            int i = 0;

            while (i < a.length && a[i] == null) { // finner første verdi i listen som ikke er null
                i++;
            }

            if (i < a.length) { // sjekker at vi ikke har gått utenfor listen
                Node<T> p = hode = new Node<>(a[i], null, null); //setter første verdi til hode og lager en kopi av hode
                antall++; //antall blir satt til 1

                for (i++; i < a.length; i++) { // løkke som går igjennom alle verdiene i listen etter den første
                    if (a[i] != null) { //sjekke om verdien er null
                        //oppretter en node med verdien fra liste og setter p til neste node. setter forrige til det p var
                        //og setter neste til foreløpig null. Oppdaterer også forrige p sin neste til den nye noden.
                        p = p.neste = new Node<>(a[i], p, null);
                        antall++; //øker antall
                    }
                }
                hale = p; //setter den siste noden i listen til hale.
            }
        }

    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(fra, til);
        DobbeltLenketListe output = new DobbeltLenketListe(); // lager en ny liste


        for (;fra<til;fra++) {
            output.leggInn(this.hent(fra)); // denne linjen gjør mye. leggInn er laget slik at den kan brukes på tomme lister. Bruker den på alle verdier [fra,til>
        }
        output.endringer = 0; //endringer skal være 0 siden den nettopp lagdes.
        return output;
    }

    @Override
    public int antall() {return antall;} // returnerer antall verdier i listen

    @Override
    public boolean tom() {return antall == 0;} // returnerer true om listen er tom

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi,"Verdien som skal legges inn kan ikke være null");
        if(antall == 0) { //sjekker om listen er tom
            Node<T> p = hode = hale = new Node<>(verdi, null, null); //setter nye noden til hode og hale
            antall++;
            endringer++;
            return true;
        }
        Node<T> p = hale.neste = new Node<>(verdi, hale, null); // legger nye noden bakerst
        hale = p; //oppdaterer hale til å bli den bakerste noden.
        antall++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Du kan ikke legge inn en null verdi i listen"); //sjekker verdien
        if(indeks < 0) throw new IndexOutOfBoundsException("Indeksen må være større enn 0"); //sjekker indeksen
        if(indeks > antall) throw new IndexOutOfBoundsException("Indeksen er for stor i forhold til listen");

        if(antall == 0) { //sjekker om listen er tom
            hode = hale = new Node<>(verdi,null,null); //setter nye verdien til hode og hale om den er det
        }

        else if(indeks == 0) { //om indeksen er 0 så skal nye verdien være hode
            Node<T> q = new Node<>(verdi,null,hode); //setter inn nye verdi foran hode
            hode.forrige = q;
            hode = q; //setter hode til den nye verdien
        }

        else if(indeks == antall) { //om indeksen er lik antall så skal nye verdien legges bakerst som hale
            Node<T> q = new Node<>(verdi,hale,null); //legger nye verdien bakerst
            hale.neste = q;
            hale = q; //setter nye verdien til hale
        }

        else {
            Node<T> p = finnNode(indeks); //finner noden som er på indeksen
            Node<T> q = new Node<>(verdi, p.forrige, p); //setter inn den nye verdien foran noden på indeksen
            p.forrige.neste = q; //oppdaterer noden foran indeksen til å peke på nye noden.
            p.forrige = q;//oppdaterer noden som var på indeksen til å peke på noden
        }
        antall++; //oppdaterer antall og endringer
        endringer++;
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) > -1;
        // sjekker om indeksTil verdien returnerer -1, da er den ikke i listen og returnerer false ellers returneres true
    }

    private Node<T> finnNode(int indeks){
        Node<T> current = null; // Init current
        indeksKontroll(indeks, false); // Sjekker indeks
        if (indeks >= 0 || indeks < antall) {
            if (indeks <= (this.antall() / 2)) { //Sjekker om indeksen er i venstre del av listen
                current = this.hode; // Setter current til hodet av listen
                while (indeks > 0) { // Går igjenom listen til vi har bevegdt oss "indeks" ganger,
                    current = current.neste;
                    indeks--;
                }
                return current; // går ut av finnNode
            }
            current = hale; // Samme som over, men for høyresiden av listen
            while (indeks < antall-1) {
                current = current.forrige;
                indeks++;
            }
        }
        return current;
    }

    @Override
    public T hent(int indeks) {
        T output = null; // init variable
        indeksKontroll(indeks,false);
        if(indeks >= 0 || indeks < antall) {
            output = this.finnNode(indeks).verdi; //setter variablen til Noden i indeks sin verdi
        }
        return output;
    }

    @Override
    public int indeksTil(T verdi) {
        Node<T> p = hode; //lager node til å traversere gjennom nodene
        for(int i = 0; i < antall; i++) { //løkke som går gjennom alle noder
            if(p.verdi.equals(verdi)) return i; //sjekker om verdien finnes i noden og returnerer isåfall indeksen
            p = p.neste; //traverserer videre til neste node
        }
        return -1; // returnerer -1 om verdien ikke blir funnet i en node
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        if (nyverdi==null){throw new NullPointerException();} // exception for null
        T oldval = hent(indeks); //hent gjør indekssjekking for oss, finner gamleverdien og lagrer den.
        finnNode(indeks).verdi = nyverdi; //endrer så verdien i listen
        endringer++; // loggfører endringen
        return oldval;
    }

    @Override
    public boolean fjern(T verdi) {
        if(verdi == null) return false;
        if(antall==1 & hode.verdi.equals(verdi)){ // hvis det kun er en verdi, må man være forsiktig å ikke kalle på forrige eller neste
            hode.verdi = null;
            endringer++;
            antall--;
            return true;
        }
        if(hode.verdi.equals(verdi)){ // hodet må flyttes hvis man skal kunne kalle listen, samt ikke fjerne noe bak
            hode = hode.neste;
            hode.forrige = null;
            antall--;
            endringer++;
            return true;
        }
        Node<T> p = hode; //lager node til å traversere gjennom nodene
        for(int i = 0; i < antall-1; i++) { //løkke som går gjennom alle noder
            if(p.verdi.equals(verdi)){
                p.neste.forrige = p.forrige; // pekeren til noden etter p, kaller heller på noden forran p
                p.forrige.neste = p.neste; // pekeren forran p kaller heller på noden etter p.
                antall--;
                endringer++;
                return true;
            }; //sjekker om verdien finnes i noden og returnerer isåfall indeksen
            p = p.neste; //traverserer videre til neste node
        }
        if(hale.verdi.equals(verdi)){ // hvis det er halen vi sletter må den også flyttes slik at den kan bruker til søk evnt.
            hale = p.forrige;
            hale.neste = null;
            antall--;
            endringer++;
            return true;
        }
        return false; // hvis vi ikke finner den, returneres false.
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);
        Node<T> p = hode; // starter på hode. For forklarelse se fjern(verdi)

        if(indeks == 0 & hode==hale){ // hvis kun en verdi
            T temp=p.verdi;
            p.verdi = null;
            antall--;
            endringer++;
            return temp;
        }

        if (indeks == 0){ // hvis hode
            hode = hode.neste;
            hode.forrige = null;
            antall--;
            endringer++;
            return p.verdi;
        }
        while (indeks > 0){
            p = p.neste;
            indeks--;
        }
        if(p == hale){ //hvis hale
            hale = p.forrige;
            hale.neste = null;
            antall--;
            endringer++;
            return p.verdi;
        }
        p.neste.forrige = p.forrige; //hvis midt i
        p.forrige.neste = p.neste;
        antall--;
        endringer++;
        return p.verdi;

    }

    @Override
    public void nullstill() {
        /**
         * //long starttime = System.nanoTime();
         *         //while(hode!=hale){
         *         //    fjern(0);
         *         //}
         *         //fjern(0);
         *         //long stopTime = System.nanoTime();
         *         //System.out.println("Action took:"+ (stopTime-starttime) + " units of time");
         *         // new method
         *         // Action took:6800 units of time
         *         treg metode som kaller fjern til den slutter. antar det tar mer tid siden vi konstant endrer
         *         både antall og endringer. Den nye metoden er raskere, selv om vi sletter mer ting
         * */
        // long starttime = System.nanoTime();
        while (hode!=hale){ //når det er flere en 1 verdi igjen
            hode.verdi = null; // slett verdien i noden
            hode = hode.neste; // gå til neste node
            hode.forrige.neste = null; // slett forrige node sin peker
            hode.forrige = null; // slett vår peker til forrige node
        }
        hode = null; //slett hode
        hale = null; //slett hale
        antall= 0; // reduser antall til 0
        endringer++; // logg endring
        /**
         * //long stopTime = System.nanoTime();
         *         // System.out.println("Action took:"+ (stopTime-starttime) + " units of time");
         *         // Action took:1700 units of time
         * */
    }

    @Override
    public String toString() {
        StringBuilder utskrift = new StringBuilder(); //lager ny stringbuilder
        utskrift.append("[");
        if(antall == 0) { //sjekker om listen er tom og returner da []
            utskrift.append("]");
            return utskrift.toString();
        }
        Node<T> p = hode; //lager node til å traversere gjennom listen

        for(int i = 0; i < antall-1; i++) { //traverserer gjennom hele listen
            utskrift.append(p.verdi + ", "); //legger til verdien i noden til utskriften
            p = p.neste; //går til neste node
        }
        utskrift.append(hale.verdi + "]"); //legger til slutt til verdien i siste noden.
        return utskrift.toString();
    }

    public String omvendtString() {
        StringBuilder utskrift = new StringBuilder(); //lager Stringbuilder
        utskrift.append("[");
        if(antall == 0) { //sjekker om listen er tom og returner da []
            utskrift.append("]");
            return utskrift.toString();
        }
        Node<T> p = hale; //lager node til å traversere gjennom listen

        for(int i = antall; i > 1; i--) { //går gjennom listen bakfra
            utskrift.append(p.verdi + ", "); //legger til verdien i noden til utskriften
            p = p.forrige; //går til forrige node
        }
        utskrift.append(hode.verdi + "]"); // legger til hode sin verdi
        return utskrift.toString();
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new DobbeltLenketListeIterator(); //lager en ny DLLI
        return iterator;
    }

    public Iterator<T> iterator(int indeks) { //lager en ny DLLI som starter på indeks
        indeksKontroll(indeks, false);
        Iterator<T> iterator = new DobbeltLenketListeIterator(indeks);
        return iterator;
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks);
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() { //itererer fra en verdi til den neste i listen.
            if(iteratorendringer != endringer){ // sjekker om vi har concurrenterror
                throw new ConcurrentModificationException("endriner er ikke lik iteratorendringer");
            }
            if(!hasNext()){ //sjekker at det er en next
                throw new NoSuchElementException("listen har ingen next()");
            };
            fjernOK = true; //setter fjernOK til true (kan brukes i remove for å sikre at vi er på en verdi
            T temp = denne.verdi; //lagrer verdien
            denne = denne.neste; //itererer
            return temp; //returnerer tidligere verdi
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    private void fratilKontroll(int fra, int til){
        if (fra < 0){
            throw new IndexOutOfBoundsException("fra("+til+") er negativ!");
        }
        if (til > this.antall()){ //antall er lett sagt lengden, og til må derfor være mindre enn den.
            throw new IndexOutOfBoundsException("til("+til+") er større enn lengden på listen!");
        }
        if (til < fra){ // trenger kun denne siden hvis fra er over antall, er den også over til. vice versa.
            throw new IllegalArgumentException("til("+til+") er større enn fra("+fra+")!");
        }
    }

} // class DobbeltLenketListe


