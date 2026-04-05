# Barrendero — Plugin Paper para limpiar items en el suelo

Build y uso rápido:

```bash
mvn -DskipTests package
# Copia el JAR generado en target/ al directorio plugins/ de tu servidor Paper (1.21+)
``` 

Comandos:
- `/barrendero reload` — recarga `config.yml`.

Configuración principal en `config.yml`:
- `interval-seconds`: cada cuántos segundos correr la limpieza
- `age-seconds`: age mínima de items (segundos) para ser eliminados
- `worlds`: lista de mundos a procesar, vacío = todos
