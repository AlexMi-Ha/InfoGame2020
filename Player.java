import greenfoot.*;

/**
 * Player ist die Klasse, die vom Spieler aktiv gespielt wird.
 * Eine Instanz der Klasse Spieler wird in der Welt platziert und kann dort interagieren.
 * Hier wird auch die Steuerung implementiert.
 * 
 * @author Anja, Daniel, Alex
 * @version 26.06.2020
 */
public class Player extends Actor {

    private int speed; //legt fest wie schnell der Spieler ist
    private int frequency; //legt fest, wie schnell geschossen wird, je höher frequency, desto länger ist die Pause zwischen den Schüssen 
    private int count; //Zählvariable, die später mit frequency verglichen wird um Abschusszeitpnkt zu ermitteln
    private boolean canShoot; //legt fest ob der Spieler gerade schießen kann

    private boolean hasKey; // Hat der Spieler den Schlüssel der Ebene bereits eingesammelt?

    private int maxhealth=100;
    private int health= 100;
    private int damage= 5;

    private int maxAnimIndexIDLE= 3;
    private int maxAnimIndexWALK = 5;

    private EntityTextures entitytextures;

    protected int yTextureOffset= 18; //verhindert, dass Spielertextur über den Collider hinausragt

    protected int facing= 2; //Attribut mit Zahlenbereich von 0-3, bestimmt die Blickrichtung des Spielers (0 oben, dann im Uhrzeigersinn weiter) 
    private String animationState ="IDLE"; //Status:  WALKING, IDLE
    protected int animIndex= 0;
    private int timer;

    public Player(){
        speed = 3;
        frequency=25;
        count=0;

        hasKey = false;

        GreenfootImage img= new GreenfootImage(30,60);
        //img.setColor(Color.RED); //Erzeugt rotes 30*30 Quadrat
        //img.fill(); // wird später durch Bild ersetzt

        entitytextures= new EntityTextures();

        setImage(img); 
    }

    /**
     * Hier wird die Steuerung festgelegt.
     */
    public void act() {
        int dx=0; //Schrittvariable in x-Richtung
        int dy=0; //Schrittvariable in y-Richtung
        boolean walking= true;
        if (Greenfoot.isKeyDown("d")){
            dx+=1; 
            facing= 1;
        }
        if(Greenfoot.isKeyDown("a")){
            dx-=1; 
            facing= 3;
        }
        if(Greenfoot.isKeyDown("w")){
            dy-=1; 
            facing= 0;
        }
        if(Greenfoot.isKeyDown("s")){
            dy+=1; 
            facing= 2;
        }
        
        if(dy== 0 && dx== 0){
            walking= false;
        }

        /*for-Schleife, damit die Figur sich pro Aufruf der act()-Methode mehrmals nach vorne bewegt, 
         * ohne dabei die Collider zu überschreiten */

        for(int i=0;i<speed;i++){

            setLocation(getX()+dx,getY());
            if(getOneIntersectingObject(Collider.class)!=null){ //Überprüft ob Collider in x-Richtung berührt wird
                setLocation(getX()-dx,getY()); //geht um dx in x-Richtung zurück
            }

            setLocation(getX(),getY()+dy);
            if(getOneIntersectingObject(Collider.class)!=null){//Überprüft ob Collider in y-Richtung berührt wird
                setLocation(getX(),getY()-dy); //geht um dy in y-Richtung zurück
            }

        }

        //Hier wird das Schussverhalten des Spielers geregelt
        if(count==frequency){
            canShoot=true;
            count=0; 
        }
        else if(!canShoot) {
            count++;
            canShoot=false;
        }
        if(canShoot) {
            if (Greenfoot.isKeyDown("up")){
                DungeonWorld.getWorld().addObject(new Projectile(0,-20,getX(),getY(), this, "player", 3),getX(),getY()); //Erzeugt Projektil gegen Enemies, das sich nach oben bewegt
                canShoot=false;
            }
            else if(Greenfoot.isKeyDown("down")){
                DungeonWorld.getWorld().addObject(new Projectile(0,+20,getX(),getY(), this,"player", 3),getX(),getY()); //Erzeugt Projektil gegen Enemies, das sich nach unten bewegt
                canShoot=false;
            }
            else if(Greenfoot.isKeyDown("right")){
                DungeonWorld.getWorld().addObject(new Projectile(+20,0,getX(), getY(), this,"player", 3),getX(),getY()); //Erzeugt Projektil gegen Enemies, das sich nach rechts bewegt
                canShoot=false;
            }
            else if(Greenfoot.isKeyDown("left")){
                DungeonWorld.getWorld().addObject(new Projectile(-20,0,getX(),getY(), this,"player", 3),getX(),getY()); //Erzeugt Projektil gegen Enemies, das sich nach links bewegt
                canShoot=false;
            }
        }

        // Autor: Alex
        // Lässt den Spieler alle Items aufheben die er berührt
        Item i = (Item)getOneIntersectingObject(Item.class);
        if(i != null) {
            i.onPickUp(); // Hebe alle Items auf, die der Spieler berührt
            DungeonWorld.getWorld().removeObject(i);
        }

        if(walking == true){
            if(!animationState.equals("WALKING")){
                timer=0;
                animIndex=0;
            }
            animationState= "WALKING";
        }
        else{
            if(!animationState.equals("IDLE")){
                timer=0;
                animIndex=0;
            }
            animationState= "IDLE";
        }

        //Abspielen der passenden Animation
        if(animationState.equals("WALKING")){
            walkingAnimation();
        }
        else if(animationState.equals("IDLE")){
            idleAnimation();
        }
        
    }

