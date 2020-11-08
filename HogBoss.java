import greenfoot.*;

/**
 * Die Klasse des HogBosses. Er kann in die Richtung des Spielers stürmen
 * und sich in eine Art Berserker modus versetzen, bei dem seine Geschwindigkeit größer wird,
 * sein Schaden höher wird, er aber weniger Resistent für Schaden wird.
 * 
 * @author Alex, Daniel
 * @version 19.6.2020
 */
public class HogBoss extends Boss {
    
    /**
     * Erzeugt eine Instanz eines neuen Hog Bosses
     */  
    public HogBoss() {
        // Platzhalter bild
        GreenfootImage img = new GreenfootImage(70,60);
        setImage(img);
        
        // Boss spezifische Attribute
        max_health = 100;
        health = 120;
        speed = 2;
        damage = 10;
        
        //Autor:Daniel
        maxAnimIndexATK = 3;
        maxAnimIndexIdle = 5;
        maxAnimIndexWalkFront = 5;
        maxAnimIndexWalkSide = 7;
        yTextureOffset = 25;
        type = "hog";
        //--------------------
        
        // Meele Gegner
        movement = new Melee();
        ability = new BossDash();
    }

    /**
     * Gibt eine zufällige Fähigkeit aus der möglichen Auswahl des Bosses zurück
     * @return Fähigkeiten Klasse, die IBossAbility implementiert (50% Dash; 50% berserker)
     */
    protected IBossAbility pickAbility() {
        // 50% Dash; 50% Berserker
        return Math.random() > 0.5 ? new BossDash() : new BossBerserker();
    }

    /**
     * Greift den Spieler (sofern er in Reichweite ist) an
     */
    protected void attack(Player player) {
        if(attackTimer == frequency){ //Wenn Gegner wieder angreifen kann
            if( movement.inRange(WorldManager.getPlayer().getX(),WorldManager.getPlayer().getY(),this.getX(),this.getY()) ){ //Wenn Spieler im Radius des Gegners ist
                WorldManager.getPlayer().setHealth(WorldManager.getPlayer().getHealth() - getDamage()); //fügt dem Spieler den Schaden des Angriffs hinzu
                buffAttackSpeedDebuff(0.0); //Gegner muss stehen bleiben um anzugreifen
                attackTimer = 0; //Zählen zum nächsten Angriff beginnt erneut
            }
        }
        else
            attackTimer++;    //zählt bis Angriff eingeleitet werden kann
    }
}
