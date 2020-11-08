import greenfoot.*;

/**
 * Die Hauptwelt-Klasse DungeonWorld, die die ganze Technik beim generieren der Räume koordiniert
 * @author Alex, Daniel, Sebi
 * @version 26.06.2020
 */
public class DungeonWorld extends World {

    private static DungeonWorld world; // Welt - singleton um zugriff von außen zu bekommen
    private boolean escapeKeyPressed=false;
    private boolean escapeLocked=false;
    
    // GAME_KOORDINATEN des jetzigen Raumes (Punkt (0|0) in der Mitte)
    protected int x, y;

    /**
     * Erstelle eine Instanz der Welt DungeonWorld.
     * Nach erzeugen der Instanz muss noch {@link #setup()} und 
     * {@link #generateLevel(String[] data)} aufgerufen werden.
     */
    public DungeonWorld() {
        super(1000, 640, 1, false);
        setPaintOrder(Healthbar.class, KeyUI.class, Minimap.class, Player.class, Boss.class, EntityTextures.class, Projectile.class);
    }

    /**
     * Setzt die grundsätzlichen Sachen des Raumes wie Singleton und Koordinaten.
     * Getrennt vom Konstruktor, da das nach dem Welt generieren
     * durch den WorldManager ausgeführt werden muss
     */
    public void setup() {
        world = this;

        x = WorldManager.getCoords()[0];
        y = WorldManager.getCoords()[1];

        // Warnung wenn Devmode noch an ist
        if(WorldManager.isInDevMode()) {
            System.out.println("DEV MODE ON");
            System.out.println("X: " + x + " | Y: " + y);
        }
    }

    /**
     * Methode um den Raum zu generieren. 
     * Setzt Hintergründe, Collider, Türen, Props, Items und Gegner
     * @param data String array der spezifischen Daten des Raumes. (room.getData())
     */
    public void generateLevel(String[] data) {
        
        // Starte das Level-lied wenn es nicht eh schon l�uft; Au�er bei Boss r�umen (eigene musik in Klasse RoomGenerator)
        if(!SoundManager.isSongLooping("soundtrack/level.mp3") && !data[4].equals("BOSS")) {
            SoundManager.stopAllLoopingSongs(); // stoppe alle anderen, falls welche spielen
            SoundManager.playSongLoop("soundtrack/level.mp3");
        }
        
        int gridSizeX = WorldManager.getGenerator().getGridSize()[0];
        int gridSizeY = WorldManager.getGenerator().getGridSize()[1];
        Room room = WorldManager.getCurrentRoom();

        switch(data[4]) { // Generiere jeden Raumtypen seperat
            case "LOOT":
            RoomGenerator.generateLootRoom(this, room);
            break;
            case "TRAP":
            RoomGenerator.generateTrapRoom(this, room);
            break;
            case "KEY":
            RoomGenerator.generateKeyRoom(this, room);
            break;
            case "BOSS":
            RoomGenerator.generateBossRoom(this, room);
            break;
            case "NORMAL":
            RoomGenerator.generateNormalRoom(this, room);
            break;
            default: // Spawn 

            GreenfootImage img = new GreenfootImage("environment/rooms/normal/room_normal" + (WorldManager.getEbene() > 4 ? 4 :WorldManager.getEbene()) + ".png");
            img.scale(world.getWidth(), world.getHeight());
            setBackground(img);
        }
        // Raum auf besucht stellen
        room.visited(true);
        // Setze North collider und Tür
        if(!data[0].equals("NONE")) {
            // 2 kurze Collider mit lücke für die Tür
            addObject(new Wall(getWidth() / 2 - 95, 1), 242, 98);
            addObject(new Wall(getWidth() / 2 - 95, 1), getWidth() - 242, 98);
            addObject(new Door(0, data[0]), getWidth() / 2, 63);
        }else
        // langer Collider für die ganze Wand
            addObject(new Wall(getWidth() - 95, 1), getWidth() / 2, 98);

        // Setze East collider und Tür
        if(!data[1].equals("NONE")) {    
            addObject(new Wall(1, getHeight() / 2 - 105), getWidth() - 50, 182);
            addObject(new Wall(1, getHeight() / 2 - 105), getWidth() - 50, getHeight() - 132);
            addObject(new Door(1, data[1]), getWidth() - 33, getHeight() / 2 + 25);
        }else
            addObject(new Wall(1, getHeight() - 100), getWidth() - 50, getHeight() / 2);

        // Setze South collider und Tür
        if(!data[2].equals("NONE")) {    
            addObject(new Wall(getWidth() / 2 - 95, 1), 242, getHeight() - 50);
            addObject(new Wall(getWidth() / 2 - 95, 1), getWidth() - 242, getHeight() - 50);
            addObject(new Door(2, data[2]), getWidth() / 2, getHeight() - 33);
        }else
            addObject(new Wall(getWidth() - 95, 1), getWidth() / 2, getHeight() - 50);

        // Setze West collider und Tür
        if(!data[3].equals("NONE")) {    
            addObject(new Wall(1, getHeight() / 2 - 105), 48, 182);
            addObject(new Wall(1, getHeight() / 2 - 105), 48, getHeight() - 132);
            addObject(new Door(3, data[3]), 33, getHeight() / 2 + 25);
        }else
            addObject(new Wall(1, getHeight() - 100), 48, getHeight() / 2);

        // Spieler setzen data[5] gibt die Position zum Spawnen an
        String[] playerData = data[5].split(";");
        if(playerData[0].equals("NAN")) // Keine Daten in der playerData -> Spieler in der Mitte setzen
            addObject(WorldManager.getPlayer(), getWidth() / 2, getHeight() / 2 + 25);
        else // Daten in der playerData -> Spieler an der gegebenen Position setzen 
            addObject(WorldManager.getPlayer(),Integer.parseInt(playerData[0]), Integer.parseInt(playerData[1]));

        // Setze Healthbar
        addObject(WorldManager.getHealthbar(), 130, 45);
        
        // Generiere und setze Minimap
        generateMinimap();
        addObject(WorldManager.getMinimap(), 910, 75);
        
        // Setze KeyUI
        addObject(WorldManager.getKeyUI(), 822, 43);
    }

