import greenfoot.*;

/**
 * Raum subklasse, die im Endeffekt auch in der Welt erzeugt wird.
 * Hier sind nur mehrere Konstruktor vorhanden, die der Superklasse sagen, welchen Raum sie generieren sollen.
 * @author Alex
 * @version 14.5.2020
 */
public class RoomWorld extends DungeonWorld {
    
    /** 
     * Erzeuge eine neue Welt an der Position (GAME POS) (0|0).
     * Generiert neues Spiel und legt den Raum an
     */
    public RoomWorld() {
        WorldManager.generateNewGame();
        setup();
        String[] data = WorldManager.loadRoomAt(0, 0);
        generateLevel(data);
    }
    
    /**
     * Erzeuge einen Raum mit speziellen data Array
     */
    public RoomWorld(String[] data) {
        setup();
        generateLevel(data);
    }
    
    /**
     * Erzeuge einen Raum an einer speziellen (GAME COORDS) position der Map
     * @param x X-Koordinate des gewünschten Raumes
     * @param y Y-Koordinate des gewünschten Raumes
     */
    public RoomWorld(int x, int y) {
        setup();
        String[] data = WorldManager.loadRoomAt(x, y);
        generateLevel(data);
    }
}
