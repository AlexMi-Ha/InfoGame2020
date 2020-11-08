import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Objekte der Unterklasse Walker versuchen sich dem Spieler so gut es geht zu n‰hern, 
 * um dann direkt zu explodieren. Sie unterscheiden 
 * sich vom Heavy durch eine hˆhere Geschwindigkeit und weniger Schaden.
 * 
 * @author Anja, Daniel
 * @version 30.05.2020
 */
public class Walker extends Enemy
{   
    private int counter;
    private int frequency;
    public Walker(){
        // Erzeugt Gegner mit 20 damage, 10 Leben, nicht flugf√§hig, 1 speed und Fortbewegungstyp Melee()
        super(20,10,false,3,new Melee(), "walker",0,0,0, 20);
        counter=0;
        frequency= 40;
        //vorl√§ufiges Bild erzeugen
        GreenfootImage img=new GreenfootImage(30,30);
        img.setColor(Color.BLUE);
        img.fill();
        img.setColor(Color.WHITE);
        img.drawString("W", 10, 20);

        setImage(img);
    }

    public void act(){
        super.act();
        if(getHealth() < 1){ //vermeidet Treffer nach Tod
            return;
        }
        melee(); // √ºberpr√ºft ob Gegner angreifen kann; greift an
    }

    /**
     * √úberpr√ºft ob Gegner den Spieler angreifen kann; greift im Fall an.
     * Autor: Daniel
     */
    public void melee(){
        // Gegner schaut ob der Spieler in reichweite ist
        if( movement.inRange(WorldManager.getPlayer().getX(),WorldManager.getPlayer().getY(),this.getX(),this.getY()) ){ //Wenn Spieler im Radius des Gegners ist
            counter++; // Wenn er in der n‰he ist z‰hlt er hoch
            if(counter >= frequency) { // Wenn er fertig gez‰hlt hat explodiert er
               WorldManager.getPlayer().setHealth(WorldManager.getPlayer().getHealth() - getDamage()); //f√ºgt dem Spieler den Schaden des Angriffs hinzu 
               kill(); // explodiert
            }
        }else { // Falls er nicht mehr in der N‰he ist wird der Timer resettet
            counter = 0;
        }
    }

}
