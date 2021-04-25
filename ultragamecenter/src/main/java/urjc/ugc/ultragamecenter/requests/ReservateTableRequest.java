package urjc.ugc.ultragamecenter.requests;

public class ReservateTableRequest {
    private String type;
    private String day;
    private Integer hour;
    private String email;

    private String[] possibletypes = { "PC", "PS5", "XBOX_ONE" };
    private Integer[] possibleHours = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

    public static boolean containsString(final String[] array, final String v) {
        boolean result = false;
        for (String i : array) {
            if (i.equals(v)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static boolean contains(final Integer[] array, final Integer v) {
        boolean result = false;
        for (int i : array) {
            if (i == v) {
                result = true;
                break;
            }
        }
        return result;
    }

    public ReservateTableRequest(String type, String day, Integer hour, String email) {
        if (containsString(possibletypes, type) && contains(possibleHours, hour)) {
            this.type = type;
            this.day = day;
            this.hour = hour;
            this.email = email;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (containsString(possibletypes, type)) {
            this.type = type;
        }
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        if (contains(possibleHours, hour)) {
            this.hour = hour;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return String.format("%s %s %d %s", day,email,hour,type);
    }
}
