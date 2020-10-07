package com.company;

import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;

public class CuckooHash {
    //four values
    //table size, number of flows, number of hashes, number of cuckoo steps
    private int nEntries;
    private int nFlows;
    private int nHashes;
    private int nCuckoo;
    private int[] table;
    public int[] hashes;
    public List<Integer> flows;
    public CuckooHash(int nEntries, int nFLows, int nHashes, int nCuckoo){
        this.nHashes = nHashes;
        this.nFlows = nFLows;
        this.nEntries = nEntries;
        this.nCuckoo = nCuckoo;
        initializeHash();
    }


    private void initializeHash() {
        table = new int[nEntries];
        Arrays.fill(table, -1);
        Random random = new Random();
        hashes = random.ints( 0, Integer.MAX_VALUE).distinct().limit(nHashes).toArray();
        flows = random.ints(0, Integer.MAX_VALUE).distinct().limit(nFlows).boxed().collect(Collectors.toList());

    }

    public boolean insert(int flow){
        for(int i = 0; i < nHashes; i++){
            int nextIndex = getIndex(i, flow);
            if(table[nextIndex] == -1){
                table[nextIndex] = flow;
                return true;
            }
        }
        for(int i = 0; i < nHashes; i++){
            int nextIndex = getIndex(i,flow);
            if(move(nextIndex, nCuckoo)){
                table[nextIndex] = flow;
                return true;
            }
        }
        return false;

    }

    public boolean move(int index, int s){
        if(s == 0){
            return false;
        }
        int flow = table[index];

        for(int i = 0; i < nHashes; i++){
            int nextIndex = getIndex(i,flow);
            if(nextIndex != index && table[nextIndex] == -1){
                table[nextIndex] = flow;
                return true;
            }
        }

        for(int i = 0; i < nHashes; i++){
            int nextIndex = getIndex(i,flow);
            if(nextIndex != index && move(nextIndex, s-1)){
                table[nextIndex] = flow;
                return true;
            }
        }
        return false;
    }




//    private void cuckoo(int count, int flow, int next, HashSet<Integer> set){
//        if(count == nCuckoo){
//            return;
//        }
//
//        next = next % nHashes;
//        int index = (hashes[next] ^ flow)%nEntries;
//        if(set.contains(index)){
//            return;
//        }
//        if(table[index] == -1) {
//            table[index] = flow;
//        }else{
//            set.add(index);
//            cuckoo(count + 1,table[index], next + 1, set);
//            table[index] = flow;
//        }
//
//    }

    public int getIndex(int i,  int flow){
        return (hashes[i] ^ flow)%nEntries;
    }

    private void print(){
        int empty = 0;
        for(int i : table){
            if(i == -1){
                empty++;
            }
        }
        System.out.println("Flows in Cuckoo: " + (table.length - empty));

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


//    private void simulate(){
//        for(int i : flows){
//
//            int index = nextHash(0,i,new HashSet<Integer>());
//            if(index != -1){
//                table[index] = i;
//            }
//        }
//    }


}
