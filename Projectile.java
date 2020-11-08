import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Projektile sind die Geschosse, die von Spieler und den Archern abgeschossen werden. Sie haben eine maximale Schussweite nach der sie sich automatisch selbst zerstören. Außerdem 
 * gehen sie kaputt an allen Collidern, sowie an ihrem jeweiligen Ziel. Diesem fügen sie jedoch zuvor Schaden zu.
 * 
 * @author Anja,Daniel, Alex
 * @version 25.06.2020
 */
public class Projectile extends Actor
{
    private int speed;
    private int distance; //maximale Flugweite
    private Actor sender;
    private int startX, startY; //Koordinaten,an denen Projektil erzeugt wurde;

    private int timer;
    private int maxAnimIndex;
    private int animIndex;
    private String type;
    private boolean isAnimated;

    public Projectile(int dx, int dy, int startX,int startY, Actor s){ //Konstruktor für normale Projektile
        speed=4;
        distance=350;
        this.startX=startX;
        this.startY=startY;
        sender=s;

        GreenfootImage img=new GreenfootImage(10,10);
        img.setColor(Color.BLACK);
        img.fill();

        isAnimated=false;

        setImage(img);
        turnTowards(dx, dy); //Projectile drehen sich einmalig in Richtung ihres Ziels, können die Richtung danach aber nicht mehr ändern

        // Spielt den Schuss sound ab; Autor: Alex
        SoundManager.playSound("sounds/shoot.wav", SoundManager.getSFXVol());
    }

    public Projectile(int dx, int dy, int startX,int startY, Actor s, String type, int maxAnimIndex){ //Konstruktor für Projektile mit besonderem Image
        speed=4;
        distance=350;
        this.startX=startX;
        this.startY=startY;
        sender=s;

        this.type= type;
        this.maxAnimIndex= maxAnimIndex;
        timer=0;
        isAnimated=true;
        setImage("images/fx/projectile/" + type + "/" + 0 + ".png");
        getImage().scale(10, 10);

        turnTowards(dx, dy); //Projectile drehen sich einmalig in Richtung ihres Ziels, können die Richtung danach aber nicht mehr ändern

        // Spielt den Schuss sound ab; Autor: Alex
        SoundManager.playSound("sounds/shoot.wav", SoundManager.getSFXVol());
    }

    /**
     * Hier bewegt sich das Projektil vorwärts und zerstört sich selbst wenn mindestens eine der Bedingungen zutrifft, zieht dem Ziel Leben auf Treffer ab
     */
    public void act() 
    {
        //Variable, die innerhalb der Schleife verwendet wird, um Fehler vorzubeugen
        boolean remove=false;

        for(int i=0;i<speed;i++) {
            move(1);
            if(getOneIntersectingObject(Collider.class)!=null){ //Wird Collider berührt?
                remove=true;
            }
            if((getX()<=15)||(getX()>=getWorld().getWidth()-15)||(getY()<=25)||(getY()>=getWorld().getHeight()-15)){ //Ist Projektil durch Türe geflogen?

                remove=true;
            }
            if(((getX()-startX)*(getX()-startX))+((getY()-startY)*(getY()-startY))>(distance*distance)){ //Wurde maximale Distanz erreicht?
                remove=true;
            }

            if(sender instanceof Enemy){ //Wenn Angreifer auf Player zielt
                if(getOneIntersectingObject(Player.class)!=null) {
                    Enemy enemy= (Enemy) sender; //Casten auf Enemy
                    WorldManager.getPlayer().setHealth(WorldManager.getPlayer().getHealth() - enemy.getDamage()); //zieht dem getroffenen Spieler den Schadenswert vom Leben ab
                    remove=true; 
                }
            }
            if(sender instanceof Boss){ //Wenn Angreifer auf Player zielt
                if(getOneIntersectingObject(Player.class)!=null) {
                    Boss boss= (Boss) sender; // Casten auf Boss
                    WorldManager.getPlayer().setHealth(WorldManager.getPlayer().getHealth() - boss.getDamage()); //zieht dem getroffenen Spieler den Schadenswert vom Leben ab
                    remove=true; 
                }
            }

            if(sender instanceof Player){//Wenn Angreifer auf Enemies zielt
                Enemy enemyHit = (Enemy) getOneIntersectingObject(Enemy.class);
                if(enemyHit !=  null) {
                    Player player= (Player) sender;
                    enemyHit.setHealth(enemyHit.getHealth() - player.getDamage()); //zieht dem getroffenen Gegner den Schadenswert vom Leben ab
                    remove=true; 
                }
                Boss bossHit = (Boss) getOneIntersectingObject(Boss.class);
                if(bossHit !=  null) {
                    Player player= (Player) sender;
                    bossHit.setHealth(bossHit.getHealth() - player.getDamage()); //zieht dem getroffenen Gegner den Schadenswert vom Leben ab
                    remove=true; 
                }
            }
        }

        if(remove==true){
            DungeonWorld.getWorld().removeObject(this);  //entfernt sich selbst
        }

        if(isAnimated && WorldManager.areProjectilesAnimated()){
            idleAnimation();
        }
    } 

    /**
     * Spielt die spezifische Animation des Projektils ab 
     */
    private void idleAnimation(){
        timer++;
        if( timer > 4 ){
            setImage(loadTexture());
            animIndex++;
            timer= 0;
            if(animIndex > maxAnimIndex ){
                animIndex = 0;
            }
        }
    }

    public GreenfootImage loadTexture() {
        GreenfootImage img = new GreenfootImage("images/fx/projectile/" + type + "/" + animIndex + ".png");
        
        if(type.equals("alhoon")){
            img.scale(20, 20);
        }
        else if(type.equals("demogorgon")){
            img.scale(20, 20);
        }
        else{img.scale(10, 10);}
        return img;
    }
}
