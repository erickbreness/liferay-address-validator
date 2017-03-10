/**
 * Copyright (C) 2005-present Rivet Logic Corporation.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.rivetlogic.address.listener;

import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.rivetlogic.address.activator.AddressValidatorActivator;
import com.rivetlogic.address.portlet.util.AddressValidatorConstants;
import com.rivetlogic.address.service.AddressProcessingToolsLocalServiceUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(
    immediate = true,
    service = ModelListener.class
)
public class AddressListener extends BaseModelListener<Address> {

	 private static final Log _log = LogFactoryUtil.getLog(AddressListener.class);

	@Override
	public void onAfterCreate(Address model) throws ModelListenerException {

		updateAddressExpandoFields(model);

		super.onAfterCreate(model);
	}

	@Override
	public void onAfterRemove(Address model) throws ModelListenerException {

		try {
			ExpandoValueLocalServiceUtil.deleteValues(Address.class.getName(), model.getAddressId());
		} catch (SystemException e) {
			_log.error(e.getLocalizedMessage());
		}

		super.onAfterRemove(model);
	}

	@Override
	public void onAfterUpdate(Address model) throws ModelListenerException {

		updateAddressExpandoFields(model);

		super.onAfterUpdate(model);
	}

	private com.rivetlogic.address.model.Address getAddressFromModel(Address model) {

		com.rivetlogic.address.model.Address address = new com.rivetlogic.address.model.Address();

		String street1 = model.getStreet1();
		String street2 = model.getStreet2();
		String street3 = model.getStreet3();
		String fullStreet = (String.format(AddressValidatorConstants.FULL_ADDRESS_FORMAT, street1, street2, street3))
				.trim();

		address.setStreet(fullStreet);
		address.setCity(model.getCity());
		address.setPostalCode(model.getZip());
		address.setRegion(model.getRegion().getRegionCode());
		address.setCountry(model.getCountry().getA2());

		return address;
	}

	private void updateAddressExpandoFields(Address model) {

		List<com.rivetlogic.address.model.Address> matchedAddresses = AddressProcessingToolsLocalServiceUtil
				.matchAddress(getAddressFromModel(model));

		if (!matchedAddresses.isEmpty()) {
			com.rivetlogic.address.model.Address rlAddress = matchedAddresses.get(0);
			if (rlAddress.getLongitude() != null && !rlAddress.getLongitude().isEmpty()) {
				model.getExpandoBridge().setAttribute(AddressValidatorActivator.ADDRESS_LONGITUDE_FIELD,
						rlAddress.getLongitude());
			}
			if (rlAddress.getLatitude() != null && !rlAddress.getLatitude().isEmpty()) {
				model.getExpandoBridge().setAttribute(AddressValidatorActivator.ADDRESS_LATITUDE_FIELD,
						rlAddress.getLatitude());
			}
		}
	}

}
