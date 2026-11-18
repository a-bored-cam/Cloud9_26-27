package org.firstinspires.ftc.teamcode.pedro;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static MecanumConfig driveConfig = new MecanumConfig(
    c -> {
        c.frontLeftName.set("frontLeft");
        c.backLeftName.set("backLeft");
        c.frontRightName.set("frontRight");
        c.backRightName.set("backRight");

        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    }
    );
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        c.xPodOffset.set(4.636012099859283);
        c.yPodOffset.set(-10.019721083753693);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.11087410457964111);
                Controller secondaryTranslationalForward = Controller.proportional(0.040965019031539845);
                Controller primaryTranslationalLateral = Controller.proportional(0.216318456058445);
                Controller secondaryTranslationalLateral = Controller.proportional(0.07992388937799524);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.012232192000585302));
                c.brake.set(Controller.proportionalFeedforward(0.010397363200497507));

                c.headingFeedback.set(Controller.proportional(3.9791873955658006));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.044658374103653746, 0.009021018017924196));

                c.linearBrakeCoefficients.set(Matrix.diag(0.06619019534671622, 0.0937941154178404));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0015309666631610206, 9.37898333172377E-4));

                c.maxAchievableForwardVelocity.set(83.26020643748066);
                c.maxAchievableStrafeVelocity.set(71.09773082708332);
                c.naturalForwardDeceleration.set(31.585776731377578);
                c.naturalStrafeDeceleration.set(41.024583665502504);
            }
    );
    public static Follower create(HardwareMap h) {
        return new Follower(
            new PinpointLocalizer(h, localizerConfig),
            new Mecanum(h, driveConfig),
            new Foresight(foresightConfig)
        );
    }
}