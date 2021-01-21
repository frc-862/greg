package frc.lightning.auto;

import java.util.HashMap;
import java.util.Set;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lightning.commands.DashboardWaitCommand;

/**
 * Autonomous command configuration class.
 * 
 * Register autonomous commands with {@link Autonomous#register(String, Command)}, and load them to
 * the "Autonomous" {@link edu.wpi.first.wpilibj.shuffleboard.Shuffleboard} tab with {@link Autonomous#load()}.
 * 
 * TODO - consider adding support for setting a default autonomous command. 
 */
public class Autonomous {

    /**
     * Number of registered commands
     */
    private static int autonCommandCount = 0; 

    /**
     * Flag indicates if a {@link frc.lightning.commands.DashboardWaitCommand} will be used in a 
     * {@link edu.wpi.first.wpilibj2.command.SequentialCommandGroup} before the configured autonomous command.
     */
    private static boolean wait = true;

    /**
     * Map of autonomous commands. Key ({@link java.lang.String}) corresponds to the name of the 
     * {@link edu.wpi.first.wpilibj2.command.Command} that will be displayed on 
     * {@link edu.wpi.first.wpilibj.shuffleboard.Shuffleboard}.
     */
    private static HashMap<String, Command> autons = new HashMap<>();

    /**
     * {@link edu.wpi.first.wpilibj.shuffleboard.Shuffleboard} selection menu
     */
    private static SendableChooser<Command> chooser = new SendableChooser<>();

    /**
     * Map of autonomous commands where the key ({@link java.lang.String}) corresponds to the name of the 
     * value {@link edu.wpi.first.wpilibj2.command.Command} that will be displayed on 
     * {@link edu.wpi.first.wpilibj.shuffleboard.Shuffleboard}.
     * @return the map of commands registered with {@link Autonomous#register(String, Command)}
     */
    public static HashMap<String, Command> getCommands() { return autons; }
    
    /**
     * Add command to list of autonomous commands. Will group with a {@link frc.lightning.commands.DashboardWaitCommand}
     * if enabled ({@link Autonomous#setHasDashboardWaitCommand(boolean)}).
     * @param name Identifier of autonomous command to be displayed on {@link edu.wpi.first.wpilibj.shuffleboard.Shuffleboard}.
     * @param cmd {@link edu.wpi.first.wpilibj2.command.Command} to be registered.
     */
    public static void register(String name, Command cmd) { 
        if(wait) autons.put(name, new SequentialCommandGroup(new DashboardWaitCommand(), cmd));
        else autons.put(name, cmd); 
    } 

    /**
     * Configures if {@link Autonomous#register(String, Command)} will group command with a {@link frc.lightning.commands.DashboardWaitCommand}
     * if enabled ({@link Autonomous#setHasDashboardWaitCommand(boolean)}).
     * @return {@code true} by default ({@link Autonomous#setHasDashboardWaitCommand(boolean)} not called), value set by 
     * {@link Autonomous#setHasDashboardWaitCommand(boolean)} otherwise.
     */
    public static boolean hasDashboardWaitCommand() { return wait; }

    /**
     * Configures if {@link Autonomous#register(String, Command)} will group command with a {@link frc.lightning.commands.DashboardWaitCommand}.
     * @param hasWait {@code true} if there needs to be a {@link frc.lightning.commands.DashboardWaitCommand}, false otherwise.
     */
    public static void setHasDashboardWaitCommand(boolean hasWait) {
        wait = hasWait;
    }

    /**
     * Loads all commands configured with {@link Autonomous#register(String, Command)} to "Autonomous" tab on
     * {@link edu.wpi.first.wpilibj.shuffleboard.Shuffleboard}. Selection menu will have name "Auto Mode".
     */
    public static void load() {
        final var tab = Shuffleboard.getTab("Autonomous");
        if(autons != null && !autons.isEmpty()) {
            Set<String> names = autons.keySet();
            for(var name : names) {
                loadRegisteredCommand(name, autons.get(name));
                System.out.println("Autonomous.load " + name);
            }
        }
        tab.add("Auto Mode", chooser);
    }

    /**
     * Gets the command selected on {@link edu.wpi.first.wpilibj.shuffleboard.Shuffleboard}.
     * @return {@link edu.wpi.first.wpilibj2.command.Command} to be run in Autonomous.
     */
    public static Command getAutonomous() {
        return chooser.getSelected();
    }

    /**
     * Adds a {@link edu.wpi.first.wpilibj2.command.Command} to the menu on {@link edu.wpi.first.wpilibj.shuffleboard.Shuffleboard}.
     * @param name The indentifier for the command to be placed on {@link edu.wpi.first.wpilibj.shuffleboard.Shuffleboard}.
     * @param command The {@link edu.wpi.first.wpilibj2.command.Command} to be loaded.
     */
    private static void loadRegisteredCommand(String name, Command command) {
        if (autonCommandCount == 0) chooser.setDefaultOption(name, command);
        else chooser.addOption(name, command);
        autonCommandCount++;
    }
    
}
