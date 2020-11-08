import greenfoot.*;

/**
 * Die Klasse des Demogorgon bosses. Er kann sich hinter den Spieler teleportieren
 * und Gegner, die ihm helfen spawnen. Dieser Boss kämpft aus der Ferne
 * 
 * @author Alex
 * @version 26.06.2020
 */
public class Demogorgon extends Boss {
    
    /**
     * Erzeugt eine Instanz eines neuen Demogorgon Bosses
     */    
    public Demogorgon() {
        // Platzhalter bild
        GreenfootImage img = new GreenfootImage(60,60);
        /*img.setColor(Color.BLUE);
        img.fill();*/
        setImage(img);
        
        // Boss spezifische Attribute
        max_health = 100;
        health = 100;
        speed = 1;
        damage = 5;
        
        //Autor:Daniel ( Attribute f�r die Animation )
        maxAnimIndexATK = 2;
        maxAnimIndexIdle = 4;
        maxAnimIndexWalkFront = 4;
        maxAnimIndexWalkSide = 4;
        yTextureOffset = 10;
        
        //--------------------
        
        type = "demogorgon";
        
        // Ranged Gegner
        movement = new Ranged(50, 350);
        ability = new BossTp();
    }

    /**
     * Gibt eine zufällige Fähigkeit aus der möglichen Auswahl des Bosses zurück
     * @return Fähigkeiten Klasse, die IBossAbility implementiert (50% Minions; 50% Tp)
     */
    protected IBossAbility pickAbility() {
        // 50% Minions; 50% Tp
        return Math.random() > 0.5 ? new BossMinionSpawn() : new BossTp();
    }

    /**
     * Greift den Spieler (sofern er in Reichweite ist) an
     */
    protected void attack(Player player) {
        boolean inRange=movement.inRange(player.getX(),player.getY(),this.getX(),this.getY()); //Ist der Boss in Reichweite?

        if (inRange){
            if(attackTimer==frequency){
                DungeonWorld.getWorld().addObject(new Projectile(player.getX()-getX(),player.getY()-getY(),getX(),getY(), this, "demogorgon", 3), getX(), getY()); //Erzeuge Projektil in Richtung des Spielers 
                attackTimer=0;
                buffAttackSpeedDebuff(0.9); //verlangsamt den Boss beim Verschie�en seiner Feuerb�lle.
            }
            else
                attackTimer++;
        }
    }
}
