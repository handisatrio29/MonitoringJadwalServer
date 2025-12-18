package monitoringjadwalserver;

public class Jadwal {

    private String id;
    private String serverName;
    private String ip;
    private String monitoringType;
    private String scheduleDate;
    private String note;

    public Jadwal(String id, String serverName, String ip,
                  String monitoringType, String scheduleDate, String note) {
        this.id = id;
        this.serverName = serverName;
        this.ip = ip;
        this.monitoringType = monitoringType;
        this.scheduleDate = scheduleDate;
        this.note = note;
    }

    public String[] toRow() {
        return new String[]{
            id, serverName, ip, monitoringType, scheduleDate, note
        };
    }
}