    /**
     *  Diese Methode wird jeden Tick aufgerufen und schaut ob der Spieler out of map ist. 
     *  Der Spieler wird in den nächsten Raum gesetzt sobald er außerhalb des Raums ist.
     *  
     *  Au�erdem wird hier bestimmt ob T�ren auf oder zu sind, ob der Spieler sterben soll
     *  und ob das PauseMen� kommen soll
     */
    public void act() {
        // Teleportiere den Spieler in den nächsten Raum sobald er out of map ist (bzw die Tür offen ist)
        Player player = WorldManager.getPlayer();
        if(player.getY() < 95) { //nach Norden
            String playerData = getWidth() / 2 + ";555";
            loadNewRoom(0, -1, playerData);
        }else if(player.getY() > 590) { //nach Süden
            String playerData = getWidth() / 2 + ";132";
            loadNewRoom(0, 1, playerData);
        }else if(player.getX() < 50) { //nach Westen
            String playerData = "935;" + (getHeight() / 2 + 25);
            loadNewRoom(-1, 0, playerData);
        }else if(player.getX() > 950) { //nach Osten
            String playerData = "65;" + (getHeight() / 2 + 25);
            loadNewRoom(1, 0, playerData);
        }

        // Autor: Daniel
        // Schlie�e die T�ren sobald Gegner oder Bosse da sind
        if(getObjects(Enemy.class).size() > 0 || getObjects(Boss.class).size() > 0){
            lockRoom(true);
        }
        else{
            lockRoom(false);
        }
        // T�te den Spieler wenn er 0 Leben hat (und Dev Mode nicht an ist)
        playerDeath();
        
        //PauseMenu wird aufgerufen 
        //@author Anja
        
        if(Greenfoot.isKeyDown("escape")) {
            if(!escapeKeyPressed) {
                Greenfoot.setWorld(new PauseMenu(this,true));
                escapeKeyPressed = true;
            }
        }else
            escapeKeyPressed = false;
            
        if(escapeLocked)
            escapeKeyPressed = true;
            
        
    }

