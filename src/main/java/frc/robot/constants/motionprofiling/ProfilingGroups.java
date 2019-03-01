package frc.robot.constants.motionprofiling;

/**
 * Enumeration representing groups of paths to be driven together
 */
public enum ProfilingGroups {

    // Beginning at lvl 2 on the right, drives to cargo 3, then right collect, then cargo 2
    RightLvl2_C3_RightCollect_C2 (ProfilingPaths.RightLvl2_C3, ProfilingPaths.C3_RightCollect),

    ;

    /**
     * The paths part of this group
     */
    private final ProfilingPaths[] paths;

    /**
     * Initializes the profiling group with the paths within it
     * @param paths
     */
    private ProfilingGroups (ProfilingPaths... paths){
        this.paths = paths;
    }


}