RequestApiEarthquake
Este proyecto es una aplicación Java que utiliza Swing para consultar y mostrar información sobre terremotos a través de la API de USGS. Permite al usuario especificar un rango de fechas y un número máximo de resultados, y muestra los datos en una tabla.

Características
- Interfaz gráfica sencilla con campos de entrada para fechas y límite de resultados.
- Consulta en tiempo real a la API de USGS para obtener información sobre terremotos.
- Tabla que muestra la magnitud, localidad, hora y un enlace para más información sobre cada evento.

Requisitos
- Java Development Kit (JDK) 8 o superior.
- Librerías:
  - org.json (asegúrate de incluir la librería JSON para el manejo de datos).

Uso
1. Abre la aplicación.
2. Introduce la fecha inicial y final en formato YYYY-MM-DD.
3. Opcionalmente, establece un límite para el número de resultados.
4. Haz clic en "Consultar info terremotos" para obtener los datos.
5. Los resultados se mostrarán en una tabla con columnas para magnitud, localidad, hora y un enlace para más detalles.

Ejemplo de uso

![image](https://github.com/user-attachments/assets/cb0d7c08-b48c-4713-aadf-ce7f9cceb0a0)
