package frc.robot.constants.network;

/**
 * Class for defining the target data from vision
 */
public class VisionTargetInfo{

    private final double xDiff;
    private final double yDiff;
    private final double heightRatio;
    private final double ttsr;

    public VisionTargetInfo (double xDiff, double yDiff, double heightRatio, double ttsr){
        this.xDiff = xDiff;
        this.yDiff = yDiff;
        this.heightRatio = heightRatio;
        this.ttsr = ttsr;
    }

    public double getXDiff(){
        return this.xDiff;
    }

    public double getYDiff(){
        return this.yDiff;
    }

    public double getHeightRatio(){
        return this.heightRatio;
    }

    public double getTTSR(){
        return this.ttsr;
    }

    public boolean isError (){
        return getXDiff() < -9998;
    }


}