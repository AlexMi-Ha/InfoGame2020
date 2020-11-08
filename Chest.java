import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Klasse für die Truhe, die in Loot-& Fallenräumen steht.
 * @author Daniel
 * @version 22.6.2020
 */
public class Chest extends Collider
{   
    //Werte für die Animation
    private int animIndex= 0;
    private int timer= 0;
    private boolean isAnimationRunning= false;
    
    private boolean isOpened= false; //bestimmt aktuellen Status der Truhe
    private String room; //bestimmt den Raum, in dem die Truhe steht
    
    //siehe Gegnerplatzierung in RoomGenerator
    private int scaling;
    private int xOffset;
    private int yOffset;
    public Chest(boolean open, String room) {
        super(true);
        this.isOpened= open;
        this.room=room;
        
        //Attribute zum Erzeugen der Gegner bzw Positionieren (s. Raumgeneration)
        scaling= 60;
        xOffset=50 + scaling/ 2; 
        yOffset= 100 + scaling/2;
        
        GreenfootImage img;
        //Textur berücksichtigt aktuellen Status des Öffnens, auch beim neuen Betreten eines Raumes
        if(isOpened){
            img = new GreenfootImage("images/environment/misc/chest/" + room +"/chest3.png");
        }
        else{
            img = new GreenfootImage("images/environment/misc/chest/" + room +"/chest0.png");
        }
        img.scale( (int)(img.getWidth()* 3.3), (int)(img.getHeight()* 3.3));
        setImage(img);        
    }

    public void act() {
        //Wenn der Spieler in Reichweite der Truhe steht und "E" zum Interagieren drückt.
        if(!getObjectsInRange(100, Player.class).isEmpty() && Greenfoot.isKeyDown("e") && !isAnimationRunning && !isOpened) {
            isAnimationRunning= true;
            SoundManager.playSound("sounds/chest_open.wav", SoundManager.getSFXVol());
        }
        //Wenn erstes if erfüllt ist, wird die Animation des Öffnens abgespielt.
        if(isAnimationRunning && !isOpened) {
            animation();
        }
    }   

    /** 
     * Spielt die Animation vom �ffnen der Truhe ab
     */
    private void animation(){
        timer++;
        if( timer > 12 ){
            setImage(createImage());
            animIndex++;
            timer= 0;
            if(animIndex > 3 ){
                animIndex = 0;
                isAnimationRunning = false;
                isOpened= true;
                event();
            }
        }
    }
    
    /**
     * @ Lädt & gibt die Textur aus 
     */
    public GreenfootImage createImage() {
        GreenfootImage img;
        img = new GreenfootImage("environment/misc/chest/"+ room +"/chest" + animIndex + ".png");
        img.scale((int)(img.getWidth()* 3.3), (int)(img.getHeight()* 3.3));
        return img;
    }
    
    /**
     * Löst das Event nach der Animation aus:
     *  Loot: droppt ein Herz-Item
     *  Falle: droppt entweder Herz ODER spawnt Gegner
     */
    private void event(){
        if(room.equals("TRAP")){
            WorldManager.getCurrentRoom().setRoomEventTriggerd(true);
        }
        if(room.equals("TRAP") && Math.random() < 0.7 ){    //ermittelt, welches Event ausgew�hlt wird
            SoundManager.playSound("sounds/chest_unlucky.wav", SoundManager.getSFXVol());
            //Gegner spawnen
            DungeonWorld.getWorld().addObject(new Bird(), 6* scaling + xOffset, 2* scaling + yOffset);
            DungeonWorld.getWorld().addObject(new Bird(), 6* scaling + xOffset, 5* scaling + yOffset);
            DungeonWorld.getWorld().addObject(new Heavy(), 8* scaling + xOffset, 2* scaling + yOffset);
            DungeonWorld.getWorld().addObject(new Archer(), 8* scaling + xOffset, 5* scaling + yOffset);
        }
        else{
            //Herz fallen lassen
            SoundManager.playSound("sounds/chest_lucky.wav", SoundManager.getSFXVol());
            DungeonWorld.getWorld().addObject(new Heart(), getX(), getY()+70);
        }
    }
}
