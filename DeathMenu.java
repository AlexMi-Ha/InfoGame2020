import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *Klasse f�r das Men�, das angezeigt wird, wenn der Player stirbt
 * 
 * @author Anja
 * @version 08.06.2020
 */
public class DeathMenu extends World
{

    private boolean keyPressed = false;     //wird gerade eine taste gedr�ckt?
    private boolean keysLocked = false;     //ist die tasteneingabe gerade gesperrt?

    private MenuButton newgame, quit;  //alle Buttons, die in dieser Welt existieren

    private static DeathMenu world; //world singleton f�r den zugriff von au�en

    
    
    public DeathMenu(boolean keyPressed){    
        super(1000, 640, 1, false); 
        world=this; //belegt world singleton
        
        this.keyPressed=keyPressed;

        GreenfootImage img= new GreenfootImage("menu/death/background.png");
        img.scale(1000, 640);
        img.setColor(Color.WHITE);
        img.setFont(new Font(35));
        img.drawString(Integer.toString(WorldManager.getEbene()), 415, 260);
        setBackground(img);

        newgame = new MenuButton("newgame", true); //am Anfang ist der obere Button schon gedr�ckt
        quit = new MenuButton("quit", false); 

        addObject(newgame, 500, 357);
        addObject(quit, 503 , 457);

    }

    /**
     *Navigierung durch die Men�punkte mit den Pfeiltasten und Enter
     */
    public void act(){
        if(Greenfoot.isKeyDown("up")){
            if(!keyPressed&&!keysLocked){ //damit die Aktion nur einmal durchgef�hrt wird
                if(newgame.isSelected()){ //wechselt zwischen den beiden Buttons
                    newgame.setSelected(false);
                    quit.setSelected(true);
                }
                else if(quit.isSelected()){
                    newgame.setSelected(true);
                    quit.setSelected(false);
                }
                //Damit Aktion  beim n�chsten mal wegen dem if-Statement nicht mehr ausgef�hrt wird
                keyPressed=true; 
            }

        }
        else if(Greenfoot.isKeyDown("down")){
            if(!keyPressed&&!keysLocked){
                if(newgame.isSelected()){ //damit die Aktion nur einmal durchgef�hrt wird

                    newgame.setSelected(false);
                    quit.setSelected(true);
                }
                else if(quit.isSelected()){

                    newgame.setSelected(true);
                    quit.setSelected(false);
                }
                //Damit Aktion  beim n�chsten mal wegen dem if-Statement nicht mehr ausgef�hrt wird
                keyPressed=true;
            }

        }

        else if(Greenfoot.isKeyDown("enter")){ //Auswahl "einloggen"
            if(!keyPressed && !keysLocked) {
                if(newgame.isSelected()) {
                    Greenfoot.setWorld(new RoomWorld()); //erstellt neues Spiel
                    
                }
                else if(quit.isSelected()) {
                    Greenfoot.setWorld(new StartMenu()); //wechselt zum StartMenu
                }
                //Damit Aktion  beim n�chsten mal wegen dem if-Statement nicht mehr ausgef�hrt wird
                keyPressed = true;
            }
        }
        
        else{
            keyPressed=false; //keine der Tasten wurde gedr�ckt
        }

    }

    /**
     * legt fest, ob die Navigierung durch die Tasten gerade gesperrt ist
     * 
     */
    public void setKeyLock(boolean keysLocked) {
        this.keysLocked = keysLocked;
    } 

    /**
     * getWorld()-method f�r DeathMenu
     */
    public static DeathMenu getWorld(){ 
        return world;
    }

}
