import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Objekte der Unterklasse Bird versuchen sich dem Spieler so gut es geht zu nähern, 
 * um ihn dann direkt mit einem Schlag anzugreifen. Als einzige Unterklasse von Enemy können Bird Instanzen über einige Collider 
 * hinwegfliegen.
 * 
 * @author Anja, Alex
 * @version 30.05.2020
 */
public class Bird extends Enemy
{   
    private int frequency; //legt fest, wie schnell geschossen wird, je höher frequency, desto länger ist die Pause zwischen den Schüssen 
    private int counter;    //Z�hlvariable, die sp�ter mit frequency verglichen wird um Abschusszeitpnkt zu ermitteln

    public Bird(){
        // Erzeugt Gegner mit 5 damage, 5 Leben, flugfähig, 2 speed und Fortbewegungstyp Melee()
        super(5, 5, true, 2, new Melee(), "bird", 4, 4, 2, 15);
        frequency= 25;
        counter=0;

        //vorläufiges Bild erzeugen
        GreenfootImage img=new GreenfootImage(30,30);
        setImage(img);
    }        

    public void act(){
        super.act();
        if(getHealth() < 1){ //vermeidet Treffer durch Gegner nach dem Tode
            return;
        }
        melee(); // überprüft ob Gegner angreifen kann; greift an
    }

    /**
     * Überprüft ob Gegner den Spieler angreifen kann; greift im Fall an.
     * Autor: Daniel
     */
    public void melee(){
        if(counter == frequency){ //Wenn Gegner wieder angreifen kann
            if( movement.inRange(WorldManager.getPlayer().getX(),WorldManager.getPlayer().getY(),this.getX(),this.getY()) ){ //Wenn Spieler im Radius des Gegners ist

                //Spielt den Melee sound ab; Autor: Alex
                SoundManager.playSound("sounds/enemy_melee.wav", SoundManager.getSFXVol());
                //--------------------------------------

                WorldManager.getPlayer().setHealth(WorldManager.getPlayer().getHealth() - getDamage()); //fügt dem Spieler den Schaden des Angriffs hinzu
                speedBuff = 0; //Gegner muss stehen bleiben um anzugreifen
                counter= 0; //Zählen zum nächsten Angriff beginnt erneut
            }
        }
        else{counter++;}//zählt bis Angriff eingeleitet werden kann
        //speedBuff wird nach dem Vervollständigen der meleeAnimation wieder auf 1 gesetzt! (s. meleeAnimation in Enemy)
    }

}
