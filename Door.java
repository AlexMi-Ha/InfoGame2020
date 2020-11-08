
import greenfoot.*;

/**
 * Diese Klasse regelt das Türverhalten und das Verschließen der Tür
 * @author Alex, Daniel
 * @version 24.5.2020
 */
public class Door extends ProgressionDoor {

    // Gibt an, in welche Richtung die Tür zeigt. 0: N; 1: E; 2:S; 3:W
    private int facing;
    // ROOM_TYPE was für eine Tür textur gewollt ist
    private String doorType;
    // Attribute für den Verlauf der Animation
    private int animIndex= 0;
    private int timer= 0;
    // aktueller Zustand(öffnen, schließen, nicht) der Animation
    private String animationState= "NONE";
    
    /**
     * Erzeugt eine Tür und setzt ihr Bild
     * @param facing Blickrichtung der Tür 0: N; 1: E; 2:S; 3:W
     * @param doorType Typ der Tür, Room.ROOM_TYPE.toString()
     */
    public Door(int facing, String doorType) {
        this.facing = facing;
        this.doorType = doorType;
        setImage(createImage(false, 0, this.doorType)); // offene Tür textur laden
    }

    /**
     * (Author: Daniel)
     */
    public void act(){
        if(animationState.equals("LOCK")){ //Überpüft, ob die Schließanimation spielt und setzt sie dementsprechend fort
            lockAnimation();
        }
        else if(animationState.equals("UNLOCK")){ //Überpüft, ob die Öffnungsanimation spielt und setzt sie dementsprechend fort
            unlockAnimation();
        }
    }

    /**
     * Diese Methode wird aufgerufen sobald der Actor zu einer Welt hinzugefügt wurde.
     * Sie regelt ob die Tür dauerhaft verschlossen sein soll oder nicht
     */
    public void addedToWorld(World world) {
        if(doorType.equals("BOSS") && !WorldManager.getPlayer().hasKey()) { // Ist es eine Boss tür und hat der Spieler keinen Schlüssel?
            setLocked(true); // Collider setzen
        } else {

            locked = false; // offen lassen
        }
    }

    /**
     * Regelt das VerschlieÃŸen der TÃ¼r. Beim SchlieÃŸen setzt es Collider und beim Ã–ffnen wird der Collider wieder entfernt
     * @param lock Ist die Tür verschlossen?
     */
    public void setLocked(boolean lock) {
        if(locked == lock) // Ignoriere mehrere, unnütze Aufrufe der Methode
            return;
        if(!lock && !WorldManager.getPlayer().hasKey() && doorType.equals("BOSS")) // Sperr nicht auf wenn der Spieler den Schlüssel der Bosstür nicht hat
            return;
        locked = lock;
        if(locked) {
            switch(facing) {
                case 0:
                DungeonWorld.getWorld().addObject(new DoorCollider(getImage().getWidth(), 1), getX(), getY() + 35);
                break;
                case 1:
                DungeonWorld.getWorld().addObject(new DoorCollider(1, getImage().getHeight()), getX() - 17, getY());
                break;
                case 2:
                DungeonWorld.getWorld().addObject(new DoorCollider(getImage().getWidth(), 1), getX(), getY() - 17);
                break;
                case 3:
                DungeonWorld.getWorld().addObject(new DoorCollider(1, getImage().getHeight()), getX() + 15, getY());
                break;
            }
            animationState= "LOCK";
        }else
            animationState= "UNLOCK";      
    }

    /**
     * LÃ¤dt eine gewollte Textur
     * @param locked Ist die TÃ¼r verschlossen?
     * @param lockState In welchem Animations zyklus ist die TÃ¼r
     * @param doorType Ist es eine BosstÃ¼r, eine KeytÃ¼r oder eine Normale
     * @return Gibt das gewollte Bild als GreenfootImage, richtig gescaled zurÃ¼ck
     */
    public GreenfootImage createImage(boolean locked, int lockState, String doorType) {
        String lock = locked ? ("_locked_" + lockState) : "";
        String folder = "normal/";
        if(doorType.equals("BOSS")) folder = "boss/";
        else if(doorType.equals("KEY")) folder = "key/";
        GreenfootImage img;
        int width = DungeonWorld.getWorld().getWidth();
        int height = DungeonWorld.getWorld().getHeight();
        switch(facing) {
            case 0:
            img = new GreenfootImage("environment/doors/top/"+folder+"top"+lock+".png");
            img.scale((int)(0.136 * width), (int)(0.17 * height));
            break;
            case 1:
            img = new GreenfootImage("environment/doors/right/"+folder+"right"+lock+".png");
            img.scale((int)(0.058 * width), (int)(0.2125 * height));
            break;
            case 2:
            img = new GreenfootImage("environment/doors/bottom/"+folder+"bottom"+lock+".png");
            img.scale((int)(0.136 * width), (int)(0.091 * height));
            break;
            case 3:
            img = new GreenfootImage("environment/doors/right/"+folder+"right"+lock+".png");
            img.scale((int)(0.058 * width), (int)(0.2125 * height));
            img.mirrorHorizontally();
            break;

            default:
            img = new GreenfootImage(60, 60);
            img.setColor(Color.BLUE);
            img.fill();
            break;
        }
        return img;
    }

    /**
     * Animiert den Schließvorgang der Türe
     * (Autor: Daniel)
     */
    private void lockAnimation(){
        timer++;
        if( timer > 6 ){ //Geschwindigkeit der Animation
            setImage(createImage( true, animIndex, doorType));
            animIndex++;
            timer= 0;
            if(animIndex > 4 ){ //Animation hat 5 Bilder
                animIndex = 0;
                animationState= "NONE"; //Beendet den Zustand der Animation
            }
        }
    }

    /**
     * Animiert den Öffnungsvorgang der Türe
     * (Autor: Daniel)
     */    
    private void unlockAnimation(){
        timer++;
        if( timer > 8 ){
            int i= 0;
            if(animIndex == 0){
                i= 4;
            } 
            else if(animIndex == 1){
                i= 1;
            }
            else if(animIndex == 2){
                i=0;
            }
            else{
                i=-1;
            }
            setImage(createImage( i==-1 ? false : true , i, doorType));
            animIndex++;
            timer= 0;
            if(animIndex > 4){
                animIndex = 0;
                animationState= "NONE"; // Beendet den Status der Animation
                DungeonWorld.getWorld().removeObject(getOneIntersectingObject(DoorCollider.class)); //Öffnet die Tür (entfernt Collider)
            }
        }

    }
}
