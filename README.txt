# **TheKnife**
---

## Compilazione ed esecuzione del progetto:
1. Script per la compilazione su Windows 10
```
javac -d bin (Get-ChildItem -Path src -Recurse -Filter *.java | Select -Expand FullName)
```
2. Generazione file Jar su Windows 10
```
jar cvmf manifest.mf TheKnife.jar -C bin .
```
### Il file .Jar verrà generato nella stesso percorso in cui si lancia il comando.

---

## Esecuzione del file:
### Multipiattaforma
### Testato su piattaforme Windows 10/11 e distribuzioni Linux in particolare Debian
1. Apire una console di comando e puntare al percorso dov'è presente il file "TheKnife.jar"
2. Eseguire il seguente comando
```
java -jar TheKnife.jar
```

Se la cartella `data` non è presente nello stesso percorso dell'eseguibile,
il programma la genererà autonomamente e creerà i file .csv necessari per il salvataggio delle risorse.
---
