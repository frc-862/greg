/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.Robot;
import frc.robot.auton.PathGenerator.Paths;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class AutonGenerator {

    private PathGenerator pathGenerator;

    // Make Drivetrain Test Functions
    private static Command TEST_PATH_1,
                            TEST_PATH_2;

    // Make Shooting Functions
    private static Command SHOOT3,
                            SHOOT5;
    
    // Make Moving Functions                        
    private static Command MOVE_OFF_LINE_FWD,
                            MOVE_OFF_LINE_REV,
                            MOVE_LINE_2_TR,
                            MOVE_LINE_2_OPPTR,
                            MOVE_INNERPORT_FROMOPPTR,
                            MOVE_INNERPORT_FROMTR,
                            MOVE_OUTERPORT_FROMOPPTR;

    // Make Collecting Functions
    private static Command COLL3TR,
                            COLL2_OPPTR;

    // Make Sequential Autons
    private static SequentialCommandGroup SHOOT3_MOVE_FWD,
                                            SHOOT3_MOVE_REV,
                                            MOVE_FWD_SHOOT3,
                                            MOVE_REV_SHOOT3,
                                            SHOOT3_COLL3TR_SHOOT3,
                                            COLL2_OPPTR_SHOOT5,
                                            SHOOT3_COLL3,
                                            COLL2_MOVEINNERPORT_SHOOT5,
                                            SHOOT3_COLL3TR_MOVEINNERPORT;

    private LightningDrivetrain drivetrain;

    public AutonGenerator(LightningDrivetrain drivetrain/*, 
                            Collector collector, 
                            Indexer indexer, 
                            Shooter shooter*/ ) {

        this.drivetrain = drivetrain;

        pathGenerator = new PathGenerator();

        // Set Up Drivetrain Test Functions
        TEST_PATH_1 = pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH);
        TEST_PATH_2 = pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH_TWO); 

        // // Set Up Shooting Functions
        // SHOOT3 = null;
        // SHOOT5 = null;

        // // Set Up Moving Functions
        // MOVE_OFF_LINE_FWD = pathGenerator.getRamseteCommand(drivetrain, Paths.FWD_OFF_INIT_LINE);
        // MOVE_OFF_LINE_REV = pathGenerator.getRamseteCommand(drivetrain, Paths.BACK_OFF_INIT_LINE);
        // MOVE_LINE_2_TR = pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_2_TRENCHRUN);
        // MOVE_LINE_2_OPPTR = pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_2_OPP_TRENCHRUN);
        // MOVE_INNERPORT_FROMOPPTR = pathGenerator.getRamseteCommand(drivetrain, Paths.OPP_TRENCHRUN_2_SHOOTING_POSE_INNER);
        // MOVE_INNERPORT_FROMTR = pathGenerator.getRamseteCommand(drivetrain, Paths.TRENCHRUN_2_SHOOTING_POSE);
        // MOVE_OUTERPORT_FROMOPPTR = pathGenerator.getRamseteCommand(drivetrain, Paths.OPP_TRENCHRUN_2_SHOOTING_POSE_OUTER);

        // // Set Up Collecting Functions
        // COLL3TR = new SequentialCommandGroup(MOVE_LINE_2_TR);
        // COLL2_OPPTR = new SequentialCommandGroup(MOVE_LINE_2_OPPTR);
        
        // // Set Up Sequential Commands
        // SHOOT3_MOVE_FWD = new SequentialCommandGroup(SHOOT3, MOVE_OFF_LINE_FWD);
        // SHOOT3_MOVE_REV = new SequentialCommandGroup(SHOOT3, MOVE_OFF_LINE_REV);
        // MOVE_FWD_SHOOT3 = new SequentialCommandGroup(MOVE_OFF_LINE_FWD, SHOOT3);
        // MOVE_REV_SHOOT3 = new SequentialCommandGroup(MOVE_OFF_LINE_REV, SHOOT3);
        // SHOOT3_COLL3TR_SHOOT3 = new SequentialCommandGroup(SHOOT3, COLL3TR, SHOOT3);
        // COLL2_OPPTR_SHOOT5 = new SequentialCommandGroup(COLL2_OPPTR, MOVE_OUTERPORT_FROMOPPTR, SHOOT5);
        // SHOOT3_COLL3 = new SequentialCommandGroup(SHOOT3, COLL3TR);
        // COLL2_MOVEINNERPORT_SHOOT5 = new SequentialCommandGroup(COLL2_OPPTR, MOVE_INNERPORT_FROMOPPTR, SHOOT5);
        // SHOOT3_COLL3TR_MOVEINNERPORT = new SequentialCommandGroup(SHOOT3, COLL3TR, MOVE_INNERPORT_FROMTR);

    }

    public Command[] getCommands() {

        // Command[] cmdz = {pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH)};

        // return cmdz;

        Command[] cmds = {

            // TEST COMMANDS
            TEST_PATH_1,
        //     TEST_PATH_2,
// 
        //     // // COMPLETE AUTON COMMANDS
        //     // SHOOT3_MOVE_FWD,
        //     // SHOOT3_MOVE_REV,
        //     // MOVE_FWD_SHOOT3,
        //     // MOVE_REV_SHOOT3,
        //     // SHOOT3_COLL3TR_SHOOT3,
        //     // COLL2_OPPTR_SHOOT5,
        //     // SHOOT3_COLL3,
        //     // COLL2_MOVEINNERPORT_SHOOT5,
        //     // SHOOT3_COLL3TR_MOVEINNERPORT,
// 
        //     // // PATH CMDS
        //     // MOVE_OFF_LINE_FWD,
        //     // MOVE_OFF_LINE_REV,
        //     // MOVE_LINE_2_TR,
        //     // MOVE_LINE_2_OPPTR,
        //     // MOVE_INNERPORT_FROMOPPTR,
        //     // MOVE_INNERPORT_FROMTR,
        //     // MOVE_OUTERPORT_FROMOPPTR,
// 
        //     // // SHOOTING COMMANDS 
        //     // SHOOT3,
        //     // SHOOT5,
// 
        //     // // COLLECTING COMMANDS
        //     // COLL3TR,
        //     // COLL2_OPPTR

        };

        return cmds;

    }

}