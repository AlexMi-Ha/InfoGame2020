/**
 * Interface fÃ¼r die Methoden, die die FÃ¤higkeiten der Bosse steuern
 * 
 * @author Alex, Daniel
 * @version 26.06.2020
 */
public interface IBossAbility {

    /** 
     * Führt einen Schritt der Fähigkeit aus.
     * @param boss Boss, der die Fähigkeit ausführt
     * @return Boolean, ob die Methode nochmal aufgerufen werden soll / Die Fähigkeit nicht zu ende ist
     */
    public boolean useAbility(Boss boss);

    /**
     * Stoppt die FÃ¤higkeit und resetet alles
     * @param boss Boss, für den die Fähigkeit gestoppt werden soll
     */
    public void stopAbility(Boss boss);

    /**
     * Gibt die Bezeichnung der Fähigkeit zurück.
     */
    public String getAbilityName();

    /**
     * Passt alle Animationsspezifischen Werte des Bosses an 
     * um die richtigen Frames für die Fähigkeit zu erhalten.
     * Autor: Daniel
     */
    public void manageAnim(Boss boss);
}
