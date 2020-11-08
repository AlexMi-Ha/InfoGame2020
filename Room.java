/**
 * Diese Klasse besitzt alle Daten eines bestimmten Raums den der Level Generator generiert
 * @author Alex 
 * @version 6.6.2020
 */
public class Room {
    
    // Ortsvektor des Raumes in Game coords
    private int[] pos;
    
    // Spezielles Raumevent (Gegner gespawnt, Kiste gelooted, Key genommen...)
    private boolean triggeredRoomEvent = false;
    
    private ROOM_TYPE type;
    // Wo sind Türen gesetzt (NONE := Keine Tür)
    public ROOM_TYPE doorN, doorS, doorE, doorW;
    
    // Wahrscheinlichkeiten für das eintreten der ersten 3 enums bei der Auswahl des Raumtyps in der Levelgeneration
    public static int[] room_probability = {15, 20, 65};
    
    // Diese Möglichkeiten gibt es für den typ eines Raumes und den Typ einer Tür
    public enum ROOM_TYPE {
        LOOT,   // \
        TRAP,   // | Die drei Räume sind im Zufallstopf für jedem Raum der generiert wird und einen zufälligen Raumtyp braucht
        NORMAL, // /
        SPAWN,  // \
        BOSS,   // | Die drei Typen werden nur einmal in jedem Level direkt vom Generator an 3 Räume vergeben
        KEY,    // /
        NONE;   // - Nur für Tür-variablen, wo keine Tür gesetzt wird
    }
    
    // Hat der Spieler diesen Raum schon besucht?
    private boolean visited = false;
    
    // Vektor der Raumspezifischen Zufallszahlen zur Rauminhalts generation
    private int[] roomRandoms = new int[4];
    
    // Gibt die spezifische Raumvariation des Raumes an
    private int roomTypeID;
    
    // Gibt an wie viele verschiedene Variationen es von einem Normalen Raum gibt
    private final int MAX_ROOM_ID = 5;
    
    /**
     * Erzeugt einen neuen Raum
     * @param pos Ortsvektor zu diesem Raum als Game Coords
     * @param type ROOM_TYPE des Raumes
     */
    public Room(int[] pos, ROOM_TYPE type) {
        this.pos = pos;
        this.type = type;
        for(int i= 0; i< roomRandoms.length; ++i){
            roomRandoms[i]= (int) (Math.random()*100);
        }
        roomTypeID= (int) (Math.random() * MAX_ROOM_ID);
    }
    
    /**
     * Gibt die Raumdaten dieses Raumes zurück.
     * @return Gibt Array mit den Raumdaten zurÃ¼ck: [0]=doorN; [1]=doorE; [2]=doorS; [3]=doorW; [4]=ROOM_TYPE;
     * [5]=CharacterPosition(kann NAN sein)
     */
    public String[] getData() {
        String[] ret = {doorN + "", doorE + "", doorS + "", doorW + "", type + "", "NAN;NAN"};
        return ret;
    }
    
    /**
     * Gibt die zufallszahlen des Raumes zurück
     * @return Gibt den Vektor der Raumspezifischen Zufallszahlen zur Rauminhalts generation zurÃ¼ck
     */
    public int[] getRoomRandoms() {
        return roomRandoms;
    }
    
    /**
     * Gibt den Typ des Raumes zurück
     * @return Gibt den ROOM_TYPE des Raums zurÃ¼ck
     */
    public ROOM_TYPE getType() {
        return type;
    }
    
    /**
     * Gibt die RaumID dieses Raumes zurück
     * @return Gibt die Variation des Raums zurÃ¼ck
     */
    public int getRoomTypeID() {
        return roomTypeID;
    }
    
    /**
     * Setzt den ROOM_TYPE von dem Raum
     */
    public void setType(ROOM_TYPE type) {
        this.type = type;
    }
    
    /**
     * Wurde dieser Raum schon besucht?
     * @return Boolean, ob der Spieler schon in diesem Raum war
     */
    public boolean isVisited() {
        return visited;
    }
    
    /**
     * Setzt ob der Raum schon besucht wurde
     */
    public void visited(boolean visited) {
        this.visited = visited;
    }
    
    /**
     * Wurde das Raumevent schon betÃ¤tigt? (Gegner gespawnt, Key genommen, Kisten geÃ¶ffnet...)
     * @return Boolean, ob das Raumevent schon betätigt wurde
     */
    public boolean triggeredRoomEvent() {
        return triggeredRoomEvent;
    }
    
    /**
     * Setzt ob das Raumevent schon betätigt wurde. (Gegner gespawnt, Key genommen...)
     */
    public void setRoomEventTriggerd(boolean triggeredRoomEvent) {
        this.triggeredRoomEvent = triggeredRoomEvent;
    }
}