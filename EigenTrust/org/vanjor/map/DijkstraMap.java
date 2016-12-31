/**
 * Georgia Tech
 * DISL
 * 2016
 */
//package core_lib;

package org.vanjor.map;

import java.util.ArrayList;
import java.util.LinkedList;
 
/**
 * Dijkstra���·����Ѱ�㷨Javaʵ��
 * @author Vanjor
 */
public class DijkstraMap {
 
    /**
     * ʵ��Dijkstra���·����Ѱ�㷨
     * @param map
     *        ���룬Ϊͼ���󣬿������������ͼ������Ϊ-1��ʾ����Զ��һ�Ǹ�ֵ��ʾһ��ʵ�ʾ���
     *        map[i][i]��������ʼ��Ϊ0��
     *        ��ʼ�ڵ��0��ʼ
     * @param start
     *        �����ڵ�
     * @param end
     *        Ŀ��ڵ�
     * @return
     *        LinkedList<Integer> ����һλ��ʾ����̣�-1��ʾ����Զ������ʾ��start��end��·���ڵ�
     */
    public static LinkedList<Integer> getShortest(int[][] map, int start,
            int end) {
 
        int len = map.length;
 
        // ��ӦpathMileage[i]Ϊ��ǰstart��i����̾���
        int[] pathMileage = new int[len];
        boolean[] isDefined = new boolean[len];
        ArrayList<ArrayList<Integer>> pathWay = new ArrayList<ArrayList<Integer>>();
 
        // ��ʼ����̼������Ƿ�ȷ��Ϊ�����̵ı����
        for (int cursor = 0; cursor < len; cursor++) {
            pathMileage[cursor] = map[start][cursor];
            pathWay.add(new ArrayList<Integer>());
            if (pathMileage[cursor] >= 0) {
                pathWay.get(cursor).add(start);
                pathWay.get(cursor).add(cursor);
            }
            isDefined[cursor] = false;
        }
 
        // ��ʼ����Ӧ start->start����̾���Ϊ0������ȷ��Ϊstart->start������
        pathMileage[start] = 0;
        isDefined[start] = true;
 
        // ��len���ȵĵ㣬�������飬һ����ȷ������start���õ���̾���ĵ㼯��
        // ��ӦisDefined���Ϊtrue�� ��һ��Ϊ��δȷ������ӦisDefined���Ϊtrue
        // ����addUpCountΪ����ȷ������̾���㼯�ļ���
 
        int addUpCount = 1;
 
        // ���ڵ�ȫ��ȷ����̾���󣬼��˳�ѭ�����
        while (addUpCount < len) {
            // ÿһ�ִΣ���ȷ����̾��뼯�е���Сֵ
            int turnMinMileage = -1;
            // ��Ӧ��Сֵ��pathMileage��ֵ
            int turnMinPoint = -1;
 
            for (int cusor = 0; cusor < len; cusor++) {
                // �����Ӧ�Ľڵ�cusor�Ѿ�ȷ����̾����򲻲���������Ѱ
                if (isDefined[cusor]) {
                    continue;
                }
                // �ڵ�ǰδȷ����̾���Ľڵ㼯���ҳ���̾���turnMinMileage
                // �Լ���Ӧ�ڵ�isDefined
                if (turnMinMileage == -1 && pathMileage[cusor] >=0) {
                    // ��������һ����������Զ�ľ���㣬����Ӧ��ֵ���ǳ�ʼ����
                    turnMinMileage = pathMileage[cusor];
                    turnMinPoint = cusor;
                } else if (turnMinMileage >= 0 && pathMileage[cusor] >=0
                        && pathMileage[cusor] < turnMinMileage) {
                    // �ҵ���С�ĵ㣬���и���
                    turnMinMileage = pathMileage[cusor];
                    turnMinPoint = cusor;
                }
            }
 
            // �����ǰ�ִΣ���Ҳ�Ҳ������������Զ��δȷ��Զ�㣬
            // ��֤��ʣ�µĵ�Ϊ����ͨ�㣬Ҳ��ͬ���˳�whileѭ�����
            if (turnMinMileage == -1) {
                break;
            }
 
            // Ϊ��ǰ���ҵ�����̾������Ϊ��ȷ��
            isDefined[turnMinPoint] = true;
            // ��ȷ����+1����
            addUpCount++;
 
            // �����鿴�������¼ӵ�turnKeyPoint��
            // ��û�п��ܸĽ�ԭʼ��start���������̾���
            for (int cursor = 0; cursor < len; cursor++) {
                // ��ȷ���ľͲ��ڿ�����
                if (isDefined[cursor]) {
                    continue;
                }
                // ��pathMileage�ԵǼǵ�start->turnKeyPoint�Լ�
                // turnKeyPoint��>cursor�ľ��붼������Զ������Կ��ǱȽ�
                if (pathMileage[turnMinPoint] != -1
                        && map[turnMinPoint][cursor] != -1) {
                    // �����ǵ��¼ӽڵ�turnKeyPoint��ɵ�·��start->..->turnKeyPoint->cursor��
                    int newLen = pathMileage[turnMinPoint]
                            + map[turnMinPoint][cursor];
                    // ���Ƚϣ���С�򸲸�,����ԭ������Ϊ����ԶҲ����
                    if (newLen < pathMileage[cursor]
                            || pathMileage[cursor] == -1) {
                        pathMileage[cursor] = newLen;
                        copyPathWay(pathWay.get(turnMinPoint), pathWay
                                .get(cursor), cursor);
                    }
                }
            }
        }
        LinkedList<Integer> rs = new LinkedList<Integer>();
        rs.addAll(pathWay.get(end));
        rs.add(0, pathMileage[end]);
        return rs;
    }
 
    private static void copyPathWay(ArrayList<Integer> source,
            ArrayList<Integer> destiny, int addPoint) {
        while (!destiny.isEmpty()) {
            destiny.remove(0);
        }
        destiny.addAll(source);
        destiny.add(addPoint);
    }
 
    //��ʼ��ͼ
    public static int[][] generateMap() {
        int[][] map = new int[6][6];
       /* map[0][5] = 100;
        map[0][4] = 30;
        map[0][2] = 10;
        map[1][2] = 5;
        map[2][3] = 50;
        map[2][5] = 20;
        map[3][5] = 10;
        map[4][5] = 60;
        map[4][3] = 20;
        map[5][3] = 10;
        */
        map[0][5] = 1;
        map[0][4] = 1;
        map[0][2] = 1;
        map[1][2] = 1;
        map[2][3] = 1;
        map[2][5] = 1;
        map[3][5] = 1;
        map[4][5] = 1;
        map[4][3] = 1;
        map[5][3] = 1;
        int len = map.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (i == j) {
                    map[i][j] = 0;
                } else if (map[i][j] == 0) {
                    map[i][j] = -1;
                }
            }
        }
        return map;
    }
 
    public static void main(String[] args) {
        System.out.println(getShortest(generateMap(), 0, 5));
        System.out.println(Integer.MAX_VALUE);
        
        double[] trust= new double[3];
        trust[0]=0.2;
        trust[1]=0.6;
        trust[2]=0.4;
        double t;
        
        for (int i=0;i<trust.length;i++)
			System.out.println(trust[i]);
        
		for(int i=1;i<=trust.length-1;i++) { 
		   for(int j=0;j<trust.length-i;j++) { 
	        	if(trust[j]>trust[j+1])  { 
		              t=trust[j]; 
		              trust[j]=trust[j+1]; 
		              trust[j+1]=t; 
		           } 
		      } 
		}
		
		for (int i=0;i<trust.length;i++)
			System.out.println(trust[i]);
    }
}
