/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.*;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.auto.PathGenerator.Paths;
import frc.robot.commands.FullAutoFireMagazine;
import frc.robot.commands.shooter.FireFive;
import frc.robot.commands.shooter.FireThree;
import frc.robot.commands.shooter.SetIndexAngle;
import frc.robot.commands.shooter.SpinUpFlywheelManual;
import frc.robot.subsystems.*;

import java.util.HashMap;

public class AutonGenerator {

    public final boolean TESTING = false;

    private PathGenerator pathGenerator;

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

        /*
         * Shoot Pre-Loaded 3, 
         * Move Off Line w/ Collector Forward (Away From Alliance Wall)
         */
        map.put("3 Ball Off Line Collector Fwd", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new FireThree(shooter, indexer, shooterAngle, vision, collector),
            // new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer),
            pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_COLLECT_FWD)
        ));

        /*
         * Shoot Pre-Loaded 3, 
         * Move Off Line w/ Shooter Forward (Toward Alliance Wall)
         */
        map.put("3 Ball Off Line -> Alliance Wall", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new FireThree(shooter, indexer, shooterAngle, vision, collector),
            // new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer),
            pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_FWD2SHOOT)
        ));

        /*
         * Vision Turn, 
         * Shoot Pre-Loaded 3, 
         * Move Off Line w/ Collector Forward (Away From Alliance Wall)
         */
        map.put("3 Ball Off Line Collector Fwd w/VIS", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            // new RunCommandTime(new FullAutoFireOne(drivetrain, vision, shooter, shooterAngle, indexer, false), 4d),
            new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer),
            pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_COLLECT_FWD)
        ));

        /*
         * Vision Turn, 
         * Shoot Pre-Loaded 3, 
         * Move Off Line w/ Shooter Forward (Toward Alliance Wall)
         */
        map.put("3 Ball Off Line -> Alliance Wall w/VIS", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            // new RunCommandTime(new FullAutoFireOne(drivetrain, vision, shooter, shooterAngle, indexer, false), 4d),
            new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer),
            pathGenerator.getRamseteCommand(drivetrain, Paths.INIT_LINE_FWD2SHOOT)
        ));

        /*
         * Collect 2 from Opponent's Trench Run,
         * Move closer to target,
         * Shoot 5 into outer
         */
        map.put("5 Ball Opp TR Outer", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new AutoDriveCollect(drivetrain, collector, indexer, Paths.INIT_LINE_2_OPP_TRENCHRUN),
            // pathGenerator.getRamseteCommand(drivetrain, Paths.OPP_TR_ADJUST),
            pathGenerator.getRamseteCommand(drivetrain, Paths.OPP_TRENCHRUN_2_SHOOTING_POSE_OUTER),
            new FireFive(shooter, indexer, shooterAngle, vision, collector)
            // new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer)
        ));

        /*
         * Collect 2 from Opponent's Trench Run,
         * Move closer to target,
         * Vision Turn, 
         * Shoot 5 into outer
         */
        map.put("5 Ball Opp TR Outer w/VIS", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new AutoDriveCollect(drivetrain, collector, indexer, Paths.INIT_LINE_2_OPP_TRENCHRUN),
            pathGenerator.getRamseteCommand(drivetrain, Paths.OPP_TRENCHRUN_2_SHOOTING_POSE_OUTER),
            new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer)
            // new RunCommandTime(new FullAutoFireOne(drivetrain, vision, shooter, shooterAngle, indexer, false), 4d)
            // new FireFive(shooter, indexer, shooterAngle, vision, collector)
        ));

        /*
         * Collect 2 from Opponent's Trench Run,
         * Move closer to target,
         * Shoot 5 into outer
         */
        map.put("5 Ball Opp TR Inner", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new AutoDriveCollect(drivetrain, collector, indexer, Paths.INIT_LINE_2_OPP_TRENCHRUN),
            pathGenerator.getRamseteCommand(drivetrain, Paths.OPP_TRENCHRUN_2_SHOOTING_POSE_INNER),
            // new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer)
            new FireFive(shooter, indexer, shooterAngle, vision, collector)
        ));

        /*
         * Collect 2 from Opponent's Trench Run,
         * Move closer to target,
         * Vision Turn, 
         * Shoot 5 into outer
         */
        map.put("5 Ball Opp TR Inner w/VIS", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new AutoDriveCollect(drivetrain, collector, indexer, Paths.INIT_LINE_2_OPP_TRENCHRUN),
            pathGenerator.getRamseteCommand(drivetrain, Paths.OPP_TRENCHRUN_2_SHOOTING_POSE_INNER),
            new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer)
            // new RunCommandTime(new FullAutoFireOne(drivetrain, vision, shooter, shooterAngle, indexer, false), 4d)
            // new FireFive(shooter, indexer, shooterAngle, vision, collector)
        ));

        map.put("Test Auto", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new AutoDriveCollect(drivetrain, collector, indexer, Paths.INIT_LINE_2_OPP_TRENCHRUN)
        ));

        /* 
         * Shoot Pre-Loaded 3, 
         * Collect 3 in Trench Run, 
         * Come back (but not on line),
         * Shoot 3                               
         */
        map.put("6 Ball TR Inner", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new FireThree(shooter, indexer, shooterAngle, vision, collector),
            // new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer),
            new AutoDriveCollect(drivetrain, collector, indexer, Paths.INIT_LINE_2_TRENCHRUN),
            pathGenerator.getRamseteCommand(drivetrain, Paths.TRENCHRUN_2_SHOOTING_POSE),
            // new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer)
            new FireThree(shooter, indexer, shooterAngle, vision, collector)
        ));

        /* 
         * Shoot Pre-Loaded 3, 
         * Collect 3 in Trench Run, 
         * Come back (but not on line),
         * Vision Turn and Shoot 3                             
         */
        map.put("6 Ball TR Inner w/VIS", new SequentialCommandGroup(
            new ParallelCommandGroup(
                    new InitAuto(vision, indexer, collector),
                    new SpinUpFlywheelManual(shooter, 3000),
                    new SetIndexAngle(shooterAngle, 39)
            ),
            // new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer),
            // new RunCommandTime(new FullAutoFireOne(drivetrain, vision, shooter, shooterAngle, indexer, false), 3.5d),
            new FireThree(shooter, indexer, shooterAngle, vision, collector),
            new AutoDriveCollect(drivetrain, collector, indexer, Paths.INIT_LINE_2_TRENCHRUN),
            pathGenerator.getRamseteCommand(drivetrain, Paths.TRENCHRUN_2_SHOOTING_POSE),
            new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer)
            // new RunCommandTime(new FullAutoFireOne(drivetrain, vision, shooter, shooterAngle, indexer, false), 4d)
            // new InstantCommand(collector::puterOuterOut, collector),
            // new FireThree(shooter, indexer, shooterAngle, vision, collector)
        ));

        /* 
         * Shoot Pre-Loaded 3,  
         * Collect 3 In Trench Run,
         * Vision Turn and Shoot Collected 3                               
         */
        map.put("6 Ball TR Outer w/VIS", new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new InitAuto(vision, indexer, collector),
                        new SpinUpFlywheelManual(shooter, 3000),
                        new SetIndexAngle(shooterAngle, 39)
                ),
                // new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer),
                // new RunCommandTime(new FullAutoFireOne(drivetrain, vision, shooter, shooterAngle, indexer, false), 3.5d),
                new FireThree(shooter, indexer, shooterAngle, vision, collector),
                new AutoDriveCollect(drivetrain, collector, indexer, Paths.INIT_LINE_2_TRENCHRUN),
                pathGenerator.getRamseteCommand(drivetrain, Paths.BACK_TR_2_FRONT_TR),
                new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer)
                // new RunCommandTime(new FullAutoFireOne(drivetrain, vision, shooter, shooterAngle, indexer, false), 4d)
                // new InstantCommand(collector::puterOuterOut, collector),
                // new FireThree(shooter, indexer, shooterAngle, vision, collector)
        ));

        // Return New List
        return map;

    }

}