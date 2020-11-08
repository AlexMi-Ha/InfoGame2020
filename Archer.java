import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Objekte der Unterklasse Archer sind Bogensch�tzen, die versuchen sich in einem Ring um den Spieleraufzuhalten, 
 * um ihn von dort mit Pfeilen anzugreifen.
 * Wenn sie zu nah am Spieler sind, laufen sie von ihm weg bis sie wieder im richtigen Bereich sind.
 * Dieser Bereich wird in der Klasse Ranged festgelegt.
 * 
 * @author Anja, Daniel 
 * @version 22.6.2020
 */
public class Archer extends Enemy 
{
    private int frequency; //legt fest, wie schnell geschossen wird, je höher frequency, desto länger ist die Pause zwischen den Schüssen 
    private int count; //Z�hlvariable, die sp�ter mit frequency verglichen wird um Abschusszeitpnkt zu ermitteln

    public Archer(){
        // Erzeugt Gegner mit 15 damage, 5 Leben, nicht flugfähig, 3 speed und Fortbewegungstyp Ranged()
        super(15,5,false,3, new Ranged(), "archer", 3,10,0, 40);
        frequency=25;
        count=0;

        //vorläufiges Bild erzeugen
        GreenfootImage img=new GreenfootImage(40,40);
        setImage(img);
    }

    /**
     * Die gleiche act()-Methode wie bei der Superklasse Enemy, nur dass außerdem auf den Spieler geschossen wird
     *
     */
    public void act(){
        super.act();
        if(getHealth() < 1){
            return;
        }
        Player player=WorldManager.getPlayer(); //speichere jetzige Instanz des Players

        boolean inRange=movement.inRange(player.getX(),player.getY(),this.getX(),this.getY()); //Ist der Archer in Reichweite?

        if (inRange==true){
            if(count==frequency){ //Wenn der Archer in Reichweite ist und bereits wieder schie�en kann
                DungeonWorld.getWorld().addObject(new Projectile(player.getX()-getX(), player.getY()-getY(), getX(), getY(), this, "archer", 3), getX(), getY() ); //Erzeuge Projektil in Richtung des Spielers 
                speedBuff= 0.5; //Verlangsamung des Archers beim Schießen
                count=0;
            }
            else{
                count++;
                //speedBuff wird nach dem Vervollständigen der meleeAnimation wieder auf 1 gesetzt! (s. meleeAnimation in Enemy)
            }
        }
    }
    

}
