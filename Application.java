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
    
    // TAMBAHAN: Untuk menyimpan dokumen persyaratan
    public Map<String, String> documents; // Key: nama dokumen, Value: path file

    public Application(){
        documents = new HashMap<>();
    }

    public String toJson(){
        // Convert documents map to JSON string
        StringBuilder docJson = new StringBuilder("{");
        int i = 0;
        for(Map.Entry<String, String> entry : documents.entrySet()){
            if(i > 0) docJson.append(",");
            docJson.append("\\\"").append(esc(entry.getKey())).append("\\\":\\\"").append(esc(entry.getValue())).append("\\\"");
            i++;
        }
        docJson.append("}");
        
        return String.format("{\"nim\":\"%s\",\"scholarshipName\":\"%s\",\"fullName\":\"%s\",\"ttl\":\"%s\",\"nik\":\"%s\",\"address\":\"%s\",\"photoPath\":\"%s\",\"status\":\"%s\",\"documents\":%s}", 
            esc(nim), esc(scholarshipName), esc(fullName), esc(ttl), esc(nik), esc(address), esc(photoPath), esc(status), docJson.toString());
    }

    static String esc(String s){ 
        if(s==null) return ""; 
        return s.replace("\\","\\\\").replace("\"","\\\""); 
    }

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
        
        // Parse documents
        int docStart = s.indexOf("\"documents\":{");
        if(docStart >= 0){
            int docEnd = s.indexOf("}", docStart + 13);
            if(docEnd >= 0){
                String docStr = s.substring(docStart + 13, docEnd);
                if(!docStr.trim().isEmpty()){
                    String[] pairs = docStr.split(",(?=\\\"[^\\\"]*\\\":)");
                    for(String pair : pairs){
                        String[] kv = pair.split("\":\"|\\\"");
                        if(kv.length >= 3){
                            String key = unescape(kv[1]);
                            String value = unescape(kv[2]);
                            a.documents.put(key, value);
                        }
                    }
                }
            }
        }
        
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
    
    static String unescape(String s){ 
        return s.replace("\\\"","\"").replace("\\\\","\\"); 
    }
}