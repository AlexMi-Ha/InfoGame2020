import greenfoot.*;
/**
 * Diese Klasse wird zur Kommunikation zwischen dem Level Generator und der eigentlichen Welt benutzt.
 * Besitzt nur statische Attribute und Methoden
 * 
 * @author Alex, Daniel, Sebi
 * @version 13.06.
 */
public class WorldManager {
    
    // Statische Variable fÃ¼r den Level Generator
    private static LevelGeneration generator;
    
    // Statische Variable fÃ¼r die jetzige Spieler instanz
    private static Player player;
    
    private static Healthbar healthbar;
    
    private static Minimap map;
    
    private static KeyUI keyUI;
    
    //Statische Variable fÃ¼r die Ebene
    private static int ebene= 0;
    
    private static int[] currentCoords = {0, 0};
    
    //------------------------------------------------------------
    // EINSTELLBAR
    private static boolean devMode = false; // Mit angeschaltenem Dev mode kann der Spieler nicht sterben!
    
    private static boolean areProjectilesAnimated = true; //bessere Performance wenn auf false

    private static boolean musicOn = true;  // Bestimmt ob Musik und Sounds abgespielt werden sollen
    
    //------------------------------------------------------------
    
    /**
     * Lade einen bestimmten Raum an den GAME_KOORDINATEN (x|y)
     */
    public static String[] loadRoomAt(int x, int y) {
        x += getGenerator().getGridSize()[0];
        y += getGenerator().getGridSize()[1];
        return getGenerator().getRooms()[x][y].getData();
    }
    
    /**
     * Generiert nur ein neues Level. BehÃ¤lt jetzige Spielerdaten, ÃœberprÃ¼ft, ob ein neuer Highscore erreicht wurde.
     * 
     */
    public static void generateNewFloor() {
        generator = new LevelGeneration();
        currentCoords[0] = 0;
        currentCoords[1] = 0;
        player.setKey(false);
        ebene++;
        if (ebene> DataManager.read()){
            DataManager.write(ebene);
        }
        keyUI= new KeyUI();
    }
    
    /**
     * Generiert ein neues Spiel (Reset mit neuem Spieler)
     */
    public static void generateNewGame() {
        generator = new LevelGeneration();
        currentCoords[0] = 0;
        currentCoords[1] = 0;
        player = new Player();
        ebene= 0;
        healthbar= new Healthbar(200, 20, Color.GREEN);
        map= new Minimap();
        keyUI= new KeyUI();
    }
    
    /**
     * Ist Punkt (x, y) außerhalb des Raumes?
     * @return Boolean, ob Punkt (x, y) außerhalb des Raumes ist.
     */
    public static boolean outOfBounds(int x, int y) {
        return !(x > 98 && x < DungeonWorld.getWorld().getWidth() - 50 && y > 98 && y < DungeonWorld.getWorld().getHeight() - 50);
    }
    
    /**
     * Gibt die LevelGeneration zurück
     * @return Gibt den Level Generator zurÃ¼ck
     */
    public static LevelGeneration getGenerator() {
        return generator;
    }
    
    /**
     * Gibt den Spieler zurück
     * @return Gibt die statische Player instanz zurÃ¼ck
     */
    public static Player getPlayer() {
        return player;
    }
    
    /**
     * Gibt die Position des jetzigen Raumes zurück (GAME COORDS)
     * @return Gibt den Ortsvektor des jetzigen Raums zurÃ¼ck (GAME COORDS)
     */
    public static int[] getCoords() {
        return currentCoords;
    }
    
    /**
     * Setze die jetzigen Koordinaten (in GAME_KOORDINATEN ((0|0) in der Mitte))
     * @param x Zu setzende x Koordinate
     * @param y Zu setzende y Koordinate
     */
    public static void setCoords(int x, int y) {
        currentCoords[0] = x;
        currentCoords[1] = y;
    } 
    
    /**
     * Gibt den jetzigen Raum zurück
     * @return Gibt den Raum zurÃ¼ck in dem sich der Spieler gerade befindet
     */
    public static Room getCurrentRoom() {
        return generator.getRooms()[currentCoords[0] + getGenerator().getGridSize()[0]][currentCoords[1] + getGenerator().getGridSize()[1]];
    }
    
    /**
     * @return Gibt die aktuelle Ebene des Spielers zurÃ¼ck 
     * Autor: Daniel
     */
    public static int getEbene(){
        return ebene;
    }
    
    /**
     * @return Gibt die aktuelle Instanz der Healthbar zurÃ¼ck 
     * Autor: Daniel
     */
    public static Healthbar getHealthbar(){
        return healthbar;
    }
    
    /**
     * @return Gibt die aktuelle Instanz der Minimap zurÃ¼ck 
     * Autor: Sebi
     */
    public static Minimap getMinimap(){
        return map;
    }
    /**
     * @return Gibt die aktuelle Instanz der Key-UI zurück
     * Autor: Sebi
     */
    public static KeyUI getKeyUI(){
        return keyUI;
    }
    
    /**
     * Gibt zurück ob der DevMode gerade aktiviert ist
     * @return Boolean, ob der DevMode an ist
     */
    public static boolean isInDevMode() {
        return devMode;
    }
    
    /**
     * Gibt zurück ob Projektile animiert sein sollen
     * Autor: Daniel
     * @return Boolean, ob Projektile animiert sind
     */
    public static boolean areProjectilesAnimated() {
        return areProjectilesAnimated;
    }
    
    /**
     * Gibt zurück ob die Musik und Sounds im Spiel an sein sollen
     * @return Boolean, ob Sounds abgespielt werden sollen
     */
    public static boolean isMusicOn() {
        return musicOn;
    }
}
