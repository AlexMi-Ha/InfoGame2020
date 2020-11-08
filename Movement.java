/**
 * Das Interface Movement gibt eine Methode vor, die von den Gegnern im Spiel aufgerufen wird, die Methode zielBerechnen(int,int,int,int).
 * 
 * 
 * @author Anja 
 * @version 19.05.2020
 */
public interface Movement 
{

    /**
     * Als Rückgabewert dient ein Feld mit zwei Stellen, in die bei Methodenausführung zwei int-Werte eingefügt werden, die den Vektor beschreiben,
     * um den sich die Gegner bewegen müssen. Deshalb benötigt man auch  als Parameter die jeweiligen Standort Koordinaten von Gegner und Spieler. Mit ihnen 
     * wird nämlich dieser Vektor berechnet.
     */
    public int[] zielBerechnen(int playerX,int playerY,int enemyX,int enemyY);
    
    public boolean inRange(int playerX,int playerY,int enemyX,int enemyY);
}
