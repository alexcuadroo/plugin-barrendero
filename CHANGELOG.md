# Changelog
## v2.0.1

### Cambios
- **Compatibilidad multi-versión**: El plugin ahora funciona en Paper **26.1.2** y **26.2** con un único JAR. Se compila contra la API de Paper 26.1.2 (la más antigua soportada) y se baja `api-version` a `"26.1"`, de modo que los servidores 26.1.2 ya no rechazan cargar el plugin (antes declaraba `api-version: "26.2"`).
- **Fix de arranque (NoSuchMethodError)**: Se fija **todas** las librerías Adventure a la versión **5.2.0** (mediante `adventure-bom` e `adventure-api` explícita). Antes, `paper-api` forzaba `adventure-api` 4.26.1 y se shadeaba una mezcla incompatible con MiniMessage 5.2.0 (`Services.service(ServiceLoader, Class)` no existía), lo que hacía fallar el `onEnable` con `NoSuchMethodError`.

## v2.0.0

### Cambios
- **Compatibilidad con Java 25**: Actualizado el plugin para compilar y ejecutarse con Java 25, eliminando perfiles Maven y fijando la versión de compilación.

## v1.2.5

### Nuevas funcionalidades
- **Sistema de mensajes en chat**: Mensajes broadcast configurables al limpiar items y aviso previo a la limpieza.
  - `messages.enabled` — Activar/desactivar mensajes.
  - `messages.warn-before-seconds` — Segundos de anticipación para el aviso.
  - `messages.prefix` — Prefijo personalizable.
  - `messages.cleanup-message` — Mensaje post-limpieza con placeholder `{removed}`.
  - `messages.warning-message` — Mensaje de aviso con placeholder `{seconds}`.
- **Autocompletado de comandos**: `/barrendero` ahora sugiere `reload` al pulsar Tab.

### Mejoras
- **Migración a Adventure API**: Reemplazados `ChatColor` y `Bukkit.broadcastMessage()` (deprecated) por `MiniMessage` y `Bukkit.broadcast(Component)`.
- Los mensajes en `config.yml` ahora usan formato **MiniMessage** (`<green>`, `<gold>`, etc.) en lugar de códigos legacy (`&a`, `&6`).
- `sender.sendMessage()` en comandos migrado a `Component.text()`.
