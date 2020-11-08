import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Superklasse für alle Gegner gegen die der Spieler kämpfen muss. Beinhaltet
 * grundlegende Attribute wie speed, leben, damage und ob der Gegner fliegen 
 * kann. Außerdem hat Enemy eine Referenz auf das Interface Movement, das festlegt, 
 * wie sich die Unterklassen fortbewegen.
 * 
 * @author Anja, Daniel, Alex
 * @version 14.6.2020
 */
public abstract class Enemy extends Actor
{
    private int damage;
    protected int health;
    private boolean flying;
    private int speed;

    private boolean walking= false; //Attribut zum Überprüfen der aktuellen Bewegung (=> Animation)
    protected double speedBuff= 1;

    protected Movement movement;

    protected int facing= 2; //Attribut mit Zahlenbereich von 0-3, bestimmt die Blickrichtung des Gegners (0 oben, dann im Uhrzeigersinn weiter) 
    private String type; //Gegnertyp(Bird,Walker,Heavy,Archer)
    private String animationState ="IDLE"; //Status: ATTACKING, WALKING, IDLE
    protected int animIndex= 0;
    private int timer;
    //berücksichtigt, dass verschiedene Animationen verschiedener Gegner unterschiedlich viele Frames besitzen können.
    private int maxAnimIndexIDLE;
    private int maxAnimIndexWALK;
    private int maxAnimIndexATK;
    
    private EntityTextures entitytextures;
    
    protected int yTextureOffset; //verhindert, dass Gegnertextur über den Collider hinausragt
    
    public Enemy(int damage, int health, boolean flying, int speed, Movement movement, String type, int maxAnimIndexIDLE, int maxAnimIndexWALK, int maxAnimIndexATK, int yTextureOffset){
        this.damage=damage;
        this.health=health;
        this.flying=flying;
        this.speed=speed;
        this.movement=movement;
        this.type=type;
        this.maxAnimIndexIDLE=maxAnimIndexIDLE;
        this.maxAnimIndexWALK=maxAnimIndexWALK;
        this.maxAnimIndexATK=maxAnimIndexATK;
        this.yTextureOffset=yTextureOffset;
        entitytextures= new EntityTextures();
    }

    /**
     * Mithilfe des Vektors, der von einer der Movement-Unterklassen zurückgegeben
     * wird, wird berechnet in welche Richtung der Gegner sich bewegen muss.
     * Dabei werden ähnlich wie bei der Player Klasse die Collider beachtet,
     * mit dem Unterschied, dass fliegende Gegner über einige Collider hinwegfliegen
     * können.
     */
    public void act() 
    {
        Player player=WorldManager.getPlayer();
        int[] ziel= movement.zielBerechnen(player.getX(), player.getY(), this.getX(), this.getY());
        int dx=0; //Schrittvariable in x-Richtung
        int dy=0; //Schrittvariable in y-Richtung
        walking=false;

        if (ziel[0]>0){//Der Spieler befindet sich rechts vom Gegner
            dx+=1; 
            if(Math.abs(ziel[0]) >= Math.abs(ziel[1])){facing=1;}
            walking= true;
        }
        else if(ziel[0]<0){//Der Spieler befindet sich links vom Gegner
            dx-=1;
            if(Math.abs(ziel[0]) >= Math.abs(ziel[1])){facing=3;}
            walking= true;
        }

        if(ziel[1]<0){//Der Spieler befindet sich über dem Gegner
            dy-=1; 
            if(Math.abs(ziel[0]) < Math.abs(ziel[1])){facing=0;}
            walking= true;
        }
        else if(ziel[1]>0){//Der Spieler befindet sich unter dem Gegner
            dy+=1; 
            if(Math.abs(ziel[0]) < Math.abs(ziel[1])){facing=2;}
            walking = true;
        }
        
        if(ziel[2] == 0){
            walking= false;
            dx= 0;
            dy= 0;
        }
        //Animations-Bereich:

        //Überprüfung der aktuellen Bewegung

        if(speedBuff< 1 && !type.equals("archer")){ //weil Gegner verlangsamt werden /stehen bleiben müssen, um anzugreifen, kann man hier den Speed(De)Buff ansehen, um den Status zu determinieren.
            if(!animationState.equals("ATTACKING")){
                timer=0;
                animIndex=0;
            }
            animationState= "ATTACKING";
        }
        else{
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
        }

        //Abspielen der passenden Animation
        if(animationState.equals("WALKING")){
            walkingAnimation();
        }
        else if(animationState.equals("IDLE")){
            idleAnimation();
        }
        else{
            attackAnimation();
        }

        /*for-Schleife, damit die Figur sich pro Aufruf der act()-Methode mehrmals nach vorne bewegt, 
        ohne dabei die Collider zu überschreiten, Attribute wie solidToFlying
        und flying werden beachtet*/

        for(int i=0;i<speed * speedBuff;i++){ //zu speed* speedBuff: Gegner kann nur gehen, wenn er nicht angreift( beim Angriff wird speedBuff 0)
            setLocation(getX()+dx,getY()); 

            if(getOneIntersectingObject(Collider.class)!=null){ //Überprüft ob Collider in x-Richtung berührt wird
                if(flying==false){//Der Gegner kann nicht fliegen
                    setLocation(getX()-dx,getY()); //geht um dx in x-Richtung zurück 

                }
                else if(((Collider)getOneIntersectingObject(Collider.class)).isSolidToFlying()){//Das Hindernis kann nicht überflogen werden
                    setLocation(getX()-dx,getY()); //geht um dx in x-Richtung zurück 
                }
                else{
                    //tut nichts wenn Enemy fliegen kann und der Collider nicht solidToFlying ist
                }
            }

            if(getOneIntersectingObject(Player.class)!=null || getOneIntersectingObject(Enemy.class) != null){ //Überprüft ob Spieler in x-Richtung berührt wird
                setLocation(getX()-dx,getY()); //geht um dx in x-Richtung zurück
            }
            setLocation(getX(),getY()+dy); 
            if(getOneIntersectingObject(Collider.class)!=null){ //Überprüft ob Collider in y-Richtung berührt wird
                if(flying==false){//Der Gegner kann nicht fliegen
                    setLocation(getX(),getY()-dy); //geht um dy in y-Richtung zurück
                }
                else if(((Collider)getOneIntersectingObject(Collider.class)).isSolidToFlying()){//Das Hindernis kann nicht überflogen werden
                    setLocation(getX(),getY()-dy); //geht um dy in y-Richtung zurück
                }
                else{
                    //tut nichts wenn Enemy fliegen kann und der Collider nicht solidToFlying ist
                }
            }
            if(getOneIntersectingObject(Player.class)!=null || getOneIntersectingObject(Enemy.class) != null){ //Überprüft ob Spieler in y-Richtung berührt wird
                setLocation(getX(),getY()-dy); //geht um dy in y-Richtung zurück
            }
        }

        deathCheck();
    }    
    /**
     * Sobald der Gegner in die Welt gesetzt wird, wird auch dessen Textur kreiert.
     */
    public void addedToWorld(World world){
        DungeonWorld.getWorld().addObject(entitytextures, getX(), getY() - yTextureOffset);
    }

