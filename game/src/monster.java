//Skapar en klass för de tvp monster som ska finnas
public class monster {
    //Ett monster har Namn och en Beskrvining
    //HP & DMG för fighter
    //Boolean alive för att se om de lever.
    private String mDesc, mName;
    private int mHP, mDMG;
    private Boolean alive;
    monster (String Name, String Desc, int hp, int dmg){
        this.mName = Name;
        this.mDesc = Desc;
        this.mHP = hp;
        this.mDMG = dmg;
        this.alive = true;
    }
    //Funktion för att ta bort hp från monstret. Om hp <= 0 så sätts alive till false vilket innebär att den är död.
    public void mMinusHP(int hp){
        this.mHP = this.mHP - hp;
        if (this.mHP <= 0) {
            this.alive = false;
        }
    }
    //Funktion för när monstret attackerar spelaren
    public void mAttack (Player player){
        System.out.println(this.mName + " attackerar dig och gör " + this.mDMG + " skada\n");
        player.pMinusHP(this.mDMG);
    }
    //Funktion för att få värdet på alive
    public boolean getAlive_value (){
        return this.alive;
    }
    //Funktion för att setta värdet på alive.
    public void setAlive_value(Boolean value){
        this.alive = value;
    }
    //Funktion för att få ett monsters namn
    public String get_mName (){
        return this.mName;
    }
    public String get_mDesc(){
        return this.mDesc;
    }
    //Funktionen för hur en fight går till
    public void monster_fight (Player p1, monster monster, bild pic){
        if(monster.mName.equals("Drake")){
            System.out.print(pic.get_drake() + monster.mDesc);
        }
        else{
            System.out.print(monster.mDesc);
        }
        //Så länge både spelaren och monstret lever så fightas dom.   
        while (p1.getAlive_value() && monster.getAlive_value()){
                // Spelaren attakerar monstret
                p1.pAttack(monster);
                // Kollar ifall monstret fortfarande lever
                if (!monster.getAlive_value()) {
                    //Ifall monstret är dött -> Skriv ut meddelande och bryt loopen.
                    //Ifall de är drake lägg till n på namnet om de är odjur lägg till et på namnet
                    if(monster.mName.equals("Drake")){
                        System.out.println("Du har besegrat " + monster.mName + "n\n");
                        System.out.println("Du har nu " + p1.get_pHP() + " hp kvar");
                    break;
                    }
                    else{
                        System.out.println("Du har besegrat " + monster.mName + "et\n");
                        System.out.println("Du har nu " + p1.get_pHP() + " hp kvar, en dryck hade nog suttit bra");
                        break;
                    }
                }
                // Monstret attakerar spelare
                monster.mAttack(p1);
                // Kollar om spelar fortfarande lever
                if (!p1.getAlive_value()) {
                    //Ifall spelaren är död -> Skriv ut meddelande och bryt loopen.
                    //Ifall de är drake lägg till n på namnet om de är odjur lägg till et på namnet
                        if (monster.mName.equals("Drake")){
                            System.out.println(monster.mName + "n" + " har besegrat dig");
                            break; 
                        }
                        else {
                            System.out.println(monster.mName + "et" + " har besegrat dig");
                            break;
                        }
                }
        }
    }
}
    
