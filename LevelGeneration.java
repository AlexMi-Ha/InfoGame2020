import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.io.File;

/**
 * Diese Klasse ist verantwortlich dafür, einen zufällig vernetzten Raum-graphen zu erstellen.
 * Dieser besitzt, solange es nicht mehr Räume sind als Platz ist,
 * immer so viele Räume wie dem Konstruktor übergeben werden.
 * <p>Um ein Level zu Generieren, muss entweder der Konstruktor 
 * oder die {@link #generateLevel()} Methode aufgerufen werden.</p>
 * 
 * @author Alex
 * @version 17.5.2020
 */
public class LevelGeneration {
    
    // Gibt an, wie groß die Map in eine Richtung ist
    private int[] worldSize = {6, 6};
    // Raum matrix, wo alle Räume in reeller Anordnung festgehalten werden
    private Room[][] rooms;
    // Alle besetzten Position
    private List<int[]> takenPos = new ArrayList<int[]>();
    
    private int numOfRooms = 20;
    private int gridSizeX, gridSizeY;
    
    /**
     * Erzeugt eine neue LevelGeneration mit numOfRooms Räumen
     * @param numOfRooms Wie viele Räume soll der Generator generieren?
     */
    public LevelGeneration(int numOfRooms) {
        this.numOfRooms = numOfRooms;
        generateLevel();
    }
    
    /**
     * Erzeugt eine neue LevelGeneration mit 20 Räumen
     */
    public LevelGeneration() {
        generateLevel();
    }
    
    /**
     * Diese Methode wird benutzt um das Level zu generieren
     */
    public void generateLevel() {
        // Anpassen der Raumanzahl, falls sie zu groß ist
        if(numOfRooms >= worldSize[0] * worldSize[1] * 4)
            numOfRooms = worldSize[0] * worldSize[1] * 4;
        
        gridSizeX = worldSize[0];
        gridSizeY = worldSize[1];
        
        // Erzeuge alle Räume
        createRooms();
        // Setzte alle Türen
        createRoomDoors();
        
        // Berechne die Koords des Bossraums und markiere die Türen um ihn
        int[] bossCoords = getFurthestRoomFrom(toWorldCoords(zeroVector())); // Maximale distanz vom Spawn raum
        markDoors(bossCoords, Room.ROOM_TYPE.BOSS);
        
        // Berechne die Koords des Keyraums und markiere die Türen um ihn
        int[] keyCoords = getFurthestRoomFrom(bossCoords); // Maximale distanz vom Boss raum
        markDoors(keyCoords, Room.ROOM_TYPE.KEY);
        
        takenPos.clear();
        
        // Speichere eine Bild repräsentation des Graphen
        try {
            saveMapImg();
        } catch(IOException ex) {
            System.out.println("<LevelGeneration> Fehler beim erstellen von map.png");
        }
    }
    
    /**
     * Hier wird der zufällige Raum-graph erstellt
     */
    private void createRooms() {
        
        rooms = new Room[gridSizeX * 2][gridSizeY * 2];
        // Setze den Spawnraum in der Mitte
        rooms[gridSizeX][gridSizeY] = new Room(zeroVector(), Room.ROOM_TYPE.SPAWN);
        takenPos.add(0, toWorldCoords(zeroVector()));
        
        float randomCompareStart = 0.2f, randomCompareEnd = 0.01f;
        for(int i = 0; i < numOfRooms - 1; ++i) { // numOfRooms - 1 weil der Spawn raum schon festlegt
            int[] checkPos = newPos();
            
            float randomPerc = ((float)i/ ((float)numOfRooms - 1));
            float randomCompare = lerp(randomCompareStart, randomCompareEnd, randomPerc);
            // Wahrscheinlichkeit größer weniger Nachbarn zu haben je weiter man vom Spawn weggeht
            if(numberOfNeighbors(checkPos) > 1 && Math.random() > randomCompare) { 
                // In diesem if wird eine Position mit weniger Nachbarn berechnet
                int iter = 0;
                do {
                    checkPos = bestNewPos();
                    iter++;
                } while(numberOfNeighbors(checkPos) > 1 && iter < 100);
                if(iter >= 50)
                    System.out.println("<LevelGeneration> Kein Raum mit weniger als " + numberOfNeighbors(checkPos) + "Nachbarn");
            }
            // Füge die Position als Raum in den Graphen ein
            rooms[checkPos[0]][checkPos[1]] = new Room(toGameCoords(checkPos), pickRandomRoomType());
            takenPos.add(0, checkPos);
        }
    }
    
