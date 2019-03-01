package frc.robot.constants.motionprofiling;

/**
 * Enumeration representing the generated motion profiling paths
 */
public enum ProfilingPaths {

    // Starting on the right side of lvl 2, drive to cargo 1
    RightLvl2_C1 ("RightLvl2-C1", false, true, true),

    // Starting on the right side of lvl 1, drive to cargo 3
    RightLvl1_C3 ("RightLvl1-C3", false, true, true),

    // Starting on the right side of lvl 1, drive to cargo 2
    RightLvl1_C2 ("RightLvl1-C2", false, true, true),

    // Starting on cargo 3, drive to right collect
    C3_RightCollect ("C3-RightCollect", true, true, true),

    // Starting on the right side of lvl 2, drive to cargo 3
    RightLvl2_C3 ("RightLvl2-C3", false, true, true),

    // Starting on the right side of lvl 2, drive to cargo 2
    RightLvl2_C2 ("RightLvl2-C2", false, true, true),

    // Starting on the right side of lvl 1, drive to cargo 1
    RightLvl1_C1 ("RightLvl1-C1", false, true, true),

    ;

    /** 
     * The name of the path (used to derive file name) 
     */
    public final String name;

    /** 
     * Represents whether or not the robot drives in reverse for the path 
     */
    public final boolean reverse;

    /**
     * Represents whether or not the drive sensors should reset at the initation of the path 
     * For example, if other system is used to calibrate to a known location (i.e. vision) then this would be true
     */
    public final boolean reset;

    /**
     * Represents whether or not vision should be activated at the end
     */
    public final boolean allignVisionAtEnd;

    /**
     * Initializes the profile and stores the variables
     * @param name
     * @param reverse
     * @param reset
     */
    private ProfilingPaths (String name, boolean reverse, boolean reset, boolean allignVisionAtEnd) {
        this.name = name;
        this.reverse = reverse;
        this.reset = reset;
        this.allignVisionAtEnd = allignVisionAtEnd;
    }


}