/**
 * Hier wird die Fähigkeit Teleport implementiert. Der Boss versucht sich in die ungefähre 
 * Richtung des Spielers zu teleportieren, um somit von hinten angreifen zu können und
 * den Spieler gleichzeitig zu verwirren.
 * 
 * @author Alex, Daniel
 * @version 26.06.2020
 */
public class BossTp implements IBossAbility {
    
    private boolean active = false;
    
    /** 
     * Führt einen Schritt der Fähigkeit aus.
     * @param boss Boss, der die Fähigkeit ausführt
     * @return Boolean, ob die Methode nochmal aufgerufen werden soll / Die Fähigkeit nicht zu ende ist
     */
    public boolean useAbility(Boss boss) {
        boss.setInAbility(true);
        
        // Verhindert bug beim mehrmaligen aufrufen
        if(active)
            return false;
        active = true;
        
        SoundManager.playSound("sounds/boss_tp.wav", SoundManager.getSFXVol());
        
        Player player = WorldManager.getPlayer();
        // in welche Richtung?
        //int dx = player.getX() > boss.getX() ? 1 : (player.getX() < boss.getX() ? -1 : 0);
        //int dy = player.getY() > boss.getY() ? 1 : (player.getY() < boss.getY() ? -1 : 0);
        int dx = 0,dy = 0;
        if(player.getX() > boss.getX())
            dx += 1;
        else
            dx -= 1;
        if(player.getY() > boss.getY())
            dy += 1;
        else
            dy -= 1;
            
        // tpVector erzeugen
        int[] tpVector = {dx, dy};
        
        int[] rayCast = {boss.getX(), boss.getY()};
        
        // In diese Richtung Raycasten bis das Ray out of Bounds ist
        while(!WorldManager.outOfBounds(rayCast[0], rayCast[1])) {
            rayCast[0] += tpVector[0] * 50;
            rayCast[1] += tpVector[1] * 50;
        }
        // Setzte die Position des Bosses zu dem Ziel des Raycasts
        boss.setLocation(rayCast[0], rayCast[1]);
        
        // Gehe den Raycast so viele Schritte zurück, bis der Boss in keinem Collider mehr steckt und innerhalb des Raumes ist
        while(boss.inCollider() || WorldManager.outOfBounds(boss.getX(), boss.getY()))
            boss.setLocation(boss.getX() - tpVector[0] * 10, boss.getY() - tpVector[1] * 10);
            
        // Diese Fähigkeit ist direkt nach dem Aufruf vorbei und kann somit gestoppt werden
        return false;
    }

    /**
     * Stoppt die Fähigkeit und resettet alles
     * @param boss Boss, für den die Fähigkeit gestoppt werden soll
     */
    public void stopAbility(Boss boss) {
        boss.setInAbility(false);
        active = false;
    }
    
    /**
     * Gibt den Namen der Fähigkeit zurück
     * @return Name der Fähigkeit als String
     */
    public String getAbilityName() {
        return "tp";
    }
    
    // Autor: Daniel
    public void manageAnim(Boss boss) {
        boss.manageNormalAnim();
    }
}
