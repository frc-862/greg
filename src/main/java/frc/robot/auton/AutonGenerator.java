/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auton;

import java.util.HashMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.Robot;
import frc.robot.auton.PathGenerator.Paths;
import frc.robot.commands.AutoCollectThree;
import frc.robot.commands.AutoIndexThree;
import frc.robot.commands.Collect;
import frc.robot.commands.Index;
import frc.robot.commands.VisionRotate;
import frc.robot.commands.shooter.FireThree;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterAngle;
import frc.robot.subsystems.Vision;

public class AutonGenerator {

    private PathGenerator pathGenerator;

    // Make Drivetrain Test Functions
    private static Command TEST_PATH_1,
                            TEST_PATH_2,
                            TEST_PATH_COMPILATION;
    
    // Make Moving Functions                        
    private static Command MOVE_OFF_LINE_SHOOT_FWD,
                            MOVE_OFF_LINE_COLLECT_FWD,
                            MOVE_LINE_2_TR,
                            MOVE_LINE_2_OPPTR,
                            MOVE_INNERPORT_FROMOPPTR,
                            MOVE_INNERPORT_FROMTR,
                            MOVE_OUTERPORT_FROMOPPTR;

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
    private Collector collector;
    private Indexer indexer; 
    private Shooter shooter;
    private ShooterAngle shooterAngle;
    private Vision vision;

