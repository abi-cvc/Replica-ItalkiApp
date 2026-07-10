# Replica-ItalkiApp

Clon nativo de la UI de **italki** (marketplace de tutores de idiomas), desarrollado en **Kotlin + Jetpack Compose** como entregable del taller *"Native UI Re-Engineering & UX Analysis"* (Facultad de Ingeniería de Sistemas).

El taller completo está en [`instrucciones.txt`](instrucciones.txt) y [`Taller de Clonado de UI y Análisis de Producto (1).pdf`](<Taller de Clonado de UI y Análisis de Producto (1).pdf>). Las diapositivas de análisis de producto y propuesta de mejora están en [`Analisis-Producto-y-Mejora-iTalki.pptx`](Analisis-Producto-y-Mejora-iTalki.pptx).

## Stack técnico

- Kotlin + Jetpack Compose (Material 3)
- Navigation Compose
- Coroutines / `StateFlow`
- Coil (carga de imágenes)
- Sin backend ni Room: los datos se generan con `FakeDataGenerator`, determinista (semilla fija) para poder estresar las listas con datos extensos sin depender de red.

`minSdk 24` · `targetSdk / compileSdk 35` · package `com.italkiclone.app`.

## Cómo correrlo

```bash
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.italkiclone.app/.MainActivity
```

## Funcionalidad

La app simula un login ya iniciado con un toggle real **Estudiante / Profesor** ("Cambiar de modo" desde "Yo"), sin autenticación real — gobierna qué bottom nav y qué pantallas se muestran, igual que en la app real.

| Modo | Bottom nav |
|---|---|
| Estudiante | Hoy · Profesores · Chats · Clases · Yo |
| Profesor | Hoy · Chats · Yo (Clases y Estudiantes se alcanzan desde "Yo") |

### Las 3 listas obligatorias del taller (+ 1 bonus)

- **Profesores** — cards con foto, badge de descuento, rating y precio. Incluye barra de búsqueda por nombre y filtros por idioma y por tipo (Profesional / Comunidad).
- **Clases** — `TabRow` por estado (Todos/Completada/Cancelada/…) con conteos en vivo, franja de color por estado sobre cada card. Estresada con 400 registros simulados.
- **Estudiantes** *(solo modo profesor)* — sub-tabs Actual/Potencial que muestran campos distintos por tab (última clase y lecciones vs. idioma potencial y competencias lingüísticas).
- **Chats** *(bonus)* — lista de conversaciones.

Todas usan `LazyColumn` con `key` estable y `Modifier.animateItem()`, y muestran un `LoadingSkeleton` antes de resolver los datos (delay simulado de 400 ms por repositorio) para que el estado de carga sea perceptible.

### Fase C — defecto de UX detectado y solución implementada

En la app real, "Hoy" del profesor abre con información accionable (ganancias, clases que requieren acción), mientras que "Hoy" del estudiante abre con contenido puramente promocional (test de tipo de estudiante, publicidad de italki Plus) sin ningún elemento de progreso real — viola la heurística de Nielsen de *visibilidad del estado del sistema*.

Mejora implementada: "Hoy" del estudiante antepone su próxima clase agendada (o un CTA para buscar profesor si no tiene ninguna), reutilizando las listas de Clases/Profesores ya construidas. De paso se completó "Hoy" del profesor con ganancias, mini-dashboard de acción y estadísticas generales, derivadas de los mismos datos que alimentan la lista de Clases.

## Arquitectura

```
app/src/main/java/com/italkiclone/app/
├── data/
│   ├── model/        # Teacher, Student, ClassSession, ChatConversation
│   ├── fake/          # FakeDataGenerator (datos simulados, deterministas)
│   └── repository/    # Implementaciones fake + AppContainer (contenedor de dependencias)
├── domain/
│   └── repository/    # Interfaces de repositorio
└── ui/
    ├── components/     # Avatar, RatingStars, StatusBadge, LoadingSkeleton
    ├── navigation/     # AppNavHost, BottomNavBar, Screen
    ├── screens/        # home, teachers, classes, students, chats, profile
    ├── state/          # UiState<T>, AppModeState (toggle Estudiante/Profesor)
    └── theme/          # Color/Theme/Type (paleta real extraída de diseños/)
```

Capas `data` / `domain` / `ui`, patrón Repository (interfaz en `domain`, implementación fake en `data`), un `ViewModel` por pantalla de lista exponiendo `StateFlow<UiState<List<T>>>`. Sin capa de casos de uso: se evaluó innecesaria para el alcance del taller.

## Fidelidad visual

La carpeta [`diseños/`](diseños/) contiene capturas reales de italki (modo estudiante y modo profesor) usadas como fuente de verdad para colores, tipografía, espaciados y estructura de navegación — la paleta (`#FF4438` coral, `#3A3B52` slate, verde/menta para estados de éxito) se extrajo directamente de ahí, no de branding externo.
