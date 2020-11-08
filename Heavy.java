import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Objekte der Unterklasse Heavy versuchen sich dem Spieler so gut es geht zu nähern, 
 * um ihn dann direkt mit einem Schlag anzugreifen. Sie unterscheiden 
 * sich vom Walker durch eine niedrigere Geschwindigkeit und mehr Schaden.
 * 
 * @author Anja, Daniel
 * @version 30.05.2020
 */
public class Heavy extends Enemy
{
    private int counter;
    private int frequency;
    public Heavy(){
        // Erzeugt Gegner mit 15 damage, 15 Leben, nicht flugfähig, 1 speed und Fortbewegungstyp Melee()
        super(15,15,false,1, new Melee(), "heavy", 4,12,3, 33);
        counter=0;
        frequency= 40;
        //vorläufiges Bild erzeugen
        GreenfootImage img=new GreenfootImage(40,40);
        setImage(img);
    }
    
        public void act(){
        super.act();
        if(getHealth() < 1){    //vermeidet das Registrieren eines Treffers nach dem Tod des Gegner
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
        //speedBuff wird nach dem Vervollständigen der meleeAnimation wieder auf 1 gesetzt!
    }

}
