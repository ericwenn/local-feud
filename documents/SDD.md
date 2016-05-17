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
Ett interface vi har skapat är **DataResponseListener**, som håller reda på huruvida datan lyckades/misslyckades hämtas från antingen servern eller ett lokalt system.
	public interface DataResponseListener<D> {
	
    void onSuccess( D data );
    
    void onFailure(DataResponseError error, String errormessage );

    Type getType();
    
	}

Vi har även ett interface som heter **IChatDataHandler**, som sköter de allmänna funktionerna för *alla* chatter, såsom om man vill skapa en ny chat med någon eller få en lista på de man redan chattar med.
	public interface IChatDataHandler {

    void sendRequest(Post post, int userID, DataResponseListener<Chat> listener);

    void getList(DataResponseListener<List<Chat>> listener);
    
	}
	
**IChatMessageDataHandler** är det interface som har hand om de funktionerna för *en specifik* chat. Här finns möjlighet att få listan med meddelanden samt skicka ett nytt meddelande.
	public interface IChatMessageDataHandler {

    void getList(Chat chat, DataResponseListener<List<ChatMessage>> listener);

    void send(Chat chat, ChatMessage message, DataResponseListener<ChatMessage> listener );
    
	}
	

**ICommentDataHandler** sköter kommentarerna på en post, både hämta, skapa och ta bort.
	public interface ICommentDataHandler {

    void getList(Post post, DataResponseListener<List<Comment>> listener );

    void getSingle( int id, DataResponseListener<Comment> listener);

    void delete( Comment comment, DataResponseListener<Void> listener);

    void create( Post post, Comment comment, DataResponseListener<Comment> listener);

	}

**ILikeDataHandler** fungerar precis som *ICommentDataHandler*, fast med gillningar på en post istället för kommentarer.

	public interface ILikeDataHandler {

    void getList( Post post, DataResponseListener<List<Like>> listener);

    void create( Post post, DataResponseListener<Like> listener);

    void delete( Post post, DataResponseListener<Void> listener);
    
	}

**IMeDataHandler** sköter information angående användaren.
	public interface IMeDataHandler {

    void get(DataResponseListener<Me> listener);

    void setMe( Me me );

    Me getMe() throws NullPointerException;
    
	}

**IPostDataHandler** innehåller funktioner som har med posts att göra. 

	public interface IPostDataHandler {

    void getList( Position pos, DataResponseListener<List<Post>> listener );

    void getSingle( int id, DataResponseListener<Post> listener );

    void create( Post post, DataResponseListener<Post> listener );

    void delete( Post post, DataResponseListener<Void> listener );

	}
####2.2.3 Layering

####2.2.4 Dependency analysis

###2.3 Concurrency issues

###2.4 Persistent data management

###2.5 Access control and security

###2.6 Boundary conditions 

##3 References

##APPENDIX 
