package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MultiHash multiHash = new MultiHash(3,1000,1000);
        multiHash.startSimulation();

        CuckooHash cuckooHash = new CuckooHash(1000,1000,3,2);
        cuckooHash.hashes = multiHash.hashes;
        cuckooHash.flows = multiHash.flows;
        cuckooHash.startSimulation();
    }
}