    /**
     * Diese Methode berechnet eine neue zufällige Position
     * @return Gibt einen Ortsvektor zu der Position zurück
     */
    private int[] newPos() {
        int x = 0, y = 0;
        int[] checkPos = toWorldCoords(zeroVector());
        
        do {
            // Zufällige Position aus den vergebenen Positionen
            int i = Math.round((float)(Math.random() * (takenPos.size() - 1)));
            x = takenPos.get(i)[0];
            y = takenPos.get(i)[1];
            
            // Zufällig nach oben/unten oder rechts/links gehen
            boolean changeXorY = Math.random() < 0.5;
            boolean subtractOrAdd = Math.random() < 0.5;
            if(changeXorY) {
                if(subtractOrAdd)
                    y += 1;
                else
                    y -= 1;
            }else {
                if(subtractOrAdd)
                    x += 1;
                else
                    x -= 1;
            }
            checkPos[0] = x;
            checkPos[1] = y;
        } while(posTaken(checkPos) || x < 0 || x >= 2 * gridSizeX || y < 0 || y >= 2 * gridSizeY); // Solange besetzt oder out of map
        
        return checkPos;
    }
    
    /**
     * Diese Methode berechnet eine optimale, neue Position, mit möglichst wenigen Nachbarn
     * @return Gibt einen Ortsvektor zu der Position zurück
     */
    private int[] bestNewPos() {
        int x = 0, y = 0;
        int[] checkPos = toWorldCoords(zeroVector());
        
        do {
            int iter = 0;
            int i = 0;
            // Wähle eine zufällige Position mit möglichst wenig Nachbarn
            do {
                i = Math.round((float)(Math.random() * (takenPos.size() - 1)));
                iter++;
            } while(numberOfNeighbors(takenPos.get(i)) > 1 && iter < 100);
            x = takenPos.get(i)[0];
            y = takenPos.get(i)[1];
            
            // Zufällig nach oben/unten oder rechts/links gehen
            boolean changeXorY = Math.random() < 0.5;
            boolean subtractOrAdd = Math.random() < 0.5;
            if(changeXorY) {
                if(subtractOrAdd)
                    y += 1;
                else
                    y -= 1;
            }else {
                if(subtractOrAdd)
                    x += 1;
                else
                    x -= 1;
            }
            checkPos[0] = x;
            checkPos[1] = y;
        } while(posTaken(checkPos) || x < 0 || x >= 2 * gridSizeX || y < 0 || y >= 2 * gridSizeY); // Solange besetzt oder out of map
        
        return checkPos;
    }
    
    /**
     * Berechnet die Anzahl der Nachbarn eines bestimmten Raumes
     * @param pos Ortsvektor der zu überprüfenden Position
     * @return Gibt die Anzahl der Nachbarn des Raumes zurück
     */
    private int numberOfNeighbors(int[] pos) {
        int count = 0;

        if(posTaken(rightVector(pos)))
            count++;
        if(posTaken(leftVector(pos)))
            count++;
        if(posTaken(upVector(pos)))
            count++;
        if(posTaken(downVector(pos)))
            count++;
            
        return count;
    }
    
    /**
     * ÃœberprÃ¼ft ob eine bestimmte Position bereits von einem Raum besetzt ist
     * @param pos Ortsvektor der zu Ã¼berprÃ¼fenden Position
     * @return Boolean, ob die Position besetzt ist
     */
    private boolean posTaken(int[] pos) {
        for(int[] i : takenPos) {
            if(i[0] == pos[0] && i[1] == pos[1])
                return true;
        }
        return false;
    }
    
