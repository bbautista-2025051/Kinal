# Medidas de Seguridad — Kinal Lecturas

## 1. Autenticación y Contraseñas
- **BCrypt cost-factor 12**: 4x más lento para brute-force que el valor por defecto (10).
- **Política de contraseñas fuerte**: mínimo 8 caracteres, al menos 1 mayúscula, 1 minúscula y 1 número.
- **Bloqueo de cuenta en BD**: tras 5 intentos fallidos la cuenta queda bloqueada 15 minutos (persistido en `estudiantes.bloqueado_hasta`).
- **Rate limiting en memoria** (RateLimitFilter): bloquea la IP a nivel de filtro antes de llegar al controlador.
- **Mensaje de error genérico**: no se revela si el email existe o no (previene enumeración de usuarios).
- **Delay fijo** de 500 ms en intentos fallidos (timing-safe para brute-force).
- **Normalización de email**: siempre en minúsculas al guardar y al buscar.
- **Dominio institucional obligatorio**: solo `@kinal.edu.gt` o `@fundacionkinal.org`.

## 2. Sesión
- **Session Fixation Protection**: `changeSessionId()` regenera el ID de sesión tras login.
- **Una sesión simultánea por cuenta** (maximumSessions = 1).
- **Timeout de sesión**: 30 minutos de inactividad.
- **Cookie de sesión**: `HttpOnly`, `Secure`, `SameSite=Strict`, nombre `KSESSION` (no el default `JSESSIONID`).
- **Logout seguro**: solo por POST con token CSRF, invalida sesión y elimina la cookie.

## 3. CSRF
- **Spring Security CSRF** habilitado con `CookieCsrfTokenRepository`.
- **testToken único por test**: UUID generado al iniciar el test, validado al calificar (previene replay attacks).

## 4. Cabeceras HTTP de Seguridad
| Cabecera | Valor |
|---|---|
| `Strict-Transport-Security` | `max-age=31536000; includeSubDomains` |
| `X-Frame-Options` | `SAMEORIGIN` |
| `X-Content-Type-Options` | `nosniff` |
| `X-XSS-Protection` | `1; mode=block` |
| `Referrer-Policy` | `strict-origin-when-cross-origin` |
| `Content-Security-Policy` | Solo recursos propios + Google Fonts + CDN |
| `Permissions-Policy` | Deshabilita cámara, micrófono, geolocalización, pagos |

## 5. Validación de Entradas
- **InputSanitizer**: escapa los 5 caracteres HTML críticos (`<`, `>`, `"`, `'`, `&`).
- **Anotaciones Bean Validation** estrictas en `RegistroDTO` (tamaño, patrón, rango).
- **Validación en controladores**: IDs de ruta deben ser positivos.
- **Validación de ownership en test**: cada opción enviada se verifica que pertenezca a la pregunta correcta (previene manipulación de respuestas).

## 6. Exposición de Información
- **Stack traces ocultos**: `server.error.include-stacktrace=never` y `include-exception=false`.
- **Mensajes de error genéricos**: CustomErrorController nunca expone rutas internas ni excepciones.
- **Spring Actuator**: solo el endpoint `/actuator/health` habilitado.
- **DevTools desactivado** en producción.
- **JPA show-sql=false**: las queries no se imprimen en logs.

## 7. Auditoría
- **SecurityAuditLogger**: registra login exitoso, login fallido, registro de cuenta, envío de test y accesos denegados en el logger `SECURITY_AUDIT`.
- **Email ofuscado** en logs (`a***@kinal.edu.gt`) para proteger datos personales.
- **AuthEventListener**: integrado con el sistema de eventos de Spring Security.

## 8. Base de Datos
- **Credenciales por variables de entorno**: `DB_URL`, `DB_USER`, `DB_PASS`.
- **HikariCP configurado**: pool de 10 conexiones máximo, timeouts definidos.
- **open-in-view=false**: previene consultas lentas no intencionadas fuera del servicio.
- **Índice en `estudiantes.email`** para búsquedas rápidas y seguras.

## Configuración en producción
```bash
export DB_URL="jdbc:mysql://host:3306/kinal_lector?useSSL=true&requireSSL=true&serverTimezone=America/Guatemala"
export DB_USER="kinal_app"
export DB_PASS="contraseña_segura_aqui"
java -jar kinal-lecturas.jar
```
