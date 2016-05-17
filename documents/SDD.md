#System design document for NNN

  *Version: 0.0.1*
  
  *Date: 9/5/2016*
  
  *Author: Local Feud team yao*

This version overrides all previous versions.

##1 Introduction

###1.1 Design goals

Vi kommer sträva efter en så löskopplad design som möjligt.

###1.2 Definitions, acronyms and abbreviations
* *Android*, ett operativsystem för mobiltelefoner.
* *Gradle*, ett verktyg som hanterar systemets beroende av andra bibliotek.
* *Java*, ett programmeringsspråk som är plattformsoberoende.
* *GUI*, grafiskt användargränssnitt
* *MVC*, ett sätt att bygga ett program med GUI där man delar upp GUI-koden, programkoden och all data i strikta delar.
* *Feed*, appens flöde av innehåll.
* *Post*, ett inlägg i appens flöde.
* *Chat*, en konversation mellan två personer.

##2 System design

###2.1 Overview
Applikationen kommer att använda sig av MVC. Vyerna är uppdelade i olika aktiviteter, exempelvis flödesaktivitet och skapa inläggsaktivitet. Vissa aktivteter kan används till flera olika saker. Dessa saker, exempelvis inläggsflödet och chattflödet, är uppdelade i fragment, som sätts till aktiviteterna. Små detaljer och objekt som upprepas flera gånger, exempelvis ett enskiljt inlägg, är uppdelade i adaptrar som läggs in i fragmenten och aktiviteterna.

###2.2 Software decomposition


####2.2.1 General

Applikationens paket är uppdelade på följande sätt:
![Packages](https://raw.githubusercontent.com/ericwenn/local-feud/master/documents/packages.png)
* **com.chalmers.tda367.localfeud** är applikationens huvudpaket.
	* **data** innehåller all typ av data som kommunicerar med servern.
		* **handler** innehåller de klasser som sköter datans kommunikation med servern.
			* **interfaces** innehåller de interfaces som implementeras av handler-klasserna.
	* **control** är det paket där kontrollen med vyerna finns.
		* **authentication** innehåller de kontrolldelarna som har med autentiseringen att göra.
		* **chat** innehåller de kontrolldelarna som har med chatten att göra.
		* **me** innehåller de kontrolldelarna som är personliga för användaren, så som inställningar och notiser.
		* **permission** innehåller de nödvändiga kontrolldelarna som behövs för att få behörigheter av användaren.
		* **post** innehåller de kontrolldelarna som berör applikationens flöde och inlägg.
	* **services** är det paket som sköter nätverksdelen med servern.
	* **util** innehåller de övriga verktyg som behövs för applikationen.

####2.2.2 Decomposition into subsystems 

####2.2.3 Layering

####2.2.4 Dependency analysis

###2.3 Concurrency issues

###2.4 Persistent data management

###2.5 Access control and security

###2.6 Boundary conditions 

##3 References

##APPENDIX 
