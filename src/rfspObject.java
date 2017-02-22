import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;

public class rfspObject {
    public class rfspArray extends rfspObject{
         private ArrayList contents_;

         public Boolean append(String type, String payload){
             /*Types: Character "c", Integer "i",
             Double "d", String "s"
              */
             this.contents_.add(type + payload);
             return true;
         }
         public Boolean remove(int index){
             if (this.contents_.size() - 1 > index){
                 this.contents_.remove(index);
                 return true;
             }else{
                 return false;
             }
         }
         public int getlen(){
             return this.contents_.size();
         }
         public void setContents(ArrayList payload){
             this.contents_ = payload;
         }
         public String getItem(int index){
             return contents_.get(index).toString();
         }
    }
    private rfspArray rfspArray;


    private Boolean checkObjectHealth(rfspArray payload) {
        return true;
    }

    private String finalParse(rfspArray array) {
        int length = array.getlen();
        int x = 0;
        String parsed_ = "[";
        while (length <= x){
            StringBuilder sb = new StringBuilder(rfspArray.getItem(x));
            if(Character.toString(rfspArray.getItem(x).charAt(1)).equals("s")){
                sb.deleteCharAt(0);
                parsed_ += '"' + sb.toString() + '"';
            }else{
                sb.deleteCharAt(0);
                parsed_ += sb.toString();
            }
        }
        return parsed_;
    }

    public Boolean alterObject(rfspArray payload) {
        if (checkObjectHealth(payload)) {
            this.rfspArray = payload;
            return true;
        }
        return false;
    }

    public Boolean dumps(String name, String dir) {
        try {
            Formatter dump = new Formatter(dir  + name + ".rfs");
            String _dump = finalParse(rfspArray);
            dump.format(_dump);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public rfspArray loads(String dir){
        int x = 1;
        ArrayList Allowed = new ArrayList();
        File loads = new File(dir);
        String AbsoluteLoads = loads.toString();
        rfspArray loadsoutput = new rfspArray();
        while (AbsoluteLoads.length() != x){
            if (Character.toString(AbsoluteLoads.charAt(x)).equals('"')){
                x++;
                String build = "";
                while(! Character.toString(AbsoluteLoads.charAt(x)).equals('"')){
                    build += Character.toString(AbsoluteLoads.charAt(x));
                    x++;
                }
                loadsoutput.append("s", build);
            }else{

            }
        }
    }
    public ArrayList convertArrayList(ArrayList payload){
        int x = 0;
        ArrayList generated = new ArrayList();
        while(payload.size() <= x){
            if (payload.get(x) instanceof String){
                generated.add("s" + payload.get(x));
            }else if (payload.get(x) instanceof Double){
                generated.add("d" + payload.get(x));
            }else if (payload.get(x) instanceof Integer){
                generated.add("i" + payload.get(x));
            }else if (payload.get(x) instanceof Character){
                generated.add("c" + payload.get(x));
            }
            x++;
        }
        return generated;
    }
}
