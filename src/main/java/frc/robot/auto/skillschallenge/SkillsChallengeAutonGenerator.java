/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.skillschallenge;

import edu.wpi.first.wpilibj2.command.*;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.auto.Autonomous;
import frc.lightning.auto.Path;
import frc.lightning.auto.Paths;
import frc.robot.auto.InitAuto;
import frc.robot.auto.skillschallenge.SkillsChallengeAutoDriveCollect;
import frc.robot.commands.FullAutoFireMagazine;
import frc.robot.commands.shooter.FireFive;
import frc.robot.commands.shooter.FireThree;
import frc.robot.commands.shooter.SetIndexAngle;
import frc.robot.commands.shooter.SpinUpFlywheelManual;
import frc.robot.subsystems.*;

import java.util.Map;
import java.util.Set;

public class SkillsChallengeAutonGenerator {

    public final boolean TESTING = false;

    private SkillsChallengePathGenerator pathGenerator;

    private LightningDrivetrain drivetrain;
    private Collector collector;
    private Indexer indexer; 
    private Shooter shooter;
    private LeadScrew leadScrew;
    private Vision vision;

    public SkillsChallengeAutonGenerator(LightningDrivetrain drivetrain, 
                            Collector collector, 
                            Indexer indexer, 
                            Shooter shooter,
                            LeadScrew leadScrew,
                            Vision vision) {

        this.drivetrain = drivetrain;
        this.collector = collector;
        this.indexer = indexer;
        this.shooter = shooter;
        this.leadScrew = leadScrew;
        this.vision = vision;

        pathGenerator = new SkillsChallengePathGenerator();

    }

    public void registerAutonomousCommands() {

        Set<String> paths = Paths.getPaths().keySet();

        for(String name : paths){
            if(name.startsWith("IR")){
                Autonomous.register(name.substring(3), new SequentialCommandGroup(
                    new InitAuto(vision, indexer, collector),
                    new SkillsChallengeAutoDriveCollect(drivetrain, collector, indexer, Paths.getPath(name))
                ));
            }
        }
    }

}