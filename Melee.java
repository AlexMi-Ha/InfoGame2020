/**
 * Die Unterklasse Melee implementiert das Interface Movement und damit auch die Methode zielBerechnen(int, int, int, int) sowie inRange(int, int, int, int).
 * 
 * 
 * @author Anja, Daniel
 * @version 22.06.2020
 */
public class Melee implements Movement 
{
    private int distMax = 50;
    public Melee(){

    }
    
    public Melee(int distMax){
        this.distMax = distMax;
    }

    /**
     * Die Methode ist so gemacht, dass der Rückgabe-Vektor genau vom Gegner auf den Spieler führt.
     */
    public int[] zielBerechnen(int playerX,int playerY,int enemyX,int enemyY){
        int[] ziel=new int[3]; // 0.Stelle ist x-Verschiebung, 1.Stelle ist y-Verschiebung, 2. Stelle ermittelt, ob er sich bewegen muss
        //Berechnung der X-Verschiebung 
        ziel[0]=playerX-enemyX; 
        //Berechnung der X-Verschiebung 
        ziel[1]=playerY-enemyY;
        ziel[2]=1;
        return ziel;
    }
    
    /**
     * Selbes wie bei Ranged Movement inRange -Methode; jedoch Beachtung der Melee-Bewegungsart: Weglaufen nicht notwendig
     * Autor: Daniel
     */
    public boolean inRange(int playerX,int playerY,int enemyX,int enemyY){ //gibt zurück, ob der Spieler in Reichweite des Gegners ist
        double distance=Math.sqrt((playerX-enemyX)*(playerX-enemyX)+(playerY-enemyY)*(playerY-enemyY)); 
        if(distance <= distMax){ //Spieler befindet sich in Reichweite
            return true;
        }
        else{
            return false;
        }
    }
}
