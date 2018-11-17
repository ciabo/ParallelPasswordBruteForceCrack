public class SharedFlag {
    volatile boolean flag;
    public SharedFlag(boolean flag){
        this.flag=flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag(){
        return this.flag;
    }
}
