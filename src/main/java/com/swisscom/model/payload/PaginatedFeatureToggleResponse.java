package com.swisscom.model.payload;

import java.util.List;

public class  PaginatedFeatureToggleResponse extends PaginatedResponse<FeatureToggleOut> {


	/**
	 * ResultsList builder for update and create
	 */
	public static final class Builder {

		private PaginatedFeatureToggleResponse paginatedFeatureToggleResponse = new PaginatedFeatureToggleResponse();

		public Builder perPage(long perPage) {
			this.paginatedFeatureToggleResponse.setDataPerPage(perPage);
			return this;
		}

		public Builder totalCount(long totalCount) {
			this.paginatedFeatureToggleResponse.setTotalCount(totalCount);
			return this;
		}

		public Builder totalPages(long totalPages) {
			this.paginatedFeatureToggleResponse.setTotalPages(totalPages);
			return this;
		}
		public Builder currentPage(long currentPage) {
			this.paginatedFeatureToggleResponse.setCurrentPage(currentPage);
			return this;
		}

		public Builder payload(List<FeatureToggleOut> list) {
			this.paginatedFeatureToggleResponse.setPayload(list);
			return this;
		}

		public PaginatedFeatureToggleResponse build() {
			return paginatedFeatureToggleResponse;
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
