/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auton.skillschallenge;

import edu.wpi.first.wpilibj2.command.*;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.auton.PathGenerator.Paths;
import frc.robot.commands.FullAutoFireMagazine;
import frc.robot.commands.shooter.FireFive;
import frc.robot.commands.shooter.FireThree;
import frc.robot.commands.shooter.SetIndexAngle;
import frc.robot.commands.shooter.SpinUpFlywheelManual;
import frc.robot.auton.skillschallenge.SkillsChallengeAutoDriveCollect;
import frc.robot.auton.InitAuto;
import frc.robot.subsystems.*;

import java.util.HashMap;

public class SkillsChallengeAutonGenerator {

    public final boolean TESTING = false;

    private SkillsChallengePathGenerator pathGenerator;

    private LightningDrivetrain drivetrain;
    private Collector collector;
    private Indexer indexer; 
    private Shooter shooter;
    private ShooterAngle shooterAngle;
    private Vision vision;

    public SkillsChallengeAutonGenerator(LightningDrivetrain drivetrain, 
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

        pathGenerator = new SkillsChallengePathGenerator();

    }

    public HashMap<String, Command> getCommands() {

        HashMap<String, Command> map = new HashMap<>();

        map.put("Blue A", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new SkillsChallengeAutoDriveCollect(drivetrain, collector, indexer, SkillsChallengePathGenerator.Path.FIELD_A_BLUE)
        ));

        map.put("Blue B", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new SkillsChallengeAutoDriveCollect(drivetrain, collector, indexer, SkillsChallengePathGenerator.Path.FIELD_B_BLUE)
        ));

        map.put("Red A", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new SkillsChallengeAutoDriveCollect(drivetrain, collector, indexer, SkillsChallengePathGenerator.Path.FIELD_A_RED)
        ));

        map.put("Red B", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new SkillsChallengeAutoDriveCollect(drivetrain, collector, indexer, SkillsChallengePathGenerator.Path.FIELD_B_RED)
        ));

        map.put("Barrel Racing", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new SkillsChallengeAutoDriveCollect(drivetrain, collector, indexer, SkillsChallengePathGenerator.Path.BARREL_RACING)
        ));

        map.put("Slalom", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new SkillsChallengeAutoDriveCollect(drivetrain, collector, indexer, SkillsChallengePathGenerator.Path.SLALOM)
        ));

        map.put("Bounce1", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new SkillsChallengeAutoDriveCollect(drivetrain, collector, indexer, SkillsChallengePathGenerator.Path.BOUNCE1)
        ));

        map.put("Bounce2", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new SkillsChallengeAutoDriveCollect(drivetrain, collector, indexer, SkillsChallengePathGenerator.Path.BOUNCE2)
        ));

        map.put("Bounce3", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new SkillsChallengeAutoDriveCollect(drivetrain, collector, indexer, SkillsChallengePathGenerator.Path.BOUNCE3)
        ));

        map.put("Bounce4", new SequentialCommandGroup(
            new InitAuto(vision, indexer, collector),
            new SkillsChallengeAutoDriveCollect(drivetrain, collector, indexer, SkillsChallengePathGenerator.Path.BOUNCE4)
        ));

      
        // Return New List
        return map;

    }

}