import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Klasse f�r das Men�, das angezeigt wird, wenn der Player die ESC-Taste dr�ckt und somit das Spiel
 * pausiert
 * 
 * @author Anja 
 * @version 08.06.2020
 */
public class PauseMenu extends World
{

    
    private boolean keyPressed = false;     //wird gerade eine taste gedr�ckt?
    private boolean keysLocked = false;     //ist die tasteneingabe gerade gesperrt?

    private MenuButton controls, quit;  //alle Buttons, die in dieser Welt existieren
    
    private static PauseMenu world;  //world singleton f�r den zugriff von au�en
    
    private World worldToEnter; //Die Welt mit der weitergespielt wird
    public PauseMenu(World worldToEnter, boolean keyPressed){    
        super(1000, 640, 1, false); 
        world=this;
        this.worldToEnter=worldToEnter;
        this.keyPressed=keyPressed;
        
        GreenfootImage img= new GreenfootImage("menu/pause/background.png");
        img.scale(1000, 640);
        img.setColor(Color.WHITE);
        img.setFont(new Font(35));
        img.drawString(Integer.toString(WorldManager.getEbene()), 580, 313);
        setBackground(img);

        
        controls = new MenuButton("controls", true); //am Anfang ist der obere Button schon gedr�ckt
        quit = new MenuButton("quit", false);

        
        addObject(controls, 507, 397);
        addObject(quit, 505 , 492);

    }
    
    /**
     *Navigierung durch die Men�punkte mit den Pfeiltasten,Enter und Escape
     */
    public void act(){
       
        if(Greenfoot.isKeyDown("up")){
            if(!keyPressed&&!keysLocked){ //damit die Aktion nur einmal durchgef�hrt wird
                if(controls.isSelected()){//wechselt zwischen den beiden Buttons
                    controls.setSelected(false);
                    quit.setSelected(true);
                }
                else if(quit.isSelected()){
                    controls.setSelected(true);
                    quit.setSelected(false);
                }
                //Damit Aktion  beim n�chsten mal wegen dem if-Statement nicht mehr ausgef�hrt wird
                keyPressed=true;
            }

        }
        else if(Greenfoot.isKeyDown("down")){
            if(!keyPressed&&!keysLocked){//damit die Aktion nur einmal durchgef�hrt wird
                if(controls.isSelected()){//wechselt zwischen den beiden Buttons
                    
                    controls.setSelected(false);
                    quit.setSelected(true);
                }
                else if(quit.isSelected()){
                    
                    controls.setSelected(true);
                    quit.setSelected(false);
                }
                //Damit Aktion  beim n�chsten mal wegen dem if-Statement nicht mehr ausgef�hrt wird
                keyPressed=true;
            }

        }

        else if(Greenfoot.isKeyDown("enter")){//Auswahl "einloggen"
            if(!keyPressed && !keysLocked) {//damit die Aktion nur einmal durchgef�hrt wird
                if(controls.isSelected()) {
                    addObject(new ControlPanel(this), getWidth() / 2, getHeight() / 2);
                    keysLocked = true; 
                }
                else if(quit.isSelected()) {
                   Greenfoot.setWorld(new StartMenu());
                }
                //Damit Aktion  beim n�chsten mal wegen dem if-Statement nicht mehr ausgef�hrt wird
                keyPressed = true;
            }
        }
        else if(Greenfoot.isKeyDown("escape")){//kehre zum Spiel zur�ck
            if(!keyPressed&&!keysLocked){
              Greenfoot.setWorld(worldToEnter); //Spielfortschritt bleibt wegen worldToEnter erhalten
             
            }
            //Damit Aktion  beim n�chsten mal wegen dem if-Statement nicht mehr ausgef�hrt wird
             keyPressed=true; 
        }
        else{
            keyPressed=false; // //keine der Tasten wurde gedr�ckt
        }

    }
    
    /**
     * legt fest, ob die Navigierung durch die Tasten gerade gesperrt ist
     */
    public void setKeyLock(boolean keysLocked) { 
        this.keysLocked = keysLocked;
    } 
    
    /**
     * getWorld()-method f�r PauseMenu
     */
    public static PauseMenu getWorld(){
        return world;
    }
    
    
}
