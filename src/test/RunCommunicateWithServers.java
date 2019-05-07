package test;

import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;
import Server.Server;
import Client.Client;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class RunCommunicateWithServers {

    public static void main(String[] args) {
//Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        //Server stringReverserServer = new Server(5402, 1000, new ServerStrategyStringReverser());
//Starting servers
        solveSearchProblemServer.start();

        mazeGeneratingServer.start();
//stringReverserServer.start();
//Communicating with servers
        Thread t1 = new Thread1();
        t1.start();
        Thread t2 = new Thread1();
        t2.start();
        Thread t3 = new Thread1();
        t3.start();
        Thread t4 = new Thread1();
        t4.start();
        Thread t5 = new Thread1();
        t5.start();
        Thread t6 = new Thread1();
        t6.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        CommunicateWithServer_MazeGenerating();
//        CommunicateWithServer_MazeGenerating();
//        CommunicateWithServer_MazeGenerating();
//        CommunicateWithServer_MazeGenerating();
//        CommunicateWithServer_MazeGenerating();
//        CommunicateWithServer_MazeGenerating();

//        CommunicateWithServer_SolveSearchProblem();
//        CommunicateWithServer_SolveSearchProblem();
//        CommunicateWithServer_SolveSearchProblem();
//        CommunicateWithServer_SolveSearchProblem();
//        CommunicateWithServer_SolveSearchProblem();
//        CommunicateWithServer_SolveSearchProblem();


//CommunicateWithServer_StringReverser();
//Stopping all servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
//stringReverserServer.stop();
    }


}

class Thread1 extends Thread {
    public void run() {
        System.out.println("------------------------------  " + Thread.currentThread().getId());
        CommunicateWithServer_MazeGenerating();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
          CommunicateWithServer_SolveSearchProblem();
    }
    private static void CommunicateWithServer_MazeGenerating() {

        try {

            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer,
                                           OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new
                                ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new
                                ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{10, 10};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[])
                                fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new
                                ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[10000000 /*CHANGESIZE ACCORDING TO YOU MAZE SIZE*/];
                        //allocating byte[] for the decompressedmaze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        Maze maze = new Maze(decompressedMaze);
                        maze.print();
                        System.out.println();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new
                    IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                                toServer.flush();
                                MyMazeGenerator mg = new MyMazeGenerator();
                                Maze maze = mg.generate(10, 10);
                                //maze.print();
                                toServer.writeObject(maze); //send maze to server
                                toServer.flush();
                                Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                                // Print Maze Solution retrieved from the server
                                System.out.println(String.format("Solution steps: %s", mazeSolution));
                                ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                                for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                                    System.out.println(String.format("%s. %s", i,
                                            mazeSolutionSteps.get(i).toString()));
                                }
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}

//    private static void CommunicateWithServer_StringReverser() {
//        try {
//            Client client = new Client(InetAddress.getLocalHost(), 5402, new
//                    IClientStrategy() {
//                        @Override
//                        public void clientStrategy(InputStream inFromServer,
//                                                   OutputStream outToServer) {
//                            try {
//                                BufferedReader fromServer = new BufferedReader(new
//                                        InputStreamReader(inFromServer));
//                                PrintWriter toServer = new PrintWriter(outToServer);
//                                String message = "Client Message";
//                                String serverResponse;
//                                toServer.write(message + "\n");
//                                toServer.flush();
//                                serverResponse = fromServer.readLine();
//                                System.out.println(String.format("Server response: %s", serverResponse));
//                                toServer.flush();
//                                fromServer.close();
//                                toServer.close();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//            client.communicateWithServer();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }
