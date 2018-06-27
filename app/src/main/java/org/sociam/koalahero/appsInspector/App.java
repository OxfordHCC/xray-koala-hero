package org.sociam.koalahero.appsInspector;

import android.content.Context;

import org.sociam.koalahero.koala.KoalaData.InteractionRequestDetails;
import org.sociam.koalahero.xray.XRayAppInfo;

public class App implements Comparable<App>{

    private XRayAppInfo xRayAppInfo;
    private boolean selectedToDisplay;
    private boolean inTop10;

    private java.util.Map<Interval,Long> usageTimes;

    public App(XRayAppInfo xRayAppInfo, Context context){
        this.xRayAppInfo = xRayAppInfo;
        this.selectedToDisplay = false;
        this.inTop10 = false;

        usageTimes = new java.util.HashMap<Interval,Long>();

        usageTimes.put(Interval.DAY,AppsInspector.calculateAppTimeUsage(Interval.DAY, this.xRayAppInfo.app, context));
        usageTimes.put(Interval.WEEK,AppsInspector.calculateAppTimeUsage(Interval.WEEK, this.xRayAppInfo.app, context));
        usageTimes.put(Interval.MONTH,AppsInspector.calculateAppTimeUsage(Interval.MONTH, this.xRayAppInfo.app, context));

    }

    public String getPackageName(){
        return xRayAppInfo.app;
    }

    public boolean isSelectedToDisplay(){
        return selectedToDisplay;
    }

    public void setIsSelectedToDisplay( boolean display){
        this.selectedToDisplay = display;
    }

    public void setSelectedToDisplay( boolean selectedToDisplay){
        this.selectedToDisplay = selectedToDisplay;
    }

    public XRayAppInfo getxRayAppInfo() {
        return xRayAppInfo;
    }

    public boolean isInTop10() {
        return inTop10;
    }

    public void setInTop10(boolean inTop10) {
        this.inTop10 = inTop10;
    }


    public long getDayUsage() {
        return usageTimes.get(Interval.DAY);
    }

    public void setDayUsage(long dayUsage) {
        usageTimes.put(Interval.DAY,dayUsage);
    }

    public long getWeekUsage() {
        return usageTimes.get(Interval.WEEK);
    }

    public void setWeekUsage(long weekUsage) {
        usageTimes.put(Interval.WEEK,weekUsage);
    }

    public long getMonthUsage() {
        return usageTimes.get(Interval.MONTH);
    }

    public void setMonthUsage(long monthUsage) {
        usageTimes.put(Interval.MONTH,monthUsage);
    }

    public long getUsage(Interval inter){
        return usageTimes.get(inter);
    }

    private Interval sortBy = Interval.WEEK;
    public void setSortMode( Interval sort ){
        sortBy = sort;
    }

    @Override
    public int compareTo(App other){

        // Default done by week
        if( usageTimes.get(sortBy) < other.getUsage(sortBy)) return -1;
        if( usageTimes.get(sortBy) > other.getUsage(sortBy)) return 1;
        else return 0;

    }
}
