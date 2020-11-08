import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Klasse für das Menü, das angezeigt wird, wenn das Spiel gestartet wird.
 * Dient als Startbildschirm
 * 
 * @author Anja, Alex 
 * @version 09.06.2020
 */
public class StartMenu extends World
{

    private boolean keyPressed = true;     //wird gerade eine taste gedrückt?
    private boolean keysLocked = false;     //ist die tasteneingabe gerade gesperrt?

    private MenuButton play, controls, credits; //alle Buttons, die in dieser Welt existieren

    private static StartMenu world;  //world singleton für den zugriff von außen

    private static boolean story = true; //Soll die Story am Anfang angezeigt werden?
    /**
     *Standard Konstruktor zum Spielstart, versucht erst Story abzuspielen
     */
    public StartMenu(){
        super(1000, 640, 1, false); 
        world=this; //belegt world singleton

        GreenfootImage img= new GreenfootImage("menu/start/background.png");
        img.scale(1000, 640);
        img.setColor(Color.WHITE);
        img.setFont(new Font(35));
        img.drawString(Integer.toString(DataManager.read()), 220, 30);
        setBackground(img);

        play = new MenuButton("play", true); //am Anfang ist der obere Button schon gedrückt
        controls = new MenuButton("controls", false);
        credits = new MenuButton("credits", false);

        addObject(play, 505, 350);
        addObject(controls, 505, 450);
        addObject(credits, 505 , 545);

        //---------------------------------------------------------------
        // Starte das Menü-Lied wenn es nicht eh schon spielt
        // Autor: Alex
        if(!SoundManager.isSongLooping("soundtrack/title_screen.mp3")) {
            SoundManager.stopAllLoopingSongs(); // Stoppe außerdem alles andere
            SoundManager.playSongLoop("soundtrack/title_screen.mp3");
        }
        
        //---------------------------------------------------------------

        if(story) {  //Spielt Story nur ab wenn sie noch auf true gesetzt ist
            Greenfoot.setWorld(new StoryScreen());

        }
    }

    /**
     * Navigierung durch die Menüpunkte mit den Pfeiltasten und Enter
     */
    public void act(){       
        if(Greenfoot.isKeyDown("up")){//wechselt zwischen den Buttons
            if(!keyPressed&&!keysLocked){
                if(play.isSelected()){
                    play.setSelected(false);
                    controls.setSelected(false);
                    credits.setSelected(true);
                }
                else if(controls.isSelected()){
                    play.setSelected(true);
                    controls.setSelected(false);
                    credits.setSelected(false);
                }
                else if(credits.isSelected()){
                    play.setSelected(false);
                    controls.setSelected(true);
                    credits.setSelected(false);
                }
                //Damit Aktion  beim nächsten mal wegen dem if-Statement nicht mehr ausgeführt wird
                keyPressed=true; 
            }

        }
        else if(Greenfoot.isKeyDown("down")){//wechselt zwischen den Buttons
            if(!keyPressed&&!keysLocked){
                if(play.isSelected()){
                    play.setSelected(false);
                    controls.setSelected(true);
                    credits.setSelected(false);
                }
                else if(controls.isSelected()){
                    play.setSelected(false);
                    controls.setSelected(false);
                    credits.setSelected(true);
                }
                else if(credits.isSelected()){
                    play.setSelected(true);
                    controls.setSelected(false);
                    credits.setSelected(false);
                }
                //Damit Aktion  beim nächsten mal wegen dem if-Statement nicht mehr ausgeführt wird
                keyPressed=true;
            }

        }
        else if(Greenfoot.isKeyDown("enter")){ //Auswahl wird "eingeloggt"
            if(!keyPressed && !keysLocked) {
                if(play.isSelected()) {
                    
                    // Title Music stoppen
                    // Autor: Alex
                    SoundManager.stopAllLoopingSongs();
                    //------------------------
                    
                    Greenfoot.setWorld(new RoomWorld());//neues Spiel wird erzeugt
                }
                else if(controls.isSelected()) {
                    //Control Panel wird  angezeigt und Navigierung durch Tasten gesperrt
                    addObject(new ControlPanel(this), getWidth() / 2, getHeight() / 2);
                    keysLocked = true; 
                }
                else if(credits.isSelected()) {
                    //Credits Panel wird  angezeigt und Navigierung durch Tasten gesperrt
                    addObject(new CreditPanel(), getWidth() / 2, getHeight() / 2);
                    keysLocked = true; 
                }
                //Damit Aktion  beim nächsten mal wegen dem if-Statement nicht mehr ausgeführt wird
                keyPressed = true;
            }
        }
        else{
            keyPressed=false; //keine der Tasten wurde gedrückt
        }

    }

    /**
     * legt fest, ob die Navigierung durch die Tasten gerade gesperrt ist
     */
    public void setKeyLock(boolean keysLocked) {
        this.keysLocked = keysLocked;
    } 

    /**
     * getWorld()-method für StartMenu
     */
    public static StartMenu getWorld(){
        return world;
    }

    public static void setStory(boolean s){
        story=s;
    }
}