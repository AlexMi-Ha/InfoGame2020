/**
 * Interface für die Methoden, die die Fähigkeiten der Bosse steuern
 * 
 * @author Alex, Daniel
 * @version 26.06.2020
 */
public interface IBossAbility {

    /** 
     * F�hrt einen Schritt der F�higkeit aus.
     * @param boss Boss, der die F�higkeit ausf�hrt
     * @return Boolean, ob die Methode nochmal aufgerufen werden soll / Die F�higkeit nicht zu ende ist
     */
    public boolean useAbility(Boss boss);

    /**
     * Stoppt die Fähigkeit und resetet alles
     * @param boss Boss, f�r den die F�higkeit gestoppt werden soll
     */
    public void stopAbility(Boss boss);

    /**
     * Gibt die Bezeichnung der F�higkeit zur�ck.
     */
    public String getAbilityName();

    /**
     * Passt alle Animationsspezifischen Werte des Bosses an 
     * um die richtigen Frames f�r die F�higkeit zu erhalten.
     * Autor: Daniel
     */
    public void manageAnim(Boss boss);
}
