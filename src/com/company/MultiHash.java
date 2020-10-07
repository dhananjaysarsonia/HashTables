package com.company;

import java.util.*;

public class MultiHash {
    //three entries, number of hashes, number of flows, and number of table entries

    private int nHash;
    private int nFlows;
    private int nEntries;
    private int[] table;
    private List<Integer> flows;
    int[] hashes;
    public MultiHash(int nHash, int nFlows, int nEntries){
        this.nFlows = nFlows;
        this.nHash = nHash;
        this.nEntries = nEntries;
        initializeHash();

    }

    public void startSimulation(){
        simulate();
        print();
    }

    private void initializeHash() {
       table = new int[nEntries];
       Arrays.fill(table, -1);
       Random random = new Random();
       hashes = random.ints(nHash, 0, Integer.MAX_VALUE).toArray();
       flows = new ArrayList<Integer>();
       for(int i= 0; i < nFlows; i++ ){
           flows.add(i);
       }
        Collections.shuffle(flows);
    }

    public int nextHash(int next, int flow, HashSet<Integer> set){

        //make sure next is modded before coming her
        next = next % nHash;
        int index = (hashes[next] ^ flow)%nEntries;
        //System.out.println(index);
        if(set.contains(index)){
           // System.out.println("here");

            return -1;
        }

        if(table[index] == -1){
            return index;
        }else{
            set.add(index);
            return nextHash(next + 1,flow, set);
        }
    }

    //simulate hash
    private void simulate(){
        for(int i : flows){

            int index = nextHash(0,i,new HashSet<Integer>());
            if(index != -1){
                table[index] = i;
            }
        }
    }
    //printOutputs
    private void print(){
        int empty = 0;
        for(int i : table){
            if(i == -1){
                empty++;
            }
        }
        System.out.println("Empty indexes: " + empty);

    }

}
