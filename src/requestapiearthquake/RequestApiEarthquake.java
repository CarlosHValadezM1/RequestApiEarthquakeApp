import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class RequestApiEarthquake {
    private JFrame frame;
    private JTable table;
    private JButton fetchButton;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField maxResultsField;

    public RequestApiEarthquake() {
        frame = new JFrame("Informacion de terremotos");
        fetchButton = new JButton("Consultar info terremotos");
        startDateField = new JTextField("YYYY-MM-DD", 10);
        endDateField = new JTextField("YYYY-MM-DD", 10);
        maxResultsField = new JTextField(5);

        // Table setup
        String[] columnNames = {"Magnitud", "Localidad", "Hora", "Link"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchEarthquakeData(model);
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Fecha inicial:"));
        panel.add(startDateField);
        panel.add(new JLabel("Fecha final:"));
        panel.add(endDateField);
        panel.add(new JLabel("Limite de resultados:"));
        panel.add(maxResultsField);
        panel.add(fetchButton);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void fetchEarthquakeData(DefaultTableModel model) {
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();
        String maxResults = maxResultsField.getText();
        String urlString = "https://earthquake.usgs.gov/fdsnws/event/1/query?starttime=" 
                + startDate + "&endtime=" + endDate + "&format=geojson";

        // Agregar maxResults a la URL si se proporciona
        if (!maxResults.isEmpty()) {
            urlString += "&limit=" + maxResults;
        }else{
            urlString += "&limit=100";
        }

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parsear la respuesta JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray features = jsonResponse.getJSONArray("features");

            // Limpiar las filas existentes
            model.setRowCount(0);

            // Llenar la tabla con los datos
            for (int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");

                // Comprobar si la magnitud es nula
                Object magObject = properties.opt("mag");
                Double magnitude = null;
                if (magObject != null && !magObject.toString().equals("null")) {
                    magnitude = ((Number) magObject).doubleValue();
                }

                String location = properties.getString("place");
                long timeMillis = properties.getLong("time");
                String link = properties.getString("url");

                // Convertir el tiempo a un formato legible
                String time = new java.util.Date(timeMillis).toString();

                // Añadir la fila al modelo de la tabla
                model.addRow(new Object[]{magnitude != null ? magnitude : "N/A", location, time, link});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(RequestApiEarthquake::new);
    }
}
