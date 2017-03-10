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
package com.rivetlogic.address.config;

import com.rivetlogic.address.util.AddressValidationConstants;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = "com.rivetlogic.address.config.AddressValidatorConfiguration")
public interface AddressValidatorConfiguration {
	@Meta.AD(
        deflt = AddressValidationConstants.GOOGLE_ADDRESS_VALIDATION_API,
        required = false
    )
    public String validationAPI();
}
