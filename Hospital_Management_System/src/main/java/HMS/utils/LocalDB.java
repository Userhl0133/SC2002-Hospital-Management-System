package HMS.utils;

import java.io.*;
import java.util.*;


/*
Example usage

Initialize DB
    LocalDB medicineDB = new LocalDB();
    medicineDB.loadData("Medicine_List.csv");

WRITE
    Add new row
        // newRow.put("<colname>", "<data>");
        Map<String, String> newRow = new HashMap<>();
        newRow.put("Name", "New User");
        newRow.put("Age", "30");
        newRow.put("Country", "CountryX");
        db.addRow(newRow);

    Update data
        // row index, col name, update string
        db.update(0, "Age", "31");
        db.displayData();

READ
    Get data
        // row index, col name
        String cellData = db.getData(1, "Name");

    Get entire row
        // row index
        Map<String, String> rowData = db.getRow(1);

    Get entire col
        // col name
        List<String> columnData = db.getColumn("Age");
*/


public class LocalDB {

    private List<Map<String, String>> data = new ArrayList<>();
    private List<String> headers = new ArrayList<>();



    // Load CSV data into memory
    public void loadData(String filename) {
        filename = "../data/" + filename;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (isFirstLine) {
                    // First line should be headers
                    headers.addAll(Arrays.asList(values));
                    isFirstLine = false;
                } else {
                    // Create a map for each row
                    Map<String, String> row = new HashMap<>();
                    for (int i = 0; i < headers.size(); i++) {
                        row.put(headers.get(i), values[i]);
                    }
                    data.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add a new row
    public void addRow(Map<String, String> newRow) {
        data.add(newRow);
    }

    // Update a row by index
    public void update(int index, String column, String newValue) {
        if (index >= 0 && index < data.size()) {
            data.get(index).put(column, newValue);
        } else {
            System.out.println("Row index out of bounds");
        }
    }

    // Get a specific cell by row index and column name
    public String getData(int rowIndex, String columnName) {
        if (rowIndex < 0 || rowIndex >= data.size()) {
            System.out.println("Row index out of bounds");
            return null;
        }
        if (!headers.contains(columnName)) {
            System.out.println("Column name not found");
            return null;
        }

        return data.get(rowIndex).get(columnName);
    }

    // Get row by index
    public Map<String, String> getRow(int index) {
        if (index >= 0 && index < data.size()) {
            return data.get(index);
        } else {
            System.out.println("Row index out of bounds");
            return null;
        }
    }

    // Get a specific column by column name
    public List<String> getColumn(String columnName) {
        List<String> columnData = new ArrayList<>();
        if (!headers.contains(columnName)) {
            System.out.println("Column name not found");
            return columnData;
        }

        for (Map<String, String> row : data) {
            columnData.add(row.get(columnName));
        }
        return columnData;
    }

}