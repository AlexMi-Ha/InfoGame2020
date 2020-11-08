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

    // Standard Lautst�rke der Hintergrundmusik
    private static final int backMusicVolume = 70;
    
    private static final int sfxVolume = 95;
    
    

    /**
     * Spiele ein Lied als Loop ab.
     * (Gleiches Lied mehrere male m�glich)
     * @param path Pfad des Liedes
     * @return Boolean, ob das Lied nun erfolgreich abgespielt wird
     */
    public static boolean playSongLoop(String path) {
        if(!WorldManager.isMusicOn())
            return false;
        try {
            // F�ge das Lied zur Liste hinzu
            currentlyPlayling.add(new GreenfootSound(path));

            // Spiele es ab und setze die Lautst�rke
            currentlyPlayling.get(currentlyPlayling.size() - 1).playLoop();
            currentlyPlayling.get(currentlyPlayling.size() - 1).setVolume(backMusicVolume);
        }catch(Exception e) { // Fehler falls kein Lautsprecher aktiviert ist oder �hnliches
            System.out.println("<SoundManager> Fehler beim abspielen von " + path);
            return false;
        }
        return true;
    }

    /**
     * Spiele ein Lied als Loop mit bestimmter Lautst�rke ab
     * (Gleiches Lied mehrere male m�glich)
     * @param path Pfad des Liedes
     * @param volume Lautst�rke des Liedes
     */
    public static void playSongLoop(String path, int volume) {
        if(!playSongLoop(path))
            return;
        try {
            currentlyPlayling.get(currentlyPlayling.size() - 1).setVolume(volume);
        }catch(Exception e) {
            System.out.println("<SoundManager> Fehler beim ver�ndern der Lautst�rke");
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
            // Liste r�ckw�rts durchgehen um kein element beim entfernen zu �berspringen
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
     * Gibt zur�ck ob ein bestimmtes Lied gerade abgespielt wird
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
            // am Ende und bei Fehlern immer false zur�ckgeben
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
            // Liste r�ckw�rts durchgehen um kein element beim entfernen zu �berspringen
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
     * @param volume Lautst�rke des Sounds
     */
    public static void playSound(String path, int volume) {
        if(!WorldManager.isMusicOn())
            return;
        try {
            GreenfootSound s = new GreenfootSound(path);
            // Spiele es ab und setze die Lautst�rke
            s.setVolume(volume);
            s.play();
        }catch(Exception e) { // Fehler falls kein Lautsprecher aktiviert ist oder �hnliches
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
     * Gibt das normale SoundVolume zur�ck
     * @return Sound Lautst�rke als Integer
     */
    public static int getSFXVol() {
        return sfxVolume;
    }
    
    /**
     * Gibt das normale MusicVolume zur�ck
     * @return Musik Lautst�rke als Integer
     */
    public static int getBackMusicVol() {
        return backMusicVolume;
    }
}
