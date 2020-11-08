
import greenfoot.*;
/**
 * Hier werden Methoden zur verfügung gestellt, die die verschiedenen Raumtypen seperat generieren und laden
 * @author Daniel, Alex
 * @version 25.6.2020
 */
public class RoomGenerator {
    /**
     * Generiert den bestimmten Raum und lädt dessen Textur, geht auf mögliche Ereignisse ein
     */
    public static void generateLootRoom(DungeonWorld world, Room room) {
        world.setBackground(loadTextures("loot", WorldManager.getEbene()));
        //platziert Kiste
        world.addObject(new Chest(room.triggeredRoomEvent(), "LOOT"), DungeonWorld.getWorld().getWidth()/2, DungeonWorld.getWorld().getHeight()/2  + 25);
    }

    public static void generateNormalRoom(DungeonWorld world, Room room) {
        world.setBackground(loadTextures("normal", WorldManager.getEbene()));       
        int[] roomRandoms= room.getRoomRandoms();
        //berücksichtigt die Skalierung des Bildes
        int scaling= 60;
        //berücksichtigt die Wände, schließlich werden Objekte nur auf dem Boden platziert
        int xOffset=50 + scaling/ 2; 
        int yOffset= 100 + scaling/2;
        
        //im Folgenden sind aus Bilder (einzelne rote (=Gegner) und schwarze Pixel (=Steine) wurden in einem Bild eingef�gt, das das Format des Raumes hat)
        //generierte Platzierungen von Steinen / Gegnern aufgelistet.
        switch(room.getRoomTypeID()){
            case 0:
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 2* scaling + xOffset, 3* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 2* scaling + xOffset, 4* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 4* scaling + xOffset, 1* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 4* scaling + xOffset, 6* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 5* scaling + xOffset, 1* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 5* scaling + xOffset, 6* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 6* scaling + xOffset, 1* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 6* scaling + xOffset, 6* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 7* scaling + xOffset, 1* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 7* scaling + xOffset, 6* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 8* scaling + xOffset, 1* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 8* scaling + xOffset, 6* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 9* scaling + xOffset, 1* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 9* scaling + xOffset, 6* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 10* scaling + xOffset, 1* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 10* scaling + xOffset, 6* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 12* scaling + xOffset, 3* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 12* scaling + xOffset, 4* scaling + yOffset);
            break;
            case 1:
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 3* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 3* scaling + xOffset, 5* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 5* scaling + xOffset, 0* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 5* scaling + xOffset, 7* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 7* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 7* scaling + xOffset, 5* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 9* scaling + xOffset, 0* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 9* scaling + xOffset, 7* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 11* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 11* scaling + xOffset, 5* scaling + yOffset);
            break;
            case 2:
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 3* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 3* scaling + xOffset, 3* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 3* scaling + xOffset, 5* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 4* scaling + xOffset, 6* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 5* scaling + xOffset, 6* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 6* scaling + xOffset, 6* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 7* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 7* scaling + xOffset, 4* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 7* scaling + xOffset, 5* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 8* scaling + xOffset, 1* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 9* scaling + xOffset, 1* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 11* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 11* scaling + xOffset, 3* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 11* scaling + xOffset, 4* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 11* scaling + xOffset, 5* scaling + yOffset);
            break;
            case 3:
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 2* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 2* scaling + xOffset, 3* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 4* scaling + xOffset, 4* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 4* scaling + xOffset, 5* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 6* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 6* scaling + xOffset, 3* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 8* scaling + xOffset, 4* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 8* scaling + xOffset, 5* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 10* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 10* scaling + xOffset, 3* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 12* scaling + xOffset, 4* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 12* scaling + xOffset, 5* scaling + yOffset);
            break;
            case 4:
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 2* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 2* scaling + xOffset, 3* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 2* scaling + xOffset, 4* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 2* scaling + xOffset, 5* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 3* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 3* scaling + xOffset, 5* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 4* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 4* scaling + xOffset, 5* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 7* scaling + xOffset, 1* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 7* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 7* scaling + xOffset, 3* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 7* scaling + xOffset, 4* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 7* scaling + xOffset, 5* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 7* scaling + xOffset, 6* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 10* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 10* scaling + xOffset, 5* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[2]/100 * 4)), 11* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[3]/100 * 4)), 11* scaling + xOffset, 5* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[0]/100 * 4)), 12* scaling + xOffset, 2* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 12* scaling + xOffset, 3* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 12* scaling + xOffset, 4* scaling + yOffset);
            world.addObject(new Rock ((int)((double)roomRandoms[1]/100 * 4)), 12* scaling + xOffset, 5* scaling + yOffset);
            break;
            default:
        }
        if(!room.triggeredRoomEvent()) {
            switch(room.getRoomTypeID()){
                case 0:
                world.addObject(new Heavy(), 6* scaling + xOffset, 3* scaling + yOffset);
                world.addObject(new Bird(), 6* scaling + xOffset, 4* scaling + yOffset);
                world.addObject(new Bird(), 7* scaling + xOffset, 3* scaling + yOffset);
                world.addObject(new Archer(), 7* scaling + xOffset, 4* scaling + yOffset);
                world.addObject(new Archer(), 8* scaling + xOffset, 3* scaling + yOffset);
                world.addObject(new Heavy(), 8* scaling + xOffset, 4* scaling + yOffset);
                break;
                case 1:
                world.addObject(new Heavy(), 1* scaling + xOffset, 6* scaling + yOffset);
                world.addObject(new Archer(), 5* scaling + xOffset, 2* scaling + yOffset);
                world.addObject(new Heavy(), 5* scaling + xOffset, 5* scaling + yOffset);
                world.addObject(new Archer(), 9* scaling + xOffset, 2* scaling + yOffset);
                world.addObject(new Bird(), 9* scaling + xOffset, 5* scaling + yOffset);
                world.addObject(new Bird(), 13* scaling + xOffset, 1* scaling + yOffset);
                break;
                case 2:
                world.addObject(new Bird(), 5* scaling + xOffset, 1* scaling + yOffset);
                world.addObject(new Heavy(), 5* scaling + xOffset, 4* scaling + yOffset);
                world.addObject(new Archer(), 9* scaling + xOffset, 3* scaling + yOffset);
                world.addObject(new Heavy(), 9* scaling + xOffset, 6* scaling + yOffset);

                break;
                case 3:
                world.addObject(new Heavy(), 2* scaling + xOffset, 5* scaling + yOffset);
                world.addObject(new Heavy(), 4* scaling + xOffset, 2* scaling + yOffset);
                world.addObject(new Archer(), 6* scaling + xOffset, 5* scaling + yOffset);
                world.addObject(new Archer(), 8* scaling + xOffset, 2* scaling + yOffset);
                world.addObject(new Bird(), 10* scaling + xOffset, 5* scaling + yOffset);
                world.addObject(new Heavy(), 12* scaling + xOffset, 2* scaling + yOffset);
                break;
                case 4:
                world.addObject(new Bird(), 6* scaling + xOffset, 2* scaling + yOffset);
                world.addObject(new Bird(), 6* scaling + xOffset, 5* scaling + yOffset);
                world.addObject(new Heavy(), 8* scaling + xOffset, 2* scaling + yOffset);
                world.addObject(new Archer(), 8* scaling + xOffset, 5* scaling + yOffset);
                break;
                default:
            }
        }
        room.setRoomEventTriggerd(true);
    }

    public static void generateTrapRoom(DungeonWorld world, Room room) {
        world.setBackground(loadTextures("normal", WorldManager.getEbene()));
        //platziert Kiste
        world.addObject(new Chest(room.triggeredRoomEvent(), "TRAP"), DungeonWorld.getWorld().getWidth()/2, DungeonWorld.getWorld().getHeight()/2  + 25);
    }

    public static void generateKeyRoom(DungeonWorld world, Room room) {
        world.setBackground(loadTextures("key", WorldManager.getEbene()));
        if(!WorldManager.getPlayer().hasKey()) {
            world.addObject(new Key(), world.getWidth() / 2, world.getHeight() / 2 + 5);
        }
    }

    public static void generateBossRoom(DungeonWorld world, Room room) {
        world.setBackground(loadTextures("boss", WorldManager.getEbene()));
        if(!room.triggeredRoomEvent()) {
            int rand = Greenfoot.getRandomNumber(3);
            if(rand == 0) {
                world.addObject(new AlhoonBoss(), world.getWidth() / 2, world.getHeight() / 2 + 25);
                
                // Boss musik abspielen
                if(!SoundManager.isSongLooping("soundtrack/knddl/alhoon_boss.mp3")) {
                    SoundManager.stopAllLoopingSongs(); // stoppe alle anderen, falls welche spielen
                    SoundManager.playSongLoop("soundtrack/knddl/alhoon_boss.mp3");
                }   
            }else if(rand == 1) {
                world.addObject(new Demogorgon(), world.getWidth() / 2, world.getHeight() / 2 + 25);
                
                // Boss musik abspielen
                if(!SoundManager.isSongLooping("soundtrack/knddl/demogorgon_boss.mp3")) {
                    SoundManager.stopAllLoopingSongs(); // stoppe alle anderen, falls welche spielen
                    SoundManager.playSongLoop("soundtrack/knddl/demogorgon_boss.mp3");
                }  
            }else {
                world.addObject(new HogBoss(), world.getWidth() / 2, world.getHeight() / 2 + 25);
                
                // Boss musik abspielen
                if(!SoundManager.isSongLooping("soundtrack/knddl/hog_boss.mp3")) {
                    SoundManager.stopAllLoopingSongs(); // stoppe alle anderen, falls welche spielen
                    SoundManager.playSongLoop("soundtrack/knddl/hog_boss.mp3");
                }  
            }
        }else
            world.addObject(new Trapdoor(), world.getWidth() / 2, world.getHeight() / 2 + 25);
    }

    /**
     * Lädt die Textur des bestimmten Raums
     * @param room Beschreibt Art des Raums
     * @param ebene Beschreibt aktuelle Ebene des Spielers, sodass sich diese nach Ebene anpassen
     * @return Gibt die Textur des bestimmten Raums auf der Ebene zurück
     */
    private static GreenfootImage loadTextures(String room, int ebene){
        if(ebene > 4){ ebene = 4; }

        if(!room.equals("boss")){
            GreenfootImage img= new GreenfootImage("environment/rooms/" + room + "/" + "room_" + room +ebene + ".png");
            img.scale(DungeonWorld.getWorld().getWidth(), DungeonWorld.getWorld().getHeight());
            return img;
        }
        else{
            GreenfootImage img= new GreenfootImage("environment/rooms/boss/room_" + room + ".png");
            img.scale(DungeonWorld.getWorld().getWidth(), DungeonWorld.getWorld().getHeight());
            return img;
        }
    }

    /**
     * Geniert den Code für die Struktur eines normalen Raumes im Bezug auf Steine- und Gegner- Platzierungen (=> Ausgabe in Konsole)
     */
    public static void generateNormalRoomCode(int number){
        GreenfootImage img= new GreenfootImage("images/environment/maps/mapEditor" + number + ".png"); //lädt mapEditor-Bild 
        //erzeugt Strings für die Codes der Platzierung von Gegnern und Umgebung
        String enemyCode=""; 
        String environmentCode="";
        //durchläuft das Bild systematisch und "notiert" sich, wo farbige Pixel sind => platziert Objekt dementsprechend an der Stelle
        for(int x = 0; x < img.getWidth(); ++x){
            for(int y= 0; y <img.getHeight(); ++y){
                if(img.getColorAt(x, y).equals(Color.RED)){
                    enemyCode= enemyCode + "\n" + "world.addObject(new Enemy(), " + x + "* scaling + xOffset, " + y +"* scaling + yOffset);"; 
                }
                else if(img.getColorAt(x,y).equals(Color.BLACK)){
                    environmentCode= environmentCode + "\n" + "world.addObject(new Rock ((int)((double)roomRandoms[" + (int) (Math.random()*4) +"]/100 * 4)), " + x + "* scaling + xOffset, " + y +"* scaling + yOffset);"; 
                }
            }
        }
        System.out.println(enemyCode + "\n" + environmentCode); //gibt Code final in der Konsole aus
    }
}
