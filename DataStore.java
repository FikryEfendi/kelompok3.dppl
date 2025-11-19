import java.nio.file.*;
import java.util.*;
import java.io.*;

public class DataStore {
    private Path usersFile = Paths.get("users.json");
    private Path appsFile = Paths.get("applications.json");
    private Path scholFile = Paths.get("scholarships.json");

    public DataStore() {
        try {
            if (!Files.exists(usersFile)) Files.write(usersFile, "[]".getBytes());
            if (!Files.exists(appsFile)) Files.write(appsFile, "[]".getBytes());
            if (!Files.exists(scholFile)) {
                // Add default scholarships
                List<Scholarship> defaultScholarships = new ArrayList<>();
                defaultScholarships.add(new Scholarship("SCH001", "Beasiswa Prestasi Akademik", 
                    "Program beasiswa untuk mahasiswa dengan prestasi akademik outstanding", 
                    "- IPK minimal 3.5\n- Aktif berorganisasi\n- Surat rekomendasi dosen", 
                    "50", "31 Desember 2025", "Active"));
                defaultScholarships.add(new Scholarship("SCH002", "Beasiswa Penelitian", 
                    "Beasiswa untuk mahasiswa yang aktif dalam penelitian ilmiah", 
                    "- Proposal penelitian\n- IPK minimal 3.0\n- Surat rekomendasi pembimbing", 
                    "30", "15 Januari 2026", "Active"));
                defaultScholarships.add(new Scholarship("SCH003", "Beasiswa Ekonomi", 
                    "Bantuan pendidikan untuk mahasiswa kurang mampu", 
                    "- Surat keterangan tidak mampu\n- KK dan slip gaji orang tua\n- IPK minimal 2.75", 
                    "100", "20 Februari 2026", "Active"));
                Files.write(scholFile, Scholarship.listToJson(defaultScholarships).getBytes());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<User> loadUsers() {
        try {
            String s = new String(Files.readAllBytes(usersFile));
            return User.listFromJson(s);
        } catch (Exception e) { e.printStackTrace(); return new ArrayList<>(); }
    }

    public void saveUsers(List<User> users) {
        try {
            Files.write(usersFile, User.listToJson(users).getBytes());
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Application> loadApplications() {
        try {
            String s = new String(Files.readAllBytes(appsFile));
            return Application.listFromJson(s);
        } catch (Exception e) { e.printStackTrace(); return new ArrayList<>(); }
    }

    public void saveApplications(List<Application> apps) {
        try {
            Files.write(appsFile, Application.listToJson(apps).getBytes());
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Scholarship> loadScholarships() {
        try {
            String s = new String(Files.readAllBytes(scholFile));
            return Scholarship.listFromJson(s);
        } catch (Exception e) { e.printStackTrace(); return new ArrayList<>(); }
    }

    public void saveScholarships(List<Scholarship> scholarships) {
        try {
            Files.write(scholFile, Scholarship.listToJson(scholarships).getBytes());
        } catch (Exception e) { e.printStackTrace(); }
    }
}