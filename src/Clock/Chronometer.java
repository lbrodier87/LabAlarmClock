package Clock;

public class Chronometer {
    long diffTime=0;
    long startTime=0;
    long endTime=0;
    boolean isRunning=false;
    
    public void resetChronometer(){
        diffTime = 0;
        isRunning=false;
    }
    public void startChronometer(){
       startTime = System.currentTimeMillis();
       isRunning=true;
    }
    public void pauseChronometer(){
        if(isRunning){
            endTime = System.currentTimeMillis();
            diffTime += endTime - startTime;
            isRunning=false;
        }
    }
    public long getChronoVal(){
        if(isRunning){
            return diffTime + (System.currentTimeMillis()-startTime);
        }else{
            return diffTime;
        }
    }    
}
