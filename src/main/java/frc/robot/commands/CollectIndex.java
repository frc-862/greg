/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;

import java.util.function.DoubleSupplier;

public class CollectIndex extends CommandBase {

    final Collector collector;
    final Indexer indexer;
    final DoubleSupplier collectPwr;
    // DoubleSupplier ejectPwr;

    /**
     * Creates a new Collect_Eject.
     */
    public CollectIndex(Collector collector, Indexer indexer, DoubleSupplier collectPwr) {
        this.collector = collector;
        this.collectPwr = collectPwr;
        this.indexer = indexer;

        addRequirements(collector,indexer);
    }
    // hello shane how are you? i hope you have a good day!
    // you make me un poco loco, un pocititito loco!!!
    // BBBBBBBBBBB
    // haBiBi confirmed
    // programing cult
    // i will continue to type until i am stopped
    // muahahahah 'evil laugh'
    // you are my dad, you're my dad! boogie woogie woogie
    // align your chakras
    // shashank gave me a google drive file that has all three seasons
    // avatar the last air bender
    // it's in 720 p
    // but Aang is immature
    // zuko has the most character development
    // I am currently reading your internet history
    // new challenge NEVER go on incognito
    // what it dooo?
    // Ken has returned with Casey
    // Ken.exe has stopped working
    // ken has a potty mouth
    // code.org is superior
    // spelling is not my strong suit
    // do you have to water proof your computer because of your hand sweat??
    // I am genuinely intrigued
    // what color was his BLOOD?
    // RED
    // code code code code code code code code
    // I am an epic coder
    // code all day everyday
    // YOU HAVE TO READ IT ALL
    // I am going to code on my phone
    // use glowscript v python
    // I hate mr. gell
    // i got a point off on my coding standard
    // I will get him expelled
    // mr. electric send him to the principal's office and get him EXPELLED
    // i have sent george lopez Shark boy and Lava girl quotes for almost 1 week
    // I will not stop until he responds or blocks me
    // I am going to invite him to prom
    // r e s p e c t find out what it means to mean
    // ENOCH
    // briscabling
    // EnABlINgg
    // what are your deepest desires
    // deep fried greasy mr, clean
    // red to black, black to red
    // i am tired

    @Override
    public void execute() {

        if(collectPwr.getAsDouble()>.1){
            collector.setPower(1);
            indexer.setPower(1);
        } else {
            indexer.setPower(0);
            collector.setPower(collectPwr.getAsDouble());
        }

    }

    @Override
    public void end(boolean interrupted) {

        super.end(interrupted);
        collector.stop();

    }

}
