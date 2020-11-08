import greenfoot.*;

/**
 * Klasse für die Falltür, die nach einem Boss spawnt, um in die nächste Ebene / ins nächste Level zu kommen
 * @author Alex, Daniel
 * @version 24.5.2020
 */
public class Trapdoor extends ProgressionDoor {
    private int animIndex= 0;
    private int timer= 0;
    private boolean isAnimationRunning= false;
    
    /**
     * Erzeugt eine neue Falltür
     */
    public Trapdoor() {
        // Anfangs immer verschlossen
        locked = true;

        setImage(createImage(true, 4));
    }
    
    /**
     * Setzt den lock-Status der Falltür
     * @param lock Ist die Falltür verschlossen?
     */
    public void setLocked(boolean lock) {
        locked = lock;
    }

    /**
     * In dieser act Methode wird das interaktions Verhalten der Falltür geregelt
     * Spieler auf der Falltür muss e drücken um das neue Level zu laden
     */
    public void act() {
        if(getOneIntersectingObject(Player.class) != null && Greenfoot.isKeyDown("e") && locked) {
            locked = false;
            isAnimationRunning= true;
            SoundManager.playSound("sounds/new_level_enter.wav", SoundManager.getSFXVol());
        }
        if(isAnimationRunning) {
            animation();
        }
    }

    /**
     * LÃ¤dt die gewollte Textur
     * @param locked Ist die FalltÃ¼r verschlossen?
     * @param lockState In welchem Animations zyklus ist die FalltÃ¼r
     * @return Gibt das gewollte Bild als GreenfootImage, richtig gescaled zurÃ¼ck
     */
    public GreenfootImage createImage(boolean locked, int lockState) {

        GreenfootImage img = new GreenfootImage("environment/doors/trapdoor/trapdoor" + animIndex + ".png");
        img.scale((int)(img.getWidth() * 4), 
            (int)(img.getHeight() * 4));

        return img;
    }

    /**
     * Lädt die neue Ebene
     */
    private void loadNewLevel(){
        // Sound abspielen
        SoundManager.playSound("sounds/new_level_entered.wav", SoundManager.getSFXVol());
        // Neues Level generieren
        WorldManager.generateNewFloor();
        // Spawn raum laden
        String[] data = WorldManager.loadRoomAt(0, 0);
        RoomWorld world = new RoomWorld(data);
        Greenfoot.setWorld(world);
    }
    
    /**
     * Animiert das Öffnen der Falltüre nach dem Drücken von E, Laden des neuen Levels
     * (Author: Daniel)
     */
    private void animation(){
        timer++;
        if( timer > 12 ){
            setImage(createImage(locked, animIndex));
            animIndex++;
            timer= 0;
            if(animIndex > 4 ){
                animIndex = 0;
                isAnimationRunning = false;
                loadNewLevel();
            }
        }
    }
}