    /**
     * Schließt oder Öffnet alle Türen im Raum
     */
    public void lockRoom(boolean lock) {
        for(Actor a : getObjects(Door.class)) 
            ((Door)a).setLocked(lock);
    }

    /**
     * Aktiviert den Raum der an der Position (dx, dy) relativ zu dem jetzigen Raum ist.
     * @param dx x-Versetzung des zu ladenden Raumes
     * @param dy y-Versetzung des zu ladenden Raumes
     * @param playerData Spieler position, an der der Spieler im neuem Raum stehen soll
     */
    private void loadNewRoom(int dx, int dy, String playerData) {
        // Raum laden
        String[] data = WorldManager.loadRoomAt(x + dx, y + dy);
        // Spieler pos hinzuf�gen
        data[5] = playerData;
        // WorldManager updaten und Welt setzen
        WorldManager.setCoords(x + dx, y + dy);
        Greenfoot.setWorld(new RoomWorld(data));
    }

    /**
     *  Getter für den Singleton
     *  @return Gibt die jetzige Instanz der DungeonWorld zurück
     */ 
    public static DungeonWorld getWorld() {
        return world;
    }
    /**
     * Überprüft, ob der Spieler stirbt
     * Autor: Daniel
     */
    public void playerDeath(){
        // Spieler kann im Dev mode nicht sterben
        if( WorldManager.getPlayer().getHealth() < 1 && !WorldManager.isInDevMode()){
            //--Sounds----------------
            // Autor: Alex
            SoundManager.stopAllLoopingSongs();
            SoundManager.playSound("soundtrack/death.mp3", SoundManager.getBackMusicVol());
            //----------------------
            
            //�ffnet das DeathMenu
            //Author Anja
            Greenfoot.setWorld(new DeathMenu(true));
        }

    }

    /**
     * Generiert eine 3x3 Minimap, die den aktuellen und umliegende Räume anzeigt.
     * Autor: Sebi
     */
    public void generateMinimap(){
        Room [][] map= WorldManager.getGenerator().getRooms();
        //Bild mit Maßen-Hintergrund
        GreenfootImage img= new GreenfootImage(3*50, 3*50);
        img.setColor(new Color(150,150,150,100));
        img.fill();
        //Abfragen der Weltkoordinaten des aktuellen Raumes
        int[] coords= WorldManager.getGenerator().toWorldCoords(WorldManager.getCoords());
        //Geht die Räume um den aktuellen ab
        for(int xi=-1; xi<=1; xi++){
            for (int yi=-1; yi<=1; yi++){
                //Verhindern eines Abfragens einer nichtexistenten Raumkoordinate
                if(coords[0]+xi >= 0 && coords[0]+xi < 2*WorldManager.getGenerator().getGridSize()[0]) {
                    if(coords[1]+yi >= 0 && coords[1]+yi < 2*WorldManager.getGenerator().getGridSize()[1]) {
                        Room room= map[coords[0]+xi][coords[1]+yi];
                        //Auswählen der richtigen Bilddatei, Zeichnen
                        if (room!=null && room.isVisited()){
                            if(xi == 0 && yi == 0) {
                                img.setColor(new Color(247, 208, 108, 179));
                                img.fillRect(((xi+1)*50) + 6, ((yi+1)*50) + 6, 38, 38);
                            }
                            
                            String path= "gui/minimap/minimap_room_";
                            if(room.doorN!=Room.ROOM_TYPE.NONE){path=path + "o";}
                            if(room.doorS!=Room.ROOM_TYPE.NONE){path=path + "u";}
                            if(room.doorE!=Room.ROOM_TYPE.NONE){path=path + "r";}
                            if(room.doorW!=Room.ROOM_TYPE.NONE){path=path + "l";}
                            img.drawImage(new GreenfootImage(path+".png"), (xi+1)*50, (yi+1)*50);
                        }
                    }
                }
            }
        }
  
        WorldManager.getMinimap().updateImage(img);
    }
    
    
}

