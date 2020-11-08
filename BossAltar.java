import greenfoot.*;
/**
 * Hier wird die Fähigkeit Altar implementiert. Der Boss setzt 4 Altare in die Ecken des Raumes,
 * die ihn stark heilen solange diese am Leben sind.
 * 
 * @author Alex, Daniel
 * @version 26.06.2020
 */
public class BossAltar implements IBossAbility {

    // Wie viele Leben sollen pro Tick geheilt werden
    private final int HEAL_AMOUNT = 1;

    // Feld für die 4 Altare
    private Altar[] altars;

    /** 
     * Führt einen Schritt der Fähigkeit aus.
     * @param boss Boss, der die Fähigkeit ausführt
     * @return Boolean, ob die Methode nochmal aufgerufen werden soll / Die Fähigkeit nicht zu ende ist
     */
    public boolean useAbility(Boss boss) {
        boss.setInAbility(true);
        // Beim ersten aufruf
        if(altars == null) {
            //Sound abspielen
            SoundManager.playSound("sounds/boss_minions.wav", SoundManager.getSFXVol());

            // Altare erstellen und in den Raum setzen
            altars = new Altar[4];
            for(int i = 0; i < 4; ++i)
                altars[i] = new Altar();

            DungeonWorld.getWorld().addObject(altars[0], 65, 115);
            DungeonWorld.getWorld().addObject(altars[1], 935, 115);
            DungeonWorld.getWorld().addObject(altars[2], 65, 575);
            DungeonWorld.getWorld().addObject(altars[3], 935, 575);
        }

        // Sind alle Altare tot?
        boolean allDead = true;
        for(Altar a : altars) {
            if(a.getWorld() != null) {
                allDead = false;
                break;
            }
        }

        // Wenn noch altare am Leben sind: Heile den Boss
        if(!allDead)
            boss.setHealth(boss.getHealth() + HEAL_AMOUNT);

        //Folge dem Spieler weiterhin
        boss.followPlayer();

        return !allDead; // Ist noch ein Altar am Leben?
    }

    /**
     * Stoppt die Fähigkeit und resettet alles
     * @param boss Boss, für den die Fähigkeit gestoppt werden soll
     */
    public void stopAbility(Boss boss) {
        if(altars != null) {
            for(int i = 0; i < altars.length; ++i) {
                if(altars[i] != null)
                    altars[i].kill();
            }
            altars = null;
        }
        boss.setInAbility(false);
    }
    
    /**
     * Gibt den Namen der Fähigkeit zurück
     * @return Name der Fähigkeit als String
     */
    public String getAbilityName() {
        return "altar";
    }
    
    // Autor: Daniel
    
    private int animTimer=0;
    /**
     * Verwaltet die Animation des Bosses in Ber�cksichtigung der aktuellen F�higkeit.
     * @author Daniel
     */
    public void manageAnim(Boss boss) {
        if( animTimer >= 32){
            boss.setBossState("NORMAL");
            boss.manageNormalAnim();
        }
        else{
            animTimer++;
            boss.setBossState(getAbilityName());
            boss.setAnimationState("IDLE");
        }
    }
}
