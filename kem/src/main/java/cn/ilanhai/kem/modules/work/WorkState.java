package cn.ilanhai.kem.modules.work;

public interface WorkState {

	String getStateName();
	
	boolean isSatisfied();
}
