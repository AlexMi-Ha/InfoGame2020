import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Klasse, die für das Laden der Texturen von verschiedenen Objekten, zB Gegnern, zuständig ist. Somit wird die Textur von dem Collider der Gegner getrennt.
 * Die Textur wird also über den tatsächlichen Gegner gelegt.
 * @author Daniel
 * @version 26.06.2020
 */
public class EntityTextures extends Actor
{   
    /**
     * Berücksichtigt Gegnertyp, Blickrichtung, aktuelle Bewegungsart und den Fortschritt der bisherigen Animation der Gegner.
     */
    public void loadTexture(String type, int facing, String animationState, int animIndex){
        GreenfootImage img;
        try{

            if(facing== 1){
                facing= 3;
                img= new GreenfootImage("images/enemies/" + type + "/" + facing + "/" + animationState + "/" + animationState + animIndex + ".png");
                img.mirrorHorizontally();
            }
            else{
                img= new GreenfootImage("images/enemies/" + type + "/" + facing + "/" + animationState + "/" + animationState + animIndex + ".png");
            }

        }
        catch(Exception ex){
            System.out.println("<EntityTextures> Fehler beim Laden des " + animIndex + "ten Bild der Animation.");
            animIndex=0;
            if(facing== 1){
                facing= 3;
                img= new GreenfootImage("images/enemies/" + type + "/" + facing + "/" + animationState + "/" + animationState + animIndex + ".png");
                img.mirrorHorizontally();
            }
            else{
                img= new GreenfootImage("images/enemies/" + type + "/" + facing + "/" + animationState + "/" + animationState + animIndex + ".png");
            }
        }
        img.scale(img.getWidth() * 3, img.getHeight() * 3);
        setImage(img);
    }

    /**
     * Berücksichtigt Bosstyp, Blickrichtung, F�higkeitsstatus, aktuelle Bewegungsart und den Fortschritt der bisherigen Animation des Bosses.
     */
    public void loadBossTexture(String type, int facing, String bossState, String animationState, int animIndex) {
        GreenfootImage img;
        try{
            if(facing== 3){
                img= new GreenfootImage("images/boss/" + type + "/" + facing + "/" + bossState + "/" + animationState + "/" + animationState + animIndex + ".png");
            }
            else if(facing == 1){
                facing= 3;
                img= new GreenfootImage("images/boss/" + type + "/" + facing + "/" + bossState + "/" + animationState + "/" + animationState + animIndex + ".png");
                img.mirrorHorizontally();
            }
            else{img= new GreenfootImage("images/boss/" + type + "/" + facing + "/" + bossState + "/" + animationState + "/" + animationState + animIndex + ".png");}

        }
        catch(Exception ex){
            System.out.println("<EntityTextures> Fehler beim Laden des " + animIndex + "ten Bild der Animation.");
            animIndex=0;
            if(facing== 3){
                img= new GreenfootImage("images/boss/" + type + "/" + facing + "/" + bossState + "/" + animationState + "/" + animationState + animIndex + ".png");
            }
            else if(facing == 1){
                facing= 3;
                img= new GreenfootImage("images/boss/" + type + "/" + facing + "/" + bossState + "/" + animationState + "/" + animationState + animIndex + ".png");
                img.mirrorHorizontally();
            }
            else{img= new GreenfootImage("images/boss/" + type + "/" + facing + "/" + bossState + "/" + animationState + "/" + animationState + animIndex + ".png");}

        } 
        img.scale(img.getWidth() * 3, img.getHeight() * 3);
        setImage(img);
    }

    /**
     * Berücksichtigt Blickrichtung, aktuelle Bewegungsart und den Fortschritt der bisherigen Animation f�r den Spieler.
     */
    public void loadPlayerTexture( int facing, String animationState, int animIndex){
        GreenfootImage img;
        try{

            if(facing== 1){
                facing= 3;
                img= new GreenfootImage("images/player/" + facing + "/" + animationState + "/" + animationState + animIndex + ".png");
                img.mirrorHorizontally();
            }
            else{
                img= new GreenfootImage(img= new GreenfootImage("images/player/" + facing + "/" + animationState + "/" + animationState + animIndex + ".png"));
            }

        }
        catch(Exception ex){
            System.out.println("<EntityTextures> Fehler beim Laden des " + animIndex + "ten Bild der Animation.");
            animIndex=0;
            if(facing== 1){
                facing= 3;
                img= new GreenfootImage(img= new GreenfootImage("images/player/" + facing + "/" + animationState + "/" + animationState + animIndex + ".png"));
                img.mirrorHorizontally();
            }
            else{
                img= new GreenfootImage(img= new GreenfootImage("images/player/" + facing + "/" + animationState + "/" + animationState + animIndex + ".png"));
            }
        }
        img.scale((int) (img.getWidth() * 2.4), (int) (img.getHeight() * 2.4));
        setImage(img);
    }
}