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

package com.rivetlogic.address.validation.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.rivetlogic.address.config.AddressValidatorConfigurationUtil;
import com.rivetlogic.address.util.AddressValidationConstants;
import com.rivetlogic.address.validation.AddressValidator;

public class AddressValidatorFactory {
    
    private static final Log _log = LogFactoryUtil.getLog(AddressValidatorFactory.class);
    
    public static String getDefaultValidationAPI() {
        return AddressValidationConstants.DUMMY_ADDRESS_VALIDATION_API;
    }
    
    public static String getCurrentValidationAPI() {
        String apiPropertyValue = PropsUtil.get(AddressValidationConstants.ADDRESS_VALIDATION_API_PROPERTY);
        
        if (_log.isDebugEnabled())
        	_log.debug(String.format("Property %s: %s", AddressValidationConstants.ADDRESS_VALIDATION_API_PROPERTY, apiPropertyValue));
        
        apiPropertyValue = AddressValidatorConfigurationUtil.getValidationAPI();

        return GetterUtil.getString(apiPropertyValue, getDefaultValidationAPI());
    }
    
    public static AddressValidator getAddressValidator() {
        
        String validationAPI = getCurrentValidationAPI();
        
        if (_log.isDebugEnabled())
        	_log.debug("Address Validation API: " + validationAPI);
        
        return getAddressValidator(validationAPI);
    }
    
    public static AddressValidator getAddressValidator(String validationAPI) {
        
        if (validationAPI.equalsIgnoreCase(AddressValidationConstants.GOOGLE_ADDRESS_VALIDATION_API)) {
            return new GoogleGeocodingValidator();
        }
        
        return new DummyValidator();
    }
}
