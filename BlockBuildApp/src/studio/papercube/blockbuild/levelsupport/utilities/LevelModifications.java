package studio.papercube.blockbuild.levelsupport.utilities;

import studio.papercube.blockbuild.edgesupport.binlevel.Bumper;

import java.io.File;
import java.util.Scanner;

public class LevelModifications {
    public static class EnergeticPrisms {
        public EnergeticPrisms(File levelDirectory, File output) {
            new LevelModifier(levelDirectory)
                    .operate(level -> {
                        level.getPrisms().forEach(prism -> prism.setEnergy((byte) 127));
                        return level;
                    })
                    .output(output);
        }
    }

    public static class SpeedModifier {
        public static void main(String[] args) throws InterruptedException {
            System.out.println("欢迎使用SpeedModifier. 这个应用可以更改一个目录下所有有效Edge关卡的速度。这是一个测试用的程序，并且不提供更新（除非我够勤快），但是欢迎反馈BUG。" +
                    "\n由PaperCube开发。源码请前往百度方块边缘官方群下载，或者发送邮件到imzhy@hotmail.com索要。");
            Scanner scanner = new Scanner(System.in);
            for (; ; )
                try {
                    System.out.println("\n\n\n");
                    System.out.println("输入关卡源目录。 两端不可以有引号。");
                    String levelPath = scanner.nextLine();

                    System.out.println("变速比率。请注意，太小或太大的数值都会出现未知BUG");
                    String speedRateString = scanner.nextLine();

                    System.out.println("输入输出目录");
                    String outputPath = scanner.nextLine();

                    double speedRate = Double.parseDouble(speedRateString);

                    new SpeedModifier(levelPath, outputPath, speedRate);
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.sleep(500);
                }
        }

        double speedRate = 1.0;

        public SpeedModifier(String levelSource, String outputDirectory, double speedRate) {
            this.speedRate = speedRate;
            new LevelModifier(levelSource)
                    .operate(level -> {
                        level.getBumpers().forEach(bumper -> {
                            for (Bumper.Side side : new Bumper.Side[]{bumper.getEast(), bumper.getNorth(), bumper.getWest(), bumper.getSouth()}) {
                                side.setPulseRate((short) multipleInt(side.getPulseRate()));
                                side.setStartDelay((short) multipleInt(side.getStartDelay()));
                            }
                        });

                        level.getMovingPlatforms().forEach(movingPlatform -> movingPlatform.getWaypoints().forEach(waypoint -> {
                            waypoint.setPauseTime((short) multipleInt(waypoint.getPauseTime()));
                            waypoint.setTravelTime((short) multipleInt(waypoint.getTravelTime()));
                        }));

                        level.getFallingPlatforms().forEach(fallingPlatform -> fallingPlatform.setFloatTime((short) multipleInt(fallingPlatform.getFloatTime())));

                        return level;
                    }).output(String.format("%s/x%.2f", outputDirectory, speedRate));
        }

        private int multipleInt(int i) {
            return (int) (i / speedRate);
        }
    }

    public static class FallingPlatformsHardlyFall {
        public FallingPlatformsHardlyFall(File levelDirectory, File output) {
            new LevelModifier(levelDirectory)
                    .operate(level -> {
                        level.getFallingPlatforms().forEach(fallingPlatform -> fallingPlatform.setFloatTime((short) 32767));
                        return level;
                    })
                    .output(output);
        }
    }

    public static class FallingPlatformsInstantlyFall {
        public FallingPlatformsInstantlyFall(File levelDirectory, File output) {
            new LevelModifier(levelDirectory)
                    .operate(level -> {
                        level.getFallingPlatforms().forEach(fallingPlatform -> fallingPlatform.setFloatTime((short) 0));
                        return level;
                    })
                    .output(output);
        }
    }
}
