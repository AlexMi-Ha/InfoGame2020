import greenfoot.*;
/**
 * Hier wird die Minion-spawn fähigkeit implementiert. Es werden 4 Gegner um den Boss rum
 * gespawnt, die den Spieler mit begrenzter Lebenszeit verfolgen
 * 
 * @author Alex, Daniel
 * @version 26.06.2020
 */
public class BossMinionSpawn implements IBossAbility {
    // Wie lange soll die Fähigkeit dauern?
    private final int MAX_ABILITY_TIME = 400;
    private int abilityTimer = 0;

    // Feld in dem die gespawnten Gegner gespeichert werden
    private Bird[] minions;

    /** 
     * Führt einen Schritt der Fähigkeit aus.
     * @param boss Boss, der die Fähigkeit ausführt
     * @return Boolean, ob die Methode nochmal aufgerufen werden soll / Die Fähigkeit nicht zu ende ist
     */
    public boolean useAbility(Boss boss) {
        boss.setInAbility(true);
        // Beim starten alle Minions spawnen
        if(abilityTimer == 0) {
            SoundManager.playSound("sounds/boss_minions.wav", SoundManager.getSFXVol());
            minions = new Bird[4];
            // 4 Vögel-gegner erzeugen und zur Welt hinzufügen
            for(int i = 0; i < 4; ++i)
                minions[i] = new Bird();
            int bossX = boss.getX(), bossY = boss.getY(), bossWidth = boss.getImage().getWidth(), bossHeight = boss.getImage().getHeight();
            DungeonWorld.getWorld().addObject(minions[0], bossX - bossWidth, bossY);
            DungeonWorld.getWorld().addObject(minions[1], bossX + bossWidth, bossY);
            DungeonWorld.getWorld().addObject(minions[2], bossX, bossY - bossHeight);
            DungeonWorld.getWorld().addObject(minions[3], bossX, bossY + bossHeight);

            // Gegner die ausserhalb des Raumes sind entfernen
            for(Bird b : minions) {
                if(WorldManager.outOfBounds(b.getX(), b.getY()) || b.inCollider())
                    b.kill();
            }
        }

        // Dem Spieler weiterhin folgen
        boss.followPlayer();
        abilityTimer++;
        // Zurückgeben ob noch Zeit über ist
        return abilityTimer <= MAX_ABILITY_TIME;
    }

    /**
     * Stoppt die Fähigkeit und resettet alles
     * @param boss Boss, für den die Fähigkeit gestoppt werden soll
     */
    public void stopAbility(Boss boss) {
        // Alle Gegner löschen

        if(minions != null) {
            for(int i = 0; i < 4; ++i) {
                if(minions[i] != null)
                    minions[i].kill();
            }
            minions = null;
        }

        abilityTimer = 0;
        boss.setInAbility(false);
    }

    /**
     * Gibt den Namen der Fähigkeit zurück
     * @return Name der Fähigkeit als String
     */
    public String getAbilityName() {
        return "minion";
    }

    // Autor: Daniel
    /**
     * Verwaltet die Animation des Bosses in Ber�cksichtigung der aktuellen F�higkeit.
     * @author Daniel
     */
    public void manageAnim(Boss boss) {
        if( abilityTimer >= 32){
            boss.setBossState("NORMAL");
            boss.manageNormalAnim();
        }
        else{
            boss.setBossState(getAbilityName());
            boss.setAnimationState("IDLE");
        }
    }
}
