package frc.robot.components.speed;

/**
 * Describes the various methods for controlling a speed controller
 */
public enum SpeedControllerConnectionType{

    PWM, // Pulse Width Modulation connected to the roborio directly
    CAN; // CAN bus connected to the rio indirectly

}