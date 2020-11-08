import greenfoot.*;

/**
 * Diese Klasse stellt die Superklasse aller Bosse dar.
 * Sie regelt das Verhalten und den Fähigkeitengebrauch des Bosses.
 * 
 * @author Alex, Daniel
 * @version 26.06.2020
 */
public abstract class Boss extends Actor {

    // Boss-spezifische Attribute. Werden hier auf standard werte gesetzt
    protected int max_health;
    protected int health, speed, damage;

    // Timer und Timerbegrenzer um den Fähigkeitengebrauch zu kontrollieren
    private int timer, timeToNextAbility;
    private final int AVG_TIME_TO_ABILITY = 300;

    // Ist der Gegner gerade in einer Fähigkeit?
    private boolean inAbility;
    // Werden die Gegner spezifischen Attribute gerade verstärkt oder abgeschwächt
    private double dmgBuff, speedBuff, resistBuff, attackSpeedDebuff;

    // Bewegungsart interface und Fähigkeiten interface
    protected Movement movement;
    protected IBossAbility ability;

    private Healthbar hBar;

    // Timer für das angreifen
    protected int attackTimer, frequency;

    // variablen f�r die Animation
    private String animationState = "IDLE"; // bewegungsstatus
    private String bossState = "NORMAL"; // welche f�higkeit
    private int facing = 2; //blickrichtung
    private int animIndex = 0;
    private int animTimer = 0;
    protected int maxAnimIndexWalkFront, maxAnimIndexWalkSide, maxAnimIndexATK, maxAnimIndexIdle;
    protected int yTextureOffset;

    protected String type; // Boss type
    private EntityTextures entitytextures; // Textur um die Graphik und den Collider getrennt zu halten

    /**
     * Konstruktor f�r die abstrakte Klasse: Boss
     * Hier werden standard werte f�r die ganzen Attribute gesetzt.
     * Die Subklassen sollen diese selbst ab�ndern
     */
    public Boss() {
        //Attribut standards
        max_health = 10;
        health = 10;
        speed = 2;
        damage = 2;
        
        // f�higkeiten werte standards setzen
        timer = 0;
        timeToNextAbility = 100;
        inAbility = false;

        dmgBuff = 1.0;
        speedBuff = 1.0;
        resistBuff = 1.0;
        attackSpeedDebuff = 1.0;

        attackTimer = 0;
        frequency = 25;

        // Animation spezifisches
        maxAnimIndexATK = 1;
        maxAnimIndexIdle = 1;
        maxAnimIndexWalkFront = 1;
        maxAnimIndexWalkSide = 1;
        yTextureOffset = 0;
        
        // Healthbar erzeugen
        hBar =  new Healthbar(400, 20, Color.RED);
        DungeonWorld.getWorld().addObject(hBar, DungeonWorld.getWorld().getWidth() / 2, 25);
        
        // Boss sound abspielen
        SoundManager.playSound("sounds/boss_enter.wav", SoundManager.getSFXVol());

        entitytextures = new EntityTextures();
    }

    /**
     * Sobald der Boss in die Welt gesetzt wird, wird auch dessen Textur kreiert.
     */
    public void addedToWorld(World world){
        DungeonWorld.getWorld().addObject(entitytextures, getX(), getY());
    }

    /**
     * In dieser Methode wird der Fähigkeitengebrauch und das folgen des Spielers sowie Animation gesteuert
     */
    public void act() {
        if(!isInAbility()) {
            //Animation  Autor: Daniel
            if(!bossState.equals("NORMAL"))
                resetAnimTime();
            bossState = "NORMAL";
            manageNormalAnim();
            executeAnimation();
            //---------------------------

            followPlayer();
            timer++;
            if(timer >= timeToNextAbility) {
                timer = 0;
                ability = pickAbility();
                // Zeit bis zur nächsten Fähigkeit zufälliger machen
                timeToNextAbility = AVG_TIME_TO_ABILITY + (int)(Math.random() * 200 - 50);
                startAbility();
                       
                resetAnimTime();
            }
        }

        // Wenn gerade eine Fähigkeit benutzt wird, benutze sie weiterhin
        if(isInAbility()) {
            // useAbility gibt zurück ob die Fähigkeit im nächsten Tick weiter ausgeführt werden soll
            boolean used = ability.useAbility(this);

            // Animation Autor: Daniel
            if(getWorld() != null) {
                ability.manageAnim(this);
                executeAnimation();
            }
            //-------------------------------

            if(!used) // Wenn die Fähigkeit fertig ist -> Stoppen
                ability.stopAbility(this);
        }
    }

