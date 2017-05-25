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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AddressProcessingTools. This utility wraps
 * {@link com.rivetlogic.address.service.impl.AddressProcessingToolsLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Rivet Logic
 * @see AddressProcessingToolsLocalService
 * @see com.rivetlogic.address.service.base.AddressProcessingToolsLocalServiceBaseImpl
 * @see com.rivetlogic.address.service.impl.AddressProcessingToolsLocalServiceImpl
 * @generated
 */
@ProviderType
public class AddressProcessingToolsLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.rivetlogic.address.service.impl.AddressProcessingToolsLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Return a JSON Array of address matching the input. If the address is
	* complete enough and valid, this list should have just one element. An
	* empty list will means the direction is wrong.
	*
	* @param address
	* @return JSON Array of address matching the input
	*/
	public static com.liferay.portal.kernel.json.JSONArray matchAddressAsJSON(
		com.rivetlogic.address.model.Address address) {
		return getService().matchAddressAsJSON(address);
	}

	/**
	* @return name of the current validation API
	*/
	public static java.lang.String getCurrentValidationAPIName() {
		return getService().getCurrentValidationAPIName();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	public static java.util.List<com.rivetlogic.address.model.Address> matchAddress(
		com.rivetlogic.address.model.Address address) {
		return getService().matchAddress(address);
	}

	public static AddressProcessingToolsLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AddressProcessingToolsLocalService, AddressProcessingToolsLocalService> _serviceTracker =
		ServiceTrackerFactory.open(AddressProcessingToolsLocalService.class);
}