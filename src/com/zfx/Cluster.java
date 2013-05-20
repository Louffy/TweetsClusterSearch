package com.zfx;

import java.util.ArrayList;

public class Cluster {
	//index of members in the cluster
	private ArrayList<Integer> currentMember = new ArrayList<Integer>();
	//mean of the cludter
	private float[] mean;
	//information of the cluster
	private ArrayList<String> info = new ArrayList<String>();
	
	
	public Cluster(int dataIndex,float[] data){
		currentMember.add(dataIndex);
		mean = new float[data.length];
		for(int i = 0; i < data.length; i++)
			mean[i] = data[i];
	}
	public void updateMean(float[][] data){
		for(int i = 0;i < mean.length; i++){
			mean[i] = (float) 0.0;
		}
		/*for(int i = 0; i < currentMember.size(); i++){
			for(int j = 0; j < mean.length; j++){
				System.out.print((int)data[currentMember.get(i)][j]+"   ");
			}
			System.out.println();
			
		}
		System.out.println("------");*/
		for(int i = 0; i < mean.length; i++){
			for(int j = 0; j < currentMember.size(); j++){
				//get sum of Y
				mean[i] += data[(int) currentMember.get(j)][i];
			}
		}
		for(int k = 0; k < mean.length; k++){
			//get average of Y
			mean[k] = mean[k] / (float)(1.0 * currentMember.size());
		}
		
	}
	public void setInfo(ArrayList<String> in){
		info = in;
	}
	public float[] getMean(){
		return mean;
	}
	public ArrayList<Integer> getCurrentMember(){
		return currentMember;
	}
	public ArrayList<String> getInfo(){
		return info;
	}

}
