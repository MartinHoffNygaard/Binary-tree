# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Martin Hoff Nygaard, s362081, s362081@oslomet.no
* Kristoffer Hauknes Bauer, s360530, s360530@oslomet.no

# Arbeidsfordeling

I oppgaven har vi hatt følgende arbeidsfordeling:
* Martin har gjort oppgave 1,2,4,5
* Kristoffer har gjort oppgave 3,6,7,8

# Oppgavebeskrivelse

I oppgave 1 så gikk vi frem ved å returnere antall i metoden antall() og returnere booleanske verdien av antall == 0 i 
tom(). I konstruktøren sjekker vi først om tabellen er tom ved hjelp av requireNonNull og sender en NullPointerException
om den er tom. Vi går så igjennom input listen til vi finner første verdi som ikke er null og setter denne verdien i en 
ny node som blir hode. Vi bruker så en kopi av hode til å traversere igjennom hele listen og sette inn verdien fra input
listen i en ny node når den ikke er null. Vi lager denne nye noden med inputverdien og setter forrige node til noden den 
var før vi oppdaterte den og setter nodens neste til foreløpig å være null ettersom noden sin neste blir oppdatert når vi
traverserer videre med en ny node. Til slutt setter vi den siste noden vi har kommet til som hale. 

I oppgave 2 så lagde vi toString() og omvendtString() ved hjelp av en StringBuilder. I metodene sjekkes det først om listen 
er tom, da returneres bare []. Om listen ikke er tom så traverserer vi henholdsvis framover og bakover i listen og legger
til node verdiene i Stringbuilderern. Til slutt returnerer vi StringBuilderen.toString(). I metoden leggInn() så sjekker 
vi først om input verdien er tom, da sendes det en NullPointerException. Så sjekker vi om listen er tom, da settes den 
nye verdien i en node som blir hode og hale. Om listen har noder i seg fra før så settes verdien inn i en ny node som 
blir satt til den nåværende halens sin neste. Vi setter den nye nodens sin forrige til å være halen. Til slutt oppdaterer
vi den nye noden som er bakerst til å være halen. 

I oppgave 3 så lagde vi finnNode(), hent(), oppdater(),subliste(), og fratilkontroll().
finnNode() sjekker først indeksen, og lager en peker p. hvis indeksen er på venstre side, starter vi i hodet, hvis høyre i halen. Hvis vi starter I hodet, går vi til høyre indeks ganger ved å telle ned vær gang vi beveger oss til vi har en indeks av null.
Høyre så teller vi opp til indeks < antall-1.
hent(), finner .verdien til denne noden, har input kontroll her også men det trengs ikke.
oppdater() endrer verdien til denne noden.
subliste() kaller fratilkontroll (denne sjekker om intervallet er over 0 under antall, og telles fra venstre til høyre) og lager en ny liste fra til. Dette gjøres ved å legge til alle verdier mellom i denne nye listen ved bruk av leggtil()


I oppgave 4 lagde vi først indeksTil() metoden. Denne traverserer gjennom alle nodene ved å lage en node som er lik hode.
Denne noden går blir oppdatert til sin neste i en for løkke som er tilsvarende antall elementer i listen. I løkken sjekker
vi om input verdien er lik verdien i nåværende node og returnerer isåfall indeksen til noden. Om verdien ikke blir funnet
så returneres -1 til slutt. Inneholder() bruker bare indeksTil(), om indeksTil() returnerer -1 eller lavere så returneres 
false siden verdien ikke er i listen og eller returneres true siden verdien da er i listen. 

I oppgave 5 lagde vi først en sjekk for om verdien som skal bli lagt inn er null. Deretter sjekket vi at indeksen den skal
legges inn på er mellom 0 og antall elementer i listen, om ikke så kastes en IndexOutOfBoundsException. Deretter sjekker 
vi om listen er tom fordi da skal den nye verdien legges i en node osm både er hode og hale. Så sjekker vi om indeksen er
lik 0 da skal noden med verdien ligge helt foran og være hode. Deretter sjekker vi om indeksen er lik antall fordi da skal 
noden ligge helt bakerst og er halen. Om ikke indeksen ikke er 0 eller antall så finner vi noden som er på indeksen ved 
hjelp av finnNode(). Vi setter så den nye node med verdien foran noden som var på indeksen og oppdaterer pekerne så ingen
noder forsvinner. Til slutt oppdaterer vi antall elementer i listen og endringer som er gjort. 

I oppgave 6 lagde vi fjern metodene. en på indeks, og en på verdien.
Det finns 4 muligheter for fjerningen som må tilrettelegges for å ungå outofbounds.
1) det er kun en verdi. I dette tilfellet må verdien nulles. og antallet reduseres. halen og hodet kan også nulles men dette skaper problemer siden man ikke kan identifisere listen.
2) Slette hodet. Dette løses ved å sette hodet.neste til hode, og slette pekere til det orginalet hodet
3) Slette halen. Samme men omvent.
4) Slette midt i. Sette pekerene som peker på verdien "over" verdien. Dette gjør det så verdien ikke blir pekt på, og annses som slettet.

I oppgave 7 skrev vi nullstill metoden. Selv om fjern(0) er veldig lett å skrive, er den langt mindre effektiv enn å iterere. Dette gjøres ved å sette hode = hode.neste, sette hode.forrige.verdi til null, og hode.forrige til null. repeter til hode er hale. Når dette skjer
null hale hode, og verdien, sett antall til 0, og endringer++.

I oppgave 8 skrives Next(),Instanser av iterator, DLLIterator(indeks), og Iterator(indeks).
Mye av dette er veldig rett fram. next() flytter pekeren dette til høyre, og sjekker at man kan gjøre det.
Iterator instanser iterator.
DLLIterator(indeks) gjør som DLLIterator() men starter med pekeren på indeks ved å finne noden (finnnode(indeks))
Iterator(indeks) er identisk som Iterator() men kaller DLLIterator(indeks) istedet for den generiske.