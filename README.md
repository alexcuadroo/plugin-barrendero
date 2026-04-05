# Barrendero — Plugin Paper para limpiar items en el suelo

## Compilación

El proyecto usa **perfiles Maven** para generar un JAR compatible con cada versión de Minecraft.

### Minecraft 1.21 — requiere JDK 21

```powershell
mvn clean package -Pmc-1_21
```

Genera: `target/barrendero-paper-1.2.2-mc1.21.jar`

### Minecraft 26.1 — requiere JDK 25

Si tu `JAVA_HOME` apunta a JDK 21, setealo temporalmente antes de compilar:

```powershell
$env:JAVA_HOME = "C:\Users\alexc\AppData\Local\Programs\Eclipse Adoptium\jdk-25.0.2.10-hotspot"; mvn clean package -Pmc-26_1
```

Genera: `target/barrendero-paper-1.2.2-mc26.1.jar`

Copia el JAR correspondiente al directorio `plugins/` de tu servidor Paper. 

Comandos:
- `/barrendero reload` — recarga `config.yml`.

Configuración principal en `config.yml`:
- `interval-seconds`: cada cuántos segundos correr la limpieza
- `age-seconds`: age mínima de items (segundos) para ser eliminados
- `worlds`: lista de mundos a procesar, vacío = todos