    /**
     * Berechnet einen zufälligen Wert des ROOM_TYPE enums 
     * (nur aus den ersten drei, da nur die ersten drei von dieser Methode vergeben werden dürfen)
     * @return Gibt einen zufälligen ROOM_TYPE zurück
     */
    private Room.ROOM_TYPE pickRandomRoomType() {
        double rand = Math.random() * 100;
        int sum = 0;
        int index = 2; // index = 2 ist ein NORMAL raum  (temp)
        //Wähle einen ROOM_TYPE mit den gegebenen Wahrscheinlichkeiten aus
        for(int i = 0; i < Room.room_probability.length; ++i) {
            sum += Room.room_probability[i];
            if(rand < sum) {
                index = i;
                break;
            }
        }
        return Room.ROOM_TYPE.values()[index];
    }
    
    /**
     * Diese Methode updated alle Tür variablen der Räume um die Verbindung zwischen den Räumen herzustellen
     */
    private void createRoomDoors() {
        for(int x = 0; x < 2 * gridSizeX; ++x) {
            for(int y = 0; y < 2 * gridSizeY; ++y) {
                if(rooms[x][y] == null)
                    continue;
                int[] pos = {x, y};
                // Wenn der nachbarraum existiert: normale Tür;  wenn nicht: keine Tür
                rooms[x][y].doorN = posTaken(upVector(pos)) ? Room.ROOM_TYPE.NORMAL : Room.ROOM_TYPE.NONE;
                rooms[x][y].doorS = posTaken(downVector(pos)) ? Room.ROOM_TYPE.NORMAL : Room.ROOM_TYPE.NONE;
                rooms[x][y].doorW = posTaken(leftVector(pos)) ? Room.ROOM_TYPE.NORMAL : Room.ROOM_TYPE.NONE;
                rooms[x][y].doorE = posTaken(rightVector(pos)) ? Room.ROOM_TYPE.NORMAL : Room.ROOM_TYPE.NONE;
            }
        }
    }

    /**
     * Hier wird der am weitesten entfernte Raum, der weder Spawn, Key noch Boss raum ist, 
     * von der übergebenen pos aus berechnet
     * @param pos Ortsvektor der Startposition
     * @return Ortsvektor zu der berechneten Position
     */
    private int[] getFurthestRoomFrom(int[] pos) {
        double max_dist = 0.0;
        int[] retPos = new int[2];
        for(int x = 0; x < 2 * gridSizeX; ++x) {
            for(int y = 0; y < 2 * gridSizeY; ++y) {
                // Schließt alle Positionen die null sind und Räume die Spawn, key oder Boss sind
                if(rooms[x][y] == null 
                   || rooms[x][y].getType() == Room.ROOM_TYPE.KEY 
                   || rooms[x][y].getType() == Room.ROOM_TYPE.BOSS 
                   || rooms[x][y].getType() == Room.ROOM_TYPE.SPAWN)
                    continue;

                // Distanz mit Pythagoras
                double dist = Math.sqrt(Math.pow(x - pos[0], 2) + Math.pow(y - pos[1], 2));
                if(dist > max_dist) {
                    // Maximale entfernung merken
                    max_dist = dist;
                    retPos[0] = x;
                    retPos[1] = y;
                }    
            }
        }
        return retPos;
    }
    
    /**
     * Hier werden alle Türen zu einem Raumes und der Raum selbst an einer bestimmten Position als bestimmter Typ markiert.
     * @param pos Ortsvektor zu dem Raum
     * @param type ROOM_TYPE mit dem die Türen markiert werden sollen 
     */
    private void markDoors(int[] pos, Room.ROOM_TYPE type) {
        rooms[pos[0]][pos[1]].setType(type);
        if(posTaken(upVector(pos))) {
            rooms[pos[0]][pos[1] - 1].doorS = type;
            rooms[pos[0]][pos[1]].doorN = type;
        }
        if(posTaken(downVector(pos))) {
            rooms[pos[0]][pos[1] + 1].doorN = type;
            rooms[pos[0]][pos[1]].doorS = type;
        }
        if(posTaken(rightVector(pos))) {
            rooms[pos[0] + 1][pos[1]].doorW = type;
            rooms[pos[0]][pos[1]].doorE = type;
        }
        if(posTaken(leftVector(pos))) {
            rooms[pos[0] - 1][pos[1]].doorE = type;
            rooms[pos[0]][pos[1]].doorW = type;
        }
    }
    
