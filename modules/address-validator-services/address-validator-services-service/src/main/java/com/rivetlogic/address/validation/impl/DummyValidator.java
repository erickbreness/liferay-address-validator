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

import com.rivetlogic.address.model.Address;
import com.rivetlogic.address.validation.AddressValidator;

import java.util.ArrayList;
import java.util.List;

public class DummyValidator implements AddressValidator {
    
    @Override
    public List<Address> validateAddress(Address address) throws Exception {
        List<Address> addressList = new ArrayList<Address>();
        addressList.add(address);
        return addressList;
    }
    
}
