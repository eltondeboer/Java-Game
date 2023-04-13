//Skapar en klass som inehåller spelaren och dens namn
public class Player {
    //En spelare har flera variblar som beskrivs
    //En spelare har följande:
    //Namn
    //HP och DMG för fight scener
    //Booleans för att beskriva om spelaren besitter ett föremål och spelaren är död eller ej
    private String pName;
    private int pHP, pDMG, tidigareHP;
    private Boolean hasKey, hasTreasure, hasPotion, hasSword, alive;
    Player (String name, int hp, int dmg){
        this.pName = name;
        this.pHP= hp;
        this.pDMG = dmg;
        this.hasKey= false;
        this.hasPotion = false;
        this.hasTreasure = false;
        this.hasSword = false;
        this.alive = true;
    }
    //Funktion till för att ge spelaren skada. Om du har <0 hp så sätts alive till false. Det betyder att du är död
    public void pMinusHP(int hp){
        pHP = pHP - hp;
        if (this.pHP <= 0) {
            this.alive = false;
        }
    }
    //Funktion för att få namnet
    public String get_pName (){
        return this.pName;
    }
    //Funktion för att få värdet på hasKey.
    public boolean getKey_value (){
        return this.hasKey;
    }
    //Funtkion för att sätta värdet på hasKey.
    public void setKey_value(Boolean value){
        this.hasKey = value;
    }
    //Funktion för att få värdet på hasTreasure.
    public boolean getTreasure_value (){
        return this.hasTreasure;
    }
    //Funtkion för att sätta värdet på hasTreasure.
    public void setTreasure_value(Boolean value){
        this.hasTreasure = value;
    }
    //Funktion för att få värdet på hasPotion.
    public boolean getPotion_value (){
        return this.hasPotion;
    }
    //Funktion för att få värdet på alive.
    public boolean getAlive_value (){
        return this.alive;
    }
    //Funtkion för att sätta värdet på hasPotion.
    public void setPotion_value(Boolean value){
        this.hasPotion = value;
    }
    //Funktion för att få värdet på hasSword.
    public boolean getSword_value (){
        return this.hasSword;
    }
    //Funtkion för att sätta värdet på hasSword.
    public void setSword_value(Boolean value){
        this.hasSword = value;
    }
    //Funktionen för när en spelare dricker sin potion. hasPotion sätts till false. 
    //Spelarens hp återställs till och det skrivs ut
    public void drink_potion (){
        this.hasPotion = false;
        this.tidigareHP = 10 - pHP;
        this.pHP = 10;
        System.out.println("Du har nu drukit en hälsodryck och återfått:" + tidigareHP + "\nDitt HP:" + pHP);
    }
    //Används för att sätta vilken skada spelaren ska ha
    public void setdmg (int dmg){
        this.pDMG = dmg;
    }
    //Används för att sätta vilket hp spelaren ska ha
    public void sethp (int hp){
        this.pHP = hp;
    }
    public int get_pHP (){
        return this.pHP;
    }
    //Funktion för när spelaren attackerar ett monster
    public void pAttack (monster monster){
        System.out.println("Du attackerar " + monster.get_mName() + " och gör " + this.pDMG + " skada\n");
        monster.mMinusHP(this.pDMG);
    }
}
