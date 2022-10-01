package com.swisscom.model.payload;

import java.util.List;

public abstract class PaginatedResponse<T> {
	protected long currentPage;
	protected long totalCount;
	protected long totalPages;

	protected long dataPerPage;
	
	
	public abstract List<T> getPayload();
	public abstract void setPayload(List<T> payload);
	
	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getDataPerPage() {
		return dataPerPage;
	}

	public void setDataPerPage(long dataPerPage) {
		this.dataPerPage = dataPerPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
}