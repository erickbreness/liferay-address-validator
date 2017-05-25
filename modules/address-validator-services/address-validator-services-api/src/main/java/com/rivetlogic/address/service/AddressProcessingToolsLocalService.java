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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import com.rivetlogic.address.model.Address;

import java.util.List;

/**
 * Provides the local service interface for AddressProcessingTools. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Rivet Logic
 * @see AddressProcessingToolsLocalServiceUtil
 * @see com.rivetlogic.address.service.base.AddressProcessingToolsLocalServiceBaseImpl
 * @see com.rivetlogic.address.service.impl.AddressProcessingToolsLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AddressProcessingToolsLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AddressProcessingToolsLocalServiceUtil} to access the address processing tools local service. Add custom service methods to {@link com.rivetlogic.address.service.impl.AddressProcessingToolsLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Return a JSON Array of address matching the input. If the address is
	* complete enough and valid, this list should have just one element. An
	* empty list will means the direction is wrong.
	*
	* @param address
	* @return JSON Array of address matching the input
	*/
	public JSONArray matchAddressAsJSON(Address address);

	/**
	* @return name of the current validation API
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getCurrentValidationAPIName();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Return a list of address matching the input. If the address is complete
	* enough and valid, this list should have just one element. An empty list
	* will means the direction is wrong.
	*
	* @param address
	to match
	* @return List of addresses matching the input
	*/
	public List<Address> matchAddress(Address address);
}