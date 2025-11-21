import java.util.*;

public class Scholarship {
    public String id;
    public String name;
    public String description;
    public String requirements;
    public String quota;
    public String deadline;
    public String status; // Active, Inactive
    
    // TAMBAHAN: Daftar dokumen yang harus diupload
    public List<String> requiredDocuments; // Misal: ["Proposal Penelitian", "Surat Rekomendasi", "Transkrip Nilai"]

    public Scholarship(){
        requiredDocuments = new ArrayList<>();
    }

    public Scholarship(String id, String name, String description, String requirements, String quota, String deadline, String status){
        this.id = id;
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        this.quota = quota;
        this.deadline = deadline;
        this.status = status;
        this.requiredDocuments = new ArrayList<>();
    }
    
    // Constructor dengan required documents
    public Scholarship(String id, String name, String description, String requirements, String quota, String deadline, String status, List<String> requiredDocuments){
        this.id = id;
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        this.quota = quota;
        this.deadline = deadline;
        this.status = status;
        this.requiredDocuments = requiredDocuments != null ? requiredDocuments : new ArrayList<>();
    }

    public String toJson(){
        // Convert required documents to JSON array
        StringBuilder docsJson = new StringBuilder("[");
        for(int i=0; i<requiredDocuments.size(); i++){
            if(i > 0) docsJson.append(",");
            docsJson.append("\\\"").append(esc(requiredDocuments.get(i))).append("\\\"");
        }
        docsJson.append("]");
        
        return String.format("{\"id\":\"%s\",\"name\":\"%s\",\"description\":\"%s\",\"requirements\":\"%s\",\"quota\":\"%s\",\"deadline\":\"%s\",\"status\":\"%s\",\"requiredDocuments\":%s}", 
            esc(id), esc(name), esc(description), esc(requirements), esc(quota), esc(deadline), esc(status), docsJson.toString());
    }

    static String esc(String s){ 
        if(s==null) return ""; 
        return s.replace("\\","\\\\").replace("\"","\\\"").replace("\n", "\\n"); 
    }

    public static Scholarship fromJson(String s){
        Scholarship sch = new Scholarship();
        sch.id = between(s, "\"id\":\"", "\"");
        sch.name = between(s, "\"name\":\"", "\"");
        sch.description = between(s, "\"description\":\"", "\"");
        sch.requirements = between(s, "\"requirements\":\"", "\"");
        sch.quota = between(s, "\"quota\":\"", "\"");
        sch.deadline = between(s, "\"deadline\":\"", "\"");
        sch.status = between(s, "\"status\":\"", "\"");
        
        // Parse required documents
        int docStart = s.indexOf("\"requiredDocuments\":[");
        if(docStart >= 0){
            int docEnd = s.indexOf("]", docStart + 21);
            if(docEnd >= 0){
                String docStr = s.substring(docStart + 21, docEnd);
                if(!docStr.trim().isEmpty()){
                    String[] docs = docStr.split("\\\",\\\"");
                    for(String doc : docs){
                        String cleaned = doc.replace("\\\"", "").replace("\"", "").trim();
                        if(!cleaned.isEmpty()){
                            sch.requiredDocuments.add(unescape(cleaned));
                        }
                    }
                }
            }
        }
        
        return sch;
    }

    public static List<Scholarship> listFromJson(String s){
        List<Scholarship> res = new ArrayList<>();
        s = s.trim();
        if(s.length()<3) return res;
        s = s.substring(1, s.length()-1).trim();
        if(s.isEmpty()) return res;
        String[] parts = User.splitTopLevel(s);
        for(String p: parts) res.add(fromJson(p));
        return res;
    }

    public static String listToJson(List<Scholarship> list){
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
        return s.replace("\\\"","\"").replace("\\\\","\\").replace("\\n", "\n"); 
    }
}