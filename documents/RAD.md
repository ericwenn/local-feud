# Requirements and Analysis Document for Edda

*Version: 1.0*

*Date: 160223*

*Author: Group 11*

This version overrides all previous versions.

## 1 Introduction

This section gives a brief overview of the project.

### 1.1 Purpose of application

Applikationen kommer låta personer inom ett område anonymt kommunicera och diskutera med varandra. Syftet är att hitta nya vänner, dela med sig av tankar och åsikter eller kanske till och med hitta en framtida livspartner. Om två personer fattar tycke för varandra kommer nämligen anonymiteten
försvinna, de kommer kunna chatta med varandra och personlig information från Facebook kommer presenteras.

### 1.2 General characteristics of application

Applikationen kommer att vara anpassat för plattformar som kör Android, kopplat till våra servrar där all data hanteras.

Applikationen kommer att vara uppdelat tre olika delar. I en del kommer själva flödet av inlägg att finnas. Denna del kommer även att vara olika i två tillstånd. Ett tillstånd ska visa de senaste inläggen som skrivits i närheten av läsaren. Det andra tillståndet ska sortera på avstånd från nuvarande position. I denna del kommer man även kunna skriva inlägg.
Den andra delen kommer att innehålla användarens chattar.
Den sista delen kommer att visa användarens profil, där användaren har möjlighet att ändra inställningar.


### 1.3 Scope of application
Applikationen kommer inte innehålla någon karta utan meddelanden presenteras endast med ett avstånd från dig själv.

### 1.4 Objectives and success criteria of the project 
  * Som användare skall man kunna publicera inlägg knutna till den geografiska position man befinner sig i vid publiceringstillfället. Man skall även kunna läsa andra personers inlägg om man är tillräckligt nära deras publiceringsposition.

  * Man skall kunna interagera med inlägg genom någon form av upp/nedröstning eller gillamarkering och genom att kommentera inlägget.

  * Man skall kunna välja att sortera inlägg på olika sätt. Det ska gå att sortera på senaste inlägg samt avstånd.

  * Användare skall dessutom kunna starta privata chattar med varandra där deras anonymitet släpper. Användarnas namn och bild visas då i chatten.

### 1.5 Definitions, acronyms and abbreviations
* *Cold start*, tiden som det tar för en applikation att visa någonting på skärmen.
* *Timeout-fel*, om någonting går snett i kopplingen mellan klient och server så genereras ett Timeout-fel när max-tiden uppnåtts.
* *Java*, ett programmeringsspråk som är plattformsoberoende.
* *Android*, ett operativsystem för mobiltelefoner.

## 2 Requirements
In this section we specify all requirements

### 2.1 Functional requirements
I applikationen ska användaren kunna:
* Skriva ett inlägg samt läsa inlägg skrivna av andra.
* Interagrera med inlägget genom kommentarer och gillningar.
* Starta chatt med en okänd person och chatta med kända personer.
* Ändra inställningar.

### 2.2 Non-functional requirements

#### 2.2.1 Usability
Vårt gränssnitt förutsätter att användaren har någon typ av erfarenhet från sociala medier tidigare. För dessa användare kommer applikationen vara extremt lätthanterlig.
Användargränssnittet kommer att vara i engelska, men applikationen ska ha möjligheten att översättas till andra språk.

#### 2.2.2 Reliability 
Målet med applikationen är att ha en så pass buggfri applikation att användaren inte ska behöva uppleva några kraschar eller frysningar.

#### 2.2.3 Performance
Vår applikation ska ha en *cold start* på max 3 sekunder. När användaren väl använder applikationen så ska det i värsta fall ta max 2 sekunder från det att användaren utfört sin handling till att användaren fått respons på skärmen.
Om användaren har en sämre internetuppkoppling kan det dock ta längre tid att hämta hem information från servern. Är uppkopplingen så pass dålig att ingenting händer ger servern ett *timeout-fel* efter 30 sekunder.

#### 2.2.4 Supportability
Applikationen ska fungera på alla telefoner/surfplattor som drivs av Android  med API 21 (Lollipop) eller senare, men det ska inte vara några problem att göra applikationen bakåtkompatibel i större utsträckning. Applikationens huvudfunktioner ska vara separerade från övriga delar av programmet, så som dess vyer.

Applikationen ska även nyttja resursfilerna som finns i Android full ut, vilket gör det smidigt för framtida eventuella översättningar.

#### 2.2.5 Implementation 
Applikationen kommer att skrivas i Java för att smidigast köras på Android. Vi kommer att behöva exportera projektet till en körbar .apk-fil. Det är sedan denna fil som användaren behöver ladda ner för att kunna installera applikationen.

#### 2.2.6 Packaging and installation
Applikationen laddas ner som en .apk-fil och installeras från Google Play Store. I filen finns alla Java-klasser och resursfiler.

#### 2.2.7 Legal
Det finns inga juridiska problem i applikationen.

### 2.3 Application models

#### 2.3.1 Use case model 
Se appendix för bild och text om våra use cases.

