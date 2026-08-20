# Barrendero — Plugin Paper para limpiar items en el suelo

## Compatibilidad

El plugin compila contra la **API de Paper 26.1.2** (la más antigua soportada) y declara
`api-version: "26.1"`, por lo que **un mismo JAR funciona en Paper 26.1.2 y 26.2** (ambas
usan Java 25). La API de Paper es retrocompatible hacia atrás: todo lo que existe en 26.1.2
sigue existiendo en 26.2.

## Compilación

Requiere **JDK 25**.

```powershell
mvn clean package
```

Genera: `target/barrendero-paper-2.0.1.jar`

Copia el JAR al directorio `plugins/` de tu servidor Paper (26.1.2 o 26.2).

Comandos:
- `/barrendero reload` — recarga `config.yml`.

Configuración principal en `config.yml`:
- `interval-seconds`: cada cuántos segundos correr la limpieza
- `age-seconds`: age mínima de items (segundos) para ser eliminados
- `worlds`: lista de mundos a procesar, vacío = todos
