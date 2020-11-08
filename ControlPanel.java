import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *In ControlPanel wird ein Bild angezeigt, auf dem die Steuerung erkl�rt wird
 * 
 * @author Anja 
 * @version 08.06.2020
 */
public class ControlPanel extends Actor
{
   //Variable um festzustellen von welchem Men� aus der Actor erstellt wurde
   //Damit beim ESC dr�cken das richtige Men�(Start- oder Pausemen�) den Actor wieder entfernt
    private World menu; 

    public ControlPanel(World menu) {
        GreenfootImage img = new GreenfootImage("menu/controls/background.png");
        img.scale(1000,640);
        setImage(img);

        this.menu=menu;
    }  
    
    /**
     * �berpr�ft ob ESC-Taste gedr�ckt wird und l�sst sich dann durch Fallunterscheidung mithilfe
     * der menu-variable wieder entfernen. Deaktiviert au�erdem die Tastensperre des Men�s
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
