package com.zfx;

import java.util.Random;
import java.util.TreeMap;

public class Kmeans {
	private Cluster[] clusters;
	public float[][] data;
	private int k;
	private int dataNum;
	private int termNum;
	private int[] clusterAssignments;
	private int[] nearestCluster;
	private float[][] distanceCache;
	private Random random = new Random(1);
	public Kmeans(float[][] tdata,int tk){
		data = tdata;
		k = tk;
		dataNum = tdata.length;
		termNum = tdata[0].length;
	//System.out.println(dataNum+"zzzz");
	//	System.out.println(data[[0][1]);
		clusters = new Cluster[k];
		clusterAssignments = new int[dataNum];
		nearestCluster = new int[dataNum];
		distanceCache = new float[dataNum][dataNum];
		InitRandom();
	}
	public void InitRandom() {
		int s = k;
		int j = 0;
		int[] flag = new int[k];
		
		for(int i = 0; i < k; i++)
			flag[i] = 0;
		
		for(int i = 0; i < dataNum; i++){
			if(j == k)break;
			for(int x = 0; x < k; x ++){
				if(data[i][x] > 0){
					if(flag[x] == 0){
						flag[x]++;
						clusterAssignments[i] = j;
						clusters[j] = new Cluster(i,data[i]);
						j++;
						break;
					}
				}
			}
			//int temp = random.nextInt(dataNum);
			
		}
		if(j < k){
			for(int i = 0; i < flag.length; i++){
				if(j == k)break;
				if(flag[i] == 0){
					for(int y = 0; y < dataNum;y ++){
						if(data[y][i] > 0){
							flag[i]++;
							clusterAssignments[y] = j;
							clusters[j] = new Cluster(y,data[y]);
							j++;
							break;
						}
					}
				}
			}
		}
		for(int i = 0; i < k; i++)
			System.out.print(flag[i] + "   ");
		
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
			System.out.println("iterator   ---" + iter);
			iter ++;
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
				//System.out.println("=====" + i + "  " + nearestCluster[i]);
				clusters[nearestCluster[i]].getCurrentMember().add(i);
				clusterAssignments[i] = nearestCluster[i];
			}
		}
	}
	//get nearest cluster of the data
	public int getNearestCluster(int n) {
		int nearest = -1;
		double min = Double.MAX_VALUE;
		for(int i = 0; i < k; i++){
			float d = distanceCache[n][i];
			if(d < min){
				min = d;
				nearest = i;
			}
		}
		if(nearest == -1)nearest = 0;
		return nearest;
	}
	//get distance of the data and cluster
	public float getDistance(float[] d, float[] mean) {
		return 1 - computeCosineSimilarity(d,mean);
	}
	public Cluster[] getClusters(){
		return clusters;
	}
	public void end(String[] terms){
		for(int i = 0; i < clusters.length; i++){
			for(int j = 0;j < clusters[i].getCurrentMember().size(); j++){
				for(int k = 0; k < data[(int) clusters[i].getCurrentMember().get(j)].length; k++){
					if(data[clusters[i].getCurrentMember().get(j)][k] >= 2.5)
						if(clusters[i].getInfo().indexOf(terms[k]) == -1)
							clusters[i].getInfo().add(terms[k]);
				}
			}
			if(clusters[i].getInfo().isEmpty())
				clusters[i].getInfo().add("other");
		}
	}
	public static void main(String[] args){
		/*String[] docs = new String[5];
		docs[0] = "china is a big country,big big<font color='red'>Apple</font>country";
		docs[1] = new String("apple is a USA company");
		docs[2] = new String("i like eat apple");
		docs[3] = "we have an apple tree";
		docs[4] = "apple store are usa";*/
		/*float[][] data = {
				{0,0,1.5f,2},
				{0,1.3f,0,0},
				{1.6f,0,0,1},
				{1.8f,0,0,3},
				{0,2.4f,0,0},
				{0,0,1.9f,1}
				};
		 */
		Mylucene mylucene = new Mylucene();
		mylucene.Init();
		String query = "apple";
		String[] docs = mylucene.WSearch(query);
		TFIDFMeasure tfidf = new TFIDFMeasure(docs,query);
		tfidf.Init();
		float[][] data = tfidf.getKmeansData();
		String[] terms = tfidf.getTermsIndex();
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[i].length; j++){
				System.out.print(data[i][j] + "---");
				
			}
			System.out.println();
		}
		System.out.println("Start");
		Kmeans mykmeans = new Kmeans(data,terms.length);
		mykmeans.start();
		mykmeans.end(terms);
		Cluster[] c = mykmeans.getClusters();
		
		
		for(int i = 0; i < c.length; i++){
			System.out.println("*******************************************************************");
			System.out.println(i);
			System.out.println("******************************************************************************************************");
			
			for(int k = 0; k < c[i].getInfo().size(); k++){
				System.out.print(c[i].getInfo().get(k) + "    ");
			}
			System.out.println();
			
			System.out.println("******************************************************************************************************");
			for(int j = 0;j < c[i].getCurrentMember().size(); j++){
				System.out.println(docs[(int) c[i].getCurrentMember().get(j)]);
			}
		}
	}
}
