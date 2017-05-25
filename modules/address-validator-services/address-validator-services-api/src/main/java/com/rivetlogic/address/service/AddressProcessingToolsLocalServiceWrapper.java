/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.rivetlogic.address.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AddressProcessingToolsLocalService}.
 *
 * @author Rivet Logic
 * @see AddressProcessingToolsLocalService
 * @generated
 */
@ProviderType
public class AddressProcessingToolsLocalServiceWrapper
	implements AddressProcessingToolsLocalService,
		ServiceWrapper<AddressProcessingToolsLocalService> {
	public AddressProcessingToolsLocalServiceWrapper(
		AddressProcessingToolsLocalService addressProcessingToolsLocalService) {
		_addressProcessingToolsLocalService = addressProcessingToolsLocalService;
	}

	/**
	* Return a JSON Array of address matching the input. If the address is
	* complete enough and valid, this list should have just one element. An
	* empty list will means the direction is wrong.
	*
	* @param address
	* @return JSON Array of address matching the input
	*/
	@Override
	public com.liferay.portal.kernel.json.JSONArray matchAddressAsJSON(
		com.rivetlogic.address.model.Address address) {
		return _addressProcessingToolsLocalService.matchAddressAsJSON(address);
	}

	/**
	* @return name of the current validation API
	*/
	@Override
	public java.lang.String getCurrentValidationAPIName() {
		return _addressProcessingToolsLocalService.getCurrentValidationAPIName();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _addressProcessingToolsLocalService.getOSGiServiceIdentifier();
	}

	/**
	* Return a list of address matching the input. If the address is complete
	* enough and valid, this list should have just one element. An empty list
	* will means the direction is wrong.
	*
	* @param address
	to match
	* @return List of addresses matching the input
	*/
	@Override
	public java.util.List<com.rivetlogic.address.model.Address> matchAddress(
		com.rivetlogic.address.model.Address address) {
		return _addressProcessingToolsLocalService.matchAddress(address);
	}

	@Override
	public AddressProcessingToolsLocalService getWrappedService() {
		return _addressProcessingToolsLocalService;
	}

	@Override
	public void setWrappedService(
		AddressProcessingToolsLocalService addressProcessingToolsLocalService) {
		_addressProcessingToolsLocalService = addressProcessingToolsLocalService;
	}

	private AddressProcessingToolsLocalService _addressProcessingToolsLocalService;
}