    /**
     * Starte die gerade ausgewählte Fähigkeit (solang eine ausgewählt ist)
     */
    public void startAbility() {
        if(!isInAbility() && ability != null)
            ability.useAbility(this);
    }

    /**
     * Mit dieser Methode kann sich der Boss in die Richtung des Vektors (dx, dy) bewegen.
     * Hier wird der speed und der speedBuff und debuff des Bosses berücksichtigt.
     * @param dx 0 oder 1 um die Bewegung in x-Richtung zu beschreiben
     * @param dy 0 oder 1 um die Bewegung in y-Richtung zu beschreiben
     * @return Gibt einen boolean zurück ob der Boss sich in beide Richtungen (x && y) bewegt hat
     */
    protected boolean walk(int dx, int dy) {
        boolean walkedX = false, walkedY = false;
        // Bewege das Objekt insgesamt mit der Geschwindigkeit speed * speedBuff
        for(int i = 0; i < (int)(speed * speedBuff * attackSpeedDebuff); ++i) {
            setLocation(getX() + dx, getY()); // in X richtung bewegen
            walkedX = true;
            if(getOneIntersectingObject(Collider.class) != null) {
                // Nicht in x richtung bewegen wenn ein Collider im weg ist
                setLocation(getX() - dx, getY()); 
                walkedX = false;
            }
            setLocation(getX(), getY() + dy); // in Y Richtung bewegen
            walkedY = true;
            if(getOneIntersectingObject(Collider.class) != null) {
                // Nicht in y richtung bewegen wenn ein Collider im weg ist
                setLocation(getX(), getY() - dy);
                walkedY = false;
            }
        }
        // Wurde in beide Richtungen bewegt?
        return walkedX && walkedY;
    }

