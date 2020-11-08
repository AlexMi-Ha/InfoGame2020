import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Der Alhoon-Boss bewegt sich recht langsam und kann nur schwer getötet werden, solange seine
 * Altare am Leben sind. Außerdem hat er eine Dauerfeuer Fähigkeit die ihm erlaubt, für eine
 * gewisse Zeit mehrere Projektile schnell in alle Richtungen zu feuern.
 * 
 * @author Alex, Daniel
 * @version 26.06.2020
 */
public class AlhoonBoss extends Boss
{
    /**
     * Erzeugt eine Instanz eines neuen Alhoon Bosses
     */    
    public AlhoonBoss() {
        // Platzhalter bild
        GreenfootImage img = new GreenfootImage(60,110);
        /*img.setColor(Color.BLUE);
        img.fill();*/
        setImage(img);

        // Boss spezifische Attribute
        max_health = 100;
        health = 100;
        speed = 1;
        damage = 15;
        
        //Autor:Daniel ( Attribute f�r die Animation )
        maxAnimIndexATK = 3;
        maxAnimIndexIdle = 3;
        maxAnimIndexWalkFront = 5;
        maxAnimIndexWalkSide = 5;
        yTextureOffset = 10;
        
        //--------------------
        type = "alhoon";

        // Ranged Gegner
        movement = new Ranged(50, 300);
        ability = new BossAltar();
    }

    /**
     * Diese Methode wird ausgeführt sobald der Boss zur Welt hinzugefügt wird.
     * Hier wird zum ersten mal die Altar fähigkeit ausgeführt.
     */
    public void addedToWorld(World world) {
        super.addedToWorld(world);
        ability.useAbility(this);
        
    }

    /**
     * Gibt eine zufällige Fähigkeit aus der möglichen Auswahl des Bosses zurück
     * @return Fähigkeiten Klasse, die IBossAbility implementiert (7% Altar; 93% bullethell)
     */
    protected IBossAbility pickAbility() {
        // 7%: Altare spawnen;  93% Bullet hell
        return Math.random() < 0.07 ? new BossAltar() : new BossBulletHell();
    }
    
    /**
     * Greift den Spieler (sofern er in Reichweite ist) an
     */
    protected void attack(Player player) {
        // ist der spieler in reichweite?
        boolean inRange=movement.inRange(player.getX(),player.getY(),this.getX(),this.getY()); //Ist der Spieler in Reichweite?

        if (inRange){
            if(attackTimer==frequency){
                DungeonWorld.getWorld().addObject(new Projectile(player.getX()-getX(),player.getY()-getY(),getX(),getY(), this, "alhoon", 0), getX(), getY()); //Erzeuge Projektil in Richtung des Spielers 
                attackTimer=0;
                buffAttackSpeedDebuff(0.9);
            }
            else
                attackTimer++;
        }
    }
}
