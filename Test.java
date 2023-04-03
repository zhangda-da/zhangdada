package com.huawei.codecraft;

import java.io.*;
import java.util.Scanner;
import static java.lang.Thread.sleep;

public class Test {
    //private static final Scanner br = new Scanner(System.in);
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintStream outStream = new PrintStream(new BufferedOutputStream(System.out));
    public static void main(String[] args) throws IOException, InterruptedException {
        Test.schedule();
    }
    private static void schedule() throws IOException, InterruptedException {
        //0. 初始化，读地图
        Test.readUtilOK();
        Test.printOK();

        String line;
        while(true){
            //1. 读一帧

            line = br.readLine();
            if(line==null || "-1".equals(line)) break;
            String[] splitLine1 = line.split(" ");
            //1.1 第一行是帧Id，当前金钱

            int frameId = Integer.parseInt(splitLine1[0]);
            int curMoney = Integer.parseInt(splitLine1[1]);

            //1.2. 第二行是场上工作台的数量K
            line = br.readLine();
            String[] splitLine2 = line.split(" ");
            int k = Integer.parseInt(splitLine2[0]);

            //1.3. 紧接着K行是工作台的状态
            Workshop workshopList[] = new Workshop[k];

            String[] splitLineK;
            for (int i = 0; i < k; i++) {
                line = br.readLine();

                splitLineK = line.split(" ");
                int workShopId = Integer.parseInt(splitLineK[0]);
                double x = Double.parseDouble(splitLineK[1]);
                double y = Double.parseDouble(splitLineK[2]);
                int remainProductionTime = Integer.parseInt(splitLineK[3]);
                int materialState = Integer.parseInt(splitLineK[4]);
                int productState = Integer.parseInt(splitLineK[5]);
                Workshop workshop = new Workshop(workShopId, x, y, remainProductionTime, materialState, productState);

                workshopList[i] = workshop;
            }
            //1.4. 紧接着4行是4个机器人
            Robot robotList[] = new Robot[4];

            String[] splitFourRobot;
            for (int i = 0; i < 4; i++) {
                line = br.readLine();

                splitFourRobot = line.split(" ");
                int curWorkId = Integer.parseInt(splitFourRobot[0]);
                int materialType = Integer.parseInt(splitFourRobot[1]);
                double timeValue = Double.parseDouble(splitFourRobot[2]);
                double collisionValue = Double.parseDouble(splitFourRobot[3]);
                double angleSpeed = Double.parseDouble(splitFourRobot[4]);
                double lineSpeedX = Double.parseDouble(splitFourRobot[5]);
                double lineSpeedY = Double.parseDouble(splitFourRobot[6]);
                double radian = Double.parseDouble(splitFourRobot[7]);
                double x = Double.parseDouble(splitFourRobot[8]);
                double y = Double.parseDouble(splitFourRobot[9]);
                Robot robot = new Robot(curWorkId, materialType, timeValue, collisionValue, angleSpeed,
                        lineSpeedX, lineSpeedY, radian, x, y);
                robotList[i] = robot;
            }

            //1.5 读OK
            line = br.readLine();
            if("OK".equals(line)){}

            //2. 收到输入后，交互帧输出
            //2.1 首先输出帧ID
            outStream.printf("%d\n", frameId);
            //2.2 再输出涉及此帧的多条指令
            int lineSpeed = 3;
            double angleSpeed = 1.5;
            for (int robotId = 0; robotId < 4; robotId++) {
                Action.printAction(new Action("forward", robotId, lineSpeed));
                Action.printAction(new Action("rotate", robotId, angleSpeed));
            }
            Test.printOK();
        }
    }
    private static void readUtilOK() throws IOException {
        String line;
        while(true){
            line = br.readLine();
            if("OK".equals(line))
                break;
        }
    }
    private static void printOK(){
        outStream.println("OK");
        outStream.flush();
    }
    private static class Workshop{
        int workShopId;
        double x;
        double y;
        int remainProductionTime;
        int materialState;
        int productState;
        public Workshop() {
        }
        public Workshop(int workShopId, double x, double y, int remainProductionTime, int materialState, int productState) {
            this.workShopId = workShopId;
            this.x = x;
            this.y = y;
            this.remainProductionTime = remainProductionTime;
            this.materialState = materialState;
            this.productState = productState;
        }
    }
    private static class Robot{
        int curWorkId;
        int materialType;
        double timeValue;
        double collisionValue;
        double angleSpeed;
        double lineSpeedX;
        double lineSpeedY;
        double radian;
        double x;
        double y;

        public Robot(int curWorkId, int materialType, double timeValue, double collisionValue, double angleSpeed,
                     double lineSpeedX, double lineSpeedY, double radian, double x, double y) {
            this.curWorkId = curWorkId;
            this.materialType = materialType;
            this.timeValue = timeValue;
            this.collisionValue = collisionValue;
            this.angleSpeed = angleSpeed;
            this.lineSpeedX = lineSpeedX;
            this.lineSpeedY = lineSpeedY;
            this.radian = radian;
            this.x = x;
            this.y = y;
        }
    }
    private static class Action{
        String instruction;
        int robotId;
        double extent;

        public Action(String instruction, int robotId) {
            this.instruction = instruction;
            this.robotId = robotId;
        }

        public Action(String instruction, int robotId, double extent) {
            this.instruction = instruction;
            this.robotId = robotId;
            this.extent = extent;
        }
        public static void printAction(Action ac){
            if(ac.instruction.equals("forward") || ac.instruction.equals("rotate"))
                outStream.printf("%s %d %f\n", ac.instruction, ac.robotId, ac.extent);
            else
                outStream.printf("%s %d\n", ac.instruction, ac.robotId);
        }
    }
}
