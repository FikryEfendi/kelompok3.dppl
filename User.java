import java.util.*;

public class User {
    public String name;
    public String nim;
    public String email;
    public String password;

    public User() {}

    public User(String name,String nim,String email,String password){
        this.name=name; this.nim=nim; this.email=email; this.password=password;
    }

    public String toJson(){
        return String.format("{\"name\":\"%s\",\"nim\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}", escape(name), escape(nim), escape(email), escape(password));
    }

    public static String escape(String s){
        if(s==null) return "";
        return s.replace("\\","\\\\").replace("\"","\\\"");
    }

    public static User fromJson(String s){
        User u = new User();
        u.name = between(s, "\"name\":\"", "\"");
        u.nim = between(s, "\"nim\":\"", "\"");
        u.email = between(s, "\"email\":\"", "\"");
        u.password = between(s, "\"password\":\"", "\"");
        return u;
    }

    public static List<User> listFromJson(String s){
        List<User> res = new ArrayList<>();
        s = s.trim();
        if(s.length()<3) return res;
        s = s.substring(1, s.length()-1).trim(); // remove [ ]
        if(s.isEmpty()) return res;
        String[] parts = splitTopLevel(s);
        for(String p: parts) res.add(fromJson(p));
        return res;
    }

    public static String listToJson(List<User> users){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i=0;i<users.size();i++){
            if(i>0) sb.append(",");
            sb.append(users.get(i).toJson());
        }
        sb.append("]");
        return sb.toString();
    }

    // helpers
    static String between(String s, String a, String b){
        int i = s.indexOf(a);
        if(i<0) return "";
        i += a.length();
        int j = s.indexOf(b, i);
        if(j<0) return s.substring(i);
        return unescape(s.substring(i,j));
    }

    static String[] splitTopLevel(String s){
        List<String> out = new ArrayList<>();
        int depth=0;
        int last=0;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(c=='{') depth++;
            if(c=='}') depth--;
            if(c==',' && depth==0){
                out.add(s.substring(last,i).trim());
                last=i+1;
            }
        }
        out.add(s.substring(last).trim());
        return out.toArray(new String[0]);
    }

    static String unescape(String s){
        return s.replace("\\\"","\"").replace("\\\\","\\");
    }
}
