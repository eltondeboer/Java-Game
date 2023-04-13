import java.util.ArrayList;
import java.util.Scanner;

public class game {
    public static void playGame() {
        //Skapar en scanner för att läsa alla användar inputs
        Scanner scanner = new Scanner(System.in);
    
        //En arraylist skapas och vi definerar att den ska innehålla objekt i form av rum från klassen room. 
        //Vi lägger sedan direkt in alla rummen och skapar dom samtidigt.
        ArrayList <Room> map = new ArrayList<>();
        // Skrivs in som (Namn, Desc, N, Ö, S, V) väderstrecken är en int baserat på vilket rums int som representerar rumets idx i listan map
            map.add(new Room("Gruvingång", "Du står utanför en grotta. Det luktar svavel från öppningen\nGrottöppningen är österut. Skriv 'ö' och tryck på [Enter] för att komma in i grottan", -1, 1, -1, -1));
            map.add(new Room("Entre", "När du går in i grottan kollapsar ingången bakom dig.\nRummet är upplyst av några ljus som sitter på ett bord framför dig.\nDu kan gå norrut [n]\nDu kan gå söderut [s]\n", 5, -1, 2, -1));
            map.add(new Room("Nyckelrummet", "Du kommer in i ett rymligt bergrum med en ljusstrimma sipprandes genom en spricka i den östra väggen.\nDu ser än nyckel på en pedestal i hörnet av rummet.\nDu kan gå norrut [n]\nDu kan åka österut [ö]\nDu kan plocka upp nyckeln [p]\n", 1, 3, -1, -1));
            map.add(new Room("Potion", "Du kommer in i ett fuktigt rum med vatten sipprandes längs den västra väggen. Du ser en röd dryck på marken\nDu ser en låst dörr i öster [ö]\nDu kan gå norrut [n]\nDu kan gå västerut [v]\nDu kan plocka upp drycken[p]\n", 6, 4, -1, 2));
            map.add(new Room("Skatt", "Du ser en stor skatt på golvet som lyser upp hela rummet\nDu kan gå västerut [v]\nDu kan plocka upp skatten [p]\n", -1, -1, -1, 3));
            map.add(new Room("Svärd", "Du ser en död kropp på golvet. Ett svärd står mot väggen\nDu kan åka söderut [s]\nDu kan gå österut [ö]\nDu kan plocka upp svärdet [p]\n", -1, 6, 1, -1));
            map.add(new Room("Monster", "Du ser en brinnande fackla i rummets ena hörn och känner en motbjudande stank.\nDu ser en utgång österut [ö]\nDu kan gå västerut [v]\nDu kan åka söderut [s]\n", -1, 7, 3, 5));
            map.add(new Room("Utgång", "Du lämnar grottan med livet i behåll. Grattis, du förlorade inte!\n", -1, -1, -1, -1));
        //Här gör vi samma sak fast med en lista av monster
        ArrayList <monster> monster_list = new ArrayList<>();
        monster_list.add(new monster("Drake", "En arg drake dyker upp\n", 18, 1));
        monster_list.add(new monster("Odjur", "Ett äckligt odjur dyker upp\n", 8, 1));
        
        //Sätter vilka monster som finns i de specifika rummen
        map.get(4).set_Monster(monster_list.get(0));
        map.get(6).set_Monster(monster_list.get(1));

        //Definerar variablar för spelet.
        String opentxt, pName;
        bild pic;
        
        //Hämtar textritningarna från klassen bild och lägger de under variablen pic
        pic = new bild();
    
        //Definerar starter texten till opentxt
        opentxt = "################ Välkommen till Dragon Treasure ##################\nSkriv ditt namn och tryck på [Enter] för att starta ett nytt spel... ";
        //Frågar efter spelarens namn tills användaren har givit oss ett namn
        do{
            System.out.println(opentxt);
            pName = scanner.nextLine();
        }
        while(pName == "");

        //Skapar en instans av spelaren och döper den till p1. Definerar även spelarens HP och DMG
        Player p1 = new Player(pName, 10, 1);
    
        //Välkommnar spelaren
        System.out.println("Välkommen " + p1.get_pName() + " till ditt äventyr");

        //Sätter rummet som spelaren är i till starter rummet
        Room currentRoom = map.get(0);

        //Här börjar loopen för spelet. Först kollar spelet om spelaren befinner sig i slutrummet och i så fall avslutas spelet.
        while (true){
            if (currentRoom.get_rName().equals("Utgång")){
                //Kollar om spelaren fick med sig skatten till utgången
                if (p1.getTreasure_value()){ //Har du skatten returnas true annar false!
                    currentRoom.doNarrative();
                    System.out.println("Du lyckades även få med dig en stor skatt\n" + pic.get_kista());
                    break;
                }
                else{
                    currentRoom.doNarrative();
                    break;
                } 
            
            }
            //Kollar om du är i ett rum med ett monster och isåfall gör attack scenen
            //Kollar först om spelaren redan har dött och avslutar i så fall spelet med en text
            else if (!p1.getAlive_value()){
                System.out.println("##################Du dog spelet är nu över####################");
                break;
            }
            //Kollar om spelaren är i skatt rummet för då ska en fight brytas ut mot en drake
            //så länge draken är vid liv
            else if ((currentRoom.get_Monster() != null) && currentRoom.get_Monster().getAlive_value()){ //Kollar så att rummet har ett monster och att det lever
                //Här spelas funktionen från monster klassen, in går spelaren och monstret, samt även klassen bild ifall draken ska printas ut
                currentRoom.get_Monster().monster_fight(p1, currentRoom.get_Monster(), pic);
            }
            //Först här nere börjar de mer vanliga inputsen
            //Först skrivs rumbeskrivningen ut och den frågar vad användaren vill göra
            else{
                currentRoom.doNarrative();
                //Ifall spelaren har drycken skrivs det ut ett alternativ att dricka den
                if (p1.getPotion_value()){
                    System.out.println("Du kan dricka din dryck [d]\n");
                }
                //Efter scenen är satt frågar vi användaren vad hen vill göra
                System.out.println("Vad vill du göra?\n");
                //Sätter användar inputen till variabeln input
                String input = scanner.nextLine();

                //Om inputen är n (gå norrut) så kollar den vad den ska göra beroende på vilket rum du är i. 
                //Antingen byter den currentRoom,
                //eller så berättar den att inget finns norrut och frågar igen.
                //När du lämnar ett rum uppdateras även beskrivningen så att den är mer passande nästa gång du kommer till samma rum.
                //Eftersom exempelvis ingången inte rasar ihop 2 gånger
                if (input.equalsIgnoreCase("n")){
                    if (currentRoom.get_rName().equals("Entre")){
                        currentRoom.setrDesc("Ingången är fortfarande täckt med stenar.\nRummet är upplyst av några ljus som sitter på ett bord framför dig.\nDu kan gå norrut [n]\nDu kan gå söderut [s]\n");
                        currentRoom = map.get(5); 
                    }
                    else if (currentRoom.get_N() > 0){ // Kollar att det finns en utgång genom att se att int:en är större en noll, vägar som inte går att ta är = -1.
                        currentRoom = map.get(currentRoom.get_N());
                    }
                    else{
                        System.out.print("Det finns inget norrut\n");
                    }
                }
            //Om inputen är ö (gå österut) så kollar den vad den ska göra beroende på vilket rum du är i. 
            //Antingen byter den currentRoom,
            //eller så berättar den att inget finns österut och frågar igen.
            //Eller om du är i rummet Potion kollar den om spelaren har nyckel (hasKey=True) och förflyttar spelaren isåfall
            //Om spelaren inte har nyckel (hasKey=False) skriver den ut att dörren är låst och förflyttar inte spelaren
                else if (input.equalsIgnoreCase("ö")){
                    if (currentRoom.get_rName().equals("Potion")){
                        if (p1.getKey_value()){
                            currentRoom = map.get(4);//Kolla så att spelare har nyckel ifall du vill gå in i det låsta rummet.
                        }
                        else{
                            System.out.print("Du har ingen nyckel som passar\nDu kikar genom nyckelhållet och ser en skatkista full av guld\n" + pic.get_kista());
                        } 
                    }
                    //den vanliga sekvensen
                    else if (currentRoom.get_Ö() > 0){
                        currentRoom = map.get(currentRoom.get_Ö());
                    }
                    else{
                        System.out.print("Det finns inget österut\n");
                    }
                }
            //Om inputen är s (gå söderut) så kollar den vad den ska göra beroende på vilket rum du är i. 
            //Antingen byter den currentRoom,
            //eller så berättar den att inget finns söderut och frågar igen.
                else if (input.equalsIgnoreCase("s")){
                    if (currentRoom.get_S() > 0){
                        currentRoom = map.get(currentRoom.get_S());
                    }
                    else{
                        System.out.print("Det finns inget söderut\n");
                    }
                }
            //Om inputen är v (gå västerut) så kollar den vad den ska göra beroende på vilket rum du är i. 
            //Antingen byter den currentRoom,
            //eller så berättar den att inget finns västerut och frågar igen.
                else if (input.equalsIgnoreCase("v")){
                    if (currentRoom.get_rName().equals("Entre")){
                        System.out.print("Ingången är täkt av stenar du försöker flyta på dom men kommer ingen vart\n");
                    }
                    else if (currentRoom.get_V() > 0){
                        currentRoom = map.get(currentRoom.get_V());
                    }
                    else{
                        System.out.print("Det finns inget västerut\n");
                    }
                }
            //Om inputen är plocka upp så kollar den vad den ska göra beroende på vilket rum du är i. 
            //Antingen plockar du upp kistan(hasTreassure=True), nyckel(hasKey=True), svärdet(hasSword=true) eller drycken(hasPotion = true)
            //eller så berättar den att inget finns att plocka upp och frågar igen.
            //När du plockat upp något ändras även rumsbeskrivningen    
                else if (input.equalsIgnoreCase("p")){
                    if(currentRoom.get_rName().equals("Nyckelrummet")){
                        //Kollar ifall du har nyckeln annars ger spelaren nyckeln
                        if (p1.getKey_value()){
                            System.out.print("Du har redan nyckeln\n");
                        }
                        else {
                            p1.setKey_value(true);
                            System.out.print("Du tar nyckeln och lägger den i fickan\n");
                            currentRoom.setrDesc("Du kommer in i ett rymligt bergrum med en ljusstrimma sipprandes genom en spricka i den östra väggen.\nDu ser pedestalen som nyckel låg på, i hörnet av rummet.\nDu kan gå norrut [n]\nDu kan åka österut [ö]\n");
                        }
                    }
                    else if (currentRoom.get_rName().equals("Skatt")){
                        //Kollar ifall du har skatten annars ger spelaren skatten
                        if (p1.getTreasure_value()){
                            System.out.print("Du har redan skatten\n");
                        }
                        else {
                            p1.setTreasure_value(true);
                            System.out.print("Du tar skatten och lägger den i din ryggsäck\n");
                            currentRoom.setrDesc("Skattkistan är numera tom och allting ligger i din ryggsäck\nDu kan gå västerut [v]\n");
                        }     
                    }
                    else if (currentRoom.get_rName().equals("Potion")){
                        //Kollar ifall du har drycken annars ger spelaren drycken
                        if (p1.getPotion_value()){
                            System.out.print("Du har redan drycken\n");
                        }
                        else {
                            p1.setPotion_value(true);
                            System.out.print("Du tar drycken och lägger den i din ryggsäck\n");
                            currentRoom.setrDesc("Du kommer in i ett fuktigt rum med vatten sipprandes längs den västra väggen. Här inne låg tidigare drycken\nDu ser en låst dörr i öster [ö]\nDu kan gå norrut [n]\nDu kan gå västerut [v]\n");
                        }     
                    }
                    else if (currentRoom.get_rName().equals("Svärd")){
                        //Kollar ifall du har svärdet annars ger spelaren svärdet och ökar skadan
                        if (p1.getSword_value()){
                            System.out.print("Du har redan svärdet\n");
                        }
                        else {
                            p1.setSword_value(true);
                            System.out.print("Du tar svärdet i handen\n");
                            p1.setdmg(2);
                            currentRoom.setrDesc("Du ser en död kropp på golvet. Här inne var tidigare svärdet\nDu kan åka söderut [s]\nDu kan gå österut [ö]\n");

                        }     
                    }
                    else{
                        System.out.print("Det finns inget att plocka upp\n");
                    }
                }
                else if (input.equalsIgnoreCase("d")){
                    if (p1.getPotion_value()){
                        p1.drink_potion();
                    }
                    else {
                        System.out.println("Du har inget att dricka\n");
                    }
                }
            //Om inputen är help så skriver den ut alla möjliga kommandon
                else if (input.equalsIgnoreCase("help")){
                    System.out.print("Följande kommandon kan skrivas\nFör att gå norr : n\nFör att gå öst : ö\nFör att gå söder: s\nFör att gå väst : v\nFör att ta upp föremål: p\n För att dricka din dryck: d\nFör att avsluta : q\n");
                }
            //Om inputen är q (quit) så avslutas spelet   
                else if (input.equalsIgnoreCase("q")){
                    break;
                }
            //Om inputen är inget av omvan skriver den ut ett error meddelande och frågar igen   
                else{
                    System.out.println("Felaktigt kommando\n Skriv 'help' för en lista av kommandon\n");
                }
        
            }
      }
    }
        
}

