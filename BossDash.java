/**
 * Hier wird die Fähigkeit Dash implementiert. Der Boss setzt sich auf eine bestimmte Richtung fest
 * (Richtung Spieler vom Start aus) und läuft dann mit schneller Geschwindigkeit bis er einen
 * Collider trifft
 * 
 * @author Alex, Daniel
 * @version 19.6.2020
 */
public class BossDash implements IBossAbility {
    
    // Geschwindigkeits-Buff den der Boss bekommt sobald er in die Richtung stürmt
    private final double SPEED_BUFF = 4.0;
    
    // Vektor in welche Richtung der Boss stürmt
    private int[] dashVector;
    
    /** 
     * Führt einen Schritt der Fähigkeit aus.
     * @param boss Boss, der die Fähigkeit ausführt
     * @return Boolean, ob die Methode nochmal aufgerufen werden soll / Die Fähigkeit nicht zu ende ist
     */
    public boolean useAbility(Boss boss) {
        boss.setInAbility(true);
        // Beim ersten aufruf
        if(dashVector == null) {
            SoundManager.playSound("sounds/boss_dash.wav", SoundManager.getSFXVol());
            Player player = WorldManager.getPlayer();
            // in welche Richtung?
            int dx = player.getX() > boss.getX() ? 1 : (player.getX() < boss.getX() ? -1 : 0);
            int dy = player.getY() > boss.getY() ? 1 : (player.getY() < boss.getY() ? -1 : 0);
            // dashVector erzeugen
            dashVector = new int[2];
            dashVector[0] = dx;
            dashVector[1] = dy;
            
            boss.resetAnimTime();
        }
        // Boss geschwindigkeit buffen
        boss.buffSpeed(SPEED_BUFF);
        // gehe in die Richtung; walk() returned ob der Boss sich in beide Richtungen bewegt hat
        return boss.walk(dashVector[0], dashVector[1]);
    }
    
    /**
     * Stoppt die Fähigkeit und resettet alles
     * @param boss Boss, für den die Fähigkeit gestoppt werden soll
     */
    public void stopAbility(Boss boss) {
        dashVector = null;
        boss.buffSpeed(1);
        boss.setInAbility(false);
    }
    
    /**
     * Gibt den Namen der Fähigkeit zurück
     * @return Name der Fähigkeit als String
     */
    public String getAbilityName() {
        return "dash";
    }
    
    // Autor: Daniel
    /**
     * Verwaltet die Animation des Bosses in Ber�cksichtigung der aktuellen F�higkeit.
     * @author Daniel
     */
    public void manageAnim(Boss boss) {
        int facing = 0;
        if (dashVector[0]>0){// Der Dash geht nach rechts
            if(Math.abs(dashVector[0]) >= Math.abs(dashVector[1])){facing=1;}
        }
        else if(dashVector[0]<0){//Der Dash geht nach links
            if(Math.abs(dashVector[0]) >= Math.abs(dashVector[1])){facing=3;}
        }

        if(dashVector[1]<0){//Der Dash geht nach oben
            if(Math.abs(dashVector[0]) < Math.abs(dashVector[1])){facing=0;}
        }
        else if(dashVector[1]>0){//Der Dash geht nach unten
            if(Math.abs(dashVector[0]) < Math.abs(dashVector[1])){facing=2;}
        }
        
        // Werte des Bosses setzen
        boss.setFacing(facing);
        boss.setAnimationState("WALKING");
        boss.setBossState(getAbilityName());
    }
}
