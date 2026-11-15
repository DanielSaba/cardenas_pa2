# cardenas_pa2 — Registro de pacientes con SQLite

Proyecto de Android Studio (Java) — Producto Académico N.° 2.

- Dominio: cardenas.pa2.org  |  Package: org.pa2.cardenas.cardenas_pa2
- Base de datos: consultorio_cardenas.db  |  Tabla: pacientes

## Cómo abrirlo
1. Descomprime el .zip (carpeta `cardenas_pa2`).
2. Android Studio > File > Open > selecciona la carpeta `cardenas_pa2`.
3. Espera el Gradle Sync (necesita internet la primera vez). Si pide actualizar
   Gradle/AGP, acepta la actualización sugerida.
4. Ejecuta con el botón Run (emulador o celular).

## Exportar el APK
Build > Build Bundle(s) / APK(s) > Build APK(s) > "locate".
Ruta: app/build/outputs/apk/debug/app-debug.apk

## Exportar la base de datos SQLite
View > Tool Windows > Device Explorer (o Device File Explorer):
/data/data/org.pa2.cardenas.cardenas_pa2/databases/consultorio_cardenas.db
Clic derecho > Save As...
