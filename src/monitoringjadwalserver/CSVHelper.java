package monitoringjadwalserver;

import java.io.*;
import java.util.*;

public class CSVHelper {

    private static final String FILE_PATH = "data/jadwal_monitoring.csv";

    public static List<Jadwal> load() {
        List<Jadwal> list = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length == 6) {
                    list.add(new Jadwal(d[0], d[1], d[2], d[3], d[4], d[5]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void save(List<Jadwal> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            pw.println("ID,ServerName,IP,MonitoringType,ScheduleDate,Note");
            for (Jadwal j : list) {
                pw.println(String.join(",", j.toRow()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}