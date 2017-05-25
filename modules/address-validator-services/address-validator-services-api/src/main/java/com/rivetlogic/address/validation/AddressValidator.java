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

package com.rivetlogic.address.validation;

import com.rivetlogic.address.model.Address;

import java.util.List;

public interface AddressValidator {
    
    /***
     * Return a list of address matching the input. If the address is complete
     * enough and valid, this list should have just one element. However,
     * returned list will depend of the API. An empty list will means the
     * direction is wrong according to the API.
     * 
     * @param address to validate
     * @return List of addresses matching the input
     * @throws Exception if something goes wrong validating address
     */
    public List<Address> validateAddress(Address address) throws Exception;
}
