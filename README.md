------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE: DOM
PURPOSE OF PROJECT: Unterhaltung
VERSION or DATE: 26.06.2020
HOW TO START THIS PROJECT: project.greenfoot öffnen und auf Run drücken. 
Falls keine Welt startet muss Rechtsklick auf die "StartMenu"-Welt gemacht werden und in dem
Kontextmenü "new StartMenu()" angeklickt werden. Dann sollte das Spiel starten.
Wenn nicht, einfach Greenfoot neustarten.

AUTHORS: Alex, Anja, Daniel, Sebastian
USER INSTRUCTIONS:
Um den Spieler zu bewegen werden die "WASD"-Tasten benutzt
Um in eine bestimmte Richtung zu schießen muss die "Pfeiltaste" in die gewünschte Richtung gedrückt werden
Um mit einer Kiste zu interagieren muss "e" gedrückt werden

Durch das Menü kann man mit den "Pfeiltasten", "Enter" und "Escape" navigieren
Im Spiel kann man das Spiel jederzeit mit der "Escape" taste pausieren

Die Steuerungen sind auch nochmal im Menü unter "CONTROLS" beschrieben.


USER INTERFACE:
Oben rechts findet man im Spiel die "Minimap". Sie zeigt Räume in der Umgebung an, die bereits gefunden wurden.
Links neben der Minimap ist die "Key-Anzeige". Sie zeigt an ob der Spieler bereits den Key eingesammelt hat.
Oben Rechts sieht man die "Lebensanzeige". Sie zeigt an, wie viele Leben der Spieler hat. Diese kann durch das aufsammeln von Herzen aufgefüllt werden.

ZIEL:
Das Ziel des Spieles ist es in dem zufalls-generierten Labyrinth aus Räumen den Schlüssel der Ebene zu finden um mit diesem in den Bossraum zu kommen. Hier muss man einen Boss bekämpfen. Sobald dieser besiegt ist erscheint eine Falltüre in das nächste Level.
Je mehr Ebenen geschafft werden, ohne dass der Spieler stirbt, desto besser.
Der Highscore wird hierbei gespeichert!


------------------------------------------------------------------------------
Weitere Einstellungen
------------------------------------------------------------------------------

Um es beim durchspielen einfacher zu haben, könnte man in der Klasse "WorldManager" den Wert "devMode" auf true setzen. Wenn "devMode" wahr ist, kann der Spieler nicht sterben.

Um die Performance zu verbessern, kann man in der Klasse "WorldManager" den Wert "areProjectilesAnimated" auf false setzen. Dann werden Projektile nicht mehr animiert und die Performance des Spiels wird in teilen besser.

Um die Musik und Soundeffekte auszuschalten, kann man in der Klasse "WorldManager" den Wert "musicOn" auf false setzen. So wird keine Musik und keine Sounds mehr abgespielt. 
Empfohlen wenn kein Lautsprecher vorhanden ist, weil Greenfoot try-catch Blöcke manchmal ignoriert und so Fehler beim abspielen auftreten können.

--------------------------------------------------------------------------------