#### 2.3.2 Use cases priority
1. Skriv ett inlägg
2. Läs inlägg
3. Gilla inlägg
4. Kommentera inlägg
5. Starta chatt med okänd person
6. Chatta med känd person
7. Läsa notiser
8. Ändra inställningar

#### 2.3.3 Domain model

![Image of Domain Model](https://raw.githubusercontent.com/ericwenn/local-feud/master/domainmodel/domainmodel.jpg)

#### 2.3.4 User interface 

Vynummer refererar till bild placerad under "GUI" i sektionen "References".

**Vy 1** (Start) visar startskärmen i inloggat läge. Här listats de senaste inläggen som standard. I toppfältet går det dock att växla till att sortera efter populäraste eller närmaste. Inläggen visas som "kort" med lite marginal emellan. Översta delen av korten består av en rad innehållandes inläggsskaparens ålder, könstillhörighet, samt inom vilket avståndsintervall inlägget är skapat. Raden har även bakgrundsfärg som motsvarar intervallet (röd = väldigt nära t.ex.). Under nämnd rad visas inläggstexten, alternativt en förhandsvisning av en bild om det är ett bildinlägg. Det finns en hjärtsymbol till höger som används för att ge inlägget uppskattning. Anledningen att en placerats till höger är att där befinner sig oftast användarens tumme. Längst ned i kortet visas antalet kommentarer som gjorts på inlägget, vilket klockslag det publicerats och en liten knapp för att få upp fler alternativ (såsom möjlighet att "ogilla" inlägget eller anmäla det). Nere i högra hörnet finns en fixpositionerad floating action button (FAB) för att skapa ett inlägg. På alla vyer (om inget annat anges nedan) finns ett fixerat verktygsfält längst ned på skärmen, där man enkelt kan växla vy.

**Vy 2** (Skapa inlägg) visar hur det ser ut när man skapar ett inlägg. Vyn animeras flygande från botten av skärmen när användaren tryckt på skapa inläggsknappen. Här finns ett textfält för textinmatning, en knapp för att lägga till bild och en publiceraknapp. Toppfältet består av en bakåtknapp och en alternativknapp, där exempelvis inläggets visningsradie kan ställas in. I vy 2 visas inte verktygsfältet längst ned, då man ska få känslan av att vy 2 är en vy som läggs över resten av applikationens gränssnitt.

**Vy 3** (Chatt) visar en lista över alla konversationer användaren är aktiv i. Personer läggs till här när en chattförfrågan skickats och personen står som "väntande" tills personen accepterat förfrågan. En profilbild visas till vänster i listan. I listobjekten finns även personens förnamn, ålder, samt en förhandsgranskning av senaste meddelandet. I toppfältet finns en sökruta för att enkelt hitta chatter om användaren är aktiv i många. Sökrutan kan hitta chatter via namn och text i inlägg i chatten.

**Vy 4** (Jag/Notiser) visar användarens senaste notiser. Dessa skickas även som pushnotiser till telefonen, om användaren har det aktiverat. Exempel på notiser en användare kan få är om någon kommenterat på ett inlägg användaren skapat eller följer eller om någon visat uppskattning på inlägget. I toppfältet går det att växla till vy 5 (Jag/Inställningar).

**Vy 5** (Jag/Inställningar) visar applikations- och kontoinställningar.

**Vy 6** (Inlägg) visar ett inlägg och dess kommentarer. Hit kommer användaren efter att ha tryckt på ett inlägg i flödet i vy 1. Överst visas originalinlägget. Nedanför listas samtliga kommentarer. Alla kommenterare får ett fiktivt namn som identifierar den inom det inlägget. Samma namn används för samma användare om den kommenterar på inlägget flera gånger. Kommenterarens kön och ålder visas även i anslutning till kommentaren. En FAB används för att skriva en ny kommentar, precis som på startsidan för att skapa inlägg. Skapa kommentarsvyn fungerar på ungefär samma vis som skapa inläggsvyn. En bakåtknapp finns i toppfältet. Även en alternativknapp finns, där möjligheten att följa inlägget (för att få notiser när någon kommenterar det) och att anmäla inlägget som olämpligt ligger placerat.

**Vy 7** (Logga in) visar inloggningsskärmen. Detta är det första som visas när applikationen startas första gången. Vyn är enkel; en logotyp, en bakgrundsbild och en "logga in med Facebookknapp". Facebook är ett krav för att använda applikationen.

### 2.4 References

##### APPENDIX 
  
  ![Image of Use Cases](https://raw.githubusercontent.com/ericwenn/local-feud/master/usecases/use-case.jpg)

* Skriv ett inlägg - Hög - Implementerad.
* Kommentera ett inlägg - Medel - Implementerad.
* Gilla inlägg - Medel - Implementerad.
* Starta chatt med okänd person - Medel - Implementerad.
* Chatta med känd person - Låg - Implementerad.
* Läsa notiser - Låg - Ej implementerad.
* Läs inlägg - Hög - Implementerad.
* Ändra inställningar - Medel - Implementerad.

######  GUI
![Image of sketch](https://raw.githubusercontent.com/ericwenn/local-feud/master/documents/vyer.jpg)


