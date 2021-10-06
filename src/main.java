
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        final String FILENAME = "src/CustomerData_Example.txt";

        ArrayList<LinkedQueue<Customer>> fasterLane = new ArrayList<>();      //Arraylist to store linkedQueue of fast lane
        ArrayList<LinkedQueue<Customer>> normalLane = new ArrayList<>();    //Arraylist to store linkedQueue of normal lane
        int[] fTime = null;
        int[] nTime = null;

        int fLane = 0;            //Fast Lane
        int nLane = 0;            //Regular Lane
        int fLaneItem = 0;        //Maximum number of item for fast lane
        int numberOFCustomer = 0; //Number of customer

        try{
            File file = new File(FILENAME);
            Scanner inputFile = new Scanner(file);

            //Read first line of data first
            fLane = inputFile.nextInt();
            nLane = inputFile.nextInt();
            fLaneItem = inputFile.nextInt();
            numberOFCustomer = inputFile.nextInt();
            fTime = new int[fLane];
            nTime = new int[nLane];

            //Getting dynamic data from file
            for (int i = 0; i < numberOFCustomer; i++) {
                Customer x = new Customer(inputFile.nextInt());
                if (x.getNumberItems()<=fLaneItem){
                    if (fasterLane.size()<fLane){
                        fasterLane.add(new LinkedQueue<>());
                        fasterLane.get(fasterLane.size()-1).enqueue(x);
                        fTime[fasterLane.size()-1] = x.serveTime();
                    }
                    else if (normalLane.size()<nLane){
                        normalLane.add(new LinkedQueue<>());
                        normalLane.get(normalLane.size()-1).enqueue(x);
                        nTime[normalLane.size()-1] = x.serveTime();
                    }
                    else{
                        int ntemp = 0;
                        int ftemp = 0;
                        for (int k = 1; k < fTime.length; k++) {
                            if (fTime[k]<fTime[ftemp]){
                                ftemp = k;
                            }
                        }
                        for (int j = 1; j < nTime.length; j++) {
                            if (nTime[j]<nTime[ntemp]){
                                ntemp = j;
                            }
                        }
                        if (fTime[ftemp]<nTime[ntemp]){
                            fasterLane.get(ftemp).enqueue(x);
                            fTime[ftemp] = fTime[ftemp]+x.serveTime();
                        }
                        else {
                            normalLane.get(ntemp).enqueue(x);
                            nTime[ntemp] = nTime[ntemp]+x.serveTime();
                        }
                    }
                }
                else {
                    if (normalLane.size()<nLane){
                        normalLane.add(new LinkedQueue<>());
                        normalLane.get(normalLane.size()-1).enqueue(x);
                        nTime[normalLane.size()-1] = x.serveTime();
                    }
                    else{
                        int temp = 0;
                        for (int m = 1; m < nTime.length; m++) {
                            if (nTime[m]<nTime[temp]){
                                temp = m;
                            }
                        }
                        normalLane.get(temp).enqueue(x);
                        nTime[temp] = nTime[temp]+x.serveTime();
                    }
                }

            }

            inputFile.close();

        }
        catch (FileNotFoundException ex){
            System.out.println("Error reading data from file" +FILENAME+"Exception"+ex.getMessage());
        }

        int totalTime = fTime[0];        //Total Time to clear store
        for (int i = 1; i < fTime.length; i++) {
            if (totalTime<fTime[i]){
                totalTime = fTime[i];
            }
        }
        for (int j = 0; j < nTime.length; j++) {
            if (totalTime<nTime[j]){
                totalTime = nTime[j];
            }
        }

        for (int k = 0; k < fTime.length; k++) {
            System.out.println("Checkout(Express) #"+(k+1)+" (Est Time = "+fTime[k]+" s) = "+fasterLane.get(k));
        }
        for (int t = 0; t < nTime.length; t++) {
            System.out.println("Checkout(Normal) #"+(t+1)+" (Est Time = "+nTime[t]+" s) = "+normalLane.get(t));
        }
        System.out.println("Time To Clear Store of all customer = "+totalTime+" s");

        System.out.println("Number of Customer in line after 30 s");
        final int simulation_step = 1;
        final int printQueue = 30;

        int prevf = 0;
        int prevn = 0;

        System.out.println("Printing State after every 30s");
        for (int a = 1; a <=totalTime ; a++) {
            for (int z = 0; z < fasterLane.size(); z++) {
                Customer cur = fasterLane.get(z).peek();
                if ((cur.serveTime()+prevf)==a){
                    prevf += cur.serveTime();
                    fasterLane.get(z).dequeue();
                }
            }
            for (int x = 0; x < normalLane.size(); x++) {
                Customer cur = normalLane.get(x).peek();
                if ((cur.serveTime()+prevn)==a){
                    prevn += cur.serveTime();
                    normalLane.get(x).dequeue();
                }
            }
            if (a%printQueue==0||a==totalTime){
                System.out.println("After "+a+"s");
                for (int i = 0; i < fasterLane.size(); i++) {
                    System.out.print("Express Lane:"+i+" Size:"+fasterLane.get(i).size()+"\t");
                }
                for (int j = 0; j < normalLane.size(); j++) {
                    System.out.print("normal Lane:"+j+" Size:"+normalLane.get(j).size()+"\t");
                }
                System.out.println("\n");
            }
        }

    }

}