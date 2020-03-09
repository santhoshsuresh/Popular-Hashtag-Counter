package com.company;

import javax.swing.plaf.synth.SynthMenuBarUI;
import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
    // write your code here
        System.out.println("FibonacciHeap Test\n");
        FibHeap fh = new FibHeap();
        char ch;

        String ipFile = args[0] + ".txt";
        /*  Perform FibonacciHeap operations  */

        File f = new File(ipFile);
        try {
            Boolean empty = fh.isEmpty();
            BufferedReader b = new BufferedReader(new FileReader(f));
            HashMap<String, FibNode> hashtagMap = new HashMap<>();
            String ip;
            boolean read = true;
            while ((ip = b.readLine()) != null && read) {
                if (ip.toLowerCase().equals("stop")) {
                    read = false;
                }
                else if (ip.charAt(0) == '#') {
                    String[] cmd = ip.substring(1).split(" ");
                    String hashtag = cmd[0];
                    int val = Integer.parseInt(cmd[1]);
                    if(hashtagMap.containsKey(hashtag)) {
                        fh.increaseKey(hashtagMap.get(hashtag), val);
                    }
                    else {
                        FibNode el = fh.insert(hashtag, val);
                        hashtagMap.put(hashtag, el);
                    }
                }
                else {
                    List<String> op =  fh.extractMax(Integer.parseInt(ip));
                    System.out.print(op + "\n");

                    writeToFile(String.join(" ", op));
                    for(String tag : op){
                        fh.insert(hashtagMap.get(tag));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String data) throws IOException {
        File opfile = new File("output_file.txt");
        FileWriter fr;
        if(!opfile.createNewFile()){
            fr = new FileWriter(opfile, true);
        }
        else{
            fr = new FileWriter(opfile);
        }
        BufferedWriter br = new BufferedWriter(fr);
        br.write(data);
        br.newLine();
        br.close();
        fr.close();
    }
}