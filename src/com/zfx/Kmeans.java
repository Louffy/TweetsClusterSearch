package com.zfx;

import java.util.Random;

public class Kmeans {
	private Cluster[] clusters;
	private float[][] data;
	private int k;
	private int dataNum;
	private int[] clusterAssignments;
	private int[] nearestCluster;
	private float[][] distanceCache;
	private Random random = new Random(1);
	public Kmeans(float[][] tdata,int tk){
		data = tdata;
		k = tk;
		dataNum = tdata.length;
		//System.out.println(dataNum+"zzzz");
	//	System.out.println(data[[0][1]);
		clusters = new Cluster[k];
		clusterAssignments = new int[dataNum];
		nearestCluster = new int[dataNum];
		distanceCache = new float[dataNum][dataNum];
		InitRandom();
	}
	public void InitRandom() {
		for(int i = 0; i < k; i++){
			int temp = random.nextInt(dataNum);
			clusterAssignments[temp] = i;
			clusters[i] = new Cluster(temp,data[i]);
		}
		
	}
	//compute | vector |
	public float vectorLength(float[] vector){
		float sum = 0;
		for(int i =0;i < vector.length; i++){
			sum = sum + (vector[i] * vector[i]);
		}
		return (float) Math.sqrt(sum);
	}
	//
	public float innerProduct(float[] v1,float[] v2){
		float result = 0;
		for(int i = 0; i < v1.length; i ++){
			result += v1[i] * v2[i];
		}
		return result;
	}
	//compute cosine similarity
	public float computeCosineSimilarity(float[] v1,float[] v2){
		try{
			float denom = (vectorLength(v1) * vectorLength(v2));
			if(denom == 0)
				return 0;
			else
				return (innerProduct(v1,v2) / denom);
		}catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Different length");
			return 0;
		}
	}
	
	public void start(){
		int iter = 0;
		while(true){
			//compute means of each cluster
			for(int i = 0; i < k; i++){
				clusters[i].updateMean(data);
			}
			//compute distance of each data and each cluster
			for(int i = 0; i < dataNum; i ++){
				for(int j = 0; j < k; j++){
					float dist = getDistance(data[i],clusters[j].getMean());
					distanceCache[i][j] = dist;
				}
			}
			//compute the nearest cluster for each data
			for(int i = 0; i < dataNum; i++){
				nearestCluster[i] = getNearestCluster(i);
			}
			//compare the nearest cluster and the cluster containing the data
			//if all same,then return
			int tempk = 0;
			for(int i = 0; i < dataNum; i++){
				if(nearestCluster[i] == clusterAssignments[i])
					tempk++;
			}
			if(tempk == dataNum)
				break;
			//else continue
			for(int j = 0; j < k; j++){
				clusters[j].getCurrentMember().clear();
			}
			for(int i = 0; i < dataNum; i++){
				clusters[nearestCluster[i]].getCurrentMember().add(i);
				clusterAssignments[i] = nearestCluster[i];
			}
		}
	}
	//get nearest cluster of the data
	public int getNearestCluster(int n) {
		int nearest = -1;
		float min = Float.MAX_VALUE;
		for(int i = 0; i < k; i++){
			float d = distanceCache[n][i];
			if(d < min){
				min = d;
				nearest = i;
			}
		}
		
		return nearest;
	}
	//get distance of the data and cluster
	public float getDistance(float[] d, float[] mean) {
		return 1 - computeCosineSimilarity(d,mean);
	}
	public Cluster[] getClusters(){
		return clusters;
	}
	public static void main(String[] args){
		TFIDFMeasure tfidf = new TFIDFMeasure();
		tfidf.Init();
		float[][] data = tfidf.getKmeansData();
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[i].length; j++){
				System.out.print(data[i][j]);
				
			}
			System.out.println();
		}
		System.out.println("end");
		Kmeans mykmeans = new Kmeans(tfidf.getKmeansData(),4);
		mykmeans.start();
		Cluster[] c = mykmeans.getClusters();
		for(int i = 0; i < c.length; i++){
			System.out.println("--------------------");
			for(int j = 0;j < c[i].getCurrentMember().size(); j++){
				System.out.println(c[i].getCurrentMember().get(j));
			}
		}
	}
}
