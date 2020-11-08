import java.util.Scanner;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.Writer;

/**
 * Diese Klasse wird zur Kommunikation zwischen gespeicherten Dateien und dem Spiel verwendet.
 * 
 * @author Sebi
 * @version 07.06.
 */
public class DataManager  
{
    private static String filepath= "score.data";
    /**
     * Gibt den in score.data gespeicherten Highscore als int zurück.
     */
    public static int read(){
        String text= "";
        //Versucht Scanner zu Erstellen und die Datei zu lesen
        try{
            Scanner scanner= new Scanner(new FileInputStream(filepath));
            //Speichert den Inhalt der Datei als String zwischen
            try{
                while (scanner.hasNextLine()) {
                    text= text+ (scanner.nextLine());
                }
            //Beendet den Scanner, gibt Arbeitsspeicher wieder frei    
            } finally {
                scanner.close();
            }
        //Fängt jeden Fehler ab, Fehlermeldung    
        } catch (Exception ex) {
            System.out.println("<DataManager> Fehler beim Lesen des Highscores");
        }
        //Überprüft, ob text einen Inhalt hat, gibt sonst 0 zurück
        if (text.trim().equals("")){
            return 0;
        }
        return Integer.parseInt(text);
    }    

    /**
     * �berschreibt den in score.data gespeicherten Wert mit int score.
     */
    public static void write(int score){
        //Versucht Writer zu Erstellen und Inhalt der Datei zu �berschreiben 
        try {
            Writer out= new OutputStreamWriter(new FileOutputStream(filepath, false));
            //Schreibt den Score
            try{
                out.write(score + "");
            //Schließt Writer, gibt Arbeitspeicher wieder frei
            } finally {
                out.close();
            }
        //Fängt jeden Fehler ab, Fehlermeldung
        } catch(Exception ex) {
            System.out.println("<DataManager> Fehler beim Schreiben des Highscores");
        }
    }
}