    /**
     * Mit dieser Methode bewegt sich der Gegner auf den Spieler zu.
     * In welcher weiße er sich auf ihn zu bewegt wird durch das movement-Interface
     * bestimmt.
     * Zus�tzlich greift er an und schaut ob er sterben muss
     */
    public void followPlayer() {
        // Berechne den Vektor zum Ziel mit dem Movement Interface
        int[] target = movement.zielBerechnen(WorldManager.getPlayer().getX(),
                WorldManager.getPlayer().getY(), getX(), getY());

        // dx = 1 wenn die x komponente positiv ist, 
        //sonst wird dx auf -1 gesetzt wenn die x komponente negativ ist
        //und sonst wenn nichts zutrifft ist dx 0
        int dx = target[0] > 0 ? 1 : (target[0] < 0 ? -1 : 0);
        // das selbe mit dy
        int dy = target[1] > 0 ? 1 : (target[1] < 0 ? -1 : 0);
        if((dx != 0 || dy != 0) && target[2] != 0 ) // Nur bewegen wenn der normalisierte Vektor länger als 0 ist
            walk(dx, dy);

        attack(WorldManager.getPlayer());
        deathCheck();
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
     * T�tet den Boss, l�sst die Healthbar und die Textur verschwinden und erstellt Trapdoor
     */
    public void kill() {
        WorldManager.getCurrentRoom().setRoomEventTriggerd(true);
        DungeonWorld.getWorld().removeObject(this);
        ability.stopAbility(this);
        DungeonWorld.getWorld().removeObject(hBar);
        DungeonWorld.getWorld().removeObject(entitytextures);
        DungeonWorld.getWorld().addObject(new Trapdoor(), DungeonWorld.getWorld().getWidth() / 2, DungeonWorld.getWorld().getHeight() / 2 + 25);
        
        // Nachdem der Boss besiegt wurde: Musik stoppen und ruhige musik spielen
        if(!SoundManager.isSongLooping("soundtrack/calm_after_death.mp3")) {
            SoundManager.stopAllLoopingSongs(); // stoppe alle anderen, falls welche spielen
            SoundManager.playSongLoop("soundtrack/calm_after_death.mp3", SoundManager.getBackMusicVol() - 20);
        }  
    }

    /**
     * Besetzt alle Attribute richtig um die geh, idle oder attack animation abzuspielen
     * Autor: Daniel
     */
    public void manageNormalAnim() {
        // Wo befindet sich der Spieler
        int[] target = movement.zielBerechnen(WorldManager.getPlayer().getX(),
                WorldManager.getPlayer().getY(), getX(), getY());
        boolean walking=false;
        if (target[0]>0){//Der Spieler befindet sich rechts vom Gegner
            if(Math.abs(target[0]) >= Math.abs(target[1])){facing=1;}
            walking= true;
        }
        else if(target[0]<0){//Der Spieler befindet sich links vom Gegner
            if(Math.abs(target[0]) >= Math.abs(target[1])){facing=3;}
            walking= true;
        }

        if(target[1]<0){//Der Spieler befindet sich über dem Gegner
            if(Math.abs(target[0]) < Math.abs(target[1])){facing=0;}
            walking= true;
        }
        else if(target[1]>0){//Der Spieler befindet sich unter dem Gegner
            if(Math.abs(target[0]) < Math.abs(target[1])){facing=2;}
            walking = true;
        }

        if(target[2] == 0){
            walking= false;
        }

        // animationState auf ATTACKING setzen wenn der Boss gerade angreift
        if(attackSpeedDebuff< 1){ 
            if(!animationState.equals("ATTACKING")){
                animTimer=0;
                animIndex=0;
            }
            animationState= "ATTACKING";
        }else {
            if(walking) { // Gehanimation, falls der Boss sich bewegt
                if(!animationState.equals("WALKING")){
                    animTimer=0;
                    animIndex=0;
                }
                animationState = "WALKING";
            }else { // Idle animation
                if(!animationState.equals("IDLE")){
                    animTimer=0;
                    animIndex=0;
                }
                animationState = "IDLE";
            }
        }

    }

    /**
     * F�hrt die Animation mithilfe den jetzigen Werten aus
     * Autor: Daniel
     */
    public void executeAnimation() {
        if(animationState.equals("WALKING")) {
            walkingAnimation();
        }else if(animationState.equals("ATTACKING")) {
            attackAnimation();
        }else {
            idleAnimation();
        }
    }

    /**
     * Spielt die dauerhaft die Animation vom Stehen ab
     * Autor: Daniel
     */
    private void idleAnimation(){
        animTimer++;
        if( animTimer > 7 ){
            entitytextures.loadBossTexture(type, facing, bossState, "idle", animIndex);
            animIndex++;
            animTimer= 0;
            if(animIndex > maxAnimIndexIdle){
                animIndex = 0;
            }
        }
    }

    /**
     * Spielt die Animation des Gehens ab
     * Autor: Daniel
     */
    private void walkingAnimation(){
        animTimer++;
        if( animTimer > 7 ){
            // Seitenanimation haben mehr Frames als die Frontanimationen
            if(((facing == 0 || facing == 2) && animIndex > maxAnimIndexWalkFront)
            || ((facing == 1 || facing == 3) && animIndex > maxAnimIndexWalkSide)){
                animIndex = 0;
            }
            entitytextures.loadBossTexture(type, facing, bossState.toLowerCase(), "walk", animIndex);
            animIndex++;
            animTimer= 0;
        }
    }

    /**
     * Spielt die Animation des Angreifens ab
     * Autor: Daniel
     */
    private void attackAnimation(){
        animTimer++;
        if( animTimer > 3 ){
            entitytextures.loadBossTexture(type, facing, bossState, "attack", animIndex);
            animIndex++;
            animTimer= 0;
            if(animIndex > maxAnimIndexATK ){
                animIndex = 0;
                attackSpeedDebuff= 1.0;
            }
        }
    }

    /**
     * Wenn der Boss seinen Ort ändert, ändert auch die Textur ihren Ort. 
     * Hierbei wird das yOffset berücksichtigt, sodass die Textur nicht in Wänden hängt etc.
     */
    public void setLocation(int x, int y){
        super.setLocation(x, y);
        entitytextures.setLocation(x,y - yTextureOffset);
    }

    /**
     * Greift den Spieler (sofern er in Reichweite ist) an
     */
    protected abstract void attack(Player player);

    /**
     * Gibt eine zufällige Fähigkeit aus der möglichen Auswahl des Bosses zurück
     * @return Fähigkeiten Klasse, die IBossAbility implementiert
     */
    protected abstract IBossAbility pickAbility();

    /**
     * Gibt die anzahl an Leben des Bosses zurück
     * @return Integer, wie viele Leben der Boss hat
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gibt dem Boss eine bestimmte Anzahl an Leben.
     * Die Lebensanzahl wird hier automatisch im Rahmen [0, max_health] gehalten.
     * Die Healthbar wird geupdated.
     * @param health Wie viele Leben soll der Boss haben
     */
    public void setHealth(int health) {
        // resistance buff mit einberechnen
        if(health < this.health) {
            int delta = this.health - health;
            delta /= resistBuff;
            health = this.health - delta;
        }

        // health setzen
        if(health > max_health)
            this.health = max_health;
        else if(health <= 0) {
            this.health = 0;
        }else
            this.health = health;

        // Healthbar updaten
        hBar.updateHealthBar(this.health, max_health);
    }

    /** 
     * Gibt zurück wie viel Schaden der Boss macht (mit dmgBuff)
     * @return Integer, wie viel Schaden der Boss macht
     */
    public int getDamage() {
        return (int)(damage * dmgBuff);
    }

    /**
     * Setzt den Fähigkeitsstatus des Gegners
     * @param arg ist der Boss gerade in einer Fähigkeit?
     */
    public void setInAbility(boolean arg) {
        inAbility  = arg;
    }

    /**
     * Ist der Boss gerade in einer Fähigkeit?
     * @return Gibt einen boolean zurück ob der Boss in einer Fähigkeit ist
     */
    public boolean isInAbility() {
        return inAbility;
    }

    /**
     * Setzt den Buff der die Geschwindigkeit verstärkt oder abschwächt
     * @param speedBuff <1 wenn die Geschwindigkeit verringert werden soll >1 wenn die Geschwindigkeit schneller werden soll  == 1 wenn die normale Geschwindigkeit benutzt werden soll
     */
    public void buffSpeed(double speedBuff) {
        this.speedBuff = speedBuff;
    }

    /**
     * Setzt den Buff der die Geschwindigkeit verstärkt oder abschwächt beim angreifen
     * @param speedDebuff <1 wenn die Geschwindigkeit verringert werden soll >1 wenn die Geschwindigkeit schneller werden soll  == 1 wenn die normale Geschwindigkeit benutzt werden soll
     */
    public void buffAttackSpeedDebuff(double speedDebuff) {
        this.attackSpeedDebuff = speedDebuff;
    }

    /**
     * Setzt den Buff der den Schaden verstärkt oder abschwächt
     * @param dmgBuff <1 wenn der Schaden verringert werden soll >1 wenn der Schaden höher werden soll  == 1 wenn der normale Schaden benutzt werden soll
     */
    public void buffDamage(double dmgBuff) {
        this.dmgBuff = dmgBuff;
    }

    /**
     * Setzt den Buff der die Resistenz verstärkt oder abschwächt
     * @param resBuff <1 wenn die Resistenz verringert werden soll >1 wenn die Resistenz höher werden soll  == 1 wenn die normale Resistenz benutzt werden soll
     */
    public void buffResistance(double resBuff) {
        if(resBuff <= 0)
            return;
        this.resistBuff = resBuff;
    }

    /**
     * Ermittelt ob der Boss gerade in einem Collider ist
     * @return Boolean ob er in einem Collider ist.
     */
    public boolean inCollider() {
        return getOneIntersectingObject(Collider.class) != null;
    }

    /**
     * Setzt den Boss F�higkeiten Zustand
     * Autor: Daniel
     */
    public void setBossState(String s) {
        bossState = s;
    }

    /**
     * Setzt den Animations bewegungs Zustand
     * Autor: Daniel
     */
    public void setAnimationState(String s) {
        animationState = s;
    }

    /**
     * Setzt die Blickrichtung
     * Autor: Daniel
     */
    public void setFacing(int i) {
        facing = i;
    }

    /**
     * Setzt den Animationstimer und Index zur�ck
     * Autor: Daniel
     */
    public void resetAnimTime() {
        animTimer = 0;
        animIndex = 0;
    }
}