    /**
     * Diese Methode speichert eine repräsentation des Raum-graphen als Bild ab
     * @throws IOException
     */
    public void saveMapImg() throws IOException {
        // Nicht die schönste Methode aber sie erfüllt ihren zweck
        int width = gridSizeX * 2 * 9, height = gridSizeY * 2 * 9;

        BufferedImage bi = new BufferedImage(width + 20,height + 65, BufferedImage.TYPE_INT_ARGB);
        Graphics2D ig2 = bi.createGraphics();

        for(int x = 0; x < 2 * gridSizeX; ++x) {
            for(int y = 0; y < 2 * gridSizeY; ++y) {
                if(rooms[x][y] != null) {
                    ig2.setPaint(Color.BLACK);
                    ig2.drawRect(x * 9 + 1, y * 9 + 1, 7, 7);
                    
                    if(x == gridSizeX && y == gridSizeY) {
                        ig2.setPaint(Color.GREEN);
                        ig2.drawRect(x * 9 + 4, y * 9 + 4, 1, 1);
                        ig2.setPaint(Color.BLACK);
                    }
                    
                    if(rooms[x][y].getType() == Room.ROOM_TYPE.BOSS) {
                        ig2.setPaint(Color.RED);
                        ig2.drawRect(x * 9 + 4, y * 9 + 4, 1, 1);
                        ig2.setPaint(Color.BLACK); 
                    }else if(rooms[x][y].getType() == Room.ROOM_TYPE.LOOT) {
                        ig2.setPaint(Color.decode("#34b8ef"));
                        ig2.drawRect(x * 9 + 4, y * 9 + 4, 1, 1);
                        ig2.setPaint(Color.BLACK);
                    }else if(rooms[x][y].getType() == Room.ROOM_TYPE.TRAP) {
                        ig2.setPaint(Color.decode("#6621ce"));
                        ig2.drawRect(x * 9 + 4, y * 9 + 4, 1, 1);
                        ig2.setPaint(Color.BLACK);
                    }else if(rooms[x][y].getType() == Room.ROOM_TYPE.NORMAL) {
                        ig2.setPaint(Color.decode("#787878"));
                        ig2.drawRect(x * 9 + 4, y * 9 + 4, 1, 1);
                        ig2.setPaint(Color.BLACK);
                    }else if(rooms[x][y].getType() == Room.ROOM_TYPE.KEY) {
                        ig2.setPaint(Color.decode("#fac045"));
                        ig2.drawRect(x * 9 + 4, y * 9 + 4, 1, 1);
                        ig2.setPaint(Color.BLACK);
                    }
                    
                    if(rooms[x][y].doorN != Room.ROOM_TYPE.NONE)
                        ig2.drawRect(x * 9 + 4, y * 9, 1, 1);
                    if(rooms[x][y].doorS != Room.ROOM_TYPE.NONE)
                        ig2.drawRect(x * 9 + 4, y * 9 + 9, 1, 1);
                    if(rooms[x][y].doorW != Room.ROOM_TYPE.NONE)
                        ig2.drawRect(x * 9, y * 9 + 4, 1, 1);
                    if(rooms[x][y].doorE != Room.ROOM_TYPE.NONE)
                        ig2.drawRect(x * 9 + 9, y * 9 + 4, 1, 1);
                }
            }
        }
        ig2.setPaint(Color.decode("#787878")); //grau
        ig2.drawString("Normal Room", 12, bi.getHeight() - 50);
        
        ig2.setPaint(Color.decode("#34b8ef")); //hellblau
        ig2.drawString("Loot Room", 12, bi.getHeight() - 40);
        
        ig2.setPaint(Color.decode("#fac045")); //gold
        ig2.drawString("Key Room", 12, bi.getHeight() - 30);
        
        ig2.setPaint(Color.decode("#6621ce")); //lila
        ig2.drawString("Trap Room", 12, bi.getHeight() - 20);
        
        ig2.setPaint(Color.RED);
        ig2.drawString("Boss Room", 12, bi.getHeight() - 10);
        
        ig2.setPaint(Color.GREEN);
        ig2.drawString("Spawn Room", 12, bi.getHeight() - 0);
        
        ImageIO.write(bi, "PNG", new File("map.png"));
    }
    
