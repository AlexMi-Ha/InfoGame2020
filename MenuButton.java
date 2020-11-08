import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * MenuButton ist die Klasse f�r alle Buttons die in den Men�s verwendet werden
 * 
 * @author Anja
 * @version 08.06.2020
 */
public class MenuButton extends Actor
{
    private String type; //wird benutzt um das richtige Bild zu laden, Teil des Dateinamens
    private boolean selected; //Ist der Button gerade ausgew�hlt? wenn ja wird andere Grafik benutzt

    public MenuButton(String type, boolean selected){
        this.type=type;
        this.selected=selected;

        if(selected){ //setzt als Bild den gedr�ckten Button
            GreenfootImage img=new GreenfootImage("menu/buttons/"+type+"_pressed.png");
            img.scale((int)(img.getWidth()*2.7), (int)( img.getHeight()*2.7));
            setImage(img);

        }
        else{ //setzt als Bild den normalen Button
            GreenfootImage img=new GreenfootImage("menu/buttons/"+type+".png");
            img.scale((int)(img.getWidth()*2.7), (int)( img.getHeight()*2.7));
            setImage(img);
        }
    }

    /**
     * setter-Method f�r Variable selected.
     * Updated beim Ausf�hren auch das Bild des Buttons
     * 
     */
    public void setSelected(boolean selected) 
    {
        this.selected=selected;
        if(selected){
            GreenfootImage img=new GreenfootImage("menu/buttons/"+type+"_pressed.png");
            img.scale((int)(img.getWidth()*2.7), (int)( img.getHeight()*2.7));
            setImage(img); 
        }
        else{
            GreenfootImage img=new GreenfootImage("menu/buttons/"+type+".png");
            img.scale((int)(img.getWidth()*2.7), (int)( img.getHeight()*2.7));
            setImage(img);
        }
    }    
    
    /**
     * @return Ist der Knopf gerade ausgew�hlt?
     */
    public boolean isSelected(){
        return selected;
    }
}
