/**
 * Hier wird die Fähigkeit Bullet Hell implementiert. Bei dieser schießt der Boss für eine
 * gewisse Zeit schnell mehrere Projektile in alle Richtungen.
 * 
 * @author Alex, Daniel
 * @version 26.6.2020
 */
public class BossBulletHell implements IBossAbility {
    // Wie lange soll die Fähigkeit dauern?
    private final int MAX_ABILITY_TIME = 100;
    private int abilityTimer = 0;
    
    // Was ist die Schussfrequenz?
    private final int BULLET_FREQUENCY = 20;
    
    /** 
     * Führt einen Schritt der Fähigkeit aus.
     * @param boss Boss, der die Fähigkeit ausführt
     * @return Boolean, ob die Methode nochmal aufgerufen werden soll / Die Fähigkeit nicht zu ende ist
     */
    public boolean useAbility(Boss boss) {
        if(abilityTimer == 0)
            SoundManager.playSound("sounds/boss_bullethell.wav", SoundManager.getSFXVol());
        
        boss.setInAbility(true);
        boss.buffDamage(.25);
        // Projektile alle 'BULLET_FREQUENCY' Ticks schießen
        if(abilityTimer % BULLET_FREQUENCY == 0) {
            int x = boss.getX(), y = boss.getY();
            DungeonWorld.getWorld().addObject(new Projectile(1, 0, x, y, boss, "alhoon", 0), x, y);
            DungeonWorld.getWorld().addObject(new Projectile(1, 1, x, y, boss, "alhoon", 0), x, y);
            DungeonWorld.getWorld().addObject(new Projectile(-1, 0, x, y, boss, "alhoon", 0), x, y);
            DungeonWorld.getWorld().addObject(new Projectile(-1, -1, x, y, boss, "alhoon", 0), x, y);
            DungeonWorld.getWorld().addObject(new Projectile(0, 1, x, y, boss, "alhoon", 0), x, y);
            DungeonWorld.getWorld().addObject(new Projectile(0, -1, x, y, boss, "alhoon", 0), x, y);
            DungeonWorld.getWorld().addObject(new Projectile(1, -1, x, y, boss, "alhoon", 0), x, y);
            DungeonWorld.getWorld().addObject(new Projectile(-1, 1, x, y, boss, "alhoon", 0), x, y);
        }

        abilityTimer++;
        // Zurückgeben ob noch Zeit über ist
        return abilityTimer <= MAX_ABILITY_TIME;
    }
    
    /**
     * Stoppt die Fähigkeit und resettet alles
     * @param boss Boss, für den die Fähigkeit gestoppt werden soll
     */
    public void stopAbility(Boss boss) {
        abilityTimer = 0;
        boss.buffDamage(1.0);
        boss.setInAbility(false);
    }
    
    /**
     * Gibt den Namen der Fähigkeit zurück
     * @return Name der Fähigkeit als String
     */
    public String getAbilityName() {
        return "bullethell";
    }
    
    // Autor: Daniel
    /**
     * Verwaltet die Animation des Bosses in Ber�cksichtigung der aktuellen F�higkeit.
     * @author Daniel
     */
    public void manageAnim(Boss boss) {

        boss.setBossState(getAbilityName());
        boss.setAnimationState("IDLE");
        
    }
    
}
