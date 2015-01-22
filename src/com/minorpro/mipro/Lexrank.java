package com.minorpro.mipro;

import java.util.Hashtable;
import java.util.List;


public class Lexrank {
	private Matrix _matrix;
	private List<Hashtable<String,Integer>> slist;
	
	private double _damping;
	private int[] _degree;
	
	public Lexrank(List<Hashtable<String,Integer>> slist, double[][] sMatrix, double dampint[] degring,ee)
	{
		this.slist = slist;
		//_threshold = threshold;
		_damping = damping;
		_matrix =new Matrix(sMatrix);
		_degree = degree;
		
		
		
		
		for(int i=0; i<_matrix.getRows(); i++){
			for(int j=0; j<_matrix.getCols(); j++){
				double val = _damping/slist.size() + _damping * _matrix.get(i,j) / _degree[i];
				_matrix.insert(val, i, j);
			}
		}
	}
	
	
	public double[] PowerMethod(double error)
	{
		Matrix p0 = new Matrix(slist.size(),1);
		Matrix p1 = new Matrix(slist.size(),1);
		
		for(int i=0; i<slist.size(); i++){
			p0.insert(1/(double)slist.size(),i,0);
		}
		System.out.println("P0 Matrix ");
		
		p0.show();
		
		System.out.println("_matrix Matrix ");
		
		_matrix.show();
		
		Matrix mT = _matrix.transpose();
		
		System.out.println("mT Matrix ");
		
		mT.show();
		
		Matrix pMinus; 
		p1 = mT.times(p0);
		
		System.out.println("p1 Matrix ");
		
		p1.show();
		pMinus = p1.minus(p0);
		
		pMinus.show();
		
		int iteration=0;
		while(pMinus.getMax() >= error&&iteration<=100){
			p0 = p1;
			p1 = mT.times(p0);
			pMinus = p1.minus(p0);
			iteration++;
			System.out.print(" "+iteration);
			
			//pMinus.show();
			
		}
				
		return p1.getCol(0);
	}
	
	
	
}