    /**
     * @return Gibt Schadenswert des Gegners zurück
     */
    public int getDamage(){
        return damage;
    }

    /**
     * @return Gibt aktuelle Lebenspunkte des Gegners zurück
     */
    public int getHealth(){
        return health;
    }

    /**
     * Setzt die Leben des Gegners neu
     */
    public void setHealth(int h){
        health= h;
    }

    /**
     * Überprüft, ob der Gegner noch am Leben ist; entfernt ihn aus der Welt
     */
    public void deathCheck(){
        if(health < 1){
            kill();
        }
    }
    
    /**
     * Tötet Gegner; beseitigt das Objekt und die Textur.
     */
    public void kill(){
        // Spielt den Todes sound des Gegners ab; Autor: Alex
        if(getWorld() != null)
            SoundManager.playSound("sounds/enemy_death.wav", SoundManager.getSFXVol());
            
        //---------------------------------------
        DungeonWorld.getWorld().removeObject(this);
        DungeonWorld.getWorld().removeObject(entitytextures);
    }
    

    /**
     * Gibt zurück ob der Gegner in einem Collider steckt
     * @return Boolean ob er in einem Collider ist
     */
    public boolean inCollider() {
        return getOneIntersectingObject(Collider.class) != null;
    }

    /**
     * Spielt die dauerhaft die Animation vom Stehen ab
     */
    protected void idleAnimation(){
        timer++;
        if( timer > 7 ){
            entitytextures.loadTexture(type, facing, "idle", animIndex);
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
        if( timer > 7 ){
            entitytextures.loadTexture(type, facing, "walk", animIndex);
            animIndex++;
            timer= 0;
            if(animIndex > maxAnimIndexWALK ){
                animIndex = 0;
            }
        }
    }

    /**
     * Spielt die Animation des Angreifens ab
     */
    private void attackAnimation(){
        timer++;
        if( timer > 3 ){
            entitytextures.loadTexture(type, facing, "attack", animIndex);
            animIndex++;
            timer= 0;
            if(animIndex > maxAnimIndexATK ){
                animIndex = 0;
                speedBuff= 1.0;
            }
        }
    }
    
    /**
     * Wenn der Gegner seinen Ort ändert, ändert auch die Textur ihren Ort. Hierbei wird das yOffset berücksichtigt, sodass die Textur nicht in Wänden hängt etc.
     */
    public void setLocation(int x, int y){
        super.setLocation(x, y);
        entitytextures.setLocation(x,y - yTextureOffset);
    }
}    