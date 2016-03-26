# Use Case: Läs inlägg
**Sammanfattning**: Tillvägagångssättet för att läsa text- och bildinlägg.

**Prioritet**: Hög

**Förlängning av**: -

**Inkluderar**: -

**Deltagare**: Applikationen, en användare

##Normalt händelseförlopp

Läs inlägg i flödet

|     | Actor                                         | System          |
|---  |-------------------------------------------    |--------         |
|**1**    | Trycker på flödesknappen i verktygsfältet |                 |
|**2**    |                                           |Visar flödesvyn  |
|**3**    | Läser inlägg i flödet                     |                 |


## Alternativa händelseförlopp
**Flöde 2.1** - Internetuppkoppling saknas

|     | Actor                                         | System          |
|---  | ---                                           |---              |
|**2.1.1** |                                            | Visar ett felmeddelande |



**Flöde 3.1** - Användaren vill läsa ett bildinlägg

|     | Actor                                         | System          |
|---  | ---                                           |---              |
|**3.1.1** | Trycker på ett bildinlägg                                           | |
|**3.1.2** |                                         | Visar vyn för enskilda inlägg där det valda bildinlägget presenteras med bild, bildtext och kommentarer.|
