package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class MultiHash {
    //three entries, number of hashes, number of flows, and number of table entries

    private int nHash;
    public int nFlows;
    private int nEntries;
    private int[] table;
    public List<Integer> flows;
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
        hashes = random.ints( 0, Integer.MAX_VALUE).distinct().limit(nHash).toArray();
        flows = random.ints(0, Integer.MAX_VALUE).distinct().limit(nFlows).boxed().collect(Collectors.toList());

    }

    public int nextHash(int next, int flow, HashSet<Integer> set){

        //make sure next is modded before coming her
        if(next == nHash){
            return -1;
        }
//        next = next % nHash;
        int index = (hashes[next] ^ flow)%nEntries;
        //System.out.println(index);
//        if(set.contains(index)){
//           // System.out.println("here");
//
//            return -1;
//        }

        if(table[index] == -1){
            return index;
        }else{
           // set.add(index);
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
        System.out.println("Flows in MultiHash: " + (table.length - empty));

    }

}
