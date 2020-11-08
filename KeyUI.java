import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * UI-Element, welches angibt ob der Spieler den Bossschl�ssel der aktuellen Ebene eingesammelt hat. 
 * 
 * @Sebi, Daniel
 * @14.06.
 */
public class KeyUI extends Actor
{   
    //Konstruktor, erzeugt Graphik bei nicht-eingesammeltem Schl�ssel
    public KeyUI(){
        GreenfootImage img=new GreenfootImage("images/gui/key/keyfalse.png");
        img.scale((int) (img.getWidth()*1.5), (int) (img.getHeight()*1.5));
        setImage(img);
    }
    //Wechselt zur Graphik f�r einen eingesammelten Schl�ssel
    public void keyCollected(){
        GreenfootImage img= new GreenfootImage("images/gui/key/keytrue.png");
        img.scale((int) (img.getWidth()*1.5), (int) (img.getHeight()*1.5));
        setImage(img);
    }
}
