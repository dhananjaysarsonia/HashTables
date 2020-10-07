package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DLeftHash {
    //input: number of table entries, int numFlows, int numberOfSegments
    private int nEntries;
    private int nFlows;
    private int nSegments;
    private int[] table;
    private int[] hashes;
    private List<Integer> flows;
    private int size;

    public DLeftHash(int nEntries, int nFlows, int nSegments){
        this.nEntries = nEntries;
        this.nFlows = nFlows;
        this.nSegments = nSegments;
        size = nEntries/nSegments;
        initializeHash();
    }

    private void initializeHash() {
        table = new int[nEntries];
        Arrays.fill(table, -1);
        Random random = new Random();
        hashes = random.ints( 0, Integer.MAX_VALUE).distinct().limit(nSegments).toArray();
        flows = random.ints(0, Integer.MAX_VALUE).distinct().limit(nFlows).boxed().collect(Collectors.toList());

    }

    private void insert(int flow){
        //int segment = flow % nSegments;
        for(int i = 0; i < nSegments; i++){
            int start = i*size;
            int index = start + getIndex(flow,i);
            if(table[index] == -1){
                table[index] = flow;
                break;
            }
        }

    }
    private int getIndex(int flow, int segment){
        return (hashes[segment] ^ flow)%size;
    }


    private void print(){
        int empty = 0;
        for(int i : table){
            if(i == -1){
                empty++;
            }
        }
        System.out.println("Flows in DLeftHash: " + (table.length - empty));
    }

    private void simulate(){
        for(int i : flows){
            insert(i);
        }
    }


    public void startSimulation(){
        simulate();
        print();
    }


}
