/**
 * Die Unterklasse Melee implementiert das Interface Movement und damit auch die Methode zielBerechnen(int, int, int, int) sowie inRange(int, int, int, int).
 * 
 * @author Anja 
 * @version 22.06.2020
 */
public class Ranged implements Movement 
{

    private int distMin = 50, distMax = 120;
    public Ranged(){

    }

    public Ranged(int distMin, int distMax){
        this.distMin = distMin;
        this.distMax = distMax;
    }

    /**
     * Der Ring, in den die Vektoren führen beginnt 50 Einheiten vom Player entfernt und endet nach weiteren 70 Einheiten. Die Methode ist so gemacht, 
     * dass der Vektor immer den kürzesten Weg in den Ring zeigt.
     */
    public int[] zielBerechnen(int playerX,int playerY,int enemyX,int enemyY){
        int[] ziel=new int[3]; // 0.Stelle ist x-Verschiebung, 1.Stelle ist y-Verschiebung, 3. Stelle ermittelt, ob er sich bewegen muss
        //Berechnung der Distanz zwischen Spieler und Gegner mithilfe des Satz des Pythagoras 
        double distance=Math.sqrt((playerX-enemyX)*(playerX-enemyX)+(playerY-enemyY)*(playerY-enemyY)); 
        ziel[2]= 1;
        if(distance>distMin&&distance<distMax){ //Spieler befindet sich im Ring, keine Bewegung notwendig
            ziel[2]= 0;
            ziel[0]=playerX-enemyX;
            ziel[1]=playerY-enemyY;
        }
        if(distance>distMax){//Spieler befindet sich außerhalb des Rings, Bewegung zum Spieler hin
            ziel[0]=playerX-enemyX;
            ziel[1]=playerY-enemyY;
        }
        if(distance<distMin){//Spieler befindet sich innerhalb des Rings, Bewegung vom Spieler weg
            ziel[0]=(playerX-enemyX)*(-1);
            ziel[1]=(playerY-enemyY)*(-1);
        }

        return ziel;
    }

    public boolean inRange(int playerX,int playerY,int enemyX,int enemyY){ //gibt zurück, ob der Spieler in Reichweite des Gegners ist
        double distance=Math.sqrt((playerX-enemyX)*(playerX-enemyX)+(playerY-enemyY)*(playerY-enemyY)); 
        if(distance>distMin&&distance<=distMax){ //Spieler befindet sich im Ring
            return true;
        }
        else{
            return false;
        }
    }
}