    public AutonGenerator(LightningDrivetrain drivetrain, 
                            Collector collector, 
                            Indexer indexer, 
                            Shooter shooter,
                            ShooterAngle shooterAngle,
                            Vision vision) {

        this.drivetrain = drivetrain;
        this.collector = collector;
        this.indexer = indexer;
        this.shooter = shooter;
        this.shooterAngle = shooterAngle;
        this.vision = vision;

        pathGenerator = new PathGenerator();

        // Set Up Drivetrain Test Functions
        TEST_PATH_1 = pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH);
        TEST_PATH_2 = pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH_TWO); 
        TEST_PATH_COMPILATION = new SequentialCommandGroup(pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH), 
                                                            new InstantCommand(() -> {
                                                                drivetrain.stop();
                                                                // drivetrain.resetSensorVals();
                                                            }, drivetrain), 
                                                            new WaitCommand(2), 
                                                            pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH_TWO));

        // Set Up Shooting Functions
        // SHOOT3 = null;
        // SHOOT5 = null;

        // Set Up Moving Functions
        MOVE_OFF_LINE_SHOOT_FWD = pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_FWD2SHOOT);
        MOVE_OFF_LINE_COLLECT_FWD = pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_COLLECT_FWD);
        MOVE_LINE_2_TR = pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_2_TRENCHRUN);
        MOVE_LINE_2_OPPTR = pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_2_OPP_TRENCHRUN);
        MOVE_INNERPORT_FROMTR = pathGenerator.getRamseteCommand(drivetrain, Paths.TRENCHRUN_2_SHOOTING_POSE);
        MOVE_INNERPORT_FROMOPPTR = pathGenerator.getRamseteCommand(drivetrain, Paths.OPP_TRENCHRUN_2_SHOOTING_POSE_INNER);
        MOVE_OUTERPORT_FROMOPPTR = pathGenerator.getRamseteCommand(drivetrain, Paths.OPP_TRENCHRUN_2_SHOOTING_POSE_OUTER);

        // Set Up Collecting Functions
        // COLL3TR = new SequentialCommandGroup(MOVE_LINE_2_TR);
        // COLL2_OPPTR = new SequentialCommandGroup(MOVE_LINE_2_OPPTR);
        
        // Set Up Sequential Commands
        // SHOOT3_MOVE_FWD = new SequentialCommandGroup(SHOOT3, MOVE_OFF_LINE_FWD);
        // SHOOT3_MOVE_REV = new SequentialCommandGroup(SHOOT3, MOVE_OFF_LINE_REV);
        // MOVE_FWD_SHOOT3 = new SequentialCommandGroup(MOVE_OFF_LINE_FWD, SHOOT3);
        // MOVE_REV_SHOOT3 = new SequentialCommandGroup(MOVE_OFF_LINE_REV, SHOOT3);
        // SHOOT3_COLL3TR_SHOOT3 = new SequentialCommandGroup(SHOOT3, COLL3TR, SHOOT3);
        // COLL2_OPPTR_SHOOT5 = new SequentialCommandGroup(COLL2_OPPTR, MOVE_OUTERPORT_FROMOPPTR, SHOOT5);
        // COLL2_OPPTR_SHOOT5 = new SequentialCommandGroup(pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_2_OPP_TRENCHRUN),
        //                                                 new InstantCommand(() -> {
        //                                                     drivetrain.stop();
        //                                                     // for(int i = 0 ; i < 10 ; i++) drivetrain.resetSensorVals(); // Just to be sure...
        //                                                 }, drivetrain), 
        //                                                 new WaitCommand(2),
        //                                                 pathGenerator.getRamseteCommand(drivetrain, Paths.OPP_TRENCHRUN_2_SHOOTING_POSE_OUTER));
        // SHOOT3_COLL3 = new SequentialCommandGroup(SHOOT3, COLL3TR);
        // COLL2_MOVEINNERPORT_SHOOT5 = new SequentialCommandGroup(COLL2_OPPTR, MOVE_INNERPORT_FROMOPPTR, SHOOT5);
        // SHOOT3_COLL3TR_MOVEINNERPORT = new SequentialCommandGroup(SHOOT3, COLL3TR, MOVE_INNERPORT_FROMTR);

    }

    public HashMap<String, Command> getCommands() {

        HashMap<String, Command> map = new HashMap<>();

        // For Testing
            // map.put("### TEST MAIN ###", TEST_PATH_COMPILATION);
            // map.put("Test Path #1", TEST_PATH_1);
            // map.put("Test Path #2", TEST_PATH_2);

        // Auton Paths
            // map.put("Off Line Shooter Fwd", MOVE_OFF_LINE_SHOOT_FWD);
            // map.put("Off Line Intake Fwd", MOVE_OFF_LINE_COLLECT_FWD);
            // map.put("->OppTR", MOVE_LINE_2_OPPTR);
            // map.put("OppTR->ShootOuter", MOVE_OUTERPORT_FROMOPPTR);
            // map.put("OppTR->ShootInner", MOVE_INNERPORT_FROMOPPTR);
            // map.put("->TR", MOVE_LINE_2_TR);
            // map.put("TR->ShootInner", MOVE_INNERPORT_FROMTR);

        // AUTONS

        map.put("6Ball TR Inner Auto", new SequentialCommandGroup(new InstantCommand(collector::puterOuterIn, collector),
                                                                new InstantCommand(indexer::resetBallCount, indexer), // TODO - not the solution, get proper decrement code
                                                            new FireThree(shooter, indexer, shooterAngle, vision, collector),
                                                            new InstantCommand(indexer::safteyClosed),
                                                            new ParallelCommandGroup(new AutoCollectThree(collector, () -> 1d),
                                                                                        new Index(indexer),
                                                                                        pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_2_TRENCHRUN))
                                                                                        {
                                                                                            double initTime = 0d;
                                                                                            double duration = pathGenerator.getDuration(drivetrain, Paths.INIT_LINE_2_TRENCHRUN);
                                                                                            @Override
                                                                                            public void initialize() {
                                                                                                super.initialize();
                                                                                                initTime = Timer.getFPGATimestamp();
                                                                                            }
                                                                                            @Override
                                                                                            public boolean isFinished() {
                                                                                                return (Timer.getFPGATimestamp() - initTime) > (duration + 0.5d);
                                                                                            }
                                                                                        },
                                                            new WaitCommand(0.1),
                                                            pathGenerator.getRamseteCommand(drivetrain, Paths.TRENCHRUN_2_SHOOTING_POSE),
                                                            // new InstantCommand(collector::puterOuterOut, collector),
                                                                new InstantCommand(indexer::resetBallCount, indexer), // TODO - not the solution, get proper decrement code
                                                            new FireThree(shooter, indexer, shooterAngle, vision, collector)
                                                            ));

        // Return New List
        return map;

    }

}