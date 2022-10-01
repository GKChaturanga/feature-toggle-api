package com.swisscom.model.payload;

import java.util.List;

public class  PaginatedFeatureToggleResponse extends PaginatedResponse<FeatureToggleOut> {


	/**
	 * ResultsList builder for update and create
	 */
	public static final class Builder {

		private PaginatedFeatureToggleResponse paginatedStockResponse = new PaginatedFeatureToggleResponse();

		public Builder perPage(long perPage) {
			this.paginatedStockResponse.setDataPerPage(perPage);
			return this;
		}

		public Builder totalCount(long totalCount) {
			this.paginatedStockResponse.setTotalCount(totalCount);
			return this;
		}

		public Builder totalPages(long totalPages) {
			this.paginatedStockResponse.setTotalPages(totalPages);
			return this;
		}
		public Builder currentPage(long currentPage) {
			this.paginatedStockResponse.setCurrentPage(currentPage);
			return this;
		}

		public Builder payload(List<FeatureToggleOut> list) {
			this.paginatedStockResponse.setPayload(list);
			return this;
		}

		public PaginatedFeatureToggleResponse build() {
			return paginatedStockResponse;
		}

	}

	private List<FeatureToggleOut> payload;
	 

	@Override
	public List<FeatureToggleOut> getPayload() {
		return payload;
	}

	@Override
	public void setPayload(List<FeatureToggleOut> payload) {
		this.payload = payload;
	}

	
}
