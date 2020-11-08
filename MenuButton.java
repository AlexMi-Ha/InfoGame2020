import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * MenuButton ist die Klasse für alle Buttons die in den Menüs verwendet werden
 * 
 * @author Anja
 * @version 08.06.2020
 */
public class MenuButton extends Actor
{
    private String type; //wird benutzt um das richtige Bild zu laden, Teil des Dateinamens
    private boolean selected; //Ist der Button gerade ausgewählt? wenn ja wird andere Grafik benutzt

    public MenuButton(String type, boolean selected){
        this.type=type;
        this.selected=selected;

        if(selected){ //setzt als Bild den gedrückten Button
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
     * setter-Method für Variable selected.
     * Updated beim Ausführen auch das Bild des Buttons
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
     * @return Ist der Knopf gerade ausgewählt?
     */
    public boolean isSelected(){
        return selected;
    }
}
