package sendmessage.aditya.com.message;

public class Blog {
    private String time,message;
    public Blog() {
    }
    public Blog(String time, String message, String image) {
        this.time = time;
        this.message = message;
    }
    public String gettime() {
        return time;
    }
    public void settime(String time) {
        this.time = time;
    }
    public String getmessage() {
        return message;
    }
    public void setmessage(String message) {
        this.message = message;
    }
}