    /**
     * Gibt die Raummatrix zurück
     * @return 2D Array aller Räume
     */
    public Room[][] getRooms() {
        return rooms;
    }
    
    /**
     * Gibt den gridSize Vektor zurück
     * @return 2DVektor mit der Gridgröße in eine Richtung
     */
    public int[] getGridSize() {
        int[] v = {gridSizeX, gridSizeY};
        return v;
    }
    
    /**
     * Diese Methode geht um f-prozent von a zu b
     * @param a Start-wert
     * @param b End-wert
     * @param f wie nah soll an b angenähert werden. Von f = 0 -> returns a bis f = 1 -> returns b
     * @return Gibt den berechneten Wert als float zurück
     */
    private float lerp(float a, float b, float f) {
        return (a * (1.0f - f)) + (b * f);
    }
    
    /**
     * Gibt den Nullvektor zurück
     * @return Gibt einen Nullvektor zurÃ¼ck
     */
    private int[] zeroVector() {
        int[] v = {0, 0};
        return v;
    }
    
    /**
     * Addiert den Vektor (1, 0) zu dem Ã¼bergebenen Vektor
     * @param Ortsvektor von welchem aus gerechnet werden soll
     * @return Gibt neuen Vektor, der rechts von Vektor v ist, zurück
     */
    private int[] rightVector(int[] v) {
        int[] vRet = {v[0] + 1, v[1]};
        return vRet;
    }
    /**
     * Addiert den Vektor (-1, 0) zu dem Ã¼bergebenen Vektor
     * @param Ortsvektor von welchem aus gerechnet werden soll
     * @return Gibt neuen Vektor, der links von Vektor v ist, zurück
     */
    private int[] leftVector(int[] v) {
        int[] vRet = {v[0] - 1, v[1]};
        return vRet;
    }
    /**
     * Addiert den Vektor (0, -1) zu dem Ã¼bergebenen Vektor
     * @param Ortsvektor von welchem aus gerechnet werden soll
     * @return Gibt neuen Vektor, der über Vektor v ist, zurück
     */
    private int[] upVector(int[] v) {
        int[] vRet = {v[0], v[1] - 1};
        return vRet;
    }
    /**
     * Addiert den Vektor (0, 1) zu dem Ã¼bergebenen Vektor
     * @param Ortsvektor von welchem aus gerechnet werden soll
     * @return Gibt neuen Vektor, der unter Vektor v ist, zurück
     */
    private int[] downVector(int[] v) {
        int[] vRet = {v[0], v[1] + 1};
        return vRet;
    }
    
    /**
     * Transformiert die Ã¼bergebene Position in das Weltkoordinatensystem ((0|0) oben links)
     * @param pos Ortsvektor in Game Coords
     * @return Gibt Ortsvektor pos als World Coords zurück
     */
    public int[] toWorldCoords(int[] pos) {
        int[] v = {pos[0] + gridSizeX, pos[1] + gridSizeY};
        return v;
    }
    /**
     * Transformiert die Ã¼bergebene Position in das Game-koordinatensystem ((0|0) in der Mitte)
     * @param pos Ortsvektor in World Coords
     * @return Gibt Ortsvektor pos als Game Coords zurück
     */
    public int[] toGameCoords(int[] pos) {
        int[] v = {pos[0] - gridSizeX, pos[1] - gridSizeY};
        return v;
    }
}
