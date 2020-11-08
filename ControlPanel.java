import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *In ControlPanel wird ein Bild angezeigt, auf dem die Steuerung erklärt wird
 * 
 * @author Anja 
 * @version 08.06.2020
 */
public class ControlPanel extends Actor
{
   //Variable um festzustellen von welchem Menü aus der Actor erstellt wurde
   //Damit beim ESC drücken das richtige Menü(Start- oder Pausemenü) den Actor wieder entfernt
    private World menu; 

    public ControlPanel(World menu) {
        GreenfootImage img = new GreenfootImage("menu/controls/background.png");
        img.scale(1000,640);
        setImage(img);

        this.menu=menu;
    }  
    
    /**
     * Überprüft ob ESC-Taste gedrückt wird und lässt sich dann durch Fallunterscheidung mithilfe
     * der menu-variable wieder entfernen. Deaktiviert außerdem die Tastensperre des Menüs
     *
     */
    public void act() {
        
        if(Greenfoot.isKeyDown("escape")) {
            if(menu instanceof StartMenu){
                StartMenu.getWorld().setKeyLock(false);
                StartMenu.getWorld().removeObject(this);
            }
            else if(menu instanceof PauseMenu){
                PauseMenu.getWorld().setKeyLock(false);
                PauseMenu.getWorld().removeObject(this);
            }
        }

    }
}
