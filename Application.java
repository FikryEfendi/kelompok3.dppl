import java.util.*;

public class Application {
    public String nim;
    public String scholarshipName;
    public String fullName;
    public String ttl;
    public String nik;
    public String address;
    public String photoPath;
    public String status; // Pending, Accepted, Rejected

    public Application(){}

    public String toJson(){
        return String.format("{\"nim\":\"%s\",\"scholarshipName\":\"%s\",\"fullName\":\"%s\",\"ttl\":\"%s\",\"nik\":\"%s\",\"address\":\"%s\",\"photoPath\":\"%s\",\"status\":\"%s\"}", esc(nim), esc(scholarshipName), esc(fullName), esc(ttl), esc(nik), esc(address), esc(photoPath), esc(status));
    }

    static String esc(String s){ if(s==null) return ""; return s.replace("\\","\\\\").replace("\"","\\\""); }

    public static Application fromJson(String s){
        Application a = new Application();
        a.nim = between(s, "\"nim\":\"", "\"");
        a.scholarshipName = between(s, "\"scholarshipName\":\"", "\"");
        a.fullName = between(s, "\"fullName\":\"", "\"");
        a.ttl = between(s, "\"ttl\":\"", "\"");
        a.nik = between(s, "\"nik\":\"", "\"");
        a.address = between(s, "\"address\":\"", "\"");
        a.photoPath = between(s, "\"photoPath\":\"", "\"");
        a.status = between(s, "\"status\":\"", "\"");
        return a;
    }

    public static List<Application> listFromJson(String s){
        List<Application> res = new ArrayList<>();
        s = s.trim();
        if(s.length()<3) return res;
        s = s.substring(1, s.length()-1).trim();
        if(s.isEmpty()) return res;
        String[] parts = User.splitTopLevel(s);
        for(String p: parts) res.add(fromJson(p));
        return res;
    }

    public static String listToJson(List<Application> list){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i=0;i<list.size();i++){
            if(i>0) sb.append(",");
            sb.append(list.get(i).toJson());
        }
        sb.append("]");
        return sb.toString();
    }

    static String between(String s, String a, String b){
        int i = s.indexOf(a);
        if(i<0) return "";
        i += a.length();
        int j = s.indexOf(b, i);
        if(j<0) return s.substring(i);
        return unescape(s.substring(i,j));
    }
    static String unescape(String s){ return s.replace("\\\"","\"").replace("\\\\","\\"); }
}