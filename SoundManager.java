import greenfoot.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Die Methoden dieser Klassen werden benutzt um das Sound-system zu steuern.
 * 
 * @author Alex
 * @version 9.6.2020
 */
public class SoundManager {

    // Liste mit allen Sounds die grad gelopped werden
    private static List<GreenfootSound> currentlyPlayling = new ArrayList();

    // Standard Lautstärke der Hintergrundmusik
    private static final int backMusicVolume = 70;
    
    private static final int sfxVolume = 95;
    
    

    /**
     * Spiele ein Lied als Loop ab.
     * (Gleiches Lied mehrere male möglich)
     * @param path Pfad des Liedes
     * @return Boolean, ob das Lied nun erfolgreich abgespielt wird
     */
    public static boolean playSongLoop(String path) {
        if(!WorldManager.isMusicOn())
            return false;
        try {
            // Füge das Lied zur Liste hinzu
            currentlyPlayling.add(new GreenfootSound(path));

            // Spiele es ab und setze die Lautstärke
            currentlyPlayling.get(currentlyPlayling.size() - 1).playLoop();
            currentlyPlayling.get(currentlyPlayling.size() - 1).setVolume(backMusicVolume);
        }catch(Exception e) { // Fehler falls kein Lautsprecher aktiviert ist oder ähnliches
            System.out.println("<SoundManager> Fehler beim abspielen von " + path);
            return false;
        }
        return true;
    }

    /**
     * Spiele ein Lied als Loop mit bestimmter Lautstärke ab
     * (Gleiches Lied mehrere male möglich)
     * @param path Pfad des Liedes
     * @param volume Lautstärke des Liedes
     */
    public static void playSongLoop(String path, int volume) {
        if(!playSongLoop(path))
            return;
        try {
            currentlyPlayling.get(currentlyPlayling.size() - 1).setVolume(volume);
        }catch(Exception e) {
            System.out.println("<SoundManager> Fehler beim verändern der Lautstärke");
        }
    }

    /**
     * Stoppe den Loop eines Liedes
     * @param path Pfad des Liedes
     */
    public static void stopSongLoop(String path) {
        if(!WorldManager.isMusicOn())
            return;
        try {
            // Liste rückwärts durchgehen um kein element beim entfernen zu überspringen
            for(int i = currentlyPlayling.size() - 1; i >= 0; --i) {
                // Pfad rausfiltern
                String p = filterSongPath(currentlyPlayling.get(i));
                if(p.equals(path)) {
                    // Lied stoppen und entfernen
                    currentlyPlayling.get(i).stop();
                    currentlyPlayling.remove(i);
                    continue;
                }
            }
        }catch(Exception e) {
            System.out.println("<SoundManager> Fehler beim Stoppen von " + path);
        }
    }

    /**
     * Gibt zurück ob ein bestimmtes Lied gerade abgespielt wird
     * @param path Pfad des Liedes
     * @return Boolean, ob das Lied 'path' gerade abgespielt wird
     */
    public static boolean isSongLooping(String path) {
        if(!WorldManager.isMusicOn())
            return false;
        try {
            for(int i = 0; i < currentlyPlayling.size(); ++i) {
                // Pfad rausfiltern
                String p = filterSongPath(currentlyPlayling.get(i));
                if(p.equals(path)) 
                    return true;
            }
            // am Ende und bei Fehlern immer false zurückgeben
            return false;
        }catch(Exception e) {
            System.out.println("<SoundManager> Fehler beim Suchen von " + path);
            return false;
        }
    }

    /**
     * Stoppe alle Lieder die gerade gelooped werden
     */
    public static void stopAllLoopingSongs() {
        if(!WorldManager.isMusicOn())
            return;
        try {
            // Liste rückwärts durchgehen um kein element beim entfernen zu überspringen
            for(int i = currentlyPlayling.size() - 1; i >= 0; --i) {
                currentlyPlayling.get(i).stop();
                currentlyPlayling.remove(i);
            }
        }catch(Exception e) {
            System.out.println("<SoundManager> Fehler beim Stoppen aller Songs");
        }
    }
    
    /**
     * Spiele einen Sound einmal ab
     * @param path Pfad des Sounds
     * @param volume Lautstärke des Sounds
     */
    public static void playSound(String path, int volume) {
        if(!WorldManager.isMusicOn())
            return;
        try {
            GreenfootSound s = new GreenfootSound(path);
            // Spiele es ab und setze die Lautstärke
            s.setVolume(volume);
            s.play();
        }catch(Exception e) { // Fehler falls kein Lautsprecher aktiviert ist oder ähnliches
            System.out.println("<SoundManager> Fehler beim abspielen von " + path);
        }
    }

    /**
     * Pfad eines GreenfootSounds herausfinden
     * @param s GreenfootSound, von dem der Pfad ermittelt werden soll
     * @return Gefilterter Pfad der Sounddatei als String
     */
    private static String filterSongPath(GreenfootSound s) {
        // String aus dem Lied entnehmen
        // greenfoot.GreenfootSound@4ba9b file: soundtrack/title_screen.mp3 . Is playing: false
        String p = s.toString();
        // soundtrack/title_screen.mp3 . Is playing
        p = p.split(":")[1];
        // soundtrack/title_screen.mp3
        p = p.replaceFirst(" . Is playing", "");
        //soundtrack/title_screen.mp3
        p = p.substring(1);
        return p;
    }
    
    /**
     * Gibt das normale SoundVolume zurück
     * @return Sound Lautstärke als Integer
     */
    public static int getSFXVol() {
        return sfxVolume;
    }
    
    /**
     * Gibt das normale MusicVolume zurück
     * @return Musik Lautstärke als Integer
     */
    public static int getBackMusicVol() {
        return backMusicVolume;
    }
}