    /**
     * Gibt zurück ob der Spieler bereits den Schlüssel aus dem KeyRaum aufgesammelt hat
     * @return Boolean, ob der Spieler den Schlüssel hat
     * Autor: Alex
     */
    public boolean hasKey() {
        return hasKey;
    }

    /**
     * Setze ob der Spieler den Schlüssel aus dem KeyRaum hat.
     * Autor: Alex
     */
    public void setKey(boolean hasKey) {
        this.hasKey = hasKey;
        WorldManager.getKeyUI().keyCollected();
    }

    /**
     * @return Gibt den Schadenswert des Spielers zurück
     * Autor: Daniel
     */
    public int getDamage(){
        return damage;
    }

    /**
     * @return Gibt die maximalen Lebenspunkte des Spielers zurück
     * Autor: Daniel
     */
    public int getMaxHealth(){
        return maxhealth;
    }

    /**
     * @return Gibt die aktuellen Lebenspunkte des Spielers zurück
     * Autor: Daniel
     */
    public int getHealth(){
        return health;
    }

    /**
     * @return Setzt die Lebenspunkte des Spielers neu, passt das UI-Element Healthbar an
     * Autor: Daniel
     */
    public void setHealth(int h){
        if(h < health)
            SoundManager.playSound("sounds/player_dmg.wav", SoundManager.getSFXVol());
        if(h > maxhealth)
            h = maxhealth;
        health= h;
        if(health < 0 ){
            health= 0;
        }

        WorldManager.getHealthbar().updateHealthBar(getHealth(), getMaxHealth());
    }

    /**
     * Sobald der Gegner in die Welt gesetzt wird, wird auch dessen Textur kreiert.
     */
    public void addedToWorld(World world){
        DungeonWorld.getWorld().addObject(entitytextures, getX(), getY() - yTextureOffset);
    }

    /**
     * Spielt die dauerhaft die Animation vom Stehen ab
     */
    protected void idleAnimation(){
        timer++;
        if( timer > 7 ){
            entitytextures.loadPlayerTexture(facing, "idle", animIndex);
            animIndex++;
            timer= 0;
            if(animIndex > maxAnimIndexIDLE ){
                animIndex = 0;
            }
        }
    }

    /**
     * Spielt die Animation des Gehens ab
     */
    private void walkingAnimation(){
        timer++;
        if( timer > 6 ){
            entitytextures.loadPlayerTexture(facing, "walk", animIndex);
            animIndex++;
            timer= 0;
            if(animIndex > maxAnimIndexWALK ){
                animIndex = 0;
            }
        }
    }
    
    /**
     * Wenn der Spieler seinen Ort ändert, ändert auch die Textur ihren Ort. Hierbei wird das yOffset berücksichtigt, sodass die Textur nicht in Wänden hängt etc.
     */
    public void setLocation(int x, int y){
        super.setLocation(x, y);
        entitytextures.setLocation(x,y - yTextureOffset);
    }
}
