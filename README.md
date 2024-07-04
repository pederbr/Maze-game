# Labyrint-spill #

Av Peder Brennum

### Beskrivelse av appen ###

For å starte må filen `src\main\java\mazeGame\MazeApplication.java` kjøres.

Denne applikasjonen er et labyrint-spill der spilleren kan generere og prøve å løse labyrinter med valgfri vanskelighetsgrad. Gjennomføringen til spilleren lagres i en dynamisk rekordliste, som er designet slik at den tar høyde for prestasjon kontra tid. Spilleren kan altså komme høyere opp på rekordlisten dersom han velger en høyere vanskelighetsgrad, selv om han bruker lengre tid enn tidligere spillere. Labyrinten genereres av en algoritme som kalles «recursive backtracking», og som fungerer slik:

* Labyrint-generatoren starter på et tilfeldig sted på høyre side av grid-en.
* Generatoren sjekker naboene sine, om den kan bevege seg dit eller om det allerede er en vei der.
* Generatoren velger tilfeldig en av de åpne naboene og går dit.
* Dette gjentas helt til generatoren sitter fast i et hjørne. Når dette skjer går den bakover i en stack som består av generatoriens tidligere posisjoner, og sjekker om disse har flere åpne naboer.
* Om generatoren finner noen åpne naboer vil den gå videre med denne veien, helt til den igjen setter seg fast
* Dette vil gjentas helt til stacken er tom og generatoren er tilbake der den startet.

### Diagram ###

![objekt-diagram](src\main\resources\mazeGame\diagram.png)

### Spørsmål ###

#### Hvilke deler av pensum i emnet dekkes i prosjektet, og på hvilken måte? (For eksempel bruk av arv, interface, delegering osv.) ####

* Prosjektet dekker følgende konsepter: Delegering, interface, implementering av interface, IO-streams, validering, feilhåndtering, observatør-observert teknikken og innkapsling. Årsaken til dette er:
  * Delegering dekkes fordi Controller-klassen delegerer bla. filbehandling til et Filehandler og Labyrint-generering til den statiske metoden `static Grid generateMaze(Grid maze){}`, som ligger i den statiske klassen HelpMethods. Dette gjøres for å fordele arbeidet Controller-klassen må gjøre, slik at koden blir mer lettleselig.
  * Interface dekkes 2 steder. Det dekkes ved Iplayer-interfacet, som implementeres av Player-klassen. Dette er implementert for å sikre god innkapsling, og for å gjøre koden mer lettleselig. Om spillet skal videreutvikles senere, bla. ved å implementere flerspiller-funksjon, er det også nyttig med et slikt interface
  * IO-streams dekkes i FileHandler-klassen ved at det opprettes en PrintWriter som skriver strengen som skal utgjøre innholdet i filen, til filen.
  * Validering dekkes flere steder i prosjektet, men spesielt i front-end delen. Et eksempel er alle metodene som kjøres av knapper som eksisterer i winPane-panen kun kan kjøres når den er synlig, og at alle metoder som skal kjøres når den er usynlig sjekker dette.
  * feilhåndtering dekkes bla. i FileHandler-klassen, som kjører try-catch statements rundt alle metoder, og returnerer ingenting hvis det er noe som er feil med metoden.
  * observatør-observert teknikken dekkes indirekte, ved at jeg legger Highscore-objekter i en ObservableArrayList, som igjen sørger for at tabellen på WinPane holder seg oppdatert, ved at den er en observatør og HighscoreList er observert. Dette har jeg ikke implementert direkte, men heller brukt funksjonalitet som allerede ligger i JavaFX.
  * Innkapsling dekkes bla. i HighScore-klassen, der verdier kun settes i konstruktøren, og ikke kan endres senere. Dette gjør at HighScoreListen ikke kan tukles med.

#### Dersom deler av pensum ikke er dekket i prosjektet deres, hvordan kunne dere brukt disse delene av pensum i appen? ####

* Projektet dekker ikkje følgende konsepter: Arv og polymorfisme. Årsaken til dette er:
  * Det er ikke en nødvendighet med arv og polymorfisme i prosjektet mitt, da det ikke eksisterer klasser som er "like" hverandre. Alle klassene i prosjektet er fundamentalt ulike, med hver sin funksjon. Arv og polymorfisme hadde væet overflødig i denne sammenhengen. Om jeg derimot måtte implementere det, kunne jeg lagt til en felles superklasse for player og grid med noen felles getter-metoder. Det hadde derimot kun gjort koden mer forvirrende og mindre leselig.

#### Hvordan forholder koden deres seg til Model-View-Controller-prinsippet? (Merk: det er ikke nødvendig at koden er helt perfekt i forhold til Model-View-Controller standarder. Det er mulig (og bra) å reflektere rundt svakheter i egen kode) ####

* Koden min forholder seg til Model-Veiw-Controller-prinsippet til en viss grad. Controller-klassen står for controller-delen, ved at den styrer samhandling mellom modellen og bruker-grensesnittet. FXML-filen står for veiw-delen, ved at den gir brukeren et visuelt grensesnitt å samhandle med. De andre klassene i prosjektet står for model-delen, ved at de håndterer backend-logikken i projektet, som generering, validering og lagring.
* For å bedre forholde seg til MVC-prinsippet kunne jeg tatt mer logikk ut av Controller-klassen og inn i andre klasser. Dette hadde gjort koden mer lettleselig, mer testbar, og mindre sårbar for bugs.

#### Hvordan har dere gått frem når dere skulle teste appen deres, og hvorfor har dere valgt de testene dere har? Har dere testet alle deler av koden? Hvis ikke, hvordan har dere prioritert hvilke deler som testes og ikke? (Her er tanken at dere skal reflektere rundt egen bruk av tester) ####

* Når jeg skulle teste appen, gikk jeg først gjennom koden for å se etter mulige sårbarheter, for så å bygge tester rundt disse sårbarhetene. En slik potensiell sårbarhet er FileHandler-klassen, som har høy risiko for å feile, i forhold til andre klasser. Derfor skrev jeg tester for bla. denne filen.
