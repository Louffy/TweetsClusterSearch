package com.zfx;

import java.util.ArrayList;

public class Cluster {
	//index of members in the cluster
	private ArrayList currentMember = new ArrayList();
	//mean of the cludter
	private float[] mean;
	public Cluster(int dataIndex,float[] data){
		currentMember.add(dataIndex);
		mean = data;
	}
	public void updateMean(float[][] coordinates){
		for(int i =0; i < currentMember.size(); i++){
			float[] coord = coordinates[(int) currentMember.get(i)];
			for(int j = 0; j < coord.length; j++){
				//get sum of Y
				mean[j] += coord[j];
			}
			for(int k = 0; k < mean.length; k++){
				//get average of Y
				mean[k] /= coord.length;
			}
		}
		
	}
	public float[] getMean(){
		return mean;
	}
	public ArrayList getCurrentMember(){
		return currentMember;
	}

}
