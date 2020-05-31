/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.auton.PathGenerator.Paths;
import frc.robot.subsystems.*;

import java.util.HashMap;

public class AutonGeneratorTwitchy {

    public final boolean TESTING = false;

    private PathGenerator pathGenerator;

    private LightningDrivetrain drivetrain;
    private Collector collector;
    private Indexer indexer;
    private Shooter shooter;
    private ShooterAngle shooterAngle;
    private Vision vision;

    public AutonGeneratorTwitchy(LightningDrivetrain drivetrain) {

        this.drivetrain = drivetrain;


        pathGenerator = new PathGenerator();

    }

    public HashMap<String, Command> getCommands() {

        HashMap<String, Command> map = new HashMap<>();

        // For Testing
        if(TESTING) {
            map.put("### TEST MAIN ###", new SequentialCommandGroup(pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH), 
                new InstantCommand(() -> {
                    drivetrain.stop();
                    // drivetrain.resetSensorVals();
                }, drivetrain), 
                new WaitCommand(2), 
                pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH_TWO)
            ));
            map.put("Test Path #1", pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH));
            map.put("Test Path #2", pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH_TWO));
        }

        // Auton Paths
        if(TESTING) {
            map.put("Off Line Shooter Fwd", pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_FWD2SHOOT));
            map.put("Off Line Intake Fwd", pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_COLLECT_FWD));
            map.put("->OppTR", pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_2_OPP_TRENCHRUN));
            map.put("OppTR->ShootOuter", pathGenerator.getRamseteCommand(drivetrain, Paths.OPP_TRENCHRUN_2_SHOOTING_POSE_OUTER));
            map.put("OppTR->ShootInner", pathGenerator.getRamseteCommand(drivetrain, Paths.OPP_TRENCHRUN_2_SHOOTING_POSE_INNER));
            map.put("->TR", pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_2_TRENCHRUN));
            map.put("TR->ShootInner", pathGenerator.getRamseteCommand(drivetrain, Paths.TRENCHRUN_2_SHOOTING_POSE));
        }

        // AUTONS


        map.put("Test Auto", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new AutoDriveCollect(drivetrain, collector, indexer, Paths.INIT_LINE_2_OPP_TRENCHRUN)
        ));

        // Return New List
        return map;

    }

}