import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * UI-Element, welches angibt ob der Spieler den Bossschlüssel der aktuellen Ebene eingesammelt hat. 
 * 
 * @Sebi, Daniel
 * @14.06.
 */
public class KeyUI extends Actor
{   
    //Konstruktor, erzeugt Graphik bei nicht-eingesammeltem Schlüssel
    public KeyUI(){
        GreenfootImage img=new GreenfootImage("images/gui/key/keyfalse.png");
        img.scale((int) (img.getWidth()*1.5), (int) (img.getHeight()*1.5));
        setImage(img);
    }
    //Wechselt zur Graphik für einen eingesammelten Schlüssel
    public void keyCollected(){
        GreenfootImage img= new GreenfootImage("images/gui/key/keytrue.png");
        img.scale((int) (img.getWidth()*1.5), (int) (img.getHeight()*1.5));
        setImage(img);
    }
}
