package com.swisscom.model.payload;

public class PaginatedRequest {
	private int currentPage ;
	private int pageSize ; 

	
	public PaginatedRequest(int currentPage , int pageSize) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getResultsPerPage() {
		return pageSize;
	}

	public void setResultsPerPage(int pageSize) {
		this.pageSize = pageSize;
	} 
	
	
	
}