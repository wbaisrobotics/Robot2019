package frc.robot.oi;

public enum ButtonConfiguration {
    kBumperLeft(5),
    kBumperRight(6),
    kStickLeft(9),
    kStickRight(10),
    kA(1),
    kB(2),
    kX(3),
    kY(4),
    kBack(7),
    kStart(8);

    private int m_port;

    ButtonConfiguration(int port) {
      this.m_port = port;
    }
    
    public int getPort() {
    		return m_port;
    }
}
