/**
 * Hier wird die Fähigkeit Berserker implementiert. Bei dieser bekommt der Boss einen
 * Wutanfall, bei dem er schneller wird, mehr schaden macht aber weniger Resistent wird.
 * 
 * @author Alex, Daniel
 * @version 22.6.2020
 */
public class BossBerserker implements IBossAbility {
    // Wie lange soll die Fähigkeit dauern?
    private final int MAX_ABILITY_TIME = 100;
    private int abilityTimer = 0;
    
    // Wie hoch ist der Buff?
    private final double SPEED_BUFF = 2.0;
    private final double DMG_BUFF = 2.0;
    private final double RESIST_BUFF = .5;
    
    /** 
     * Führt einen Schritt der Fähigkeit aus.
     * @param boss Boss, der die Fähigkeit ausführt
     * @return Boolean, ob die Methode nochmal aufgerufen werden soll / Die Fähigkeit nicht zu ende ist
     */
    public boolean useAbility(Boss boss) {
        boss.setInAbility(true);
        // Beim starten alle Buffs setzen
        if(abilityTimer == 0) {
            SoundManager.playSound("sounds/boss_berserker.wav", SoundManager.getSFXVol());
            boss.buffSpeed(SPEED_BUFF);
            boss.buffDamage(DMG_BUFF);
            boss.buffResistance(RESIST_BUFF);
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
        // Alle Buffs und Timer resetten
        boss.buffSpeed(1.0);
        boss.buffDamage(1.0);
        boss.buffResistance(1.0);
        
        abilityTimer = 0;
        boss.setInAbility(false);
    }
    
    /**
     * Gibt den Namen der Fähigkeit zurück
     * @return Name der Fähigkeit als String
     */
    public String getAbilityName() {
        return "berserker";
    }
    
    // Autor: Daniel
    /**
     * Verwaltet die Animation des Bosses in Ber�cksichtigung der aktuellen F�higkeit.
     * @author Daniel
     */
    public void manageAnim(Boss boss) {
        // Den Spieler normal verfolgen aber mit dem berserker animationsset
        boss.setBossState(getAbilityName());
        boss.manageNormalAnim();
    }
}
