//Skapar en klass som innehåller alla rummen
public class Room {
    //Ett rum har namn och beskrivening
    private String rName, rDesc;
    private int N, Ö, S, V;
    private monster monster;
    
    //När man skapar ett rum ges det ett namn och beskrivning i parantesen
    Room (String Name, String Desc, int aN, int aÖ, int aS, int aV){
        this.rName = Name;
        this.rDesc = Desc;
        this.N = aN;
        this.Ö = aÖ;
        this.S = aS;
        this.V = aV;
    }
    public int get_N () {
        return this.N;
    }
    public int get_Ö () {
        return this.Ö;
    }
    public int get_S () {
        return this.S;
    }
    public int get_V () {
        return this.V;
    }
    public void set_Monster (monster aMonster){
        this.monster = aMonster;
    }
    public monster get_Monster(){
        return this.monster;
    }
    //En funktion som skriver ut beskrivningen av rummet
    public void doNarrative(){
        System.out.println(rDesc + "\n");
    }
    //En funktion som gör att jag enkelt kan uppdatera rDesc
    public void setrDesc (String desc){
        this.rDesc = desc;
    }
    //Funktion för att få namnet på ett rum
    public String get_rName (){
        return this.rName;
    }



    
}
