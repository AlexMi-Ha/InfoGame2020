import greenfoot.*;

/**
 * Oberklasse für alle Türarten
 * 
 * @author Alex
 * @version 15.5.2020
 */
public abstract class ProgressionDoor extends Actor {
    
    protected boolean locked; // Ist die Tür verschlossen?
    
    /**
     * Setzt den Lock-status der Tür
     */
    public abstract void setLocked(boolean lock);
    
    /**
     * Ist die Tür verschlossen?
     * @return Boolean, ob die Tür verschlossen ist
     */
    public boolean isLocked() {
        return locked;
    }
}
